package pameas.incident.detection.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pameas.incident.detection.facade.DbProxyFacade;
import pameas.incident.detection.facade.LocationDataFacade;
import pameas.incident.detection.model.*;
import pameas.incident.detection.model.entity.LocationMonitor;
import pameas.incident.detection.model.enums.GfStatus;
import pameas.incident.detection.repository.LocationMonitorRepository;
import pameas.incident.detection.utils.DateUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Service
public class IncidentManagementService {

    private DbProxyFacade dbProxyFacade;
    private KafkaService kafkaService;
    private LocationDataFacade locationDataFacade;
    private LocationMonitorRepository locationMonitorRepo;

    private IncidentManagementService(DbProxyFacade dbProxyFacade,
                                      KafkaService kafkaService,
                                      LocationDataFacade locationDataFacade,
                                      LocationMonitorRepository locationMonitorRepo) {
        this.dbProxyFacade = dbProxyFacade;
        this.kafkaService = kafkaService;
        this.locationDataFacade = locationDataFacade;
        this.locationMonitorRepo = locationMonitorRepo;
    }

    @Value("passenger.speed.threshold1")
    String speedThreshold1;

    @Value("passenger.speed.threshold2")
    String speedThreshold2;

    public void managePassengerRoute() throws UnirestException, IOException, InterruptedException {
        GeofenceStatus geofenceStatus = dbProxyFacade.getGeofenceStatus();

        //map speeds per geofence
        Map<String, List<String>> gfsSpeed = getSpeedsPerGF();
        checkForCongestedGF(gfsSpeed, geofenceStatus);
    }

    public void managePassengerEvent() throws UnirestException, IOException, InterruptedException {
        List<MusteringDetails> musteringDetails = dbProxyFacade.getPassengerMusteringDetails();

        for(MusteringDetails details:musteringDetails) {
            String medicalConditions = details.getGeneral_info().getMedical_condnitions();
            String mobilityIssues = details.getGeneral_info().getMobility_issues();
            String pregnancyData = details.getGeneral_info().getPregnency_data();

            if(!"".equals(medicalConditions) || !"".equals(mobilityIssues) || !"".equals(pregnancyData)) {
                PassengerIncident incident = generatePassengerIncident(details);

                //dbProxyFacade.declarePassengerIncident(incident);

                //TODO publish to kafka
            }
        }
    }

    public void monitorPassengers(Location location) throws UnirestException {
        //List<LocationServiceDTO> passengerLocationList = new ArrayList<>();
        LocationServiceDTO passengerLocation = new LocationServiceDTO();

        passengerLocation.setTimestamp(location.getTimestamp());
        passengerLocation.setYCoord(location.getYLocation());
        passengerLocation.setXCoord(location.getXLocation());

        LocationMonitor locationMonitor = locationMonitorRepo.findByHashedMacAddress(location.getHashedMacAddress());
        if(locationMonitor == null) {
            locationMonitor = new LocationMonitor();
            locationMonitor.setHashedMacAddress(location.getHashedMacAddress());
            locationMonitor.setLocations(new ArrayList<>());
            locationMonitor.setHasIncident(false);
        }

        List<Location> passengerLocationList = locationMonitor.getLocations();


        passengerLocationList.add(location);
        locationMonitor.setLocations(passengerLocationList);

        Location startLocation = passengerLocationList.get(0);
        Location latestLocation = passengerLocationList.get(passengerLocationList.size()-1);

        LocalDateTime startTime = DateUtil.dateStringToLDT(startLocation.getTimestamp());
        LocalDateTime latestTime = DateUtil.dateStringToLDT(latestLocation.getTimestamp());

        Set<String> gfFilter = passengerLocationList.stream().map(e -> e.getGeofenceId()).collect(Collectors.toSet());

        if(latestTime.minusMinutes(5).isAfter(startTime) && !locationMonitor.getHasIncident() && gfFilter.size() == 1) {
            List<MusteringDetails> detailsList = dbProxyFacade.getPassengerMusteringDetails();
            Optional<MusteringDetails> details = detailsList.stream()
                    .filter(e -> e.getCommunication_details().getMacAddress()
                            .equals(location.getHashedMacAddress())).findFirst();
            if(details.isPresent()) {
                PassengerIncident incident = generatePassengerIncident(details.get());
                locationMonitor.setHasIncident(true);

                //TODO send to kafka
            }
        }


        locationMonitorRepo.save(locationMonitor);
    }

    private PassengerIncident generatePassengerIncident(MusteringDetails details) {
        PassengerIncident incident = new PassengerIncident();
        incident.setId(details.getGeneral_info().getIdentifier());
        incident.setPassengerName(details.getGeneral_info().getName());
        incident.setPassengerSurname(details.getGeneral_info().getSurname());
        incident.setHealthIssues(details.getGeneral_info().getMedical_condnitions());
        incident.setMobilityIssues(details.getGeneral_info().getMobility_issues());
        incident.setPregnancyStatus(details.getGeneral_info().getPregnency_data());

        //incident.setAssignedCrewMemberId(details.getGeneral_info()); no data for crew member id

        incident.setPreferredLanguage(details.getGeneral_info().getPreferred_language());
        incident.setGeofence(details.getLast_known_location().getGeofence().getGfId());
        incident.setDeck(details.getLast_known_location().getGeofence().getDeck());
        incident.setTimestamp(DateUtil.dateToString(LocalDateTime.now()));
        incident.setStatus(""); //TODO find status
        incident.setX_loc(details.getLast_known_location().getLocation().getXLocation());
        incident.setY_loc(details.getLast_known_location().getLocation().getYLocation());
        return incident;
    }

    private Map<String, List<String>> getSpeedsPerGF() throws UnirestException {
        Map<String, List<String>> gfsSpeed = new HashMap<>();
        List<MusteringDetails> musteringDetails = dbProxyFacade.getPassengerMusteringDetails();
        for(MusteringDetails details:musteringDetails){
            PassengerDTO person = dbProxyFacade.getPerson(details.getGeneral_info().getIdentifier());
            details.getLast_known_location().getGeofence().getGfId();
            String gfId = details.getLast_known_location().getGeofence().getGfId();

            List<Location> locationList = person.getLocationInfo().getLocationHistory();
            List<LocationServiceDTO> locationDtos = new ArrayList<>();

            //get the last 4 locations
            for(int i=locationList.size()-1; i>=0; i--){
                LocationServiceDTO dto = new LocationServiceDTO();
                dto.setXCoord(locationList.get(i).getXLocation());
                dto.setYCoord(locationList.get(i).getYLocation());
                dto.setTimestamp(locationList.get(i).getTimestamp());

                locationDtos.add(dto);

                if(i == locationList.size()-5) break;
            }
            //calculate speed from given locations
            String passengerSpeed = locationDataFacade.getPassengerSpeed(locationDtos);

            //add person speed to the list of passenger speeds for this geofence
            if (gfsSpeed.get(gfId) == null){
                List<String> speeds = new ArrayList<>();
                speeds.add(passengerSpeed);
                gfsSpeed.put(gfId, speeds );
            } else {
                List<String> speeds = gfsSpeed.get(gfId);
                speeds.add(passengerSpeed);
                gfsSpeed.put(gfId, speeds);
            }
        }
        return gfsSpeed;
    }

    private void checkForCongestedGF(Map<String, List<String>> gfsSpeed, GeofenceStatus geofenceStatus) throws UnirestException, IOException, InterruptedException {
        for (Map.Entry<String, List<String>> entry : gfsSpeed.entrySet()) {
            Double avgSpeed = entry.getValue().stream().map(Double::parseDouble)
                    .mapToDouble(Double::doubleValue).sum() / entry.getValue().size();

            if(avgSpeed <= Double.parseDouble(speedThreshold1) && !isMusterStation(entry.getKey(), geofenceStatus)) {
                Optional<GeofenceDTO> gf = getSimpleGeofence(entry.getKey(), geofenceStatus);
                if(gf.isPresent()){
                    GeofenceDTO gfDto = gf.get();
                    gfDto.setStatus(GfStatus.BLOCKED.getValue());
                    dbProxyFacade.updateGeofence(gfDto);
                }

            } else if (avgSpeed <= Double.parseDouble(speedThreshold2) && !isMusterStation(entry.getKey(), geofenceStatus)){
                Optional<GeofenceDTO> gf = getSimpleGeofence(entry.getKey(), geofenceStatus);
                if(gf.isPresent()){
                    GeofenceDTO gfDto = gf.get();
                    gfDto.setStatus(GfStatus.ALMOST_BLOCKED.getValue());
                    dbProxyFacade.updateGeofence(gfDto);
                }
            }

            //TODO call GEOFENCE_BLOCK_REROUTE_NEEDED in kafka
        }
    }

    private Boolean isMusterStation(String gfId, GeofenceStatus geofenceStatus){
        return geofenceStatus.getMustering().stream().anyMatch(e -> e.getId().equals(gfId));
    }

    private Optional<GeofenceDTO> getSimpleGeofence(String gfId, GeofenceStatus geofenceStatus){
        return geofenceStatus.getSimple().stream().filter(e -> e.getId().equals(gfId)).findAny();
    }
}

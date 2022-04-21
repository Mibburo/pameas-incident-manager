package pameas.incident.detection.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pameas.incident.detection.model.MinGeofenceUnitTO;
import pameas.incident.detection.model.MinLocationTO;
import pameas.incident.detection.model.MinLocationUnitTO;
import pameas.incident.detection.model.PassengerIncident;
import pameas.incident.detection.model.enums.IncidentType;
import pameas.incident.detection.service.KafkaService;
import pameas.incident.detection.utils.DateUtil;

import java.time.LocalDateTime;

@Slf4j
@RestController
public class KafkaController {

    private KafkaService kafkaService;

    @Autowired
    KafkaController(KafkaService kafkaService){
        this.kafkaService = kafkaService;
    }

    @RequestMapping("/kafkaSendLocation")
    public void sendKafkaMessage(){

        log.info("aaaaaaaaaaaaaaa kafka send");
        MinLocationTO loc = new MinLocationTO();
        loc.setGfName("arlarl geofence");
        loc.setHashedMacAddress("arlhashedmacaddressarl");
        MinGeofenceUnitTO gf = new MinGeofenceUnitTO();
        gf.setGfId("id513");
        loc.setGeofence(gf);
        MinLocationUnitTO locUnit = new MinLocationUnitTO();
        locUnit.setXLocation("5.24");
        locUnit.setYLocation("78");
        locUnit.setTimestamp(DateUtil.dateToString(LocalDateTime.now()));
        loc.setLocation(locUnit);
        kafkaService.saveLocation(loc);
    }

    @RequestMapping("/kafkaSendIncident")
    public void sendKafkaIncidentMessage(){

        log.info("aaaaaaaaaaaaaaa kafka send incident");
        PassengerIncident incident = new PassengerIncident();
        incident.setType(IncidentType.PASSENGER_ALERT_COMPLETED.toString());
        incident.setTimestamp(DateUtil.dateToString(LocalDateTime.now()));
        kafkaService.saveIncident(incident);
    }

    /*@RequestMapping("/kafkaGet")
    public void getKafkaMessage(){
        log.info("bbbbbbbbbbbbbbb kafka get");
        kafkaService.getLocation();
    }*/
}

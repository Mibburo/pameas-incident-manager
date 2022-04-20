package pameas.incident.detection.facade;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pameas.incident.detection.model.LocationServiceDTO;

import java.util.List;

import static pameas.incident.detection.utils.Constants.LOCATION_SERVICE_SPEED_URL;

@Service
public class LocationDataFacade {

    //get passenger speed from location service
    public String getPassengerSpeed(List<LocationServiceDTO> locationServiceDTO) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(LOCATION_SERVICE_SPEED_URL, locationServiceDTO, String.class);
    }


}

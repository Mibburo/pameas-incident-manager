package pameas.incident.detection.facade;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pameas.incident.detection.model.PassengerEvalDTO;

import static pameas.incident.detection.utils.Constants.EVAL_PASSENGER_URL;

@Service
public class RulesFacade {

    //get passenger evaluation from rules engine
    public String getPassengerEval(PassengerEvalDTO passengerEvalDTO) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(EVAL_PASSENGER_URL, passengerEvalDTO, String.class);
    }
}

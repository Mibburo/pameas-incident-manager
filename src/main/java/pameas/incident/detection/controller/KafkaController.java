package pameas.incident.detection.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pameas.incident.detection.model.MinGeofenceUnitTO;
import pameas.incident.detection.model.MinLocationTO;
import pameas.incident.detection.model.MinLocationUnitTO;
import pameas.incident.detection.model.PassengerIncident;
import pameas.incident.detection.model.enums.IncidentType;
import pameas.incident.detection.service.KafkaListenerService;
import pameas.incident.detection.service.KafkaService;
import pameas.incident.detection.utils.DateUtil;

import java.time.LocalDateTime;

@Slf4j
@RestController
public class KafkaController {

    private KafkaService kafkaService;
    private KafkaListenerService kafkaListenerService;

    @Autowired
    KafkaController(KafkaService kafkaService, KafkaListenerService kafkaListenerService){
        this.kafkaService = kafkaService;
        this.kafkaListenerService = kafkaListenerService;
    }

    @RequestMapping("/kafkaSendLocation")
    public void sendKafkaMessage(@RequestBody MinLocationTO loc){
        kafkaService.saveLocation(loc);
    }

    @RequestMapping("/kafkaSendIncident")
    public void sendKafkaIncidentMessage(@RequestBody PassengerIncident incident){
        kafkaService.saveIncident(incident);
    }

    @RequestMapping("/startConsumer")
    public void startKafkaConsumer() throws InterruptedException {
        kafkaListenerService.runConsumer();
    }
}

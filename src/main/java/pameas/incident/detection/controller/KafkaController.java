package pameas.incident.detection.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pameas.incident.detection.model.MinLocationTO;
import pameas.incident.detection.service.KafkaService;

@Slf4j
@RestController
public class KafkaController {

    private KafkaService kafkaService;

    @Autowired
    KafkaController(KafkaService kafkaService){
        this.kafkaService = kafkaService;
    }

    @RequestMapping("/kafkaSend")
    public void sendKafkaMessage(){

        log.info("aaaaaaaaaaaaaaa kafka send");
        MinLocationTO loc = new MinLocationTO();
        loc.setGfName("arlarl geofence");
        loc.setHashedMacAddress("arlhashedmacaddressarl");
        kafkaService.saveLocation(loc);
    }

    /*@RequestMapping("/kafkaGet")
    public void getKafkaMessage(){
        log.info("bbbbbbbbbbbbbbb kafka get");
        kafkaService.getLocation();
    }*/
}

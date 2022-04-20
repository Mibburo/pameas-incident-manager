package pameas.incident.detection.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pameas.incident.detection.model.MinLocationTO;
import pameas.incident.detection.model.PassengerIncident;

import java.time.Duration;

import static pameas.incident.detection.utils.Constants.LOCATION_TOPIC;
import static pameas.incident.detection.utils.Constants.NOTIFICATION_TOPIC;

@Service
@Slf4j
public class KafkaService {
    /*static final String TOPIC = "pameas-notification";
    static final String LOCATION_TOPIC = "pameas-location";*/

    private final KafkaProducer<String, PassengerIncident> incidentProducer;
    private final KafkaProducer<String, MinLocationTO> locationProducer;

    //private final KafkaConsumer<String, MinLocationTO> locationConsumer;


    @Autowired
    public KafkaService(KafkaProducer<String, PassengerIncident> incidentProducer, KafkaProducer<String, MinLocationTO> locationProducer/*, KafkaConsumer locationConsumer*/) {
        this.incidentProducer = incidentProducer;
        this.locationProducer = locationProducer;
        //this.locationConsumer = locationConsumer;
    }

    public void saveIncident(PassengerIncident incident) {
        try {
            log.info("pushing incident to kafka {}", incident);
            this.incidentProducer.send(new ProducerRecord<>(NOTIFICATION_TOPIC, incident));
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
//            producer.close();
        }
    }

    public void saveLocation(MinLocationTO location) {
        try {
            log.info("pushing location to kafka {}", location);

            this.locationProducer.send(new ProducerRecord<>(LOCATION_TOPIC, location));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /*public void getLocation(){
        try {
            while (true) {
                ConsumerRecords<String, MinLocationTO> records = locationConsumer.poll(Duration.ofMillis(100));
                records.forEach(record -> {
                    log.info("Consumer Record:(%d, %s, %d, %d)\n",
                            record.key(), record.value(),
                            record.partition(), record.offset());
                });

                locationConsumer.commitAsync();
            }

        } finally {
                locationConsumer.close();
        }
    }*/
}

package pameas.incident.detection.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pameas.incident.detection.facade.DbProxyFacade;
import pameas.incident.detection.model.Location;
import pameas.incident.detection.model.MinLocationTO;
import pameas.incident.detection.model.PassengerIncident;
import pameas.incident.detection.model.enums.IncidentType;
import pameas.incident.detection.utils.EnvUtils;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static pameas.incident.detection.utils.Constants.LOCATION_TOPIC;
import static pameas.incident.detection.utils.Constants.NOTIFICATION_TOPIC;

@Slf4j
@Component
public class KafkaListenerService {

    private final Properties properties = new Properties();

    private DbProxyFacade dbProxyFacade;
    private IncidentManagementService incidentManagementService;

    @Autowired
    KafkaListenerService(DbProxyFacade dbProxyFacade, IncidentManagementService incidentManagementService){
        this.dbProxyFacade = dbProxyFacade;
        this.incidentManagementService = incidentManagementService;
    }

    private Consumer<Long, String> createConsumer() {

        generateProperties();
        // Create the consumer using props.
        final Consumer<Long, String> consumer =
                new KafkaConsumer<>(this.properties);

        // Subscribe to the topic.
        //consumer.subscribe(Collections.singletonList(LOCATION_TOPIC));
        consumer.subscribe(Arrays.asList(LOCATION_TOPIC, NOTIFICATION_TOPIC));
        return consumer;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runConsumer() throws InterruptedException {
        final Consumer<Long, String> consumer = createConsumer();
        final int giveUp = 100;   int noRecordsCount = 0;

        while (true) {
            final ConsumerRecords<Long, String> consumerRecords =
                    consumer.poll(Duration.ofMillis(1000));

            if (consumerRecords.count()==0) {
                noRecordsCount++;
                if (noRecordsCount > giveUp) break;
                else continue;
            }
            for (TopicPartition partition : consumerRecords.partitions()) {
                List<ConsumerRecord<Long, String>> partitionRecords = consumerRecords.records(partition);
                partitionRecords.forEach(record -> {
                    record.topic();
                    System.out.printf("Consumer Record:(%d, %s, %d, %d, %s)\n",
                            record.key(), record.value(),
                            record.partition(), record.offset(), record.topic());
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        switch (record.topic()){
                            case "pameas-location" :
                                MinLocationTO locationTO = mapper.readValue(record.value(), MinLocationTO.class);
                                Location location = generateLocation(locationTO);
                                incidentManagementService.monitorPassengers(location);
                                break;
                            case "pameas-notification":
                                PassengerIncident incident = mapper.readValue(record.value(), PassengerIncident.class);
                                if(incident.getType().equals(IncidentType.PASSENGER_ALERT_COMPLETED.toString())){
                                    incidentManagementService.managePassengerRoute();
                                }
                                if(incident.getType().equals(IncidentType.PASSENGER_INSTRUCTIONS_COMPLETED.toString())){
                                    incidentManagementService.managePassengerEvent();
                                }
                                break;
                        }

                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                });
            }

            consumer.commitAsync();
        }
        consumer.close();
        System.out.println("DONE");
    }

    private Location generateLocation(MinLocationTO locationTO){
        Location location = new Location();
        location.setXLocation(locationTO.getLocation().getXLocation());
        location.setYLocation(locationTO.getLocation().getYLocation());
        location.setHashedMacAddress(locationTO.getHashedMacAddress());
        location.setTimestamp(locationTO.getLocation().getTimestamp());
        location.setGeofenceId(locationTO.getGeofence().getGfId());

        return location;
    }

    private void generateProperties(){
        String kafkaURI = EnvUtils.getEnvVar("KAFKA_URI_WITH_PORT","dfb.palaemon.itml.gr:30093");

//                StringUtils.isEmpty(System.getenv("KAFKA_URI"))?"dfb.palaemon.itml.gr:30093":System.getenv("KAFKA_URI");
        String trustStoreLocation = EnvUtils.getEnvVar("KAFKA_TRUST_STORE_LOCATION", "/home/ni/code/java/palaemon-db-proxy/truststore.jks");
//                StringUtils.isEmpty(System.getenv("TRUST_STORE_LOCATION"))?
//                "/home/ni/code/java/palaemon-db-proxy/truststore.jks":System.getenv("TRUST_STORE_LOCATION");
        String trustStorePass = EnvUtils.getEnvVar("KAFKA_TRUST_STORE_PASSWORD", "teststore");
//                StringUtils.isEmpty(System.getenv("TRUST_STORE_PASSWORD"))?
//                "teststore":System.getenv("TRUST_STORE_PASSWORD");


        String keyStoreLocation = EnvUtils.getEnvVar("KAFKA_KEYSTORE_LOCATION", "/home/ni/code/java/palaemon-db-proxy/keystore.jks");
//                StringUtils.isEmpty(System.getenv("KEY_STORE_LOCATION"))?
//                "/home/ni/code/java/palaemon-db-proxy/keystore.jks":System.getenv("KEY_STORE_LOCATION");
        String keyStorePass = EnvUtils.getEnvVar("KAFKA_KEY_STORE_PASSWORD", "teststore");
//                StringUtils.isEmpty(System.getenv("KEY_STORE_PASSWORD"))?
//                "teststore":System.getenv("KEY_STORE_PASSWORD");

        log.info("kafka uri :{}", kafkaURI);
        log.info("trustStoreLocation :{}", trustStoreLocation);
        log.info("trustStorePass :{}", trustStorePass);
        log.info("keyStoreLocation :{}", keyStoreLocation);
        log.info("keyStorePass :{}", keyStorePass);

        this.properties.put("bootstrap.servers", kafkaURI);
        this.properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        this.properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        this.properties.put("security.protocol", "SSL");
        this.properties.put("ssl.truststore.location", trustStoreLocation);
        this.properties.put("ssl.truststore.password", trustStorePass);
        this.properties.put("ssl.keystore.location", keyStoreLocation);
        this.properties.put("ssl.keystore.password", keyStorePass);

        this.properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        this.properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        this.properties.put(ConsumerConfig.GROUP_ID_CONFIG, "uaeg-consumer-group");

    }


}

/*
package pameas.incident.detection.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pameas.incident.detection.facade.DbProxyFacade;
import pameas.incident.detection.utils.EnvUtils;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import static pameas.incident.detection.utils.Constants.LOCATION_TOPIC;

@Slf4j
@Component
public class KafkaListenerService {

    private final Properties properties = new Properties();

    private DbProxyFacade dbProxyFacade;

    @Autowired
    KafkaListenerService(DbProxyFacade dbProxyFacade){
        this.dbProxyFacade = dbProxyFacade;
    }

@Value(value = "${listen.topic.name}")
    private String topicName;


//    @KafkaListener(topics = "pameas-location")
//    void listener(String data) throws UnirestException {
//        log.info("11111111111111111111111");
//        log.info(data);
//        if (data.equals("PASSENGER_ALERT_COMPLETED")) {
//            dbProxyFacade.getGeofenceStatus();
//        }
//    }
    private Consumer<Long, String> createConsumer() {
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
        this.properties.put(ConsumerConfig.GROUP_ID_CONFIG, "UAEG");

        // Create the consumer using props.
        final Consumer<Long, String> consumer =
                new KafkaConsumer<>(this.properties);

        // Subscribe to the topic.
        consumer.subscribe(Collections.singletonList(LOCATION_TOPIC));
        return consumer;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runConsumer() throws InterruptedException {
        final Consumer<Long, String> consumer = createConsumer();
        log.info("fffffffffffffffffffffffff");
        final int giveUp = 100;   int noRecordsCount = 0;

        while (true) {
            final ConsumerRecords<Long, String> consumerRecords =
                    consumer.poll(Duration.ofMillis(1000));

            if (consumerRecords.count()==0) {
                noRecordsCount++;
                if (noRecordsCount > giveUp) break;
                else continue;
            }

            consumerRecords.forEach(record -> {
                System.out.printf("Consumer Record:(%d, %s, %d, %d)\n",
                        record.key(), record.value(),
                        record.partition(), record.offset());
            });

            consumer.commitAsync();
        }
        consumer.close();
        System.out.println("DONE");
    }


}
*/

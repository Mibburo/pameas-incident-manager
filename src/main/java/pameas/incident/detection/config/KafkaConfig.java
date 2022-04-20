package pameas.incident.detection.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pameas.incident.detection.utils.EnvUtils;
import pameas.incident.detection.utils.KafkaJsonSerializer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static pameas.incident.detection.utils.Constants.LOCATION_TOPIC;
import static pameas.incident.detection.utils.Constants.NOTIFICATION_TOPIC;

/*@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}*/
@Slf4j
@Configuration
public class KafkaConfig {
    //TODO read these from .env
    private final Properties properties = new Properties();



    public KafkaConfig() {
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

    }

    @Bean
    public KafkaProducer producer() {
        KafkaProducer<String,String> myProducer= new KafkaProducer<String,String>(this.properties,new StringSerializer(),
                new KafkaJsonSerializer());
        return myProducer;
    }

    /*@Bean
    public Consumer consumer() {
        Consumer<String,String> myConsumer= new KafkaConsumer<String,String>(this.properties);

        myConsumer.subscribe(Collections.singletonList(LOCATION_TOPIC));
        return myConsumer;
    }*/


}

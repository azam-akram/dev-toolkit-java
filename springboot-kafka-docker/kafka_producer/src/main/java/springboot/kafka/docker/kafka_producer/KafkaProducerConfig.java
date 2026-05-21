package springboot.kafka.docker.kafka_producer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import springboot.kafka.docker.kafka_producer.model.Message;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public KafkaTemplate<String, Message> kafkaTemplate(ProducerFactory<String, Message> pf) {
        return new KafkaTemplate<>(pf);
    }
}

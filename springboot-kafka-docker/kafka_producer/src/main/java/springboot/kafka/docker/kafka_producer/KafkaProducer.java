package springboot.kafka.docker.kafka_producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import springboot.kafka.docker.kafka_producer.model.Message;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {

    private final KafkaTemplate<String, Message> kafkaTemplate;

    @Value("${application.topic.message-topic}")
    private String topic;

    public void send(Message message) {
        kafkaTemplate.send(topic, message)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to send message UUID={}: {}", message.getUuid(), ex.getMessage());
                    } else {
                        log.info("Sent message UUID={} to partition={} offset={}",
                                message.getUuid(),
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    }
                });
    }
}

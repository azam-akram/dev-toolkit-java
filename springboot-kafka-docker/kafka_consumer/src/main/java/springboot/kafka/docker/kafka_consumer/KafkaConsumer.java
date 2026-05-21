package springboot.kafka.docker.kafka_consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import springboot.kafka.docker.kafka_consumer.model.Message;

import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    @RetryableTopic(attempts = "3", backoff = @Backoff(delay = 1000))
    @KafkaListener(topics = "${application.topic.message-topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(Message message, Acknowledgment ack) {

        log.info("=================================");
        log.info("Received message:");
        log.info("UUID: {}", message.getUuid());
        log.info("FROM: {}", message.getFrom());
        log.info("TO: {}", message.getTo());
				log.info("Message: {}", message.getMessage());
        log.info("=================================");
        ack.acknowledge();
    }

    @DltHandler
    public void handleDlt(Message message, Exception e) {
        log.error("Message failed after retries — UUID: {}, error: {}", message.getUuid(), e.getMessage());
    }
}
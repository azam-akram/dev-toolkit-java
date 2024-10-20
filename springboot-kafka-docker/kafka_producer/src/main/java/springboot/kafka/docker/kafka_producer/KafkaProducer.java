package springboot.kafka.docker.kafka_producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import springboot.kafka.docker.kafka_producer.model.Message;

import java.util.UUID;

@Slf4j
@Component
public class KafkaProducer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${application.topic.message-topic}")
    private String topic;

    public void sendMessage() {
        try {
            Message message = createMessage();
            String messageJson = objectMapper.writeValueAsString(message); // Convert to JSON
            kafkaTemplate.send(topic, messageJson);
            log.info("Message sent to topic {}: {}", topic, createMessage());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }        
    }

    private Message createMessage() {
        return Message.builder()
                .uuid(UUID.randomUUID().toString())
                .from("Sender")
                .to("Receiver")
                .build();
    }
}

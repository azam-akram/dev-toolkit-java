package com.demo.kafka.boot.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${application.topic.message-topic}")
    private String topic;

    public void sendMessage() {
        kafkaTemplate.send(topic, createMessage());
    }

    private String createMessage() {
        return "{  \n" +
                "   \"uuid\":\"kdfe25b9-akda-49bf-ab3a-6482a19ahshs\",\n" +
                "   \"from\":\"Sender\",\n" +
                "   \"to\":\"Receiver\"\n" +
                "}";
    }
}

package springboot.kafka.docker.kafka_producer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.kafka.docker.kafka_producer.KafkaProducer;
import springboot.kafka.docker.kafka_producer.model.Message;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ProducerController {

	private final KafkaProducer producer;

	@GetMapping("/send")
	public String send() {
		Message message = Message.builder()
			.uuid(UUID.randomUUID().toString())
			.from("Alice")
			.to("Bob")
			.message("Hello")
			.build();

		producer.send(message);

		return "Message sent!";
	}
}

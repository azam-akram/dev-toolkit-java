package springboot.kafka.docker.kafka_consumer.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {
	private String uuid;
	private String from;
	private String to;
	private String message;
}
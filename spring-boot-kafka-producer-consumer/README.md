# spring-boot-kafka-producer-consumer

This little project demonstrate the easier and simpler way to create kafka producer and consumer and dockerizing it. The Producer sends a message to Kafka topic which is consumed y the consumer, That's it!

### Frameworks

* **Spring Boot:** "Spring Boot helps to create stand-alone, production-grade Spring based Applications that you can "just run"." Read (https://spring.io/projects/spring-boot)
* **Apache Kafka:** Apache Kafka is a distributed streaming platform, based on subscribed-publish-consumer model to exchange messages between producer and consumer. Read more (https://kafka.apache.org/intro)
* **Apache Zookeeper:** "ZooKeeper is a centralized service for maintaining configuration information, naming, providing distributed synchronization, and providing group services." (https://zookeeper.apache.org/)
* **Docker-compose:** Docker Compose is a tool for defining and running multi-container Docker applications. Read more (https://docs.docker.com/compose/)


### Prerequisites

I used docker-compose to run Zookeeper and Kafka containers, so no need to install Kafka and Zookeeper locally.

### Walk through the code
Spring Boot saves the developers from complex configurations for Kafka, and provides some properties in easy-t-use-way to configure Kafka Producer and Consumer.

```yml
server:
  port: 5555

application:
  topic:
    message-topic: demo-kafka-topic

spring:
  kafka:
    bootstrap-servers: 127.0.0.1:9092
    consumer:
      group-id: demo-kafka-consumer
      enable-auto-commit: true
      auto-offset-reset: earliest
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.apache.kafka.common.serialization.StringDeserializer
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
```

StringSerializer and StringDeserializer are used to send and receive messages in String format. 

Message class is simple POJO,

```java
public class Message {

    @NotNull
    private String uuid;

    @NotBlank
    private String from;

    @NotBlank
    private String to;
} 
```

Now we left with just to make a KafkaProducer class to send the message and a KafkaConsumer class to receive the message.

```java
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
```

Spring Boot provides kafka configuration properties, which setups the producer and consumer, all you need to Autowired the KafkaTemplate into your Producer class,

```java
@Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
```

and then send the message,

```java
kafkaTemplate.send(topic, createMessage());
```

Ofcourse we need to mention kafka topic we want to send message to.

On receiving end, we have KafkaConsumer class,

```java
@Slf4j
@Component
public class KafkaConsumer {
    @KafkaListener(topics = "${application.topic.message-topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void onMessage(final ConsumerRecord<String, String> consumerRecord) throws IOException {
        Message message = new ObjectMapper().readValue(consumerRecord.value(), Message.class);
        log.trace("Received Messasge: " + message.toString());
    }
}
```

KafkaConsumer has one very simple onMessage handler. @KafkaListner annotation does the magic for you.

ConsumerRecord is the data structure which carries all the necessary information for the exchanged message.

### Running the application

If you want to test application locally then run the docker compose by,

First run the docker compose

```bash
docker-compose up -d
```

Then run the KafkaDemoApplication to exchange the message.

Stop docker-compose (once you are done)
```bash
docker-compose down
```

### Dockerizing the application

I added a gradle task to build docker image. First include gradle dependency in build.gralde, 

```gradle
buildscript {
    ext {
        springBootVersion = '2.0.5.RELEASE'
        gradleDockerVersion   = "1.2"
    }
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}")
        // Dependency for docker
        classpath("se.transmode.gradle:gradle-docker:${gradleDockerVersion}")
    }
}
```

and docker plugin

```gradle
apply plugin: 'docker'
```

then add a gradle task in build.gradle file

```gradle
group 'spring-boot-kafka-producer-consumer' // used for tag name of the image

task buildDocker(type: Docker, dependsOn: build) {
    push = false // not pushing the image to docker hub
    applicationName = jar.baseName // tag name of image, group/jar-file-name
    dockerfile = file('Dockerfile')
    doFirst {
        copy {
            from jar
            into stageDir
        }
    }
}
```

add Dockerfile
```Dockerfile
FROM java:8
EXPOSE 5556
VOLUME /tmp
ADD spring-boot-kafka-producer-consumer-1.0-SNAPSHOT.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","/app.jar"]
```

and now all set to run the docker image,
```
docker run -i spring-boot-kafka-producer-consumer/spring-boot-kafka-producer-consumer:1.0-SNAPSHOT
```


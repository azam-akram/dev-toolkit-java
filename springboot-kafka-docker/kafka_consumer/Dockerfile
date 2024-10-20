FROM openjdk:23-jdk

EXPOSE 5556

VOLUME /tmp

ADD build/libs/kafka_consumer-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]

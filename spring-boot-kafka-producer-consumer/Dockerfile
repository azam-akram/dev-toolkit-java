FROM java:8

EXPOSE 5556

VOLUME /tmp

ADD spring-boot-kafka-producer-consumer-1.0-SNAPSHOT.jar app.jar

RUN bash -c 'touch /app.jar'

ENTRYPOINT ["java","-jar","/app.jar"]
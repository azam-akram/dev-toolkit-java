FROM java:8

MAINTAINER  Muhammad Azam Akram <akram.muhammadazam@gmail.com>

EXPOSE 8080

ADD build/libs/message-service-1.0-SNAPSHOT.jar /app/message-service.jar

WORKDIR /app/

CMD java -jar message-service.jar
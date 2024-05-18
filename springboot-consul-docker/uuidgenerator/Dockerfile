FROM eclipse-temurin:19-jdk-alpine

EXPOSE 8080

ADD build/libs/uuidgenerator-0.0.1-SNAPSHOT.jar /app/uuidgenerator.jar

WORKDIR /app/

CMD java -jar uuidgenerator.jar
FROM eclipse-temurin:19-jdk-alpine

EXPOSE 8080

ADD build/libs/clientidgenerator-0.0.1-SNAPSHOT.jar /app/clientidgenerator.jar

WORKDIR /app/

CMD java -jar clientidgenerator.jar
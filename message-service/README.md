# Message service
The message-service is a spring boot based microservice which exposes a number of endpoints to retrive messages to/from database. The service is auto-discoverable through **consul**. The message-service dynamically discovers another microsevrice **uuid-generator** (I have written https://github.com/azam-akram/uuid-generator) and fetches uuid for each new message.

### Run application without docker container

- First, run the consul service in container
```bash
docker run -p 8500:8500 consul-latest
```
Go to http://localhost:8500 and see consul service is up and running.
- Second, the message-service fetches uuid for each message from uuid-generator service. So uuid-generator service must be registered to the consul server. Find the instruction about how to run uuid-generator service here https://github.com/azam-akram/uuid-generator.

Go to uuid-generator project and build the whole project
```bash
gradlew clean build
```
and then run the GeneratorApplication.
Go to http://localhost:8500 and see new uuid-generator service is added to the dashboard.

- Then come back here and build the whole message-service projects and tests
```bash
gradlew clean build
```
- Now run MessageApplication, which starts Tomcat at port 9999 and registers itself to consul server.
Go to http://localhost:8500 and see now both uuid-generator and message-service services are added to the dashboard.

### Application Consul configuration
In order to make service discoverable in consul, I use spring cloud dependency and **@EnableDiscoveryClient**,
```java
@EnableDiscoveryClient
@SpringBootApplication
public class MessageApplication {
    public static void main(String[] args) {
        SpringApplication.run(MessageApplication.class, args);
    }
}
```
and add consul properties in bootstrap.yml files
```yml
spring:
  application:
    name: message-service
  cloud:
    consul:
      host: consul
      port: 8500
      discovery:
        enabled: true
        prefer-ip-address: true
        health-check-path: /health
        instance-id: ${spring.application.name}:${random.value}
        health-check-interval: 15s
```

### REST Controller
Call REST APIs,

Get All messages
```
HTTP Header: 
Accept: application/json
GET: http://{host-ip}:9999/message-service/v1/message
```

Get Last N number of message messages:
HTTP Header: 
Accept: application/json
```
HTTP Header: 
Accept: application/json
Get: http://{host-ip}:9999/message-service/v1/message/{count}
```

Create new message
```
HTTP Header: 
Accept: application/json
Content-Type: application/json
POST: http://{host-ip}:9999/message-service/v1/message/
body:
{
	"sender": "a sender"
}
```

Update a message
```
HTTP Header: 
Accept: application/json
Content-Type: application/json
PUT: http://{host-ip}:9999/message-service/v1/message/{message-key}
body:
{
	"sender": "a sender"
}
```

Delete older messages
```
DELET: http://{host-ip}:9999/message-service/v1/message/
```

### Build and run docker images
- Go to uuid-generator project and build the docker image by docker-compose. I just build the uuid-generator image there.
  ```bash
  docker-compose build
  ```
  now the uuid-generator image should be available in image list,
    ```bash
  docker images
  ```
- Then come back here in message-service project.
- Build message-service image by,
  ```bash
    docker-compose build
  ```
- And finally run both uuid-generator and message-service by,
  ```bash
    docker-compose up
  ```
Following is my docker-compose.yml
```yaml
 version: '2.2'

services:
  consul:
    image: consul:latest
    ports:
    - "8400:8400"
    - "8500:8500"
    - "8600:8600"

  uuid-generator:
    image: uuid-generator
    ports:
    - "8888:8888"
    depends_on:
    - consul
    links:
    - "consul"

  message-service:
    image: message-service
    build:
      context: ./
      dockerfile: Dockerfile
    ports:
    - "9999:9999"
    depends_on:
    - consul
    links:
    - "consul"
```

# Credit Card Processing Service
This projects demonstrate a simple Angular JS front-end web application communicating to the backend system through REST API. The web application takes input to register credit card, do some validation and pass to server to persist. This also containerise the application, push image to docker hub and deploy application to heroku cloud.
The communication between backend and frontend happens through REST APIs.
### Technology used
- Spring Boot
- JPA
- H2 
- Docker
- AngularJS
- Freemarker
- Gradle
- Mockito
- Heroku
### Build the application

Use gradle wrapper to build this,
```
gradlew clean build
```
### Run application outside docker container
- First, run the CreditCardApplication,
- In order to use credit card processing service application, go to 
```
http://localhost:9999/credit-card-processing-service/v1/
```
### Build and run docker image
The Dockerfile consists of all we need to create image of this application.
```Dockerfile
FROM java:8
EXPOSE 8080
ADD build/libs/credit-card-processing-service-1.0-SNAPSHOT.jar /app/credit-card-processing-service.jar
WORKDIR /app/
CMD java -jar credit-card-processing-service.jar
```
Following is the docker-compose.yml to build and run the application inside container,
```yaml
 version: '2.2'
 services:
   credit-card-service:
     image: credit-card-service
     build:
       context: ./
       dockerfile: Dockerfile
     ports:
     - "9999:9999"
```
Now we are set to build the docker image and run credit card processing service inside the container,
```bash
    docker-compose up
```
verify if credit card image is created. Image list should have credit-card-service image.
```bash
    docker images
```
go to following page and you get an AngularJS page to display existing credit cards and an input form to add more credit cards,
```
http://localhost:9999/credit-card-processing-service/v1/
```
### Pushed to Docker Hub
Docker image is also pushed to docker hub.
```
https://hub.docker.com/r/azamakram/credit-card-service/
```
### Heroku deployment
I have created a continous deployment pipe line from GitHub to Heroku cloud.
The live view of application,
```
https://credit-card-processing-service.herokuapp.com/credit-card-processing-service/v1/
```
### REST Controller
Credit card processing service exposes two REST end points,

- Get All existing credit cards information
```
HTTP Header: 
Accept: application/json
GET (running local): http://127.0.0.1:9999/credit-card-processing-service/v1/card
GET (deployed on heroku): https://credit-card-processing-service.herokuapp.com/credit-card-processing-service/v1/card
```
- Add new credit card
```
HTTP Header: 
Accept: application/json
Content-Type: application/json
GET (running local): http://127.0.0.1:9999/credit-card-processing-service/v1/card
GET (deployed on heroku): https://credit-card-processing-service.herokuapp.com/credit-card-processing-service/v1/card
body:
{
	"name":"Customer name",
	"cardNumber":"1234 8776 3567 0987",
	"accountLimit":100000
}
```
### More details
### Luhn10 implementation to verify the credit card number
```javascript
function validateCardNumberByLuhn10(creditCardInput) {
	console.log('Validating card number');
	var cardNumber = creditCardInput.cardNumber;
	var allArray = [];
	var indexAll = 0;
	var sum = 0;
	var checked;
	for (var i = cardNumber.length - 2; i >= 0; i -= 1) {
	allArray[indexAll]
	var num = parseInt(cardNumber.charAt(i), 10);
	if (i % 2 == 1) {
	    num = num*2;
	    if(num > 9) {
		num = num - 9;
	    }
	}
	allArray[indexAll] = num;
	sum += num;
	indexAll++;
	}
	checked = sum * 9 % 10;
	var checksum = sum + checked;
	if(checksum % 10 == 0) {
	console.log("Valid");
	} else {
	console.log("Invalid");
	}
}
```

#### Database modeling
```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="credit_card")
public class CreditCardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable=false)
    private String name;

    @Column(name = "card_number", nullable=false)
    private String cardNumber;

    @Column(name = "balance")
    private Integer balance;

    @Column(name = "account_limit", nullable=false)
    private Integer accountLimit;
}
```
### Repository
```java
@Repository
public interface CreditCardRepository extends JpaRepository<CreditCardEntity, Integer> {
}
```
### Spring boot data
It uses spring boot data properties to create and connect to the H2 database,
```yaml
application:
  api:
    version: v1

server:
  servlet:
    context-path: /credit-card-processing-service/${application.api.version}
  port: 9999

spring:
  datasource:
    url: jdbc:h2:mem:testdb
    username: sa
    password:
    driver-class-name: org.h2.Driver
  jpa:
    hibernate:
      ddl-auto: validate
  flyway:
    locations: classpath:db/migration
```
It also uses Flyway to create database tables and inserting some records,
```sql
CREATE TABLE credit_card (
  id INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  card_number VARCHAR(255) NOT NULL,
  balance INT(11) UNSIGNED,
  account_limit INT(11) UNSIGNED NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX UNIQUE_KEY (card_number)
) ENGINE=INNODB DEFAULT CHARSET=utf8;
```

```sql
INSERT INTO credit_card(id,name,card_number, balance,account_limit)
VALUES(1,'Customer 1','1111 1111 1111 1111',1000,100000);
INSERT INTO credit_card(id,name,card_number, balance,account_limit)
VALUES(2,'Customer 2','2222 2222 2222 2222',2000,200000);
INSERT INTO credit_card(id,name,card_number, balance,account_limit)
VALUES(3,'Customer 3','3333 3333 3333 3333',3000,300000);
INSERT INTO credit_card(id,name,card_number, balance,account_limit)
VALUES(4,'Customer 4',' 4444 4444 4444 4444',4000,400000);
INSERT INTO credit_card(id,name,card_number, balance,account_limit)
VALUES(5,'Customer 5','5555 5555 5555 5555',5000,500000);
```

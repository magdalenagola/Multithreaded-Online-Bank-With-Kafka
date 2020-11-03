# Multithreaded-Online-Bank-With-Kafka

## Table of Contents

- [About](#about)
- [Built-with](#built-with)
- [Installing](#installing)
- [Run](#run)
- [Usage](#usage)
- [Contact](#contact)

## About <a name = "about"></a>

This is an open source project created for learning purposes. This online bank web application handles requests with transactions data and process them in a multithreaded manner using Apache Kafka. The transactions are validated and saved accordingly in the database.

Application handles not only http requests but is also able to process big amount of transactions listed in a CSV file.

## Built With <a name = "built-with"></a>

* [Spring-boot](https://spring.io/projects/spring-boot)
* [PostgreSQL](https://www.postgresql.org/)
* [Hibernate](https://hibernate.org/)
* [Java 8](https://www.java.com/pl/download/faq/java8.xml)
* [Kafka](https://kafka.apache.org/)

## Installing <a name = "installing"></a>

Clone our repository using :

```
https://github.com/magdalenagola/Multithreaded-Online-Bank-With-Kafka
```

Maven properties will be set automatically.

### Important!

To run the application you need to set some data in the application properties file (src/main/resources/application.properties), that is:
- postgres database credentials:
- kafka configuration

```spring.datasource.url=
   spring.datasource.username=
   spring.datasource.password=
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQL92Dialect
   spring.jpa.hibernate.ddl-auto=
   spring.kafka.bootstrap-servers=localhost:9092
   spring.kafka.consumer.group-id=myGroup
```

Before run, you'll have to insert data to your postgres database:
```
cd src/main/java/resources
```

```
psql -h hostname -d databasename -U username -f backup_inserts.sql
```

## Run <a name = "run"></a>

To run our application simply type following commands in terminal (project directory):
```
  mvn package
```

```
  cd target 
```

```
  java -jar Multithreaded-Online-Bank-With-Kafka-0.0.1-SNAPSHOT.jar
```

## Usage <a name = "usage"></a>
Swagger not uploaded yet.
 http://localhost:8080/swagger-ui.html#/
  
## Contact <a name = "contact"></a>
Feel free to contact us.
 * turcza.magdalena@gmail.com
 * szczepan.topolski@gmail.com

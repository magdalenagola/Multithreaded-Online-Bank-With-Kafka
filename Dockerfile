FROM openjdk:8-jdk-alpine

COPY target/Multithreaded-Online-Bank-With-Kafka-0.0.1-SNAPSHOT.jar Multithreaded-Online-Bank-With-Kafka-0.0.1-SNAPSHOT.jar
CMD java -jar Multithreaded-Online-Bank-With-Kafka-0.0.1-SNAPSHOT.jar
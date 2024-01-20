
FROM openjdk:17-jdk

WORKDIR /app

COPY /target/EventTicketManagement-0.0.1-SNAPSHOT.jar /app/EventTicketManagement-0.0.1-SNAPSHOT.jar

EXPOSE 8080

CMD ["java", "-jar", "EventTicketManagement-0.0.1-SNAPSHOT.jar"]
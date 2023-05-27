FROM maven:3.8.3-openjdk-17

WORKDIR /
COPY . .
RUN mvn clean package
ENTRYPOINT ["java", "-jar", "target/university-schedule-web-app-0.0.1-SNAPSHOT.jar"]
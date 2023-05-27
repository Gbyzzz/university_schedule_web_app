FROM maven:3.8.3-openjdk-17

WORKDIR /app
COPY . /app
RUN mvn clean package -e -DskipTests
ENTRYPOINT ["java", "-jar", "target/university-schedule-web-app-0.0.1-SNAPSHOT.jar"]
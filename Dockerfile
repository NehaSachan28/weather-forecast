FROM openjdk:8-jre
ADD target/weather-forecast-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT java -jar /app.jar
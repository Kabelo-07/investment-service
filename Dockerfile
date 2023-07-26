FROM openjdk:17-jdk-slim

ENV JAVA_OPTS="-Xmx512m -Xms256m"

ADD target/investment-service-*.jar /app/investment-service.jar

ENTRYPOINT ["java","-jar","/app/investment-service.jar"]
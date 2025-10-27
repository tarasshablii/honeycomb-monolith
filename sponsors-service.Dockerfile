FROM amazoncorretto:21

COPY microservices/sponsors/build/libs/honeycomb-monolith-sponsors-service.jar /app/sponsors-service.jar

WORKDIR /app
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/sponsors-service.jar"]

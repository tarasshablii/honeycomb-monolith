FROM amazoncorretto:17.0.9

COPY microservices/initiatives/build/libs/honeycomb-monolith-initiatives-service.jar /app/initiatives-service.jar

WORKDIR /app
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/initiatives-service.jar"]

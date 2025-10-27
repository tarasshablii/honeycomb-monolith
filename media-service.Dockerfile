FROM amazoncorretto:21

COPY microservices/media/build/libs/honeycomb-monolith-media-service.jar /app/media-service.jar

WORKDIR /app
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/media-service.jar"]

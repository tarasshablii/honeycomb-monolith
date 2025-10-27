FROM amazoncorretto:21

COPY monolith/build/libs/honeycomb-monolith-monolith.jar /app/opora-backend.jar

WORKDIR /app
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/opora-backend.jar"]

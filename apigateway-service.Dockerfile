FROM amazoncorretto:17.0.9

COPY microservices/api-gateway/build/libs/honeycomb-monolith-apigateway-service.jar /app/apigateway-service.jar

WORKDIR /app
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/apigateway-service.jar"]

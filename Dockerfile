FROM openjdk:11-jre

EXPOSE 8080

WORKDIR /app

COPY ./build/libs/ols-api-0.0.1.jar ./ols-api-0.0.1.jar

CMD ["java", "-jar", "ols-api-0.0.1.jar"]
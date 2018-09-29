FROM openjdk:8-jre-alpine

LABEL maintainer="MeLi"
LABEL version="1.0"

ENV DB_NAME="mutants"
ENV DB_HOSTNAME="localhost"
ENV DB_PORT="5432"
ENV DB_USERNAME="mutants-app"
ENV DB_PWD="mutants-app"

EXPOSE 8080
EXPOSE 8181

COPY maven/*.jar mutants.jar

ENTRYPOINT [ "java" , "-Xms128m",  "-Xmx400m", "-Djdbc.url=jdbc:postgresql://${DB_HOSTNAME}:${DB_PORT}/${DB_NAME}", "-Djdbc.password=${DB_PWD}", "-Djdbc.username=${DB_USERNAME}", "-jar" , "mutants.jar"]

# FROM openjdk:8-jdk-alpine
# WORKDIR /.
# VOLUME /docker
# RUN mkdir -p /app
# COPY ./service.war /app/service.war
# ENTRYPOINT ["java", "-jar", "/app/service.war"]
FROM openjdk:8-jdk-alpine
WORKDIR /.
VOLUME /tmp
RUN mkdir -p /app
COPY ./build/libs /app/
ENTRYPOINT ["java", "-jar", "/app/service.war"]

#FROM openjdk:8-jdk-alpine
#WORKDIR /.
#VOLUME /docker
#RUN mkdir -p /app
#COPY ./src /app/
#ENTRYPOINT ["javac", "/app/test/java/com/keycloakapp/demo/DemoApplicationTests.java"]

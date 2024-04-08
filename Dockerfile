FROM openjdk:17-jdk-alpine
MAINTAINER baeldung.com
COPY target/demo-0.0.1-SNAPSHOT.jar selfcloud-demo-1.0.0.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/selfcloud-demo-1.0.0.jar"]
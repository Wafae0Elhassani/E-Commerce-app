FROM openjdk:17-jdk-alpine
WORKDIR /userApp
COPY target/*.jar userApp.jar
ENTRYPOINT ["java", "-jar", "/userApp/userApp.jar"]

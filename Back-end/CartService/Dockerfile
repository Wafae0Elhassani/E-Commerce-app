FROM openjdk:17-jdk-alpine
WORKDIR /cartApp
COPY target/*.jar CartApp.jar
ENTRYPOINT ["java", "-jar", "/cartApp/CartApp.jar"]

FROM openjdk:11
EXPOSE 8080
ADD target/management.jar management.jar
ENTRYPOINT ["java", "-jar", "/management.jar"]

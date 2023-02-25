FROM openjdk:17-oracle
EXPOSE 8080
ADD target/management.jar management.jar
ENTRYPOINT ["java", "-jar", "/management.jar"]

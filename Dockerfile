FROM openjdk:17-oracle
EXPOSE 8080
ADD target/management.jar management.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "/management.jar"]
EXPOSE 8080

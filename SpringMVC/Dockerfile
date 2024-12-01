FROM alpine/java:21.0.3

WORKDIR /app
COPY target/springcourse-0.0.1-SNAPSHOT.jar /app/myspring.jar
EXPOSE 8080/tcp
CMD ["java", "-jar", "myspring.jar"]
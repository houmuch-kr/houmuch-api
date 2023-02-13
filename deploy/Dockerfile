FROM openjdk:17-jdk-slim-buster
VOLUME /tmp
COPY build/libs/api-1.0.0.jar app.jar
ENTRYPOINT ["java", "-Duser.timezone='Asia/Seoul'", "-Dspring.profiles.active=prod", "-Xmx2048m", "-jar","/app.jar"]

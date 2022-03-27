FROM openjdk:11
ADD build/libs/springboot-jwt-tutorial-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT ["java","-jar","/app.jar"]
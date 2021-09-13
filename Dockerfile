FROM openjdk:8-jre-alpine

WORKDIR /

COPY target/builders-0.0.1-SNAPSHOT.jar /

ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005", "-jar", "builders-0.0.1-SNAPSHOT.jar"]

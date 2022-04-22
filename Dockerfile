FROM adoptopenjdk/openjdk11:latest
MAINTAINER Kon Bi
ADD ./target/incident.detection-1.0.0.jar  app.jar
VOLUME ["/store"]
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
EXPOSE 7040
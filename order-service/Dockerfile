FROM openjdk:11-jre-slim
VOLUME /tmp
WORKDIR /service
COPY target/*.jar service.jar
ENTRYPOINT exec java -server \
-noverify \
-Dspring.jmx.enabled=false \
$JAVA_OPTS \
-jar service.jar

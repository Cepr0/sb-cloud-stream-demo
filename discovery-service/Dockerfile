FROM openjdk:8-jre-slim
VOLUME /tmp
RUN apt-get update
RUN apt-get -qq -y install curl
WORKDIR /service
COPY target/*.jar service.jar
ENTRYPOINT exec java -server \
-noverify \
-XX:TieredStopAtLevel=1 \
-Dspring.jmx.enabled=false \
$JAVA_OPTS \
-jar service.jar

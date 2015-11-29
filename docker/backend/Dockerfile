# Frontend docker container image
FROM java:latest
MAINTAINER Daniel Borlay <borlay.daniel@gmail.com>

RUN apt-get -y update
RUN apt-get -y install groovy libspring-*

RUN unlink /usr/bin/java && ln -s /usr/lib/jvm/java-1.8.0-openjdk-amd64/jre/bin/java /usr/bin/java
COPY backend /backend
RUN cd /backend; ./gradlew build
ENTRYPOINT /usr/bin/java -jar /backend/build/libs/stump-0.0.1-SNAPSHOT.jar

EXPOSE 8080

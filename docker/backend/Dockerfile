# Frontend docker container image
FROM java:latest
MAINTAINER Daniel Borlay <borlay.daniel@gmail.com>

RUN apt-get -y update
RUN apt-get -y install groovy libspring-*

COPY backend /backend
RUN cd /backend; ./gradlew build
RUN cd /backend; java -jar build/lib/stump-0.0.1-SNAPSHOT.jar

EXPOSE 8080

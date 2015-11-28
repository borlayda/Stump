# Frontend docker container image
FROM java:latest
MAINTAINER Daniel Borlay <borlay.daniel@gmail.com>

RUN apt-get -y update
RUN apt-get -y install gradle groovy libspring-*

COPY backend /backend
RUN cd /backend; gradle build
RUN cd /backend; java -jar build/lib/*.jar

EXPOSE 8080

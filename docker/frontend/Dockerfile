# Frontend docker container image
FROM ubuntu
MAINTAINER Daniel Borlay <borlay.daniel@gmail.com>

RUN apt-get -y update
RUN apt-get -y install apache2 wget 

COPY frontend /tmp/frontend
RUN cp -R /tmp/frontend/html/* /var/www/html
COPY init /init
RUN chmod +x /init

ENTRYPOINT /init

EXPOSE 80

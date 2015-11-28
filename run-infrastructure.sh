#!/bin/bash

# Backend

docker run -d -t backend -h backend /bin/bash java -jar /backend/build/lib/*.jar

# Frontend

docker run -d -t frontend -h frontend /etc/init.d/apache2 restart

# Proxy

docker run -d -t proxy -h proxy /bin/bash 

#!/bin/bash

# Backend

docker run -d --hostname backend -t backend /bin/bash

# Frontend

docker run -d --hostname frontend -t frontend /bin/bash

# Proxy

docker run -d --hostname proxy -t proxy /bin/bash 

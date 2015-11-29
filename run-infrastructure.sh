#!/bin/bash

# Backend

docker run -d --hostname backend -t backend

# Frontend

docker run -d --hostname frontend -t frontend

# Proxy

docker run -d --hostname proxy -t proxy /bin/bash 

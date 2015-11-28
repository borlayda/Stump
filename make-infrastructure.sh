#!/bin/bash

cp -R frontend docker/frontend
cp -R backend docker/backend

cd docker
echo "Building Frontend ..."
docker build -t frontend frontend
echo "Building Backend ..."
docker build -t backend backend
echo "Building Proxy ..."
docker build -t proxy proxy

echo "Infrastructure prepared, you can run the application!"

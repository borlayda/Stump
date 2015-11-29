#!/bin/bash

echo "Collecting resources ..."
cp -R frontend docker/frontend
cp -R backend docker/backend

cd docker
echo "Building Frontend ..."
docker build -t frontend frontend
echo "Building Backend ..."
docker build -t backend backend
echo "Building Proxy ..."
docker build -t proxy proxy
echo "Building MongoDB ..."
docker build -t mongodb mongodb

echo "Cleaning up ..."
rm -rf frontend/frontend
rm -rf backend/backend

echo "Infrastructure prepared, you can run the application!"

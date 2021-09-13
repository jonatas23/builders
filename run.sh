#!/bin/bash
echo "Docker Desafio Builders..."
mvn clean install

echo "Create data..."
mkdir -p ./data

echo "Docker-compose..."
docker-compose up --build --force-recreate -d

#!/bin/bash

APP_NAME="shopping-list-manager"
JAR_NAME="shopping-list-manager-0.0.1-SNAPSHOT.jar"
IMAGE_NAME="shopping-list-app"
CONTAINER_NAME="shopping-list-container"

# 1. Maven build (skipping tests)
echo "Building with Maven..."
mvn clean package -DskipTests

# Check if JAR exists
if [ ! -f target/$JAR_NAME ]; then
  echo "Build failed: JAR not found!"
  exit 1
fi

# 2. Docker image build
echo "Building Docker image..."
docker build -t $IMAGE_NAME .

# 3. If there is already a container stop and delete
if [ "$(docker ps -aq -f name=$CONTAINER_NAME)" ]; then
  echo "Stopping and removing existing container..."
  docker stop $CONTAINER_NAME
  docker rm $CONTAINER_NAME
fi

# 4. Start new container
echo "Starting new container..."
docker run -d -p 8080:8080 --name $CONTAINER_NAME $IMAGE_NAME

# 5. Show logs
echo "Showing container logs..."
docker logs -f $CONTAINER_NAME

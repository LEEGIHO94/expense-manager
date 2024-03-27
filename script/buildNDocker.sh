#!/usr/bin/env bash

./gradlew spotlessApply
./gradlew clean build

if [ $? -eq 0 ]; then
    docker-compose up --build
else
    echo "Build failed!! Aborting docker-compose up --build."
    echo "check your resource"
fi


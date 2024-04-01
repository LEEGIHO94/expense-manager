#!/usr/bin/env bash

./gradlew spotlessApply

./gradlew clean build

echo 'build success'

nohup java -jar ./build/libs/expense-manager-0.0.1-SNAPSHOT.jar &


#Docker를 활용하지 않고 바로 ServerFmf Emldnsms rjt
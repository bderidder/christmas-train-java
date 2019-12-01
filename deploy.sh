#!/usr/bin/env bash

cd src/main/angular || exit

ng build --configuration=production

cd ../../..

./mvnw package -DskipTests

scp target/train.christmas-0.0.1-SNAPSHOT.jar pi@192.168.1.15:.

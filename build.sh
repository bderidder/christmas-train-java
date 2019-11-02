#!/usr/bin/env bash

cd src/main/angular

ng build --configuration=production

cd ../../../

./mvnw clean install -DskipTests
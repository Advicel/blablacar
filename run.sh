#!/bin/sh
cd backend
mvn clean package
STATUS=$?
if [ $STATUS -eq 0 ]; then
java -jar target/blacar-1.0-SNAPSHOT.jar
fi

#!/usr/bin/env bash

echo "Starting TableBooking app"

PROJECT_ROOT="C:\\WORK\\Mixed\\TableBooking"

java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 -Dspring.config.location=file:$PROJECT_ROOT\\tb-core\\src\\main\\resources\\ -jar tb-core/build/libs/tb-core.jar
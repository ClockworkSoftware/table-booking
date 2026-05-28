#!/usr/bin/env bash

echo "Starting TableBooking app"

PROJECT_ROOT="C:\\WORK\\Mixed\\table-booking"

java -Dspring.config.location=file:$PROJECT_ROOT\\tb-core\\src\\main\\resources\\ -jar tb-core/build/libs/tb-core.jar

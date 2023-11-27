#!/bin/bash
nohup mvn spring-boot:run -Dspring-boot.run.profiles=prod > log.txt 2>&1 &
echo $! > ./pid.file
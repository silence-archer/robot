#!/bin/bash
echo "Stopping SpringBoot Application"
pid=`ps -ef | grep robot-web-0.0.1-SNAPSHOT.jar | grep -v grep | awk '{print $2}'`
if [ -n "$pid" ]
then
   kill -9 $pid
fi
echo "Starting SpringBoot Application"
nohup java -jar robot-web/target/robot-web-0.0.1-SNAPSHOT.jar --spring.profiles.active=h2 >/dev/null 2>&1 &
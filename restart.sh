#!/bin/bash
echo "Stopping SpringBoot Application"
pid=`ps -ef | grep robot-web-0.0.1-SNAPSHOT.jar | grep -v grep | awk '{print $2}'`
if [ -n "$pid" ]
then
   kill -9 $pid
fi
echo "Starting SpringBoot Application"
export BUILD_ID=DONTKILLME
nohup java -jar robot-web/target/robot-web-0.0.1-SNAPSHOT.jar --server.port=80 --spring.profiles.active=h2 --logging.path=/var/lib/jenkins/robot-logs >/dev/null 2>&1 &
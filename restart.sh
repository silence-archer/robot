#!/bin/bash
SERVER_PORT=$1
ACTIVE_PROFILE=$2
SQLITE_URL=$3
echo "port: $1"
echo "spring.profiles.active: $2"
echo "Stopping SpringBoot Application"
pid=`ps -ef | grep robot-web-0.0.1-SNAPSHOT.jar | grep -v grep | awk '{print $2}'`
if [ -n "$pid" ]
then
   kill -9 $pid
fi
echo "Starting SpringBoot Application"
if [ $ACTIVE_PROFILE == "sqlite" ]
then
  echo "sqlite.url: $3"
  nohup java -jar robot-web/target/robot-web-0.0.1-SNAPSHOT.jar --spring.profiles.active=$ACTIVE_PROFILE --sqlite.url=$SQLITE_URL --server.port=$SERVER_PORT --logging.path=/var/lib/jenkins/robot-logs >/dev/null 2>&1 &
else
  nohup java -jar robot-web/target/robot-web-0.0.1-SNAPSHOT.jar --spring.profiles.active=$ACTIVE_PROFILE --server.port=$SERVER_PORT --logging.path=/var/lib/jenkins/robot-logs >/dev/null 2>&1 &
fi
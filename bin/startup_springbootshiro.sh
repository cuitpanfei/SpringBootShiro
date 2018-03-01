#!/bin/sh
. /etc/profile
export LANG=zh_CN.UTF-8

pid=`ps aux | grep 'platform-system-provider' | grep -v grep | awk '{print $2}'`
if [ "${pid}" != "" ]
then
  `kill -9 ${pid}`
fi

java -jar ../target/springbootshiro-pf-0.0.1.jar &
#!/bin/sh

DIRS=$(dirname "$PWD")
JDK_VERSION=jdk-16.0.2
#判断是否自定义jdk
JAVA_HOME="/usr/local/${JDK_VERSION}"
if [ -e "${DIRS}/${JDK_VERSION}" ]; then
      JAVA_HOME="${DIRS}/${JDK_VERSION}"
fi

APP_MAIN="net.tiklab.pipeline.PipelineDistributionApplication"

PID=0
getPID(){
    javaps=`$JAVA_HOME/bin/jps -l | grep $APP_MAIN`
    if [ -n "$javaps" ]; then
        PID=`echo $javaps | awk '{print $1}'`
    else
        PID=0
    fi
}

status(){
    getPID
    echo "================================================================================================================"
    if [ $PID -ne 0 ]; then
        echo "$APP_MAIN is running(PID=$PID)"
        echo "================================================================================================================"
    else
        echo "$APP_MAIN is not running"
        echo "================================================================================================================"
    fi
}

status
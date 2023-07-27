#!/bin/sh

DIRS=$(dirname "$PWD")
JDK_VERSION=jdk-16.0.2
if [ -d "${DIRS}/embbed/${JDK_VERSION}" ]; then
    echo "使用内嵌jdk"
    JAVA_HOME="${DIRS}/embbed/${JDK_VERSION}"
else
    JAVA_HOME="/usr/local/jdk-17.0.7"
fi

APP_MAIN="io.tiklab.matflow.starter.MatFlowApplication"

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
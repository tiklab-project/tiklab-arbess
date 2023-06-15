#!/bin/sh

DIRS=$(dirname "$PWD")
JAVA_HOME="/usr/local/jdk-17.0.7"

JDK_VERSION=jdk-16.0.2

#判断是否自定义jdk
JAVA_HOME="/usr/local/${JDK_VERSION}"
if [ -e "${DIRS}/embbed/${JDK_VERSION}" ]; then
      JAVA_HOME="${DIRS}/embbed/${JDK_VERSION}"
fi

#APP_MAIN=${application.main.class}
APP_MAIN="io.tiklab.matflow.MatFlowApplication"

PID=0
getPID(){
    javaps=`$JAVA_HOME/bin/jps -l | grep $APP_MAIN`
    if [ -n "$javaps" ]; then
        PID=`echo $javaps | awk '{print $1}'`
    else
        PID=0
    fi
}

shutdown(){
    getPID
    echo "================================================================================================================"
    if [ $PID -ne 0 ]; then
        echo -n "stopping $APP_MAIN(PID=$PID)..."
        kill -9 $PID
        if [ $? -eq 0 ]; then
            echo "[success]"
            echo "================================================================================================================"
        else
            echo "[failed]"
            echo "================================================================================================================"
        fi

        getPID

        if [ $PID -ne 0 ]; then
            shutdown
        fi
    else
        echo "$APP_MAIN is not running"
        echo "================================================================================================================"
    fi
}

shutdown

PORT=${1}

stopMysql(){
  echo "MYSQL PORT ${PORT}"
  TMP_FILE=mysql.tmp
  netstat -tlnp | tail -n +3 | awk '{print $4"|"$7}' | grep '[0-9]\+|[0-9]\+' -o >$TMP_FILE
  for line in `cat $TMP_FILE`; do
      port=`echo $line | cut -d '|' -f 1`
      pid=`echo $line | cut -d '|' -f 2`
      if [ $port -eq ${PORT} ]; then
          echo "MYSQL PID $pid"
          kill -9 ${pid}
      fi
  done
  rm -rf $TMP_FILE
}

if [ "X${PORT}" = "X" ]; then
  echo "跳过关闭MYSQL"
else
  stopMysql
fi














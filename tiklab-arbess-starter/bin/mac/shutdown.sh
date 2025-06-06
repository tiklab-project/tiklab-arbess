#!/bin/sh

DIRS=$(dirname "$PWD")

APP_MAIN="io.tiklab.arbess.starter.ArbessApplication"

#jdk
JDK_VERSION=jdk-16.0.2
YAML=${DIRS}/conf/application.yaml
valid_jdk(){
  if [ -d "${DIRS}/embbed/${JDK_VERSION}" ]; then
      echo "user embbed jdk ${JAVA_HOME}"
      JAVA_HOME="${DIRS}/embbed/${JDK_VERSION}"
  else
      echo "Unable to find embbed jdk!"
      exit 1;
  fi
}

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
    kill_pgsql
    getPID
    echo "================================================================================================================"
    if [ $PID -ne 0 ]; then
        echo "stopping $APP_MAIN(PID=$PID)..."
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

db_port="0"
pg_port(){
    db_port=$(awk -F": *" '/^postgresql:/ {
        inf=1
        next
    }
    inf && /^  db:/ {
        db=1
        next
    }
    db && /^    port:/ {
        print $2
        exit
    }' "${YAML}")

   echo "PostgreSQL start Port: ${db_port}"
}

db_enable="false"
pg_enable(){
    db_enable=$(awk -F": *" '/^postgresql:/ {
        inf=1
        next
    }
    inf && /^  embbed:/ {
        embbed=1
        next
    }
    embbed && /^    enable:/ {
        print $2
        exit
    }' "${YAML}")

   echo "PostgreSQL embbed enable: ${db_enable}"
}

kill_pgsql(){
  pg_enable
  if [ "${db_enable}" = "true" ]; then
       pg_port
        if [ "${db_port}" = "0" ]; then
            echo "find pgsql port error!"
            exit 1
        fi

        result=$(lsof -i :${db_port} -sTCP:LISTEN)
        # shellcheck disable=SC2039
        if [[ -n "$result" ]]; then
            echo "pgsql port ${db_port} be occupied！"

            pids=$(echo "$result" | awk 'NR==2{print $2}')
            echo "Killing process $pids"
            # 杀死占用端口的进程
            kill -9 $pids
        fi
  fi


}

valid_jdk
shutdown

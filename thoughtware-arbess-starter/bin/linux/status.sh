#!/bin/sh

DIRS=$(dirname "$PWD")
APP_MAIN="io.thoughtware.arbess.starter.ArbessApplication"

JDK_VERSION=jdk-16.0.2
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

status(){
    getPID
    echo "================================================================================================================"
    if [ $PID -ne 0 ]; then
        echo "$APP_MAIN is running(PID=$PID)"
        pgsql
        echo "================================================================================================================"
    else
        echo "$APP_MAIN is not running"
        echo "================================================================================================================"
    fi
}

db_port=0
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

pgsql(){
  pg_enable
  if [ "${db_enable}" = "true" ]; then
       pg_port
        if [ ${db_port} -ne 0 ]; then
            echo "find pgsql port error "
            exit 1
        fi

        result=$(netstat -tuln | grep ":${db_port}")
        # shellcheck disable=SC2039
        if [[ -n "$result" ]]; then
            pids=$(echo "$result" | awk '{print $7}' | awk -F'/' '{print $1}')
            echo "pgsql is running(PID=$pids)"
        fi
  fi


}

status
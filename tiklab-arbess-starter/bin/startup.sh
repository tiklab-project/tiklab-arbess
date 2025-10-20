#!/bin/sh

DIRS=$(dirname "$PWD")

APP_MAIN="io.tiklab.arbess.starter.ArbessApplication"

YAML=${DIRS}/conf/application.yaml
create_home(){

  data_home=$(awk -F': ' '/DATA_HOME:/ {print $2}' "${YAML}")
  echo "DATA_HOME: ${data_home}"

  # 创建目录及其上级目录(如果不存在)
  mkdir -p "${data_home}"

  # 检查目录是否创建成功
  if [ -d "${data_home}" ]; then
    echo "data ${data_home} initialized successfully！"
  else
    echo "================================================================================================================"
    echo "data ${data_home} initialized Failed!"
    echo "请更改文件${YAML}中的DATA_HOME字段，配置应用可以访问的地址,请不要配置与程序相同的目录！"
    echo "${APP_MAIN} start [failed]"
    echo "================================================================================================================"
    exit 1
  fi

}

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

PGSQL_VERSION=pgsql-10.23
valid_postgresql(){
  if [ -d "${DIRS}/embbed/${PGSQL_VERSION}/bin" ]; then
      echo "user embbed postgresql exist"
      rm -rf ${DIRS}/embbed/${PGSQL_VERSION}/${PGSQL_VERSION}.tar.gz
  else
      echo "unzip postgresql file ....."
      tar -xzf "${DIRS}/embbed/${PGSQL_VERSION}/${PGSQL_VERSION}.tar.gz" -C "${DIRS}/embbed"
      echo "unzip postgresql success!"
      rm -rf ${DIRS}/embbed/${PGSQL_VERSION}/${PGSQL_VERSION}.tar.gz
  fi
}

APP_HOME=${DIRS}
export APP_HOME

APPLY=arbess-ce

enableApply(){

      APPLYDIR="$PWD"

      serverName=enable-${APPLY}.service

      applyserver=/etc/systemd/system/${serverName}

      if [ ! -e "${applyserver}" ]; then
cat << EOF >  ${applyserver}
[Unit]
Description=Start Tiklab Apply
After=network.target remote-fs.target nss-lookup.target

[Service]
EOF

echo Environment=\"DIR=${APPLYDIR}\" >> ${applyserver}

cat << EOF >> ${applyserver}
ExecStart=/bin/bash -c 'cd "\$DIR"; sh startup.sh'
Type=forking

[Install]
WantedBy=multi-user.target
EOF

  touch ${applyserver}
  chmod 644 ${applyserver}
  systemctl enable ${serverName}

  else
cat << EOF >  ${applyserver}
[Unit]
Description=Start Tiklab Apply
After=network.target remote-fs.target nss-lookup.target

[Service]
EOF

echo Environment=\"DIR=${APPLYDIR}\" >> ${applyserver}
cat << EOF >> ${applyserver}
ExecStart=/bin/bash -c 'cd "\$DIR"; sh startup.sh'
Type=forking

[Install]
WantedBy=multi-user.target
EOF
fi

}

enableApply

JAVA_OPTS=""
add_javaOpts(){
  APP_CONFIG=${DIRS}/conf/application.yaml
  APP_LOG=${DIRS}/logs

  JAVA_OPTS="$JAVA_OPTS -server -Xms512m -Xmx512m -Xmn128m -XX:ParallelGCThreads=20 -XX:+UseParallelGC -XX:MaxGCPauseMillis=850 -Xloggc:$APP_LOG/gc.log -Dfile.encoding=UTF-8"
  JAVA_OPTS="$JAVA_OPTS -DlogPath=$APP_LOG -Duser.timezone=GMT+08"
  JAVA_OPTS="$JAVA_OPTS -Dconf.config=file:${APP_CONFIG}"
  JAVA_OPTS="$JAVA_OPTS --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.sql/java.sql=ALL-UNNAMED  -classpath"
}

CLASSPATH=${DIRS}/conf
add_classpath(){
  #加载私有依赖
  for appJar in "${DIRS}"/lib/*.jar;
  do
     CLASSPATH="$CLASSPATH":"$appJar"
  done
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

PID=0
getPID(){
    javaps=`$JAVA_HOME/bin/jps -l | grep $APP_MAIN`
    if [ -n "$javaps" ]; then
        PID=`echo $javaps | awk '{print $1}'`
    else
        PID=0
    fi
}

startup(){
    getPID
    echo "================================================================================================================"
    if [ $PID -ne 0 ]; then
        echo "$APP_MAIN already started(PID=$PID)"
        echo "================================================================================================================"
    else
        echo "starting $APP_MAIN"
        if [ ! -d "$APP_LOG" ]; then
            mkdir "$APP_LOG"
        fi

#        nohup $JAVA_HOME/bin/java $JAVA_OPTS $CLASSPATH $APP_MAIN  > info.log 2>&1 &
        nohup $JAVA_HOME/bin/java $JAVA_OPTS $CLASSPATH $APP_MAIN > /dev/null 2>&1 &

        for i in $(seq 5); do
            printf "."
            sleep 0.8
        done

        getPID

        if [ $PID -ne 0 ]; then
            echo "(PID=$PID)...[success]"
            output
        else
            echo "[failed]"
            echo "================================================================================================================"
        fi
    fi
}

output(){

  dcs_port=$(awk -F": *" '/^dcs:/ {
      inf=1
      next
  }
  inf && /^  server:/ {
      server=1
      next
  }
  server && /^    port:/ {
      print $2
      exit
  }' "${YAML}")
  echo "DCS Server Port: ${dcs_port}"

  server_port=$(awk -F": *" '/^server:/ {
      inf=1
      next
  }
  inf && /^  port:/ {
      print $2
      exit
  }' "${YAML}")
  echo "Apply Server Port: ${server_port}"

 echo "PostgreSQL start Port: ${db_port}"


  ip_address=$(ifconfig | grep -Eo 'inet (addr:)?([0-9]*\.){3}[0-9]*' | grep -Eo '([0-9]*\.){3}[0-9]*' | grep -v '127.0.0.1' | head -n 1)
  echo "====================================点击以下连接即可访问================================================="
  echo "http://${ip_address}:${server_port}"
  echo "================================================================================================================"

}

start(){
  pg_port
  create_home
  valid_jdk
  valid_postgresql
  add_javaOpts
  add_classpath

  find ${DIRS}/ -name '*.sh' | xargs dos2unix;
  startup
}

start
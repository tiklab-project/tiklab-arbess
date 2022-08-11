#!/bin/sh
#-------------------------------------------------------------------------------------------------------------

#应用名称
NAME=tiklab-matflow
#数据库名称
MYSQL_NAME=tiklab_matflow
#应用版本
VERSION=1.0.0-SNAPSHOT

#应用安装地址
APP_DIR=/usr/local/apps
#数据库位置
MYSQL_DIR=/opt
#mysql版本
MYSQL_VERSION=mysql-8.0.28
#jdk版本
JDK_VERSION=jdk-16.0.2

mkdir -p /var/lib/mysql-files
echo "查询数据库状态"
if [ ! -d "${MYSQL_DIR}/${MYSQL_VERSION}/" ]; then
  tar -xvf ${APP_DIR}/${NAME}-${VERSION}/${MYSQL_VERSION}/${MYSQL_VERSION}.tar -C ${MYSQL_DIR}
  cd ${MYSQL_DIR}/${MYSQL_VERSION}/lib && nohup ./mysqld --defaults-file=${MYSQL_DIR}/${MYSQL_VERSION}/config/my.cnf > ${MYSQL_DIR}/${MYSQL_VERSION}/log/log.txt 2>&1 &
  echo "===========================================启动成功============================================================"
  mv ${APP_DIR}/${NAME}-${VERSION}/${MYSQL_VERSION}/mysqld/* /bin
  echo "初始化数据库"
   for i in $(seq 5)
      do
        sleep 0.6
        echo -e  ".\c"
      done
  ln -s ${MYSQL_DIR}/${MYSQL_VERSION}/log/mysql.sock /var/lib/mysql/mysql.sock
  mysql -uroot -pdarth2020 -e "create database ${MYSQL_NAME}"
  echo "数据库初始化完成"
else
  echo "===========================================同步数据============================================================"
  cd ${MYSQL_DIR}/${MYSQL_VERSION}/lib && nohup ./mysqld --defaults-file=${MYSQL_DIR}/${MYSQL_VERSION}/config/my.cnf > ${MYSQL_DIR}/${MYSQL_VERSION}/log/log.txt 2>&1 &
  echo "===========================================启动成功============================================================"
  mv ${APP_DIR}/${NAME}-${VERSION}/${MYSQL_VERSION}/mysqld/* /bin
fi

#-------------------------------------------------------------------------------------------------------------
#       系统运行参数
#-------------------------------------------------------------------------------------------------------------

JAVA_HOME=$APP_DIR/$NAME-$VERSION/$JDK_VERSION

cd ${APP_DIR}/${NAME}-${VERSION}/bin

APP_MAIN="com.tiklab.matflow.MatFlowApplication"

DIR=$(cd "$(dirname "$0")"; pwd)
APP_HOME=${DIR}/..
APP_CONFIG=${APP_HOME}/conf/application-${env}.properties
APP_LOG=${APP_HOME}/logs

export APP_HOME
#export app.home=$APP_HOME

JAVA_OPTS="$JAVA_OPTS -server -Xms512m -Xmx512m -Xmn128m -XX:ParallelGCThreads=20 -XX:+UseParallelGC -XX:MaxGCPauseMillis=850 -Xloggc:$APP_LOG/gc.log -Dfile.encoding=UTF-8"
JAVA_OPTS="$JAVA_OPTS -DlogPath=$APP_LOG"
JAVA_OPTS="$JAVA_OPTS -Dconf.config=file:${APP_CONFIG}"
JAVA_OPTS="$JAVA_OPTS --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.sql/java.sql=ALL-UNNAMED  -classpath"

CLASSPATH=${APP_HOME}/conf
for appJar in "$APP_HOME"/lib/*.jar;
do
   CLASSPATH="$CLASSPATH":"$appJar"
done

echo "JAVA_HOME="$JAVA_HOME
echo "JAVA_OPTS="$JAVA_OPTS
echo "CLASSPATH="$CLASSPATH
echo "APP_HOME="$APP_HOME
echo "APP_MAIN="$APP_MAIN

#-------------------------------------------------------------------------------------------------------------
#   程序开始
#-------------------------------------------------------------------------------------------------------------

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
        echo -n "starting $APP_MAIN"
        if [ ! -d "$APP_LOG" ]; then
            mkdir "$APP_LOG"
        fi

        nohup $JAVA_HOME/bin/java $JAVA_OPTS $CLASSPATH $APP_MAIN  > ${APP_DIR}/${NAME}-${VERSION}/bin/info.log 2>&1 &

        for i in $(seq 5)
        do
          sleep 0.8
          echo -e  ".\c"
        done

        getPID

        if [ $PID -ne 0 ]; then
            echo "(PID=$PID)...[success]"
            echo "================================================================================================================"
        else
            echo "[failed]"
            echo "================================================================================================================"
        fi
    fi
}

startup
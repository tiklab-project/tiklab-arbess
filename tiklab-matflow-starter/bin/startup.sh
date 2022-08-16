#!/bin/sh
#-------------------------------------------------------------------------------------------------------------
#该脚本的使用方式为-->[sh startup.sh]
#该脚本可在服务器上的任意目录下执行,不会影响到日志的输出位置等
#-------------------------------------------------------------------------------------------------------------
#if [ ! -n "$JAVA_HOME" ]; then
#    export JAVA_HOME="/usr/local/jdk-16.0.2"
#fi
#!/bin/sh
#-------------------------------------------------------------------------------------------------------------

DIR=$(dirname "$PWD")
#数据库名称
MYSQL_NAME=tiklab_matflow
#mysql版本
MYSQL_VERSION=mysql-8.0.28
#数据库数据文件位置
MYSQL_DIR="/opt/tiklab/mysql/"
#数据库程序位置
MYSQL_HOME=${DIR}/${MYSQL_VERSION}

mkdir -p /var/lib/mysql-files

if [ ! -d ${MYSQL_DIR} ]; then
  echo "初次安装，加载数据库文件"
  mkdir -p /opt/tiklab/mysql
  mkdir -p /opt/tiklab/app

  mv ${MYSQL_HOME}/tiklab/mysql/* /opt/tiklab/mysql
  mv ${MYSQL_HOME}/tiklab/app/* /opt/tiklab/app

  echo "启动数据库"
   for i in $(seq 2)
      do
        sleep 0.6
        echo -e  ".\c"
      done

  cd ${MYSQL_HOME}/bin && nohup ./mysqld --defaults-file=${MYSQL_HOME}/config/my.cnf --datadir=/opt/tiklab/mysql --socket=${MYSQL_HOME}/log/mysql.sock --log-error=${MYSQL_HOME}/config/mysqld.log --pid-file=${MYSQL_HOME}/config/mysqld.pid --lc_messages_dir=${MYSQL_HOME}/config > ${MYSQL_HOME}/log/log.txt 2>&1 &
  echo "===========================================数据库启动成功============================================================"
  mv ${MYSQL_HOME}/mysqld/* /bin

  echo "初始化数据库"
   for i in $(seq 5)
      do
        sleep 0.6
        echo -e  ".\c"
      done

  rm -rf /var/lib/mysql/mysql.sock
  ln -s ${MYSQL_HOME}/log/mysql.sock /var/lib/mysql/mysql.sock

  mysql -uroot -pdarth2020 -e "create database ${MYSQL_NAME}"
  echo "数据库初始化完成"
  rm -rf /opt/tiklab/app
else
  echo "启动数据库"
     for i in $(seq 2)
        do
          sleep 0.6
          echo -e  ".\c"
        done
  echo "===========================================同步数据库数据============================================================"
  cd ${MYSQL_HOME}/bin && nohup ./mysqld --defaults-file=${MYSQL_HOME}/config/my.cnf --datadir=/opt/tiklab/mysql --socket=${MYSQL_HOME}/log/mysql.sock --log-error=${MYSQL_HOME}/config/mysqld.log --pid-file=${MYSQL_HOME}/config/mysqld.pid --lc_messages_dir=${MYSQL_HOME}/config > ${MYSQL_HOME}/log/log.txt 2>&1 &
  echo "===========================================数据库启动成功============================================================"

  mv ${DIR}/${MYSQL_HOME}/mysqld/* /bin
  rm -rf /opt/tiklab/app
fi

echo "启动应用程序"

JDK_HOME=$(dirname "$PWD")

JAVA_HOME=${JDK_HOME}/jdk-16.0.2
#-------------------------------------------------------------------------------------------------------------
#       系统运行参数
#-------------------------------------------------------------------------------------------------------------
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

        nohup $JAVA_HOME/bin/java $JAVA_OPTS $CLASSPATH $APP_MAIN  > info.log 2>&1 &

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
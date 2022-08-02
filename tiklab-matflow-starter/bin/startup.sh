#!/bin/sh
#-------------------------------------------------------------------------------------------------------------
#该脚本的使用方式为-->[sh startup.sh]
#该脚本可在服务器上的任意目录下执行,不会影响到日志的输出位置等
#-------------------------------------------------------------------------------------------------------------
#if [ ! -n "$JAVA_HOME" ]; then
#    export JAVA_HOME="/usr/local/jdk-16.0.2"
#fi

dir=$(dirname "$PWD")

echo "文件根目录: " $dir

mysql=$dir/mysql-8.0.28

echo "========================================================================="
cd $dir && find /opt -name '*.sh' | xargs dos2unix;
cd $dir/bin && sh startup.sh

echo "========================================================================="
echo "数据库启动中"
cd /$mysql/lib && nohup ./mysqld --defaults-file=$mysql/config/my.cnf > $mysql/log/log.txt 2>&1 &
echo "数据库启动成功"
echo "账号：root"
echo "密码：darth2020"


JAVA_HOME=$dir/jdk-16.0.2
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

        nohup $JAVA_HOME/bin/java $JAVA_OPTS $CLASSPATH $APP_MAIN &

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
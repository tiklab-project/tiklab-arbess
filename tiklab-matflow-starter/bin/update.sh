#!/bin/sh

DIR=$(dirname "$PWD")

APP_NAME=matflow

DIR_FILE="http://file.dev.tiklab.net/update/"

JAR_DIR=`find ${DIR}/lib -name "tiklab-upgrade-starter*.jar" -print`

JAVA_HOME=${DIR}/embbed/jdk-16.0.2

while getopts ":v:" opt
do
    case $opt in
        v)
VERSION=$OPTARG
        ;;
        ?)
        echo "参数${OPTARG}未知。"
        exit 1;;
    esac
done

if [ -d ${JAR_DIR} ] ; then
  echo "升级程序不存在。"
  exit 1
fi

cd ${DIR}/bin && sh shutdown.sh

cd ${JAVA_HOME}/bin && ./java -jar -Dfiledir="${DIR_FILE}" -Ddirname="${APP_NAME}" -Ddir="${DIR}" -Ddirversion="${VERSION}" "${JAR_DIR}"

for line in `cat ${DIR}/status`
do
    echo $line
done

if [ "${line}" == "UPDATE：SUCCESS" ]; then
   cd ${DIR}/bin && sh startup.sh
  else
  exit 1
fi

rm -rf ${DIR}/status

echo "系统更新完成。"






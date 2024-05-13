#!/bin/sh

DIR=$(dirname "$PWD")

JAR_DIR=`find ${DIR}/lib -name "tiklab-upgrade-starter*.jar" -print`

JAVA_HOME=${DIR}/embbed/jdk-16.0.2

if [ -d ${JAR_DIR} ] ; then
  echo "The upgrade program does not exist。"
  exit 1
fi

echo "The system starts upgrading and stops running!"

cd ${DIR}/bin && sh shutdown.sh

cd ${JAVA_HOME}/bin && ./java -jar -Dfiledir="${DIR}" "${JAR_DIR}"



#Remote_File_Name=`find ${DIR} -name "tiklab-*.tar.gz" -print`

#Remote_File_Name=$(cat ${DIR}/fileName.txt)








































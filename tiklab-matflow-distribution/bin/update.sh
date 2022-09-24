DIR=$(dirname "$PWD")

APP_NAME=matflow

IP=172.12.1.30

JAR_DIR=`find ${DIR}/lib -name "tiklab-update-starter*.jar" -print`

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

if [ ! -d ${JAR_DIR} ] ; then
  echo ${JAR_DIR}
else
  echo "无法获取到升级文件"
  exit 1
fi

if [ ! -d ${VERSION} ] ; then
  echo ${VERSION}
  echo "系统开始升级。"
else
 VERSION=true
fi

cd ${DIR}/bin && sh shutdown.sh

cd ${JAVA_HOME}/bin && ./java -jar cd ${DIR}/lib/${JAR_DIR} ${IP} ${APP_NAME} ${DIR} ${VERSION}

echo "系统开始完成。"

# shellcheck disable=SC2038
find ${DIR}/ -name '*.sh' | xargs dos2unix;

cd ${DIR}/bin && sh startup.sh



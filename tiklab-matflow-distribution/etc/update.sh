
DIR=$(dirname "$PWD")

APP_NAME=matFlow

#远程文件存放位置
SOURCE_DIR=/usr/local/apps/source

UPDATE_DIR=${DIR}/${APP_NAME}

#升级
updateApp(){
   tar -xvf ${1} -C ${DIR} --strip-components 1

   # shellcheck disable=SC2038
   find ${DIR}/ -name '*.sh' | xargs dos2unix;

   cd ${DIR}/bin && sh startup.sh

   echo "应用升级完成"
}

#读取版本文件
readFile(){
i=1
#计算文件的总行数
sum=`sed -n '$=' ${UPDATE_DIR}`
#获取文件信息
for line in $(<${UPDATE_DIR})
do
  #读取文件中的版本信息放入数组
  arr[$i]="$line"
  i=`expr $i + 1`
done
}

#备份文件
backups(){
  #关闭源程序
  sh ${DIR}/bin/shutdown.sh
  echo "程序开始备份。。。。。"
  mkdir -p ${DIR}/history
  mv $DIR/bin $DIR/history
  mv $DIR/conf $DIR/history
  mv $DIR/jdk-16.0.2 $DIR/history
  mv $DIR/lib $DIR/history
  mv $DIR/logs $DIR/history
  mv $DIR/mysql-8.0.28 $DIR/history
  mv $DIR/plugin $DIR/history
  mv $DIR/history/plugin/* $DIR/plugin
  echo "程序备份（SUCCESS）"
}

#获取版本
version(){
for i in `seq $sum` ;do
  echo "（$i）：${arr[$i]}"
done
}

#获取升级源文件
downloadSourceFile(){
  # shellcheck disable=SC2115
  rm -rf ${DIR}/${APP_NAME}

  filename=$(echo ${1}.tar.gz | sed -e 's/\r//g')

  cd ${DIR} && echo -e "get ${SOURCE_DIR}/${filename}" | lftp -u root,darth2020 172.12.1.30

  APP=`find ${DIR} -name "*.tar.gz" -print`

  if [ ! -d ${APP} ] ; then
    echo "升级成功"
    backups
    updateApp ${APP}
  else
    echo "升级失败，无法获取到升级文件（FAIL）"
  fi
}

#确定升级
judge(){
read -p "确定升级（Y）确定 （N）重新选择 （Q）退出：" put
if [[ ${put} == "y" ]] || [[ ${put} == "Y" ]] ; then
  echo "开始升级，版本：$1"
  downloadSourceFile "${1}"
elif [[ ${put} == "n" ]] || [[ ${put} == "N" ]] ; then
  getVersion
elif [[ ${put} == "q" ]] || [[ ${put} == "Q" ]] ; then
  echo "用户取消升级"
  exit 1
else
  judge $1
fi
}

#获取版本信息
getVersion(){
version
read -p "请输入版本序号：" input
if [[ ! -z ${arr[$input]} ]]; then
  echo "版本：${arr[$input]}"
  judge ${arr[$input]}
else
echo "版本不存在，请重新选择版本："
 getVersion
fi
}

#获取远程版本信息
downloadVersionFile(){
  cd ${DIR} && echo -e "get ${SOURCE_DIR}/${APP_NAME}" | lftp -u root,darth2020 172.12.1.30
  if [ -f ${DIR}/${APP_NAME} ]; then
    echo "版本获取成功,请选择升级版本："
    readFile
    getVersion
  else
    echo "当前已是最新版本，无需升级。。。。。"
    exit 1
  fi
}

downloadVersionFile






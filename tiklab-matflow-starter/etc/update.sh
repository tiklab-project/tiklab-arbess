#!/bin/sh

DIR=$(dirname "$PWD")
echo "获取版本升级信息"
for i in $(seq 5)
  do
    sleep 0.6
    echo -e  ".\c"
  done

find ${DIR}/ -name '*.sh' | xargs dos2unix;

echo mirror /usr/local/apps/source  | lftp -u root,darth2020 172.12.1.30

mv ${DIR}/etc/source/* ${DIR}
rm -rf ${DIR}/etc/source

APP=`find ${DIR} -name "*.tar.gz" -print`

if [ ! -d ${APP} ] ; then
echo "版本信息"
echo ${APP}
echo "版本获取成功，验证文件"
for i in $(seq 5)
  do
    sleep 0.6
    echo -e  ".\c"
  done
else
echo "无法获取到升级版本。"
exit 1
fi
echo "应用开始升级"
sh ${DIR}/bin/shutdown.sh

mkdir -p ${DIR} history

mv $DIR/bin $DIR/history
mv $DIR/conf $DIR/history
mv $DIR/jdk-16.0.2 $DIR/history
mv $DIR/lib $DIR/history
mv $DIR/logs $DIR/history
mv $DIR/mysql-8.0.28 $DIR/history
mv $DIR/plugin $DIR/history

tar -xvf ${APP} -C ${DIR} --strip-components 1

sh ${DIR}/bin/startup.sh

echo "应用升级完成"
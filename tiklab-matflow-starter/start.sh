#!/bin/sh

#应用名称
name=pipeline
#版本
version=1.0.0
#数据库密码
mysqlWord=darth2020
#应用端口
port=8080
#应用映射端口
mappingPort=9000
#数据库映射端口
mysqlMapping=5000

#镜像名
imageName=$name-$version
#工作目录
workdir=/usr/local/doublekit-$imageName-SNAPSHOT/bin

#镜像文件地址
path=$(dirname $(readlink -f "$0"))

#获取镜像id
imageId=`docker images -q --filter reference=$imageName`

echo "==========================================="
if [ $imageId ]; then
    echo "镜像存在"
else
    echo "加载镜像"$imageName
    cd $path;docker load -i $name-$version.tar
    echo "镜像加载完成"$imageName
fi
echo "==========================================="



echo "启动${name}镜像"

docker run -tid \
  --name $name \
  -p $mysqlMapping:3306 \
  -p $mappingPort:$port \
  -e MYSQL_ROOT_PASSWORD=$mysqlWord \
  -v /sys/fs/cgroup:/sys/fs/cgroup \
  -v /root/mysql/datadir:/var/lib/mysql \
  -v /root/mysql/conf:/etc/mysql/conf.d \
  -v /root/mysql/logs:/var/log/mysql \
  --privileged=true \
  zcamy/doublekit:$imageName

# java --list-modules      jmap -histo 11724

echo "${name}镜像启动成功"

containerId=`docker ps -aqf "name=${name}"`

echo $containerId

echo "开始启动${name}"
docker exec -it $containerId /bin/bash -c "cd ${workdir} && sh startup.sh"
echo "${name}启动成功"


















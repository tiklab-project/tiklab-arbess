#!/bin/sh

#镜像名
imageName=zcamy/darth_pipeline:pipeline-1.0.0
#容器名
containerName=pipeline
#应用端口
port=8080
#应用映射端口
mappingPort=9000
#数据库映射端口
mysqlMapping=5000

#工作目录
workdir=/usr/local/doublekit-$containerName-1.0.0-SNAPSHOT/bin

echo "启动${containerName}镜像"

docker run -tid --name $containerName \
  -p $mysqlMapping:3306 -p $mappingPort:$port \
  -v /sys/fs/cgroup:/sys/fs/cgroup \
  --privileged=true \
  $imageName \
  /usr/sbin/init

echo "${containerName}镜像启动成功"

containerId=`docker ps -aqf "name=${containerName}"`

echo $containerId

echo "开始启动${containerName}"
docker exec -it $containerId /bin/bash -c "cd ${workdir} && sh startup.sh"
echo "${containerName}启动成功"

echo "开始初始化数据库"
docker exec -it $containerId /bin/bash -c "cd ${workdir} && sh mysqlstart.sh"
echo "数据库初始化成功"


















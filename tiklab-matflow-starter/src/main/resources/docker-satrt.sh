#应用名
name=pipeline

#mysql版本
mysql=zcamy/darth_pipeline:mysql-8.0.28

#mysql端口
mysqlPort=3306

#mysql映射端口
mysqlMapping=3306

#数据库密码
mysqlPassword=darth2020

echo "装载Jdk";
docker load -i docker/jdk-16.0.2.tar
echo "Jdk装载完成";

#应用启动端口
startPort=8080

#应用映射端口
mapping=8080

if [ ! -z `docker ps -a | grep 'mysql' | awk '{print $1 }'` ]
  then 
	echo "mysql重启";
	docker container restart mysql;
	echo "mysql重启成功";
  else
	echo "装载mysql镜像";
	docker load -i docker/mysql-8.0.28.tar
	echo "mysql镜像装载完成，启动。。";
	docker run --name mysql -p $mysqlMapping:$mysqlPort -e MYSQL_ROOT_PASSWORD=$mysqlPassword -d $mysql
	echo "mysql Port:"$mysqlPort;
	echo "mysql password:$mysqlPassword";
	echo "mysql启动成功。。。";
fi

docker image build -t $name .

docker run -idm -p $mapping:$startPort $name;









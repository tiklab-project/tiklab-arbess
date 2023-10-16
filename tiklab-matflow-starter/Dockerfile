FROM dokken/centos-stream-8

#添加部署文件
ENV DIR_HOME=/usr/local/apps

ADD target ${DIR_HOME}

#工作空间
WORKDIR ${DIR_HOME}/target
#暴露端口
EXPOSE 8080
#启动
CMD APP_HOME=$(find ${DIR_HOME}/target -type d -name "*tiklab-*"); sh target/bin/startup.sh ; tail -100f target/logs/app.log



#docker login -u admin -p darth2020 http://172.13.1.11:9003 ;docker tag matflow:1.0.3 "172.13.1.11:9003/b/matflow:1.0.3"; docker push 172.13.1.11:9003/b/matflow:1.0.3


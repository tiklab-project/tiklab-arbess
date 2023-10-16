FROM dokken/centos-stream-8

#添加部署文件
ENV DIR_HOME=/usr/local/apps

ADD target ${DIR_HOME}

#工作空间
WORKDIR ${DIR_HOME}/target
#暴露端口
EXPOSE 8080
#启动
#CMD APP_HOME=$(find ${DIR_HOME}/target -type d -name "*tiklab-*"); sh ${APP_HOME}/bin/startup.sh ; tail -100f ${APP_HOME}/logs/app.log
CMD APP_HOME="${DIR_HOME}/tiklab*.tar.gz"; sh ${APP_HOME}/bin/startup.sh ; tail -100f ${APP_HOME}/logs/app.log
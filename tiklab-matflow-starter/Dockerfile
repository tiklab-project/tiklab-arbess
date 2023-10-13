FROM dokken/centos-stream-8
#获取传入的变量
ARG APP_NAME
ARG APP_DIR

#添加部署文件
ENV DIR_HOME=/usr/local/apps
ADD ${APP_DIR} ${DIR_HOME}
#工作空间
ENV APP_HOME=${DIR_HOME}/${APP}

WORKDIR ${DIR_HOME}
#暴露端口
EXPOSE 8080
#启动
CMD APP_HOME=$(find ${DIR_HOME} -type d -name "*tiklab-*") ; sh ${APP_HOME}/bin/startup.sh ; tail -100f ${APP_HOME}/logs/app.log



#docker login -u admin -p darth2020 http://172.13.1.11:9003 ;docker tag matflow:1.0.3 "172.13.1.11:9003/b/matflow:1.0.3"; docker push 172.13.1.11:9003/b/matflow:1.0.3


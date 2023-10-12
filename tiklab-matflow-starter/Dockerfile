FROM dokken/centos-stream-8
#获取传入的变量
ARG APP_NAME
ARG APP_DIR
#添加部署文件
ENV DIR_HOME /usr/local/apps
ADD ${APP_DIR} ${DIR_HOME}
#工作空间
ENV APP_HOME ${DIR_HOME}/${APP_NAME}
WORKDIR ${APP_HOME}
#暴露端口
EXPOSE 8080
#启动
CMD sh ${APP_HOME}/bin/startup.sh ; tail -100f ${APP_HOME}/logs/app.log






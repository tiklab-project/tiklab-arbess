FROM dokken/centos-stream-8

#添加部署文件
ENV DIR_HOME=/usr/local/apps
ENV TEP_HOME=/usr/local/tmp

RUN mkdir -p ${TEP_HOME}
RUN mkdir -p ${DIR_HOME}

ADD target ${TEP_HOME}

RUN APP_HOME="thoughtware-*.tar.gz" ;  tar -xvf ${TEP_HOME}/${APP_HOME} -C ${DIR_HOME}

RUN APP_HOME="thoughtware-*" ; mv ${DIR_HOME}/${APP_HOME} ${DIR_HOME}/thoughtware

#工作空间
WORKDIR ${DIR_HOME}
#暴露端口
EXPOSE 8080
#启动
CMD sh thoughtware/bin/startup.sh ; tail -100f thoughtware/logs/app.log
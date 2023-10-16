FROM dokken/centos-stream-8

#添加部署文件
ENV DIR_HOME=/usr/local/apps
ENV TEP_HOME=/usr/local/tmp

RUN mkdir -p ${TEP_HOME}
RUN mkdir -p ${DIR_HOME}

ADD target ${TEP_HOME}

RUN APP_HOME="tiklab-*.tar.gz" ;  tar -xvf ${TEP_HOME}/${APP_HOME} -C ${DIR_HOME}

RUN APP_HOME="tiklab-*" ; mv ${DIR_HOME}/${APP_HOME} ${DIR_HOME}/tiklab

#工作空间
WORKDIR ${DIR_HOME}
#暴露端口
EXPOSE 8080
#启动
CMD sh tiklab/bin/startup.sh ; tail -100f tiklab/logs/app.log
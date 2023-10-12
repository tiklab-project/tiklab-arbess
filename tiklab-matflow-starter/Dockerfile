FROM dokken/centos-stream-8
ENV DIR_HOME /usr/local
ENV VERSION tiklab-matflow-1.0.0-SNAPSHOT
ENV DEPLOY_FILE /target/${VERSION}.tar.gz
ENV APP_HOME ${DIR_HOME}/${VERSION}
ADD ${DEPLOY_FILE} ${DIR_HOME}
WORKDIR ${APP_HOME}
EXPOSE 8080
CMD sh ${APP_HOME}/bin/startup.sh ; tail -100f ${APP_HOME}/logs/app.log






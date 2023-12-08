package io.thoughtware.matflow.setting.service;

import io.thoughtware.matflow.setting.model.EnvQuery;
import io.thoughtware.matflow.support.util.PipelineUtil;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.core.page.PaginationBuilder;
import io.thoughtware.join.JoinTemplate;
import io.thoughtware.matflow.setting.dao.EnvDao;
import io.thoughtware.matflow.setting.model.Env;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnvServiceImpl implements EnvService {

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    EnvDao envDao;

    @Override
    public String createEnv(Env env) {
        env.setCreateTime(PipelineUtil.date(1));
        return envDao.creatEnv(env);
    }

    @Override
    public void updateEnv(Env env) {
        envDao.updateEnv(env);
    }

    @Override
    public void deleteEnv(String envId) {
        envDao.deleteEnv(envId);
    }

    @Override
    public Env findOneEnv(String envId) {
        Env env = envDao.findOneEnv(envId);
        joinTemplate.joinQuery(env);
        return env;
    }

    @Override
    public List<Env> findAllEnv() {
        return envDao.findAllEnv();
    }

    @Override
    public List<Env> findEnvList(EnvQuery envQuery) {
        List<Env> envList = envDao.findEnvList(envQuery);
        joinTemplate.joinQuery(envList);
        return envList;
    }

    @Override
    public Pagination<Env> findEnvPage(EnvQuery envQuery) {
        Pagination<Env> envPage = envDao.findEnvPage(envQuery);
        List<Env> dataList = envPage.getDataList();
        joinTemplate.joinQuery(dataList);
        return PaginationBuilder.build(envPage,dataList);
    }

    @Override
    public List<Env> findAllEnvList(List<String> idList) {
        return envDao.findAllEnvList(idList);
    }
}

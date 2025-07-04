package io.tiklab.arbess.setting.env.service;

import io.tiklab.arbess.setting.env.model.EnvQuery;
import io.tiklab.arbess.support.util.util.PipelineUtil;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.toolkit.join.JoinTemplate;
import io.tiklab.arbess.setting.env.dao.EnvDao;
import io.tiklab.arbess.setting.env.model.Env;
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
        joinTemplate.joinQuery(env,new String[]{"user"});
        return env;
    }

    @Override
    public List<Env> findAllEnv() {
        return envDao.findAllEnv();
    }

    @Override
    public List<Env> findEnvList(EnvQuery envQuery) {
        List<Env> envList = envDao.findEnvList(envQuery);
        joinTemplate.joinQuery(envList,new String[]{"user"});
        return envList;
    }

    @Override
    public Pagination<Env> findEnvPage(EnvQuery envQuery) {
        Pagination<Env> envPage = envDao.findEnvPage(envQuery);
        List<Env> dataList = envPage.getDataList();
        joinTemplate.joinQuery(dataList ,new String[]{"user"});
        return PaginationBuilder.build(envPage,dataList);
    }

    @Override
    public List<Env> findAllEnvList(List<String> idList) {
        return envDao.findAllEnvList(idList);
    }


    @Override
    public Integer findEnvNumber() {
        return envDao.findEnvNumber();
    }
}

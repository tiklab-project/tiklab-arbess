package io.tiklab.arbess.setting.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;
import io.tiklab.arbess.setting.model.Env;
import io.tiklab.arbess.setting.model.EnvQuery;

import java.util.List;

@JoinProvider(model = Env.class)
public interface EnvService {

    String createEnv(Env env);

    void updateEnv(Env env);


    void deleteEnv(String envId);

    @FindOne
    Env findOneEnv(String envId);

    @FindAll
    List<Env> findAllEnv();


    List<Env> findEnvList(EnvQuery envQuery);


    Pagination<Env> findEnvPage(EnvQuery envQuery);


    @FindList
    List<Env> findAllEnvList(List<String> idList);


    Integer findEnvNumber();



}

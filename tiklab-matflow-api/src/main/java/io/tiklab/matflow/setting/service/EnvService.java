package io.tiklab.matflow.setting.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.join.annotation.FindAll;
import io.tiklab.join.annotation.FindList;
import io.tiklab.join.annotation.FindOne;
import io.tiklab.join.annotation.JoinProvider;
import io.tiklab.matflow.setting.model.Env;
import io.tiklab.matflow.setting.model.EnvQuery;
import io.tiklab.matflow.setting.model.Group;

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



}

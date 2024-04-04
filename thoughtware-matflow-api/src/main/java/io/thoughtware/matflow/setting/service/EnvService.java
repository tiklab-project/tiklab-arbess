package io.thoughtware.matflow.setting.service;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.toolkit.join.annotation.FindAll;
import io.thoughtware.toolkit.join.annotation.FindList;
import io.thoughtware.toolkit.join.annotation.FindOne;
import io.thoughtware.toolkit.join.annotation.JoinProvider;
import io.thoughtware.matflow.setting.model.Env;
import io.thoughtware.matflow.setting.model.EnvQuery;

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

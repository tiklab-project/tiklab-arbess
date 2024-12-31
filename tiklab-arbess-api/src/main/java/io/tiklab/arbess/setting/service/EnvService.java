package io.tiklab.arbess.setting.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;
import io.tiklab.arbess.setting.model.Env;
import io.tiklab.arbess.setting.model.EnvQuery;

import java.util.List;

/**
 * 环境服务接口
 */
@JoinProvider(model = Env.class)
public interface EnvService {

    /**
     * 创建环境
     * @param env 环境对象
     * @return 环境ID
     */
    String createEnv(Env env);

    /**
     * 更新环境
     * @param env 环境对象
     */
    void updateEnv(Env env);

    /**
     * 删除环境
     * @param envId 环境ID
     */
    void deleteEnv(String envId);

    /**
     * 查询环境
     * @param envId 环境ID
     * @return 环境对象
     */
    @FindOne
    Env findOneEnv(String envId);

    /**
     * 查询所有环境
     * @return 环境列表
     */
    @FindAll
    List<Env> findAllEnv();


    /**
     * 查询环境列表
     * @param envQuery 查询条件
     * @return 环境列表
     */
    List<Env> findEnvList(EnvQuery envQuery);

    /**
     * 分页查询环境
     * @param envQuery 查询条件
     * @return 环境分页结果
     */
    Pagination<Env> findEnvPage(EnvQuery envQuery);


    /**
     * 根据ID列表批量查询环境
     * @param idList 环境ID列表
     * @return 环境列表
     */
    @FindList
    List<Env> findAllEnvList(List<String> idList);

    /**
     * 查询环境数量
     * @return 环境数量
     */
    Integer findEnvNumber();



}

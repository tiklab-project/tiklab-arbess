package io.tiklab.arbess.setting.service;

import io.tiklab.arbess.setting.model.AuthHostK8s;
import io.tiklab.arbess.setting.model.AuthHostK8sQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;

import java.util.List;

/**
 * 流水线主机认证服务接口
 */
@JoinProvider(model = AuthHostK8s.class)
public interface AuthHostK8sService {

    /**
     * 创建流水线主机授权
     * @param authHostK8s 流水线主机授权
     * @return 流水线主机授权id
     */
    String createAuthHostK8s(AuthHostK8s authHostK8s);

    /**
     * 删除流水线主机授权
     * @param authHostK8sId 流水线主机授权id
     */
    void deleteAuthHostK8s(String authHostK8sId);

    /**
     * 更新主机授权信息
     * @param authHostK8s 信息
     */
    @FindOne
    void updateAuthHostK8s(AuthHostK8s authHostK8s);

    /**
     * 查询主机授权信息
     * @param authHostK8sId id
     * @return 信息
     */
    AuthHostK8s findOneAuthHostK8s(String authHostK8sId);

    /**
     * 查询配置
     * @param hostQuery 类型
     * @return 配置
     */
    List<AuthHostK8s> findAuthHostK8sList(AuthHostK8sQuery hostQuery);

    /**
     * 查询所有流水线主机授权
     * @return 流水线主机授权列表
     */
    @FindAll
    List<AuthHostK8s> findAllAuthHostK8s();


    /**
     * 根据ID列表批量查询流水线主机授权
     * @param idList 流水线主机授权ID列表
     * @return 流水线主机授权列表
     */
    @FindList
    List<AuthHostK8s> findAllAuthHostK8sList(List<String> idList);

    /**
     * 分页查询流水线主机授权
     * @param hostQuery 查询条件
     * @return 流水线主机授权分页结果
     */
    Pagination<AuthHostK8s> findAuthHostK8sPage(AuthHostK8sQuery hostQuery);

    /**
     * 查询流水线主机授权数量
     * @return 流水线主机授权数量
     */
    Integer findHostNumber();
    
}

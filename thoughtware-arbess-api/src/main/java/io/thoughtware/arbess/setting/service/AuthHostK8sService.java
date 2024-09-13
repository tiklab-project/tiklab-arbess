package io.thoughtware.arbess.setting.service;

import io.thoughtware.arbess.setting.model.AuthHostK8s;
import io.thoughtware.arbess.setting.model.AuthHostK8sQuery;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.toolkit.join.annotation.FindAll;
import io.thoughtware.toolkit.join.annotation.FindList;
import io.thoughtware.toolkit.join.annotation.FindOne;
import io.thoughtware.toolkit.join.annotation.JoinProvider;

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


    @FindList
    List<AuthHostK8s> findAllAuthHostK8sList(List<String> idList);


    Pagination<AuthHostK8s> findAuthHostK8sPage(AuthHostK8sQuery hostQuery);


    Integer findHostNumber();
    
}

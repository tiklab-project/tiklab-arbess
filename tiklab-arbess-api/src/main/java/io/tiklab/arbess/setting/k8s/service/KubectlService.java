package io.tiklab.arbess.setting.k8s.service;

import io.tiklab.arbess.setting.k8s.model.Kubectl;
import io.tiklab.arbess.setting.k8s.model.KubectlQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;

import java.util.List;

/**
 * 流水线主机认证服务接口
 */
@JoinProvider(model = Kubectl.class)
public interface KubectlService {

    /**
     * 创建Kubectl
     * @param kubectl Kubectl
     * @return KubectlId
     */
    String createKubectl(Kubectl kubectl);

    /**
     * 删除Kubectl
     * @param KubectlId Kubectlid
     */
    void deleteKubectl(String KubectlId);

    /**
     * 更新主机授权信息
     * @param kubectl 信息
     */
    @FindOne
    void updateKubectl(Kubectl kubectl);

    /**
     * 查询主机授权信息
     * @param KubectlId id
     * @return 信息
     */
    Kubectl findOneKubectl(String KubectlId);

    /**
     * 查询配置
     * @param hostQuery 类型
     * @return 配置
     */
    List<Kubectl> findKubectlList(KubectlQuery hostQuery);

    /**
     * 查询所有Kubectl
     * @return Kubectl列表
     */
    @FindAll
    List<Kubectl> findAllKubectl();


    /**
     * 根据ID列表批量查询Kubectl
     * @param idList KubectlID列表
     * @return Kubectl列表
     */
    @FindList
    List<Kubectl> findAllKubectlList(List<String> idList);

    /**
     * 分页查询Kubectl
     * @param hostQuery 查询条件
     * @return Kubectl分页结果
     */
    Pagination<Kubectl> findKubectlPage(KubectlQuery hostQuery);

}

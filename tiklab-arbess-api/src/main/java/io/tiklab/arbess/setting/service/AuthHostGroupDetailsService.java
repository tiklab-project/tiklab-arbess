package io.tiklab.arbess.setting.service;

import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;
import io.tiklab.arbess.setting.model.AuthHostGroupDetails;

import java.util.List;

/**
 * @author zcamy
 */
@JoinProvider(model = AuthHostGroupDetails.class)
public interface AuthHostGroupDetailsService {


    /**
     * 创建主机组详情
     * @param hostGroupDetails 主机组详情
     * @return 主机组详情ID
     */
    String creatHostGroupDetails(AuthHostGroupDetails hostGroupDetails) ;

    /**
     * 更新主机组详情
     * @param hostGroupDetails 主机组详情
     */
    void updateHostGroupDetails(AuthHostGroupDetails hostGroupDetails) ;

    /**
     * 删除主机组详情
     * @param groupDetailsId 主机组详情ID
     */
    void deleteHostGroupDetails(String groupDetailsId) ;

    /**
     * 查询单个主机组详情
     * @param groupDetailsId 主机组详情ID
     * @return 主机组详情
     */
    @FindOne
    AuthHostGroupDetails findOneHostGroupDetails(String groupDetailsId) ;

    /**
     * 查询所有主机组详情
     * @return 主机组详情列表
     */
    @FindAll
    List<AuthHostGroupDetails> findAllHostGroupDetails() ;

    /**
     * 查询主机组详情列表
     * @param groupId 主机组ID
     * @return 主机组详情列表
     */
    List<AuthHostGroupDetails> findHostGroupDetailsList(String groupId) ;


}

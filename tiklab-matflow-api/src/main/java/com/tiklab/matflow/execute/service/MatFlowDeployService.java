package com.tiklab.matflow.execute.service;


import com.tiklab.join.annotation.FindAll;
import com.tiklab.join.annotation.FindList;
import com.tiklab.join.annotation.FindOne;
import com.tiklab.join.annotation.JoinProvider;
import com.tiklab.matflow.definition.model.MatFlowConfigure;
import com.tiklab.matflow.definition.model.MatFlowExecConfigure;
import com.tiklab.matflow.execute.model.MatFlowDeploy;

import java.util.List;


@JoinProvider(model = MatFlowDeploy.class)
public interface MatFlowDeployService {

    /**
     * 创建
     * @param matFlowDeploy deploy信息
     * @return deployId
     */
     String createDeploy(MatFlowDeploy matFlowDeploy) ;

    /**
     * 删除
     * @param deployId deployId
     */
     void deleteDeploy(String deployId) ;

    /**
     * 更新
     * @param matFlowDeploy 更新信息
     */
     void updateDeploy(MatFlowDeploy matFlowDeploy) ;

    /**
     * 查询单个信息
     * @param deployId matFlowDeployId
     * @return deploy信息
     */
    @FindOne
    MatFlowDeploy findOneDeploy(String deployId) ;

    /**
     * 查询所有信息
     * @return deploy信息集合
     */
    @FindAll
     List<MatFlowDeploy> findAllDeploy() ;

    @FindList
    List<MatFlowDeploy> findAllDeployList(List<String> idList);

}

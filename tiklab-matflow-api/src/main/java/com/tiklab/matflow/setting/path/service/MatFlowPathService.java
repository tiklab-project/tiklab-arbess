package com.tiklab.matflow.setting.path.service;

import com.tiklab.matflow.setting.path.model.MatFlowPath;

import java.util.List;

public interface MatFlowPathService {


    String createMatFlowPath(MatFlowPath matFlowPath);

    //删除
    void deleteMatFlowPath(String pathId);

    //更新
    void updateMatFlowPath(MatFlowPath matFlowPath);

    //查询
    MatFlowPath findOneMatFlowPath(String pathId);

    //查询所有
    List<MatFlowPath> findAllMatFlowPath();

    /**
     * 获取配置
     * @param type 类型
     * @return 配置信息
     */
    MatFlowPath findOneMatFlowPath(int type);

    List<MatFlowPath> selectMatFlowPathList(List<String> idList);

}

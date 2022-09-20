package net.tiklab.matflow.setting.service;

import net.tiklab.matflow.setting.model.MatFlowScm;

import java.util.List;

public interface MatFlowScmService {


    String createMatFlowScm(MatFlowScm matFlowScm);

    //删除
    void deleteMatFlowScm(String pathId);

    //更新
    void updateMatFlowScm(MatFlowScm matFlowScm);

    //查询
    MatFlowScm findOneMatFlowScm(String pathId);

    //查询所有
    List<MatFlowScm> findAllMatFlowScm();

    /**
     * 获取配置
     * @param type 类型
     * @return 配置信息
     */
    MatFlowScm findOneMatFlowScm(int type);

    List<MatFlowScm> selectMatFlowScmList(List<String> idList);

}

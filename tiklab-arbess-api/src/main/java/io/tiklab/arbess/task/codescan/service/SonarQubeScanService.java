package io.tiklab.arbess.task.codescan.service;

import io.tiklab.arbess.task.codescan.model.SonarQubeScan;
import io.tiklab.arbess.task.codescan.model.SonarQubeScanQuery;
import io.tiklab.core.page.Pagination;

import java.util.List;

public interface SonarQubeScanService {

    /**
     * 创建SonarQubeScan
     * @param sonarQubeScan
     * @return id
     */
    String creatSonarQubeScan(SonarQubeScan sonarQubeScan);

    /**
     * 查询SonarQubeScan
     * @param id
     * @return SonarQubeScan
     */
    SonarQubeScan findSonarQubeScan(String id);

    /**
     * 修改SonarQubeScan
     * @param sonarQubeScan 内容
     */
    void updateSonarQubeScan(SonarQubeScan sonarQubeScan);

    /**
     * 删除SonarQubeScan
     * @param id id
     */
    void deleteSonarQubeScan(String id);

    /**
     * 查询所有SonarQubeScan
     * @return SonarQubeScan列表
     */
    List<SonarQubeScan> findAllSonarQubeScan();

    /**
     * 查询SonarQubeScan列表
     * @param qubeScanQuery 查询参数
     * @return 列表
     */
    List<SonarQubeScan> findSonarQubeScanList(SonarQubeScanQuery qubeScanQuery);

    /**
     * 查询SonarQubeScan列表
     * @param idList id列表
     * @return 列表
     */
    List<SonarQubeScan> findSonarQubeScanList(List<String> idList);

    /**
     * 分页查询SonarQubeScan列表
     * @param qubeScanQuery 查询参数
     * @return 分页参数
     */
    Pagination<SonarQubeScan> findSonarQubeScanPage(SonarQubeScanQuery qubeScanQuery);

}

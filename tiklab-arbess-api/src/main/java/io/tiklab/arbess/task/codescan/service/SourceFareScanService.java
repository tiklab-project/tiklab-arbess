package io.tiklab.arbess.task.codescan.service;

import io.tiklab.arbess.task.codescan.model.SourceFareScan;
import io.tiklab.arbess.task.codescan.model.SourceFareScanQuery;
import io.tiklab.core.page.Pagination;

import java.util.List;

public interface SourceFareScanService {

    /**
     * 创建SourceFareScan
     * @param sourceFareScan
     * @return id
     */
    String creatSourceFareScan(SourceFareScan sourceFareScan);

    /**
     * 查询SourceFareScan
     * @param id
     * @return SourceFareScan
     */
    SourceFareScan findSourceFareScan(String id);

    /**
     * 修改SourceFareScan
     * @param sourceFareScan 内容
     */
    void updateSourceFareScan(SourceFareScan sourceFareScan);

    /**
     * 删除SourceFareScan
     * @param id id
     */
    void deleteSourceFareScan(String id);

    /**
     * 查询所有SourceFareScan
     * @return SourceFareScan列表
     */
    List<SourceFareScan> findAllSourceFareScan();

    /**
     * 查询SourceFareScan列表
     * @param qubeScanQuery 查询参数
     * @return 列表
     */
    List<SourceFareScan> findSourceFareScanList(SourceFareScanQuery qubeScanQuery);

    /**
     * 查询SourceFareScan列表
     * @param idList id列表
     * @return 列表
     */
    List<SourceFareScan> findSourceFareScanList(List<String> idList);

    /**
     * 分页查询SourceFareScan列表
     * @param qubeScanQuery 查询参数
     * @return 分页参数
     */
    Pagination<SourceFareScan> findSourceFareScanPage(SourceFareScanQuery qubeScanQuery);

}

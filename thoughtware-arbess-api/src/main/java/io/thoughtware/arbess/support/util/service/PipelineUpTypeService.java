package io.thoughtware.arbess.support.util.service;

public interface PipelineUpTypeService {


    /**
     * 更新全部流水线类型
     */
    void updatePipelineTypeList();


    /**
     * 更新指定流水线类型
     * @param pipelineId 流水线ID
     */
    void updatePipelineType(String pipelineId);


}

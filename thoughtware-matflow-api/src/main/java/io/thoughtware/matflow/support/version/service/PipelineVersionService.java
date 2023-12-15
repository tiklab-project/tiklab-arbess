package io.thoughtware.matflow.support.version.service;

public interface PipelineVersionService {

    /**
     * 获取当前系统版本
     * @return 系统版本 1.免费 2.付费
     */
    Integer version();


}
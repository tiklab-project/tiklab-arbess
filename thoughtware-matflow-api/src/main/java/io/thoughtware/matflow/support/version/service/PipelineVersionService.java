package io.thoughtware.matflow.support.version.service;

public interface PipelineVersionService {

    /**
     * 获取当前系统版本
     * @return 系统版本 false.免费 true.付费
     */
    Boolean isVip();


}

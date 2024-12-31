package io.tiklab.arbess.support.version.service;

/**
 * 流水线版本服务接口
 */
public interface PipelineVersionService {

    /**
     * 获取当前系统版本
     * @return 系统版本 false.免费 true.付费
     */
    Boolean isVip();


}

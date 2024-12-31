package io.tiklab.arbess.support.disk.service;

import io.tiklab.arbess.support.disk.model.Disk;

import java.util.List;

/**
 * 磁盘服务接口
 */
public interface DiskService {


    /**
     * 删除磁盘
     * @param pipelineId 流水线ID
     * @return 是否删除成功
     */
    Boolean deleteDisk(String pipelineId);

    /**
     * 验证存储空间
     */
    void validationStorageSpace();

    /**
     * 查询磁盘列表
     * @return 磁盘列表
     */
    List<Disk> findDiskList();

    /**
     * 清理磁盘
     * @param fileList 文件列表
     */
    void cleanDisk(String fileList);


}

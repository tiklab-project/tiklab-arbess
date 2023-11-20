package io.tiklab.matflow.support.disk.service;

import io.tiklab.matflow.support.disk.model.Disk;

import java.util.List;

public interface DiskService {


    Boolean deleteDisk(String pipelineId);


    void validationStorageSpace();


    List<Disk> findDiskList();


    void cleanDisk(String fileList);


}

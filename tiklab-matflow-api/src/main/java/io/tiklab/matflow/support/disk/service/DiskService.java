package io.tiklab.matflow.support.disk.service;

import io.tiklab.matflow.support.disk.model.Disk;

public interface DiskService {


    Boolean deleteDisk(String pipelineId);


    void ValidationStorageSpace();


    Disk findDiskList();


    void cleanDisk(Disk disk);


}

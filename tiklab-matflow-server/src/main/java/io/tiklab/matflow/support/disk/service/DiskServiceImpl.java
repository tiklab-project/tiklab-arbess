package io.tiklab.matflow.support.disk.service;

import io.tiklab.matflow.support.util.PipelineFileUtil;
import io.tiklab.matflow.support.util.PipelineUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class DiskServiceImpl implements DiskService {

    @Autowired
    PipelineUtilService utilService;

    @Override
    public Boolean deleteDisk(String pipelineId) {
        String defaultAddress = utilService.instanceAddress(1);
        String s = defaultAddress + "/" + pipelineId;
        return PipelineFileUtil.deleteFile(new File(s));
    }



}

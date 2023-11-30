package io.tiklab.matflow.support.disk.controller;

import io.tiklab.core.Result;
import io.tiklab.matflow.support.disk.model.Disk;
import io.tiklab.matflow.support.disk.service.DiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @pi.protocol: http
 * @pi.groupName: 流水线后置配置控制器
 */
@RestController
@RequestMapping("/disk")
public class DiskController {

    @Autowired
    DiskService diskService;

    /**
     * @pi.name:创建流水线后置配置
     * @pi.path:/postprocess/createPost
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param: model=postprocess
     */
    @RequestMapping(path="/findDiskList",method = RequestMethod.POST)
    public Result<List<Disk>> createPost(){
        List<Disk> disk = diskService.findDiskList();
        return Result.ok(disk);
    }

    @RequestMapping(path="/cleanDisk",method = RequestMethod.POST)
    public Result<Void> cleanDisk( String fileList){
        diskService.cleanDisk(fileList);
        return Result.ok();
    }





}

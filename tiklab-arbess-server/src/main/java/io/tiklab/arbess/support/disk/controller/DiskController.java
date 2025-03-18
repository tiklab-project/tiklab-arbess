package io.tiklab.arbess.support.disk.controller;

import io.tiklab.core.Result;
import io.tiklab.arbess.support.disk.model.Disk;
import io.tiklab.arbess.support.disk.service.DiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @pi.protocol: http
 * @pi.groupName: 流水线存储空间控制器
 */
@RestController
@RequestMapping("/disk")
public class DiskController {

    @Autowired
    DiskService diskService;

    /**
     * @pi.name:查询流水线存储空间
     * @pi.url:/disk/findDiskList
     * @pi.methodType:post
     * @pi.request-type:none
     */
    @RequestMapping(path="/findDiskList",method = RequestMethod.POST)
    public Result<List<Disk>> createPost(){
        List<Disk> disk = diskService.findDiskList();
        return Result.ok(disk);
    }

    /**
     * @pi.name:清理储存空间
     * @pi.url:/disk/cleanDisk
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=taskId;dataType=string;value=fileList;
     */
    @RequestMapping(path="/cleanDisk",method = RequestMethod.POST)
    public Result<Void> cleanDisk(String fileList){
        diskService.cleanDisk(fileList);
        return Result.ok();
    }





}

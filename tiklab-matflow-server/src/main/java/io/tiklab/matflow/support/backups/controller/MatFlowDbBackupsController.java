package io.tiklab.matflow.support.backups.controller;

import io.tiklab.core.Result;
import io.tiklab.core.exception.SystemException;
import io.tiklab.matflow.support.backups.model.MatFlowBackups;
import io.tiklab.matflow.support.backups.service.MatFlowDbBackupsService;
import io.tiklab.matflow.support.backups.service.MatFlowDbRestoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;

/**
 * 导入数据控制器
 * @author admin
 */

@RestController
@RequestMapping("/matflow/backups")
public class MatFlowDbBackupsController {

    @Autowired
    MatFlowDbBackupsService matFlowDbBackupsService;

    @Autowired
    MatFlowDbRestoreService matFlowDbRestoreService;

    @RequestMapping(path="/backups",method = RequestMethod.POST)
    public Result<Void> createWorkWidget() {
        matFlowDbBackupsService.execBackups();
        return Result.ok();
    }


    @RequestMapping(path="/findBackups",method = RequestMethod.POST)
    public Result<MatFlowBackups> findBackups() {
        MatFlowBackups backupsResult = matFlowDbBackupsService.findBackupsResult();
        return Result.ok(backupsResult);
    }


    @RequestMapping(path="/updateBackups",method = RequestMethod.POST)
    public Result<Void> updateBackups(@NotNull Boolean scheduled) {
        matFlowDbBackupsService.updateBackups(scheduled);
        return Result.ok();
    }


    @RequestMapping(path="/uploadBackups",method = RequestMethod.POST)
    public Result<String> uploadBackups(@RequestParam("uploadFile") MultipartFile uploadFile){
        String string;
        try {
            String fileName = uploadFile.getOriginalFilename();   //获取文件名字
            InputStream inputStream = uploadFile.getInputStream();
            string = matFlowDbRestoreService.uploadBackups(fileName, inputStream);
        } catch (IOException e) {
            throw new SystemException(e);
        }
        return Result.ok(string);
    }

    @RequestMapping(path="/restore",method = RequestMethod.POST)
    public Result<Void> execRestore(@NotNull String path) {
        matFlowDbRestoreService.execRestore(path);
        return Result.ok();
    }


    @RequestMapping(path="/findRestore",method = RequestMethod.POST)
    public Result<MatFlowBackups> findRestoreResult() {
        MatFlowBackups backupsResult = matFlowDbRestoreService.findRestoreResult();
        return Result.ok(backupsResult);
    }


}

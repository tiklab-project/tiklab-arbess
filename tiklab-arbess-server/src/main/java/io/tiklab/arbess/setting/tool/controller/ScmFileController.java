package io.tiklab.arbess.setting.tool.controller;

import io.tiklab.arbess.setting.tool.model.ScmRemoteFile;
import io.tiklab.arbess.setting.tool.service.ScmFileService;
import io.tiklab.arbess.support.util.util.PipelineFileUtil;
import io.tiklab.arbess.support.util.util.PipelineFinal;
import io.tiklab.core.Result;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.core.exception.SystemException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * @pi.protocol: http
 * @pi.groupName: 流水线环境配置控制器
 */
@RestController
@RequestMapping("/scm/file")
public class ScmFileController {

    @Autowired
    ScmFileService scmFileService;

    @Value("${DATA_HOME}")
    String DATA_HOME;

    private final Logger logger = LoggerFactory.getLogger(ScmFileController.class);

    @RequestMapping(path="/upload",method = RequestMethod.POST)
    public Result<String> fileUpload(@RequestParam("uploadFile") MultipartFile uploadFile){
        if(uploadFile == null){
            throw new SystemException("uploadFile must not be null.");
        }

        String fileName = uploadFile.getOriginalFilename();
        if (StringUtils.isEmpty(fileName)){
            throw new ApplicationException("fileName must not be empty.");
        }

        boolean contains = fileName.contains(".");
        if (contains){
            if (!fileName.endsWith(".tar.gz") && !fileName.endsWith(".zip") && !fileName.endsWith(".tgz")) {
                throw new ApplicationException("fileType not .tar.gz、.tgz or .zip");
            }
        }

        File destDir = new File(DATA_HOME + PipelineFinal.ARBESS_SCM);

        String filePath = destDir.getAbsolutePath() + "/" + fileName;

        PipelineFileUtil.createDirectory(destDir.getAbsolutePath());

        // 确保目录存在
        File file = new File(filePath);

        // 写入文件
        writeToFile(uploadFile,file);

        try {
            String binPath = scmFileService.fileFileBinPath(filePath);
            return Result.ok(binPath);
        } catch (Exception e) {
            throw new ApplicationException("解压失败:" + e.getMessage());
        }
    }


    @RequestMapping(path="/downloadFile",method = RequestMethod.POST)
    public Result<String> downloadFile(@RequestBody  @Valid @NotNull ScmRemoteFile remoteFile){

        String filePath = scmFileService.downloadFile(remoteFile);

        return Result.ok(filePath);
    }


    @RequestMapping(path="/downloadAndInstall",method = RequestMethod.POST)
    public Result<Void> downloadAndInstall(@RequestBody  @Valid @NotNull ScmRemoteFile remoteFile){

         scmFileService.downloadAndInstall(remoteFile);

        return Result.ok();
    }


    @RequestMapping(path="/findScmRemoteFile",method = RequestMethod.POST)
    public Result<ScmRemoteFile> findScmRemoteFile(@Valid @NotNull String id){

        ScmRemoteFile scmRemoteFile = scmFileService.findScmRemoteFile(id);

        return Result.ok(scmRemoteFile);
    }

    private void writeToFile(MultipartFile uploadFile, File file){
        try (InputStream stream = uploadFile.getInputStream()) {
            Files.copy(stream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e){
            throw new SystemException("文件写入失败："+ e.getMessage());
        }
    }


}















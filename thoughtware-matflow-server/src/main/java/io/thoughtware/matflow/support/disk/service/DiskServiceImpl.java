package io.thoughtware.matflow.support.disk.service;


import io.thoughtware.matflow.support.util.PipelineFileUtil;
import io.thoughtware.matflow.support.util.PipelineFinal;
import io.thoughtware.core.exception.SystemException;
import io.thoughtware.matflow.pipeline.definition.service.PipelineService;
import io.thoughtware.matflow.pipeline.instance.service.PipelineInstanceService;
import io.thoughtware.matflow.support.disk.model.Disk;
import io.thoughtware.matflow.support.util.PipelineUtilService;
import io.thoughtware.matflow.task.build.service.TaskBuildProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class DiskServiceImpl implements DiskService {


    @Autowired
    PipelineUtilService utilService;

    @Autowired
    PipelineService pipelineService;

    @Autowired
    PipelineInstanceService instanceService;

    @Autowired
    TaskBuildProductService buildProductService;

    @Value("${matflow.cache.size:20}")
    private Integer size;

    @Override
    public Boolean deleteDisk(String pipelineId) {
        String defaultAddress = utilService.instanceAddress(1);
        String s = defaultAddress + "/" + pipelineId;
        return PipelineFileUtil.deleteFile(new File(s));
    }

    @Override
    public void validationStorageSpace(){

        // throw new SystemException(9000,"系统空间不足，请先清理过后在运行!");

       String codeAddress = utilService.instanceAddress(1);
       String logAddress = utilService.instanceAddress(2);

       float diskSize = PipelineFileUtil.findDiskSize(logAddress);

       float dirSize = PipelineFileUtil.findDirSize(codeAddress, PipelineFinal.SIZE_TYPE_GB);
       float logDirSize = PipelineFileUtil.findDirSize(logAddress, PipelineFinal.SIZE_TYPE_GB);

       if ((diskSize - dirSize - logDirSize) < PipelineFinal.DEFAULT_SIZE){
            throw new SystemException(9000,"系统空间不足，请先清理过后在运行!");
       }
   }

    @Override
    public List<Disk> findDiskList(){

        String defaultAddress = utilService.instanceAddress(2);
        File file = new File(defaultAddress);

        List<File> largeFiles = new ArrayList<>();
        getLargeFiles(file, largeFiles);
        List<Disk> list = new ArrayList<>();
        for (File largeFile : largeFiles) {
            String type = PipelineFinal.SIZE_TYPE_MB ;
            if (largeFile.length() > 1024 * 1024 * 1024){
                type = PipelineFinal.SIZE_TYPE_GB;
            }
            String absolutePath = largeFile.getAbsolutePath();
            Disk disks = new Disk();
            float dirSize = PipelineFileUtil.findDirSize(absolutePath, type);
            String replace = absolutePath.replace(defaultAddress, "..");
            disks.setName(largeFile.getName())
                    .setUserSize(dirSize+"."+type)
                    .setPath(absolutePath)
                    .setFilePath(replace);
            list.add(disks);
        }
       return list;
   }

    public void getLargeFiles(File dir, List<File> largeFiles) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                long length = file.length();
                if (file.isFile() && length >= size * 1024 * 1024) {
                    largeFiles.add(file);
                } else if (file.isDirectory()) {
                    getLargeFiles(file, largeFiles); // 递归调用
                }
            }
        }
    }

    @Override
    public void cleanDisk(String fileList) {

        String[] split = fileList.split(",");

        for (String string : split) {
            File file = new File(string);
            if (!file.exists() || !file.isFile()){
                continue;
            }
            file.delete();
        }
    }


}
















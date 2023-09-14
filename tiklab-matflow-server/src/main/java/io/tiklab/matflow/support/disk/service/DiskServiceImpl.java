package io.tiklab.matflow.support.disk.service;


import io.tiklab.core.exception.SystemException;
import io.tiklab.matflow.pipeline.definition.model.Pipeline;
import io.tiklab.matflow.pipeline.definition.service.PipelineService;
import io.tiklab.matflow.pipeline.instance.model.PipelineInstance;
import io.tiklab.matflow.pipeline.instance.service.PipelineInstanceService;
import io.tiklab.matflow.support.disk.model.Disk;
import io.tiklab.matflow.support.util.PipelineFileUtil;
import io.tiklab.matflow.support.util.PipelineFinal;
import io.tiklab.matflow.support.util.PipelineUtil;
import io.tiklab.matflow.support.util.PipelineUtilService;
import io.tiklab.matflow.task.build.model.TaskBuildProduct;
import io.tiklab.matflow.task.build.model.TaskBuildProductQuery;
import io.tiklab.matflow.task.build.service.TaskBuildProductService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.tiklab.matflow.support.util.PipelineFinal.SIZE_TYPE_GB;
import static io.tiklab.matflow.support.util.PipelineFinal.SIZE_TYPE_MB;

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

    @Override
    public Boolean deleteDisk(String pipelineId) {
        String defaultAddress = utilService.instanceAddress(1);
        String s = defaultAddress + "/" + pipelineId;
        return PipelineFileUtil.deleteFile(new File(s));
    }

    @Override
    public void ValidationStorageSpace(){
        throw new SystemException(9000,"系统空间不足，请先清理过后在运行!");

       // String defaultAddress = utilService.instanceAddress(1);
       //
       // File file = new File(defaultAddress);
       //
       // File parentFile = file.getParentFile();
       // String dir = parentFile.getAbsolutePath();
       //
       // float diskSize = PipelineFileUtil.findDiskSize(dir);
       //
       // float dirSize = PipelineFileUtil.findDirSize(dir, SIZE_TYPE_GB);
       //
       // if ((diskSize - dirSize) < PipelineFinal.DEFAULT_SIZE){
       //      throw new SystemException(9000,"系统空间不足，请先清理过后在运行!");
       // }
   }

    @Override
    public Disk findDiskList(){

        Disk disk = new Disk();

        String defaultAddress = utilService.instanceAddress(1);
        File file = new File(defaultAddress);

        File parentFile = file.getParentFile();
        String dir = parentFile.getAbsolutePath();

        float diskSize = PipelineFileUtil.findDiskSize(dir);

        List<Disk> list = new ArrayList<>();
        List<Pipeline> userPipeline = pipelineService.findUserPipeline();
        for (Pipeline pipeline : userPipeline) {
            Disk disks = new Disk();
            String name = pipeline.getName();
            String pipelineId = pipeline.getId();
            List<PipelineInstance> instanceList = instanceService.findPipelineAllInstance(pipelineId);

            float size = 0;
            for (PipelineInstance instance : instanceList) {
                String runStatus = instance.getRunStatus();
                if (runStatus.equals(PipelineFinal.RUN_RUN)){
                    continue;
                }
                TaskBuildProductQuery taskBuildProductQuery = new TaskBuildProductQuery();
                taskBuildProductQuery.setInstanceId(instance.getInstanceId());
                taskBuildProductQuery.setKey(PipelineFinal.DEFAULT_ARTIFACT_ADDRESS);
                List<TaskBuildProduct> buildProductList = buildProductService.findBuildProductList(taskBuildProductQuery);
                if (buildProductList.isEmpty()){
                    continue;
                }
                TaskBuildProduct taskBuildProduct = buildProductList.get(0);

                String value = taskBuildProduct.getValue();
                size = size +  PipelineFileUtil.findDirSize(value, SIZE_TYPE_GB);
            }
            if (size == 0){
                continue;
            }
            String userSize = "0";
            if (size < 1){
                userSize = size * 1024 + SIZE_TYPE_MB;
            }else {
                userSize = size + SIZE_TYPE_GB;
            }
            disks.setName(name).setUserSize(userSize).setPipelineId(pipelineId);
            list.add(disks);
        }
       disk.setDiskSize(diskSize + SIZE_TYPE_GB);
       return disk.setDiskList(list);
   }

    @Override
    public void cleanDisk(Disk disk) {
        List<String> pipelineList = disk.getPipelineList();
        for (String pipelineId : pipelineList) {
            List<PipelineInstance> instanceList = instanceService.findPipelineAllInstance(pipelineId);
            for (PipelineInstance instance : instanceList) {
                String runStatus = instance.getRunStatus();
                if (runStatus.equals(PipelineFinal.RUN_RUN)){
                    continue;
                }
                TaskBuildProductQuery taskBuildProductQuery = new TaskBuildProductQuery();
                taskBuildProductQuery.setInstanceId(instance.getInstanceId());
                List<TaskBuildProduct> buildProductList = buildProductService.findBuildProductList(taskBuildProductQuery);
                if (buildProductList.isEmpty()){
                    continue;
                }

                for (TaskBuildProduct taskBuildProduct : buildProductList) {
                    String key = taskBuildProduct.getKey();
                    if (key.equals(PipelineFinal.DEFAULT_ARTIFACT_ADDRESS)){
                        String value = taskBuildProduct.getValue();
                        FileUtils.deleteQuietly(new File(value));
                    }
                    String id = taskBuildProduct.getId();
                    buildProductService.deleteBuildProduct(id);
                }
            }
        }
    }


}

















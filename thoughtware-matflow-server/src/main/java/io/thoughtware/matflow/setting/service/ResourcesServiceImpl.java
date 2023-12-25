package io.thoughtware.matflow.setting.service;

import io.thoughtware.matflow.setting.model.ResourcesDetails;
import io.thoughtware.matflow.support.util.service.PipelineUtilService;
import io.thoughtware.matflow.support.util.util.PipelineFileUtil;
import io.thoughtware.matflow.support.util.util.PipelineUtil;
import io.thoughtware.matflow.support.util.util.Time;
import io.thoughtware.matflow.support.util.util.TimeConfig;
import io.thoughtware.matflow.support.version.service.PipelineVersionService;
import io.thoughtware.core.exception.ApplicationException;
import io.thoughtware.matflow.pipeline.definition.dao.PipelineDao;
import io.thoughtware.matflow.pipeline.definition.entity.PipelineEntity;
import io.thoughtware.matflow.pipeline.definition.model.PipelineQuery;
import io.thoughtware.matflow.setting.dao.ResourcesDao;
import io.thoughtware.matflow.setting.model.Resources;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
public class ResourcesServiceImpl implements ResourcesService {


    @Autowired
    ResourcesDao resourcesDao;

    @Autowired
    PipelineVersionService versionService;

    @Autowired
    PipelineUtilService utilService;

    @Autowired
    PipelineDao pipelineDao;

    @Override
    public void instanceResources(int time){

        if (time == 0){
            time = 1;
        }

        Time dataTime = TimeConfig.findDataTime(PipelineUtil.date(2));
        String beginTime = dataTime.getMonthBeginTime();
        String endTime = dataTime.getMonthEndTime();
        Resources resources = resourcesDao.findResources(beginTime,endTime);
        if (Objects.nonNull(resources)){
            int structureNumber = resources.getUseSceNumber();
            resources.setUseSceNumber(structureNumber+time);
            resources.setMonth(dataTime.getMonth());
            updateResources(resources);
        }else {
            resources = new Resources();
            resources.setUseSceNumber(time);
            resources.setMonth(dataTime.getMonth());
            resources.setBeginTime(beginTime);
            resources.setEndTime(endTime);
            String resourcesId = createResources(resources);
            if (Objects.isNull(resourcesId)){
                throw new ApplicationException("创建资源信息失败！");
            }
        }
    }

    @Override
    public void judgeResources(){
        Resources resources = findResourcesList();

        int residueCcyNumber = resources.getResidueCcyNumber();
        if (residueCcyNumber == 0 ){
            throw new ApplicationException("并行任务已达上限，无法执行！");
        }

        double cacheNumber = resources.getResidueCacheNumber();
        if (cacheNumber == 0 ){
            throw new ApplicationException("可使用的缓存已用完，无法执行！");
        }

        int sceNumber = resources.getResidueSceNumber();
        if (sceNumber == 0 ){
            throw new ApplicationException("可使用的构建时长已用完，无法执行！");
        }
    }

    @Override
    public String createResources(Resources resources){
        return resourcesDao.createResources(resources);
    }

    @Override
    public void updateResources(Resources resources){
        resourcesDao.updateResources(resources);
    }

    @Override
    public void deleteResources(String resourcesId){
        resourcesDao.deleteResources(resourcesId);
    }

    @Override
    public Resources findOneResources(String resourcesId){
        return resourcesDao.findOneResources(resourcesId);
    }

    @Override
    public List<Resources> findAllResources(){
        List<Resources> allResources = resourcesDao.findAllResources();
        if (allResources == null || allResources.isEmpty()){
            return Collections.emptyList();
        }
        return resourcesDao.findAllResources();
    }

    @Override
    public Resources findResourcesList(){
        int version = versionService.version();

        PipelineQuery pipelineQuery = new PipelineQuery();
        pipelineQuery.setPipelineState(2);
        List<PipelineEntity> pipelineList = pipelineDao.findPipelineList(pipelineQuery);
        int number = pipelineList.size();

        // 1.免费 2.付费 可用资源总数
        if (version == 1){
            Resources resources = notVipResources(number);
            resources.setVersion(version);
            return resources;
        }else {
            Resources resources = vipResources(number);
            resources.setVersion(version);
            return resources;
        }
    }

    private static final int vipExecNumber = 4;

    private static final int vipCacheNTime = -1;

    private static final int vipExecTime = -1;

    private static final int notVipExecNumber = 2;
    private static final int notVipExecTime = -1;
    private static final int notVipCacheNTime = -1;


    public Resources vipResources(int execNumber){
        Resources resources = new Resources();

        // 总资源数
        resources.setCcyNumber(vipExecNumber);
        resources.setSceNumber(vipExecTime);
        String codeAddress = utilService.instanceAddress(1);
        float dirSize = PipelineFileUtil.findDiskSize(codeAddress);
        double diskSize = Double.parseDouble(String.format("%.2f",dirSize));
        resources.setCacheNumber(diskSize);

        // 并发数
        resources.setUseCcyNumber(execNumber);

        // 剩余并发数
        int i = vipExecNumber - execNumber;
        resources.setResidueCcyNumber(Math.max(i, 0));

        // 磁盘大小
        double size = Double.parseDouble(String.format("%.2f",vipExecNumber - getSize()));
        resources.setResidueCacheNumber(size);
        double parsed = Double.parseDouble(String.format("%.2f", getSize()));
        resources.setUseCacheNumber(parsed);

        // 构建时长
        List<Resources> allResources = findAllResources();
        if (allResources.isEmpty()){
            resources.setResidueSceNumber(vipExecTime);
        }else {
            Resources resources1 = allResources.get(0);
            int sceNumber = resources1.getUseSceNumber();
            resources.setResidueSceNumber(sceNumber/60);
        }


        return resources;
    }

    public Resources notVipResources(int execNumber){
        Resources resources = new Resources();
        // 可用资源总数
        resources.setCcyNumber(notVipExecNumber);
        resources.setSceNumber(notVipExecTime);

        String codeAddress = utilService.instanceAddress(1);
        float dirSize = PipelineFileUtil.findDiskSize(codeAddress);
        double diskSize = Double.parseDouble(String.format("%.2f",dirSize));
        resources.setCacheNumber(diskSize);

        // 并发数
        resources.setUseCcyNumber(execNumber);
        int i = notVipExecNumber - execNumber;
        resources.setResidueCcyNumber(Math.max(i, 0));

        // 磁盘数（社区版不限制磁盘大小）
        double size = Double.parseDouble(String.format("%.2f",notVipCacheNTime - getSize()));
        resources.setResidueCacheNumber(size);
        double parsed = Double.parseDouble(String.format("%.2f", getSize()));
        resources.setUseCacheNumber(parsed);

        resources.setResidueSceNumber(vipExecTime);
        // 构建时长
        List<Resources> allResources = findAllResources();
        if (allResources.isEmpty()){
            resources.setUseSceNumber(0);
        }else {
            Resources resources1 = allResources.get(0);
            int sceNumber = resources1.getUseSceNumber();
            resources.setUseSceNumber(sceNumber/60);
        }
        return resources;
    }


    public ResourcesDetails findResourcesDetails(String type){
        ResourcesDetails resourcesDetails = new ResourcesDetails();

        switch(type) {
            case "disk" ->{
                String codeAddress = utilService.instanceAddress(1);
                String logAddress = utilService.instanceAddress(2);
                resourcesDetails.setArtifactCache(findDirSize(logAddress)+"");
                resourcesDetails.setSourceCache(findDirSize(codeAddress)+"");
            }
            case "run" ->{
                PipelineQuery pipelineQuery = new PipelineQuery();
                pipelineQuery.setPipelineState(2);
                List<String> list = new ArrayList<>();
                List<PipelineEntity> pipelineList = pipelineDao.findPipelineList(pipelineQuery);
                if (pipelineList.isEmpty()){
                    resourcesDetails.setList(list);
                    break;
                }
                for (PipelineEntity pipelineEntity : pipelineList) {
                    list.add(pipelineEntity.getName());
                }
                resourcesDetails.setList(list);
            }
        }
        return resourcesDetails;
    }


    public double findDirSize(String dir){
        File file = new File(dir);
        if (!file.exists()){
            return 0;
        }
        long bytes = FileUtils.sizeOfDirectory(file);
        return  Math.round((float) (((bytes / 1024) / 1024) * 100) /1024)/100.0 ;
    }


    /**
     * 获取文件大小
     * @return 文件大小，保留两位小数点
     */
    public double getSize() {
        String codeAddress = utilService.instanceAddress(1);
        String logAddress = utilService.instanceAddress(2);
        File codeFile = new File(codeAddress);

        if (!codeFile.exists()){
            return 0;
        }

        long codeBytes = FileUtils.sizeOfDirectory(codeFile);

        double  codeSize =  Math.round((float) (((codeBytes / 1024) / 1024) * 100) /1024)/100.0 ;

        File logFile = new File(logAddress);
        long logBytes = FileUtils.sizeOfDirectory(logFile);
        double logSize =  Math.round((float) (((logBytes / 1024) / 1024) * 100) /1024)/100.0 ;
        return codeSize + logSize;
    }


}





























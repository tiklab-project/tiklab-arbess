package io.tiklab.matflow.setting.service;

import io.tiklab.core.exception.ApplicationException;
import io.tiklab.matflow.pipeline.definition.dao.PipelineDao;
import io.tiklab.matflow.pipeline.definition.entity.PipelineEntity;
import io.tiklab.matflow.pipeline.definition.model.PipelineQuery;
import io.tiklab.matflow.pipeline.execute.service.PipelineExecServiceImpl;
import io.tiklab.matflow.setting.dao.ResourcesDao;
import io.tiklab.matflow.setting.model.Resources;
import io.tiklab.matflow.support.util.PipelineUtilService;
import io.tiklab.matflow.support.version.PipelineVersionService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        if (time == 0){
            time = 1;
        }

        List<Resources> allResources = findAllResources();
        if (!allResources.isEmpty()){
            Resources resources = allResources.get(0);
            int structureNumber = resources.getUseSceNumber();
            resources.setUseSceNumber(structureNumber+time);
            int month1 = resources.getMonth();
            resources.setMonth(month);
            if (month1 != month){
                resources.setUseSceNumber(time);
            }
            updateResources(resources);
        }else {
            Resources resources = new Resources();
            resources.setUseSceNumber(time);
            resources.setMonth(month);
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
        return resourcesDao.findAllResources();
    }

    @Override
    public Resources findResourcesList(){
        // List<Resources> allResources = resourcesDao.findAllResources();
        // Resources resources = new Resources();
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
            // resources.setCcyNumber(2);
            // resources.setSceNumber(1800);
            // resources.setCacheNumber(-1);
        }else {
            Resources resources = vipResources(number);
            resources.setVersion(version);
            return resources;
            // resources.setCcyNumber(5);
            // resources.setSceNumber(-1);
            // resources.setCacheNumber(-1);
        }

        // PipelineQuery pipelineQuery = new PipelineQuery();
        // pipelineQuery.setPipelineState(1);
        // List<PipelineEntity> pipelineList = pipelineDao.findPipelineList(pipelineQuery);
        //
        // int size = pipelineList.size();
        //
        // // 并发数
        // resources.setUseCcyNumber(size);
        // if (version == 1){
        //     resources.setResidueCcyNumber(3- size);
        // }else {
        //     resources.setResidueCcyNumber(5 - size);
        // }
        //
        // // 磁盘数（社区版不限制磁盘大小）
        // resources.setResidueCacheNumber(-1);
        // resources.setUseCacheNumber(0);
        //
        // // 构建时长
        // if (allResources.isEmpty()){
        //     resources.setUseSceNumber(0);
        //     if (version == 1){
        //         resources.setResidueSceNumber(1800);
        //     }
        // }else {
        //     int useSceNumber = allResources.get(0).getUseSceNumber();
        //     int i = useSceNumber / 60;
        //     if (useSceNumber != 0 && i == 0){
        //         i = 1;
        //         resources.setUseSceNumber(1);
        //     }
        //     if (version == 1){
        //         resources.setResidueSceNumber(1800 - i);
        //     }
        // }
        // if (version == 2){
        //     resources.setResidueSceNumber(-1);
        // }
        // resources.setVersion(version);
        // return resources;
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
        resources.setCacheNumber(vipCacheNTime);

        // 并发数
        resources.setUseCcyNumber(execNumber);

        int i = vipExecNumber - execNumber;
        resources.setResidueCcyNumber(Math.max(i, 0));
        // resources.setResidueCcyNumber(vipExecNumber - execNumber);

        // 磁盘数（社区版,企业版不限制磁盘大小）
        resources.setResidueCacheNumber(vipCacheNTime);
        resources.setUseCacheNumber(0);

        // 构建时长
        resources.setResidueSceNumber(vipExecTime);

        return resources;
    }

    public Resources notVipResources(int execNumber){
        Resources resources = new Resources();
        // 可用资源总数
        resources.setCcyNumber(notVipExecNumber);
        resources.setSceNumber(notVipExecTime);
        resources.setCacheNumber(notVipCacheNTime);

        // 并发数
        resources.setUseCcyNumber(execNumber);
        int i = notVipExecNumber - execNumber;
        resources.setResidueCcyNumber(Math.max(i, 0));
        // resources.setResidueCcyNumber(notVipExecNumber- execNumber);

        // 磁盘数（社区版不限制磁盘大小）
        resources.setResidueCacheNumber(notVipCacheNTime);
        resources.setUseCacheNumber(0);

        // 构建时长
        resources.setUseSceNumber(0);
        resources.setResidueSceNumber(notVipExecTime);
        return resources;
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





























package io.tiklab.matflow.setting.service;

import io.tiklab.core.exception.ApplicationException;
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
    private ResourcesDao resourcesDao;

    @Autowired
    private PipelineVersionService versionService;

    @Autowired
    private PipelineUtilService utilService;

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
        double cacheNumber = resources.getResidueCacheNumber();

        int sceNumber = resources.getResidueSceNumber();

        if (cacheNumber == 0 ){
            throw new ApplicationException("可使用的缓存已用完，无法执行！");
        }

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
        List<Resources> allResources = resourcesDao.findAllResources();
        Resources resources = new Resources();
        int version = versionService.version();
        // 1.免费 2.付费 可用资源总数
        if (version == 1){
            resources.setCcyNumber(3);
            resources.setSceNumber(1800);
            resources.setCacheNumber(-1);
        }else {
            resources.setCcyNumber(5);
            resources.setSceNumber(-1);
            resources.setCacheNumber(-1);
        }

        Map<String, String> execMap = PipelineExecServiceImpl.execMap;
        int size = execMap.size();

        // 并发数
        resources.setUseCcyNumber(size);
        if (version == 1){
            resources.setResidueCcyNumber(3- size);
        }else {
            resources.setResidueCcyNumber(5 - size);
        }

        // 磁盘数（社区版不限制磁盘大小）
        resources.setResidueCacheNumber(-1);
        resources.setUseCacheNumber(0);

        // 构建时长
        if (allResources.isEmpty()){
            resources.setUseSceNumber(0);
            if (version == 1){
                resources.setResidueSceNumber(1800);
            }
        }else {
            int useSceNumber = allResources.get(0).getUseSceNumber();
            resources.setUseSceNumber(useSceNumber);
            if (version == 1){
                resources.setResidueSceNumber(1800-useSceNumber);
            }
        }
        if (version == 2){
            resources.setResidueSceNumber(-1);
        }
        resources.setVersion(version);
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





























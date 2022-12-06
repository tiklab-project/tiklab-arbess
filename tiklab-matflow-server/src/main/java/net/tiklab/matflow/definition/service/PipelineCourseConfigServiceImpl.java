package net.tiklab.matflow.definition.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.core.exception.ApplicationException;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.definition.dao.PipelineCourseConfigDao;
import net.tiklab.matflow.definition.entity.PipelineCourseConfigEntity;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.definition.model.PipelineCourseConfig;
import net.tiklab.matflow.orther.service.PipelineHomeService;
import net.tiklab.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 配置顺序表操作。
 */

@Service
@Exporter
public class PipelineCourseConfigServiceImpl implements PipelineCourseConfigService {

    @Autowired
    private PipelineCourseConfigDao pipelineCourseConfigDao;

    @Autowired
    private PipelineHomeService homeService;

    @Autowired
    private PipelineCourseConfigTaskService courseConfigTaskService;

    @Autowired
    private JoinTemplate joinTemplate;

    private static final Logger logger = LoggerFactory.getLogger(PipelineCourseConfigServiceImpl.class);

    //创建流水线模板
    @Override
    public void createTemplate(String pipelineId, int type) {
        PipelineCourseConfig pipelineCourseConfig = new PipelineCourseConfig(pipelineId);
        int[] ints = switch (type) {
            case 2131 -> new int[]{1,21, 31};
            case 2132 -> new int[]{1,21, 32};
            case 112131 -> new int[]{1,11, 21, 31};
            case 112132 -> new int[]{1,11, 21, 32};
            case 2231 -> new int[]{1,22, 31};
            case 2232 -> new int[]{1,22, 32};
            default -> new int[]{1};
        };
        for (int anInt : ints) {
            pipelineCourseConfig.setTaskType(anInt);
            createConfig(pipelineCourseConfig);
        }
    }

    /**
     * 查询任务配置信息
     * @param pipelineId 流水线id
     * @return 配置信息
     */
    @Override
    public List<Object> findAllConfig(String pipelineId){
        List<PipelineCourseConfig> allCourseConfig = findAllCourseConfig(pipelineId);
        if (allCourseConfig == null){
            return null;
        }
        List<Object> list = new ArrayList<>();
        for (PipelineCourseConfig pipelineCourseConfig : allCourseConfig) {
            String configId = pipelineCourseConfig.getConfigId();
            int taskType = pipelineCourseConfig.getTaskType();
            int taskSort = pipelineCourseConfig.getTaskSort();
            Object config = courseConfigTaskService.findOneTaskConfig(configId,taskType,taskSort);
            list.add(config);
        }
        return list;
    }

    /**
     * 更新任务日志
     * @param config 流水线配置信息
     */
    @Override
    public void updateConfig(PipelineCourseConfig config){
        String configId = config.getConfigId();
        int taskType = config.getTaskType();
        PipelineCourseConfig oneConfig = findOneCourseConfig(configId);
        if (oneConfig == null){
           throw new ApplicationException(50001,"查询不到配置，配置id："+configId);
        }
        int oneTaskType = oneConfig.getTaskType();
        if (taskType == oneTaskType){
            courseConfigTaskService.updateTaskConfig(configId,taskType,config.getValues());
        }else {
            courseConfigTaskService.deleteTaskConfig(oneConfig.getConfigId(),oneConfig.getTaskType());
            courseConfigTaskService.createTaskConfig(configId,taskType);
            oneConfig.setTaskType(taskType);
            updateCourseConfig(oneConfig);
        }
    }

    /**
     * 创建流水线配置及关联任务
     * @param config 配置信息
     * @return 配置id
     */
    @Override
    public String createConfig(PipelineCourseConfig config){
        Pipeline pipeline = config.getPipeline();
        String pipelineId = pipeline.getPipelineId();
        String stagesId = config.getStagesId();
        //任务顺序
        int sort;
        List<PipelineCourseConfig> allCourseConfig;
        if (stagesId == null){
            allCourseConfig = findAllCourseConfig(pipelineId);
        }else {
            allCourseConfig = findAllStagesCourseConfig(stagesId);
        }
        sort = insertCourseConfig(allCourseConfig,config.getTaskSort());

        config.setTaskSort(sort);

        if (config.getTaskType() < 10){
            config.setTaskSort(1);
            validCode(pipeline.getPipelineId());
        }
        String configId = createCourseConfig(config);
        config.setConfigId(configId);
        courseConfigTaskService.createTaskConfig(configId,config.getTaskType());
        return configId;
    }

    /**
     * 插入配置
     * @param allCourseConfig 配置信息
     * @return 顺序
     */
    public int insertCourseConfig(List<PipelineCourseConfig> allCourseConfig,int sort){

        if (allCourseConfig == null || allCourseConfig.size() == 0){
            return 1 ;
        }

        int size = allCourseConfig.size();

        if (size < sort){
            return sort;
        }

        allCourseConfig.sort(Comparator.comparing(PipelineCourseConfig::getTaskSort));

        if (size == 1){
            PipelineCourseConfig pipelineCourseConfig = allCourseConfig.get(0);
            if (pipelineCourseConfig.getTaskType() < 10){
                return size+1;
            }
        }

        for (PipelineCourseConfig pipelineCourseConfig : allCourseConfig) {
            int taskSort = pipelineCourseConfig.getTaskSort();
            if (sort <= taskSort){
                pipelineCourseConfig.setTaskSort(taskSort+1);
                updateCourseConfig(pipelineCourseConfig);
            }
        }

        return sort;
    }

    /**
     * 效验配置必填字段
     * @param pipelineId 流水线id
     * @return 效验结果
     */
    @Override
    public List<String> configValid(String pipelineId){
        List<PipelineCourseConfig> allCourseConfig = findAllCourseConfig(pipelineId);
        if (allCourseConfig == null){
            return null;
        }
        List<String> list = new ArrayList<>();
        for (PipelineCourseConfig pipelineCourseConfig : allCourseConfig) {
            int taskType = pipelineCourseConfig.getTaskType();
            String configId = pipelineCourseConfig.getConfigId();
            courseConfigTaskService.validTaskConfig(configId,taskType,list);
        }
        return list;
    }

    /**
     * 更改顺序
     * @param config 配置
     */
    @Override
    public void updateOrderConfig(PipelineCourseConfig config){
        //更改后顺序
        int sort = config.getTaskSort();
        String configId = config.getConfigId();
        PipelineCourseConfig oneCourseConfig = findOneCourseConfig(configId);
        //更改前顺序
        int taskSort = oneCourseConfig.getTaskSort();

        // 0:未改变  length>0:顺序变大 length<0:顺序变小
        int length = sort - taskSort;
        if (length == 0){
            return;
        }

        String pipelineId = oneCourseConfig.getPipeline().getPipelineId();
        List<PipelineCourseConfig> allCourseConfig = findAllCourseConfig(pipelineId);
        for (PipelineCourseConfig pipelineCourseConfig : allCourseConfig) {
            int configTaskSort = pipelineCourseConfig.getTaskSort();
            if (length > 0 && configTaskSort >= taskSort){
                pipelineCourseConfig.setTaskSort(pipelineCourseConfig.getTaskSort()-1);
            }
            if (length < 0 && configTaskSort >= sort){
                pipelineCourseConfig.setTaskSort(pipelineCourseConfig.getTaskSort()+1);
            }
            updateCourseConfig(pipelineCourseConfig);
        }
        oneCourseConfig.setTaskSort(sort);
        updateCourseConfig(oneCourseConfig);

    }

    /**
     * 删除任务配置
     * @param configId 流水线id
     */
    @Override
    public void deleteConfig(String configId){
        PipelineCourseConfig oneConfig = findOneCourseConfig(configId);
        courseConfigTaskService.deleteTaskConfig(oneConfig.getConfigId(),oneConfig.getTaskType());
        int taskSort = oneConfig.getTaskSort();
        Pipeline pipeline = oneConfig.getPipeline();
        String pipelineId = pipeline.getPipelineId();
        deleteCourseConfig(configId);
        List<PipelineCourseConfig> allCourseConfig = findAllCourseConfig(pipelineId);
        if (allCourseConfig == null || allCourseConfig.size() == 0){
            return;
        }
        //更改后续顺序
        for (PipelineCourseConfig pipelineCourseConfig : allCourseConfig) {
            int sort = pipelineCourseConfig.getTaskSort();
            if (sort > taskSort){
                pipelineCourseConfig.setTaskSort(sort-1);
                updateCourseConfig(pipelineCourseConfig);
            }
        }
    }

    /**
     * 删除流水线所有配置
     * @param pipelineId 流水线id
     */
    @Override
    public void deleteAllConfig(String pipelineId){
        List<PipelineCourseConfig> allCourseConfig = findAllCourseConfig(pipelineId);
        if (allCourseConfig == null){
            return;
        }
        for (PipelineCourseConfig pipelineCourseConfig : allCourseConfig) {
            int taskType = pipelineCourseConfig.getTaskType();
            String configId = pipelineCourseConfig.getConfigId();
            courseConfigTaskService.deleteTaskConfig(configId,taskType);
        }
    }

    /**
     * 查询配置
     * @param pipelineId 流水线id
     * @return 配置集合
     */
    @Override
    public List<PipelineCourseConfig> findAllCourseConfig(String pipelineId){
        List<PipelineCourseConfig> allConfigOrder = findAllCourseConfig();
        if (allConfigOrder == null){
            return null;
        }
        List<PipelineCourseConfig> list = new ArrayList<>();
        for (PipelineCourseConfig pipelineCourseConfig : allConfigOrder) {
            Pipeline pipeline = pipelineCourseConfig.getPipeline();
            if (pipeline.getPipelineId().equals(pipelineId)){
                list.add(pipelineCourseConfig);
            }
        }
        list.sort(Comparator.comparing(PipelineCourseConfig::getTaskSort));
        return list;
    }

    /**
     * 查询一个阶段下的所有任务
     * @param stagesId 阶段id
     * @return 配置集合
     */
    @Override
    public List<Object> findAllStagesConfig(String stagesId){
        List<PipelineCourseConfig> allStagesCourseConfig = findAllStagesCourseConfig(stagesId);
        if (allStagesCourseConfig == null){
            return null;
        }
        List<Object> list = new ArrayList<>();
        for (PipelineCourseConfig pipelineCourseConfig : allStagesCourseConfig) {
            int taskSort = pipelineCourseConfig.getTaskSort();
            int taskType = pipelineCourseConfig.getTaskType();
            String configId = pipelineCourseConfig.getConfigId();
            Object config = courseConfigTaskService.findOneTaskConfig(configId,taskType,taskSort);
            list.add(config);
        }
        return list;
    }

    /**
     * 查询一个阶段下的所有配置
     * @param stagesId 阶段id
     * @return 配置
     */
    @Override
    public List<PipelineCourseConfig> findAllStagesCourseConfig(String stagesId){
        List<PipelineCourseConfig> allCourseConfig = findAllCourseConfig();
        if (allCourseConfig == null || allCourseConfig.size() == 0){
            return null;
        }
        List<PipelineCourseConfig> list = new ArrayList<>();
        for (PipelineCourseConfig pipelineCourseConfig : allCourseConfig) {
            String id = pipelineCourseConfig.getStagesId();
            if (id == null || !id.equals(stagesId)){
                continue;
            }
            list.add(pipelineCourseConfig);
        }
        list.sort(Comparator.comparing(PipelineCourseConfig::getTaskSort));
        return list;
    }

    //效验是否存在代码源
    public void validCode(String pipelineId){
        List<PipelineCourseConfig> allCourseConfig = findAllCourseConfig(pipelineId);
        if (allCourseConfig == null){
            return ;
        }
        for (PipelineCourseConfig pipelineCourseConfig : allCourseConfig) {
            int taskType = pipelineCourseConfig.getTaskType();
            if (taskType < 10){
                throw new ApplicationException(50001,"添加错误，无法添加两个代码源");
            }
            int sort = pipelineCourseConfig.getTaskSort();
            pipelineCourseConfig.setTaskSort(sort+1);
            updateCourseConfig(pipelineCourseConfig);
        }
    }

    //更新配置
    public void updateCourseConfig(PipelineCourseConfig config){
        PipelineCourseConfigEntity configEntity = BeanMapper.map(config, PipelineCourseConfigEntity.class);
        pipelineCourseConfigDao.updateConfigure(configEntity);
    }

    //创建配置
    public String createCourseConfig(PipelineCourseConfig config){
        PipelineCourseConfigEntity configEntity = BeanMapper.map(config, PipelineCourseConfigEntity.class);
        return pipelineCourseConfigDao.createConfigure(configEntity);
    }

    //删除配置
    public void deleteCourseConfig(String configId) {
        pipelineCourseConfigDao.deleteConfigure(configId);
    }

    @Override
    public PipelineCourseConfig findOneCourseConfig(String configId){
        PipelineCourseConfigEntity oneConfigure = pipelineCourseConfigDao.findOneConfigure(configId);
        return BeanMapper.map(oneConfigure, PipelineCourseConfig.class);
    }

    @Override
    public List<PipelineCourseConfig> findAllCourseConfigList(List<String> idList) {
        List<PipelineCourseConfigEntity> allConfigureList = pipelineCourseConfigDao.findAllConfigureList(idList);
        return BeanMapper.mapList(allConfigureList, PipelineCourseConfig.class);
    }

    @Override
    public List<PipelineCourseConfig> findAllCourseConfig(){
        List<PipelineCourseConfigEntity> allConfigure = pipelineCourseConfigDao.findAllConfigure();
        List<PipelineCourseConfig> pipelineCourseConfigs = BeanMapper.mapList(allConfigure, PipelineCourseConfig.class);
        joinTemplate.joinQuery(pipelineCourseConfigs);
        return pipelineCourseConfigs;
    }

}



































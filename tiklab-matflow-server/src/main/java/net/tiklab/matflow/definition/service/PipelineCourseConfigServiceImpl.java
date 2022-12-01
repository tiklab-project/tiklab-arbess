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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static net.tiklab.matflow.orther.service.PipelineFinal.*;

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
    private PipelineCourseConfigTaskService pipelineCourseConfigTaskService;

    @Autowired
    private JoinTemplate joinTemplate;

    private static final Logger logger = LoggerFactory.getLogger(PipelineCourseConfigServiceImpl.class);

    //删除流水线配置
    @Override
    public void deleteConfig(String pipelineId) {
        List<PipelineCourseConfig> allPipelineConfig = findAllPipelineConfig(pipelineId);
        if (allPipelineConfig == null) { return; }
        for (PipelineCourseConfig pipelineCourseConfig : allPipelineConfig) {
            pipelineCourseConfig.setMessage("delete");
            updateConfig(pipelineCourseConfig);
        }
    }

    //创建流水线模板
    @Override
    public void createTemplate(String pipelineId, int type) {
        PipelineCourseConfig pipelineCourseConfig = new PipelineCourseConfig(pipelineId,"create");
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
            updateConfig(pipelineCourseConfig);
        }
    }

    //查询流水线配置
    @Override
    public List<Object> findAllConfig(String pipelineId){
        List<PipelineCourseConfig> allPipelineConfig = findAllPipelineConfig(pipelineId);
        if (allPipelineConfig == null){
            return Collections.emptyList();
        }
        return pipelineCourseConfigTaskService.findAllConfig(allPipelineConfig);
    }

    //更新流水线配置
    @Override
    public void updateConfig(PipelineCourseConfig config){
        joinTemplate.joinQuery(config);
        String message = config.getMessage();
        //判断配置类型
        switch (message) {
            case "create" -> createConfig(config,message);
            case "update" -> updateConfig(config,message);
            case "delete" -> deleteConfig(config,message);
            case "updateType" ->  updateType(config,message);
            case "order" ->  updateOrder(config);
            default -> throw new ApplicationException(50001, "配置失败，未知的操作类型。");
        }
    }

    //查询流水线的所有配置
    @Override
    public List<PipelineCourseConfig> findAllPipelineConfig(String pipelineId){
        List<PipelineCourseConfigEntity> allConfigure = pipelineCourseConfigDao.findAllConfigure(pipelineId);
        if (allConfigure == null || allConfigure.isEmpty()) return Collections.emptyList();
        List<PipelineCourseConfig> pipelineCourseConfigs = BeanMapper.mapList(allConfigure, PipelineCourseConfig.class);
        //排序
        pipelineCourseConfigs.sort(Comparator.comparing(PipelineCourseConfig::getTaskSort));
        joinTemplate.joinQuery(pipelineCourseConfigs);
        return pipelineCourseConfigs;
    }

    //获取配置详情
    @Override
    public Object findOneConfig(String pipelineId,int type){
        PipelineCourseConfig typeConfig = findTypeConfig(pipelineId, type);
        if (typeConfig == null){
            return null;
        }
        return pipelineCourseConfigTaskService.findOneConfig(typeConfig);
    }

    //效验字段
    @Override
    public Map<String, String> configValid(String pipelineId){
         List<PipelineCourseConfig> allPipelineConfig = findAllPipelineConfig(pipelineId);
         if (allPipelineConfig == null){
             return Collections.emptyMap();
         }
        return pipelineCourseConfigTaskService.configValid(allPipelineConfig);
     }

    /**
     * 更新配置
     * @param config 配置阻断字段
     * @param message 配置类型
     */
    public void updateConfig(PipelineCourseConfig config, String message){
        String pipelineId = config.getPipeline().getPipelineId();
        PipelineCourseConfig typeConfig = findTypeConfig(pipelineId, config.getTaskType());
        if (typeConfig == null) return;
        joinTemplate.joinQuery(typeConfig);
        Map<String, String> map = pipelineCourseConfigTaskService.config(message,config,typeConfig);
        map.putAll(homeService.initMap(typeConfig.getPipeline()));
        homeService.log(LOG_PIPELINE_CONFIG, LOG_MD_PIPELINE_UPDATE, LOG_TEM_PIPELINE_CONFIG_UPDATE, map);
    }

    /**
     * 更改配置类型
     * @param config 配置
     * @param message 类型
     */
    public void updateType(PipelineCourseConfig config, String message){
        Pipeline pipeline = config.getPipeline();
        PipelineCourseConfig typeConfig = findTypeConfig(pipeline.getPipelineId(), config.getTaskType());
        joinTemplate.joinQuery(typeConfig);
        Map<String, String> map = pipelineCourseConfigTaskService.config(message,typeConfig,typeConfig);
        map.putAll(homeService.initMap(pipeline));
        typeConfig.setTaskType(config.getType());
        typeConfig.setTaskId(map.get("id"));
        updateConfigOrder(typeConfig);
        homeService.log(LOG_PIPELINE_CONFIG, LOG_MD_PIPELINE_UPDATE, LOG_TEM_PIPELINE_CONFIG_UPDATE, map);
    }

    /**
     * 创建配置
     * @param config 配置阻断字段
     * @param message 配置类型
     */
    public void createConfig(PipelineCourseConfig config , String message){
        int taskType = config.getTaskType();
        Pipeline pipeline = config.getPipeline();
        String pipelineId = pipeline.getPipelineId();

        //判断是否存在相同配置
        PipelineCourseConfig typeConfig = findTypeConfig(pipelineId, config.getTaskType());
        if (typeConfig != null){
            throw new ApplicationException(50001,"已存在相同类型配置");
        }

        //获取已存在的配置，对新增加的配置添加排序
        int size = 1;
        List<PipelineCourseConfig> allPipelineConfig = findAllPipelineConfig(pipelineId);
        if (allPipelineConfig!= null){
            size = allPipelineConfig.size()+1;
        }

        PipelineCourseConfig configOrder = new PipelineCourseConfig(config.getTaskType(),size,pipelineId);
        //判断添加的配置是否为源码
        if (taskType/10 == 0){
            //更改其他配置顺序
            updateConfigOrder(new PipelineCourseConfig(pipelineId),true);
            configOrder.setTaskSort(1);
        }

        Map<String, String> map = pipelineCourseConfigTaskService.config(message,config, null);
        map.putAll(homeService.initMap(pipeline));

        configOrder.setTaskId(map.get("id"));
        String configure = createConfigure(configOrder);
        if (configure == null){
            throw new ApplicationException(50001,"配置添加失败。");
        }

        homeService.log(LOG_PIPELINE_CONFIG, LOG_MD_PIPELINE_CREATE,LOG_TEM_PIPELINE_CONFIG_CREATE, map);
    }

    /**
     * 删除配置
     * @param config 配置
     * @param message 配置类型
     */
    public void deleteConfig(PipelineCourseConfig config, String message){
        String pipelineId = config.getPipeline().getPipelineId();
        int type = config.getTaskType();
        PipelineCourseConfig typeConfig = findTypeConfig(pipelineId, type);
        if (typeConfig == null) return;

        updateConfigOrder(typeConfig,false);

        pipelineCourseConfigDao.deleteConfigure(typeConfig.getConfigId());
        Map<String, String> map = pipelineCourseConfigTaskService.config(message,typeConfig,null);
        map.putAll(homeService.initMap(typeConfig.getPipeline()));

        homeService.log(LOG_PIPELINE_CONFIG, LOG_MD_PIPELINE_DELETE,LOG_TEM_PIPELINE_CONFIG_DELETE, map);
    }

    /**
     * 更改配置顺序
     * @param config 配置
     */
    public void updateOrder(PipelineCourseConfig config) {
        String pipelineId = config.getPipeline().getPipelineId();
        int taskSort = config.getTaskSort();
        int sort = config.getSort();
        List<PipelineCourseConfig> allPipelineConfig = findAllPipelineConfig(pipelineId);
        if (allPipelineConfig == null)return;

        PipelineCourseConfig taskConfigOrder = allPipelineConfig.get(taskSort-1);
        taskConfigOrder.setTaskSort(sort);
        updateConfigOrder(taskConfigOrder);

        //如果相邻直接交换顺序
        if (taskSort-sort == 1 || taskSort-sort == -1){
            PipelineCourseConfig configOrder = allPipelineConfig.get(sort-1);
            configOrder.setTaskSort(taskSort);
            updateConfigOrder(configOrder);
            return;
        }
        if (taskSort > sort){
            for (int i = sort; i <= taskSort - 1; i++) {
                PipelineCourseConfig pipelineCourseConfig = allPipelineConfig.get(i);
                pipelineCourseConfig.setTaskSort(i + 1);
                updateConfigOrder(pipelineCourseConfig);
            }
        }else {
            for (int i = taskSort; i <= sort - 1; i++) {
                PipelineCourseConfig pipelineCourseConfig = allPipelineConfig.get(i);
                pipelineCourseConfig.setTaskSort(i);
                updateConfigOrder(pipelineCourseConfig);
            }
        }
    }

    /**
     * 查询配置
     * @param pipelineId 流水线id
     * @param type 类型
     * @return 配置信息
     */
    public PipelineCourseConfig findTypeConfig(String pipelineId, int type){
        List<PipelineCourseConfig> allPipelineConfig = findAllPipelineConfig(pipelineId);
        if (allPipelineConfig == null || allPipelineConfig.isEmpty()) return null;
        for (PipelineCourseConfig pipelineCourseConfig : allPipelineConfig) {
            int taskType = pipelineCourseConfig.getTaskType();
            if (taskType/10 != type/10){
               continue;
            }
            return pipelineCourseConfig;
        }
        return null;
    }

    /**
     * 删除配置（或添加源码配置）后的配置顺序更改
     * @param typeConfig 配置
     * @param b true 创建源码配置 false 删除配置
     */
    public void updateConfigOrder(PipelineCourseConfig typeConfig, boolean b){
        String pipelineId = typeConfig.getPipeline().getPipelineId();
        List<PipelineCourseConfig> allPipelineConfig = findAllPipelineConfig(pipelineId);
        if (allPipelineConfig == null) return ;
        if (b){
            for (PipelineCourseConfig pipelineCourseConfig : allPipelineConfig) {
                pipelineCourseConfig.setTaskSort(pipelineCourseConfig.getTaskSort() + 1);
                updateConfigOrder(pipelineCourseConfig);
            }
            return;
        }
        int taskSort = typeConfig.getTaskSort();
        if (taskSort == allPipelineConfig.size()) return;
        for (int i = taskSort-1; i < allPipelineConfig.size(); i++) {
            PipelineCourseConfig pipelineCourseConfig = allPipelineConfig.get(i);
            pipelineCourseConfig.setTaskSort(pipelineCourseConfig.getTaskSort()-1);
            updateConfigOrder(pipelineCourseConfig);
        }

    }

    //创建排序配置
    public String createConfigure(PipelineCourseConfig pipelineCourseConfig){
        PipelineCourseConfigEntity configOrder = BeanMapper.map(pipelineCourseConfig, PipelineCourseConfigEntity.class);
        return pipelineCourseConfigDao.createConfigure(configOrder);
    }

    //更新配置信息
    public void updateConfigOrder(PipelineCourseConfig configOrder){
        PipelineCourseConfigEntity configOrderEntity = BeanMapper.map(configOrder, PipelineCourseConfigEntity.class);
        pipelineCourseConfigDao.updateConfigure(configOrderEntity);
    }

    //查询单个配置
    public PipelineCourseConfig findOneConfig(String configId){
        PipelineCourseConfigEntity oneConfigure = pipelineCourseConfigDao.findOneConfigure(configId);
        return BeanMapper.map(oneConfigure, PipelineCourseConfig.class);
    }

    public List<PipelineCourseConfig> findAllConfigOrderList(List<String> idList) {
        List<PipelineCourseConfigEntity> allConfigureList = pipelineCourseConfigDao.findAllConfigureList(idList);
        return BeanMapper.mapList(allConfigureList, PipelineCourseConfig.class);
    }

    public List<PipelineCourseConfig> findAllConfigOrder(){
        List<PipelineCourseConfigEntity> allConfigure = pipelineCourseConfigDao.findAllConfigure();
        List<PipelineCourseConfig> pipelineCourseConfigs = BeanMapper.mapList(allConfigure, PipelineCourseConfig.class);
        joinTemplate.joinQuery(pipelineCourseConfigs);
        return pipelineCourseConfigs;
    }

}



































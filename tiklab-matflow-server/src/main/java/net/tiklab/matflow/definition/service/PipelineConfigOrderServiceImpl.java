package net.tiklab.matflow.definition.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.core.exception.ApplicationException;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.definition.dao.PipelineConfigOrderDao;
import net.tiklab.matflow.definition.entity.PipelineConfigOrderEntity;
import net.tiklab.matflow.definition.model.*;
import net.tiklab.matflow.orther.service.PipelineHomeService;
import net.tiklab.matflow.orther.service.PipelineUntil;
import net.tiklab.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import static net.tiklab.matflow.orther.service.PipelineUntil.*;

/**
 * 配置顺序表操作。
 */

@Service
@Exporter
public class PipelineConfigOrderServiceImpl implements PipelineConfigOrderService {

    @Autowired
    private PipelineConfigOrderDao pipelineConfigOrderDao;

    @Autowired
    private PipelineHomeService homeService;

    @Autowired
    private PipelineConfigService pipelineConfigService;

    @Autowired
    private JoinTemplate joinTemplate;

    private static final Logger logger = LoggerFactory.getLogger(PipelineConfigOrderServiceImpl.class);

    //删除流水线配置
    @Override
    public void deleteConfig(String pipelineId) {
        List<PipelineConfigOrder> allPipelineConfig = findAllPipelineConfig(pipelineId);
        if (allPipelineConfig == null) { return; }
        for (PipelineConfigOrder pipelineConfigOrder : allPipelineConfig) {
            pipelineConfigOrder.setMessage("delete");
            updateConfig(pipelineConfigOrder);
        }
    }

    //创建流水线模板
    @Override
    public void createTemplate(String pipelineId, int type) {
        PipelineConfigOrder pipelineConfigOrder = new PipelineConfigOrder(pipelineId,"create");
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
            pipelineConfigOrder.setTaskType(anInt);
            updateConfig(pipelineConfigOrder);
        }
    }

    //查询流水线配置
    @Override
    public List<Object> findAllConfig(String pipelineId){
        List<PipelineConfigOrder> allPipelineConfig = findAllPipelineConfig(pipelineId);
        if (allPipelineConfig == null){
            return Collections.emptyList();
        }
        return pipelineConfigService.findAllConfig(allPipelineConfig);
    }

    //更新流水线配置
    @Override
    public void updateConfig(PipelineConfigOrder config){
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
    public List<PipelineConfigOrder> findAllPipelineConfig(String pipelineId){
        List<PipelineConfigOrderEntity> allConfigure = pipelineConfigOrderDao.findAllConfigure(pipelineId);
        if (allConfigure == null || allConfigure.isEmpty()) return Collections.emptyList();
        List<PipelineConfigOrder> pipelineConfigOrders = BeanMapper.mapList(allConfigure, PipelineConfigOrder.class);
        //排序
        pipelineConfigOrders.sort(Comparator.comparing(PipelineConfigOrder::getTaskSort));
        joinTemplate.joinQuery(pipelineConfigOrders);
        return pipelineConfigOrders;
    }

    //获取配置详情
    @Override
    public Object findOneConfig(String pipelineId,int type){
        PipelineConfigOrder typeConfig = findTypeConfig(pipelineId, type);
        if (typeConfig == null){
            return null;
        }
        return pipelineConfigService.findOneConfig(typeConfig);
    }

    //效验字段
    @Override
    public Map<String, String> configValid(String pipelineId){
         List<PipelineConfigOrder> allPipelineConfig = findAllPipelineConfig(pipelineId);
         if (allPipelineConfig == null){
             return Collections.emptyMap();
         }
        return pipelineConfigService.configValid(allPipelineConfig);
     }

    /**
     * 更新配置
     * @param config 配置阻断字段
     * @param message 配置类型
     */
    public void updateConfig(PipelineConfigOrder config,String message){
        String pipelineId = config.getPipeline().getPipelineId();
        PipelineConfigOrder typeConfig = findTypeConfig(pipelineId, config.getTaskType());
        if (typeConfig == null) return;
        joinTemplate.joinQuery(typeConfig);
        Map<String, String> map = pipelineConfigService.config(message,config,typeConfig);
        map.putAll(PipelineUntil.initMap(typeConfig.getPipeline()));
        homeService.log(LOG_PIPELINE_CONFIG, LOG_MD_PIPELINE_UPDATE, LOG_TEM_PIPELINE_CONFIG_UPDATE, map);
    }

    /**
     * 更改配置类型
     * @param config 配置
     * @param message 类型
     */
    public void updateType(PipelineConfigOrder config,String message){
        Pipeline pipeline = config.getPipeline();
        PipelineConfigOrder typeConfig = findTypeConfig(pipeline.getPipelineId(), config.getTaskType());
        joinTemplate.joinQuery(typeConfig);
        Map<String, String> map = pipelineConfigService.config(message,config,typeConfig);
        map.putAll(initMap(pipeline));
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
    public void createConfig(PipelineConfigOrder config ,String message){
        int taskType = config.getTaskType();
        Pipeline pipeline = config.getPipeline();
        String pipelineId = pipeline.getPipelineId();

        //判断是否存在相同配置
        PipelineConfigOrder typeConfig = findTypeConfig(pipelineId, config.getTaskType());
        if (typeConfig != null){
            throw new ApplicationException(50001,"已存在相同类型配置");
        }

        //获取已存在的配置，对新增加的配置添加排序
        int size = 1;
        List<PipelineConfigOrder> allPipelineConfig = findAllPipelineConfig(pipelineId);
        if (allPipelineConfig!= null){
            size = allPipelineConfig.size()+1;
        }

        PipelineConfigOrder configOrder = new PipelineConfigOrder(config.getTaskType(),size,pipelineId);
        //判断添加的配置是否为源码
        if (taskType/10 == 0){
            //更改其他配置顺序
            updateConfigOrder(new PipelineConfigOrder(pipelineId),true);
            configOrder.setTaskSort(1);
        }

        Map<String, String> map = pipelineConfigService.config(message,config, null);
        map.putAll(PipelineUntil.initMap(pipeline));

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
    public void deleteConfig(PipelineConfigOrder config,String message){
        String pipelineId = config.getPipeline().getPipelineId();
        int type = config.getTaskType();
        PipelineConfigOrder typeConfig = findTypeConfig(pipelineId, type);
        if (typeConfig == null) return;

        updateConfigOrder(typeConfig,false);

        pipelineConfigOrderDao.deleteConfigure(typeConfig.getConfigId());
        Map<String, String> map = pipelineConfigService.config(message,typeConfig,null);
        map.putAll(PipelineUntil.initMap(typeConfig.getPipeline()));

        homeService.log(LOG_PIPELINE_CONFIG, LOG_MD_PIPELINE_DELETE,LOG_TEM_PIPELINE_CONFIG_DELETE, map);
    }

    /**
     * 更改配置顺序
     * @param config 配置
     */
    public void updateOrder(PipelineConfigOrder config) {
        String pipelineId = config.getPipeline().getPipelineId();
        int taskSort = config.getTaskSort();
        int sort = config.getSort();
        List<PipelineConfigOrder> allPipelineConfig = findAllPipelineConfig(pipelineId);
        if (allPipelineConfig == null)return;

        PipelineConfigOrder taskConfigOrder = allPipelineConfig.get(taskSort-1);
        taskConfigOrder.setTaskSort(sort);
        updateConfigOrder(taskConfigOrder);

        //如果相邻直接交换顺序
        if (taskSort-sort == 1 || taskSort-sort == -1){
            PipelineConfigOrder configOrder = allPipelineConfig.get(sort-1);
            configOrder.setTaskSort(taskSort);
            updateConfigOrder(configOrder);
            return;
        }
        if (taskSort > sort){
            for (int i = sort; i <= taskSort - 1; i++) {
                PipelineConfigOrder pipelineConfigOrder = allPipelineConfig.get(i);
                pipelineConfigOrder.setTaskSort(i + 1);
                updateConfigOrder(pipelineConfigOrder);
            }
        }else {
            for (int i = taskSort; i <= sort - 1; i++) {
                PipelineConfigOrder pipelineConfigOrder = allPipelineConfig.get(i);
                pipelineConfigOrder.setTaskSort(i);
                updateConfigOrder(pipelineConfigOrder);
            }
        }
    }

    /**
     * 查询配置
     * @param pipelineId 流水线id
     * @param type 类型
     * @return 配置信息
     */
    public PipelineConfigOrder findTypeConfig(String pipelineId,int type){
        List<PipelineConfigOrder> allPipelineConfig = findAllPipelineConfig(pipelineId);
        if (allPipelineConfig == null || allPipelineConfig.isEmpty()) return null;
        for (PipelineConfigOrder pipelineConfigOrder : allPipelineConfig) {
            int taskType = pipelineConfigOrder.getTaskType();
            if (taskType/10 != type/10){
               continue;
            }
            return pipelineConfigOrder;
        }
        return null;
    }

    /**
     * 删除配置（或添加源码配置）后的配置顺序更改
     * @param typeConfig 配置
     * @param b true 创建源码配置 false 删除配置
     */
    public void updateConfigOrder(PipelineConfigOrder typeConfig,boolean b){
        String pipelineId = typeConfig.getPipeline().getPipelineId();
        List<PipelineConfigOrder> allPipelineConfig = findAllPipelineConfig(pipelineId);
        if (allPipelineConfig == null) return ;
        if (b){
            for (PipelineConfigOrder pipelineConfigOrder : allPipelineConfig) {
                pipelineConfigOrder.setTaskSort(pipelineConfigOrder.getTaskSort() + 1);
                updateConfigOrder(pipelineConfigOrder);
            }
            return;
        }
        int taskSort = typeConfig.getTaskSort();
        if (taskSort == allPipelineConfig.size()) return;
        for (int i = taskSort-1; i < allPipelineConfig.size(); i++) {
            PipelineConfigOrder pipelineConfigOrder = allPipelineConfig.get(i);
            pipelineConfigOrder.setTaskSort(pipelineConfigOrder.getTaskSort()-1);
            updateConfigOrder(pipelineConfigOrder);
        }

    }

    //创建排序配置
    public String createConfigure(PipelineConfigOrder pipelineConfigOrder){
        PipelineConfigOrderEntity configOrder = BeanMapper.map(pipelineConfigOrder, PipelineConfigOrderEntity.class);
        return pipelineConfigOrderDao.createConfigure(configOrder);
    }

    //更新配置信息
    public void updateConfigOrder(PipelineConfigOrder configOrder){
        PipelineConfigOrderEntity configOrderEntity = BeanMapper.map(configOrder, PipelineConfigOrderEntity.class);
        pipelineConfigOrderDao.updateConfigure(configOrderEntity);
    }

    //查询单个配置
    public PipelineConfigOrder findOneConfig(String configId){
        PipelineConfigOrderEntity oneConfigure = pipelineConfigOrderDao.findOneConfigure(configId);
        return BeanMapper.map(oneConfigure, PipelineConfigOrder.class);
    }

    public List<PipelineConfigOrder> findAllConfigOrderList(List<String> idList) {
        List<PipelineConfigOrderEntity> allConfigureList = pipelineConfigOrderDao.findAllConfigureList(idList);
        return BeanMapper.mapList(allConfigureList, PipelineConfigOrder.class);
    }

    public List<PipelineConfigOrder> findAllConfigOrder(){
        List<PipelineConfigOrderEntity> allConfigure = pipelineConfigOrderDao.findAllConfigure();
        List<PipelineConfigOrder> pipelineConfigOrders = BeanMapper.mapList(allConfigure, PipelineConfigOrder.class);
        joinTemplate.joinQuery(pipelineConfigOrders);
        return pipelineConfigOrders;
    }

}



































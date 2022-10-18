package net.tiklab.matflow.definition.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.definition.dao.PipelineConfigOrderDao;
import net.tiklab.matflow.definition.entity.PipelineConfigOrderEntity;
import net.tiklab.matflow.definition.model.*;
import net.tiklab.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 维护配置关联信息
 */

@Service
@Exporter
public class PipelineConfigOrderServiceImpl implements PipelineConfigOrderService {

    @Autowired
    PipelineConfigOrderDao pipelineConfigOrderDao;

    @Autowired
    PipelineCodeService pipelineCodeService;

    @Autowired
    PipelineBuildService pipelineBuildService;

    @Autowired
    PipelineTestService pipelineTestService;

    @Autowired
    PipelineDeployService pipelineDeployService;

    private static final Logger logger = LoggerFactory.getLogger(PipelineConfigOrderServiceImpl.class);

    //删除流水线配置
    @Override
    public void deleteConfig(String pipelineId){
        //PipelineCode code = pipelineCodeService.findCode(pipelineId);
        //if (code != null){
        //    pipelineCodeService.deleteCode(code.getCodeId());
        //}
        //
        //PipelineTest test = pipelineTestService.findTest(pipelineId);
        //if (test != null){
        //    pipelineTestService.deleteTest(test.getTestId());
        //}
        //
        //PipelineBuild build = pipelineBuildService.findBuild(pipelineId);
        //if(build!= null){
        //    pipelineBuildService.deleteBuild(build.getBuildId());
        //}
        //
        //PipelineDeploy deploy = pipelineDeployService.findDeploy(pipelineId);
        //if (deploy != null){
        //    pipelineDeployService.deleteDeploy(deploy.getDeployId());
        //}

    }

    //查询流水线配置
    @Override
    public List<Object> findAllConfig(String pipelineId){
        List<Object> list = new ArrayList<>();
        List<PipelineConfigOrder> allPipelineConfig = findAllPipelineConfig(pipelineId);
        if (allPipelineConfig == null){
            return null;
        }

        for (PipelineConfigOrder pipelineConfigOrder : allPipelineConfig) {
            String taskId = pipelineConfigOrder.getTaskId();
            int taskSort = pipelineConfigOrder.getTaskSort();
            int type = pipelineConfigOrder.getTaskType();
            if (type < 10){
                PipelineCode code = pipelineCodeService.findOneCode(taskId);
                code.setType(type);
                code.setSort(taskSort);
                list.add(code);
            }else if (10<type && type<20){
                PipelineTest test = pipelineTestService.findOneTest(taskId);
                test.setType(type);
                test.setSort(taskSort);
                list.add(test);
            }else if (20<type && type<30){
                PipelineBuild build = pipelineBuildService.findOneBuild(taskId);
                build.setType(type);
                build.setSort(taskSort);
                list.add(build);
            }else if (30<type && type<40){
                PipelineDeploy deploy = pipelineDeployService.findOneDeploy(taskId);
                deploy.setType(type);
                deploy.setSort(taskSort);
                list.add(deploy);
            }
        }
        return list;
    }

    //更新流水线配置
    @Override
    public void updateConfig(PipelineConfigOrder config){
        int type = config.getTaskType();
        String pipelineId = config.getPipelineId();
        String types = null;
        String message = config.getMessage();
        switch (type) {
            case 1,2,3,4,5 -> types = "code";
            case 11,12,13,14 -> types = "test";
            case 21,22,23,24 -> types = "build";
            case 31,32,33 -> types = "deploy";
        }
        if (types == null){
            throw new ApplicationException(50001, "请选择类型");
        }

        if (message == null){
            throw new ApplicationException(50001, "配置失败，无法获取操作属性。");
        }

        //判断配置类型
        switch (message) {
            case "create" -> createConfig(config,types);
            case "update" -> updateOneConfig(config,types);
            case "delete" -> deleteConfig(pipelineId,types,type);
            case "order" -> updateOrder(pipelineId,config.getTaskSort(),config.getSort());
        }
    }

    //查询流水线的所有配置
    @Override
    public List<PipelineConfigOrder> findAllPipelineConfig(String pipelineId){
        List<PipelineConfigOrderEntity> allConfigure = pipelineConfigOrderDao.findAllConfigure(pipelineId);
        if (allConfigure == null || allConfigure.size() == 0) return null;
        List<PipelineConfigOrder> pipelineConfigOrders = BeanMapper.mapList(allConfigure, PipelineConfigOrder.class);
        //排序
        pipelineConfigOrders.sort(Comparator.comparing(PipelineConfigOrder::getTaskSort));
        return pipelineConfigOrders;
    }

    //获取配置详情
    @Override
    public PipelineConfigOrder findOneConfig(String pipelineId,int type){
        PipelineConfigOrder typeConfig = findTypeConfig(pipelineId, type);
        if (typeConfig == null){
            return null;
        }
        String taskId = typeConfig.getTaskId();
        int taskType = typeConfig.getTaskType();
        if (type < 10){
            PipelineCode oneCode = pipelineCodeService.findOneCode(taskId);
            oneCode.setType(taskType);
            typeConfig.setPipelineCode(oneCode);
        }else if (10<type && type<20){
            PipelineTest oneTest = pipelineTestService.findOneTest(taskId);
            oneTest.setType(taskType);
            typeConfig.setPipelineTest(oneTest);
        }else if (20<type && type<30){
            PipelineBuild oneBuild = pipelineBuildService.findOneBuild(taskId);
            oneBuild.setType(taskType);
            typeConfig.setPipelineBuild(oneBuild);
        }else if (30<type && type<40){
            PipelineDeploy oneDeploy = pipelineDeployService.findOneDeploy(taskId);
            oneDeploy.setType(taskType);
            typeConfig.setPipelineDeploy(oneDeploy);
        }
        return typeConfig;
    }


    /**
     * 更改配置顺序
     * @param pipelineId 流水线id
     * @param taskSort 顺序
     * @param sort 更改后顺序
     */
    private void updateOrder(String pipelineId,int sort, int taskSort) {
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
            for (int i = sort; i <= taskSort-1; i++) {
                PipelineConfigOrder pipelineConfigOrder = allPipelineConfig.get(i);
                pipelineConfigOrder.setTaskSort(i+1);
                updateConfigOrder(pipelineConfigOrder);
            }
        }else {
            for (int i = taskSort; i <= sort-1; i++) {
                PipelineConfigOrder pipelineConfigOrder = allPipelineConfig.get(i);
                pipelineConfigOrder.setTaskSort(i);
                updateConfigOrder(pipelineConfigOrder);
            }
        }
    }

    /**
     * 更新配置
     * @param config 配置阻断字段
     * @param types 配置类型
     */
    private void updateOneConfig(PipelineConfigOrder config,String types){
        String pipelineId = config.getPipelineId();
        PipelineConfigOrder typeConfig = findTypeConfig(pipelineId, config.getTaskType());
        if (typeConfig == null) return;
        switch (types) {
            case "code" -> {
                PipelineCode pipelineCode = config.getPipelineCode();
                pipelineCode.setCodeId(typeConfig.getTaskId());
                pipelineCodeService.updateCode(pipelineCode);
            }
            case "test" -> {
                PipelineTest pipelineTest = config.getPipelineTest();
                pipelineTest.setTestId(typeConfig.getTaskId());
                pipelineTestService.updateTest(pipelineTest);
            }
            case "build" -> {
                PipelineBuild pipelineBuild = config.getPipelineBuild();
                pipelineBuild.setBuildId(typeConfig.getTaskId());
                pipelineBuildService.updateBuild(pipelineBuild);
            }
            case "deploy" -> {
                PipelineDeploy pipelineDeploy =  config.getPipelineDeploy();
                pipelineDeploy.setDeployId(typeConfig.getTaskId());
                pipelineDeployService.updateDeploy(pipelineDeploy);
            }
        }
    }

    /**
     * 创建配置
     * @param config 配置阻断字段
     * @param types 配置类型
     */
    private void createConfig(PipelineConfigOrder config ,String types){
        int type = config.getTaskType();
        String pipelineId = config.getPipelineId();
        int size = 1;
        List<PipelineConfigOrder> allPipelineConfig = findAllPipelineConfig(pipelineId);
        if (allPipelineConfig!= null){
            size = allPipelineConfig.size()+1;
        }
        PipelineConfigOrder configOrder = new PipelineConfigOrder();
        configOrder.setPipelineId(pipelineId);
        configOrder.setTaskType(type);
        configOrder.setTaskSort(size);
        switch (types) {
            case "code" -> {
                updateConfigOrder(new PipelineConfigOrder(pipelineId),true);
                PipelineCode pipelineCode = new PipelineCode();
                String codeId = pipelineCodeService.createCode(pipelineCode);
                if (codeId == null){
                    throw new ApplicationException(50001,"添加失败");
                }
                configOrder.setTaskId(codeId);
                configOrder.setTaskSort(1);
            }
            case "test" -> {
                PipelineTest pipelineTest = new PipelineTest();
                String testId = pipelineTestService.createTest(pipelineTest);
                if (testId == null){
                    throw new ApplicationException(50001,"添加失败");
                }
                configOrder.setTaskId(testId);
            }
            case "build" -> {
                PipelineBuild pipelineBuild = new PipelineBuild();
                String buildId = pipelineBuildService.createBuild(pipelineBuild);
                if (buildId == null){
                    throw new ApplicationException(50001,"添加失败");
                }
                configOrder.setTaskId(buildId);
            }
            case "deploy" -> {
                PipelineDeploy pipelineDeploy =  new PipelineDeploy();
                String deployId = pipelineDeployService.createDeploy(pipelineDeploy);
                if (deployId == null){
                    throw new ApplicationException(50001,"添加失败");
                }
                configOrder.setTaskId(deployId);
            }
        }
        String configure = createConfigure(configOrder);
        if (configure == null){
            throw new ApplicationException(50001,"创建配置顺序信息失败");
        }
    }

    /**
     * 删除配置
     * @param pipelineId 配置阻断字段
     * @param types 配置类型
     * @param type 配置详细类型
     */
    private void deleteConfig(String pipelineId ,String types,int type){
        PipelineConfigOrder typeConfig = findTypeConfig(pipelineId, type);
        if (typeConfig == null) return;
        updateConfigOrder(typeConfig,false);
        switch (types) {
            case "code" -> {
                pipelineCodeService.deleteCode(typeConfig.getTaskId());
            }
            case "test" -> {
                pipelineTestService.deleteTest(typeConfig.getTaskId());
            }
            case "build" -> {
                pipelineBuildService.deleteBuild(typeConfig.getTaskId());
            }
            case "deploy" -> {
                pipelineDeployService.deleteDeploy(typeConfig.getTaskId());
            }
        }
        pipelineConfigOrderDao.deleteConfigure(typeConfig.getConfigId());
    }

    /**
     * 查询配置
     * @param pipelineId 流水线id
     * @param type 类型
     * @return 配置信息
     */
    private PipelineConfigOrder findTypeConfig(String pipelineId,int type){
        List<PipelineConfigOrder> allPipelineConfig = findAllPipelineConfig(pipelineId);
        if (allPipelineConfig == null || allPipelineConfig.size() == 0) return null;
        for (PipelineConfigOrder pipelineConfigOrder : allPipelineConfig) {
            if (pipelineConfigOrder.getTaskType() != type){
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
    private void updateConfigOrder(PipelineConfigOrder typeConfig,boolean b){
        String pipelineId = typeConfig.getPipelineId();
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
    private String createConfigure(PipelineConfigOrder pipelineConfigOrder){
        PipelineConfigOrderEntity configOrder = BeanMapper.map(pipelineConfigOrder, PipelineConfigOrderEntity.class);
        return pipelineConfigOrderDao.createConfigure(configOrder);
    }

    //更新配置信息
    private void updateConfigOrder(PipelineConfigOrder configOrder){
        PipelineConfigOrderEntity configOrderEntity = BeanMapper.map(configOrder, PipelineConfigOrderEntity.class);
        pipelineConfigOrderDao.updateConfigure(configOrderEntity);
    }




}



































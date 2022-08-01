package com.tiklab.matflow.definition.service;


import com.tiklab.matflow.execute.model.MatFlowCode;
import com.tiklab.matflow.execute.model.MatFlowDeploy;
import com.tiklab.matflow.execute.model.MatFlowStructure;
import com.tiklab.matflow.execute.model.MatFlowTest;
import com.tiklab.matflow.execute.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 维护各个阶段的配置信息
 */

@Service
public class MatFlowTaskServiceImpl implements MatFlowTaskService{

    @Autowired
    MatFlowCodeService matFlowCodeService;

    @Autowired
    MatFlowTestService matFlowTestService;

    @Autowired
    MatFlowStructureService matFlowStructureService;

    @Autowired
    MatFlowDeployService matFlowDeployService;


    /**
     * 更新配置信息
     * @param type 更新类型
     */
    @Override
    public void updateTaskConfig(Object o,String taskId,Integer type) {
        switch (type) {
            case 10 -> {
                MatFlowCode matFlowCode = (MatFlowCode) o;
                matFlowCode.setCodeId(taskId);
                matFlowCodeService.updateCode(matFlowCode);
            }
            case 20 -> {
                MatFlowTest matFlowTest = (MatFlowTest) o;
                matFlowTest.setTestId(taskId);
                matFlowTestService.updateTest(matFlowTest);
            }
            case 30 -> {
                MatFlowStructure matFlowStructure = (MatFlowStructure) o;
                matFlowStructure.setStructureId(taskId);
                matFlowStructureService.updateStructure(matFlowStructure);
            }
            case 40 -> {
                MatFlowDeploy matFlowDeploy = (MatFlowDeploy) o;
                matFlowDeploy.setDeployId(taskId);
                matFlowDeployService.updateDeploy(matFlowDeploy);
            }
        }
    }

    /**
     * 创建配置信息
     * @param type 创建类型
     */
    @Override
    public String createTaskConfig(Object o,Integer type) {
        switch (type) {
            case 10 -> {
                MatFlowCode matFlowCode = (MatFlowCode) o;
                matFlowCode = matFlowCodeService.getUrl(matFlowCode);
                return matFlowCodeService.createCode(matFlowCode);
            }
            case 20 -> {
                MatFlowTest matFlowTest = (MatFlowTest) o;
                return matFlowTestService.createTest(matFlowTest);
            }
            case 30 -> {
                MatFlowStructure matFlowStructure = (MatFlowStructure) o;
                return matFlowStructureService.createStructure(matFlowStructure);
            }
            case 40 -> {
                MatFlowDeploy matFlowDeploy = (MatFlowDeploy) o;
                return matFlowDeployService.createDeploy(matFlowDeploy);
            }
        }
        return null;
    }


    /**
     * 删除配置信息
     * @param type 删除类型
     */
    @Override
    public void deleteTaskConfig(String taskId,Integer type) {
        switch (type) {
            case 10 -> matFlowCodeService.deleteCode(taskId);
            case 20 -> matFlowTestService.deleteTest(taskId);
            case 30 -> matFlowStructureService.deleteStructure(taskId);
            case 40 -> matFlowDeployService.deleteDeploy(taskId);
        }
    }

    @Override
    public List<Object> findOneTask( List<Object> list,String taskId, Integer type){
        if (type < 10){
            list.add(matFlowCodeService.findOneCode(taskId));
            return list;
        }else if (10 < type && type < 20){
            list.add(matFlowCodeService.findOneCode(taskId));
            return list;
        } else if (20 < type && type < 30){
            list.add(matFlowStructureService.findOneStructure(taskId));
            return list;
        } else if (30 < type && type < 40){
            list.add(matFlowDeployService.findOneDeploy(taskId));
            return list;
        }
        return null;
    }
}

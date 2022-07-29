package com.tiklab.matflow.execute.service;


import com.tiklab.beans.BeanMapper;
import com.tiklab.matflow.definition.model.MatFlow;
import com.tiklab.matflow.definition.model.MatFlowConfigure;
import com.tiklab.matflow.definition.model.MatFlowExecConfigure;
import com.tiklab.matflow.definition.service.MatFlowConfigureService;
import com.tiklab.matflow.execute.dao.MatFlowTestDao;
import com.tiklab.matflow.execute.entity.MatFlowTestEntity;
import com.tiklab.matflow.execute.model.MatFlowTest;
import com.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Exporter
public class MatFlowTestServiceImpl implements MatFlowTestService {

    @Autowired
    MatFlowTestDao matFlowTestDao;

    @Autowired
    MatFlowStructureService matFlowStructureService;

    @Autowired
    MatFlowConfigureService matFlowConfigureService;

    //创建
    @Override
    public String createTest(MatFlowTest matFlowTest) {
        return matFlowTestDao.createTest(BeanMapper.map(matFlowTest, MatFlowTestEntity.class));
    }

    //创建构建表
    @Override
    public  String createConfigure(String matFlowId, MatFlowTest matFlowTest){
        String testId = createTest(matFlowTest);

        MatFlowConfigure matFlowConfigure = new MatFlowConfigure();
        matFlowConfigure.setTaskAlias("测试");
        matFlowConfigure.setTaskAlias(matFlowTest.getTestAlias());
        matFlowConfigure.setTaskType(matFlowTest.getSort());
        matFlowConfigure.setTaskId(testId);
        matFlowConfigure.setTaskSort(matFlowTest.getSort());

        matFlowConfigureService.createTask(matFlowConfigure,matFlowId);
        return testId;
    }

    //删除
    @Override
    public void deleteTest(String testId) {
        matFlowTestDao.deleteTest(testId);
    }

    @Override
    public void deleteTask(String taskId, int taskType) {
        if (10 < taskType && taskType <= 20){
            deleteTest(taskId);
            return;
        }
        matFlowStructureService.deleteTask(taskId,taskType);
    }


    //修改
    @Override
    public void updateTest(MatFlowTest matFlowTest) {
        matFlowTestDao.updateTest(BeanMapper.map(matFlowTest, MatFlowTestEntity.class));
    }

    @Override
    public void updateTask(MatFlowExecConfigure matFlowExecConfigure) {
        MatFlowTest matFlowTest = matFlowExecConfigure.getMatFlowTest();
        MatFlow matFlow = matFlowExecConfigure.getMatFlow();
        MatFlowConfigure oneConfigure = matFlowConfigureService.findOneConfigure(matFlow.getMatflowId(), 20);

        //判断新配置是否删除了测试配置
        if (oneConfigure != null && matFlowTest.getType() == 0){
            deleteTest(oneConfigure.getTaskId());
            matFlowConfigureService.deleteConfigure(oneConfigure.getConfigureId());
            matFlowStructureService.updateTask(matFlowExecConfigure);
            return;
        }

        if (oneConfigure == null ){
            oneConfigure = new MatFlowConfigure();
        }

        if (matFlowTest.getType() == 0){
            matFlowStructureService.updateTask(matFlowExecConfigure);
            return;
        }
        oneConfigure.setTaskSort(matFlowTest.getSort());
        oneConfigure.setTaskAlias(matFlowTest.getTestAlias());
        oneConfigure.setView(matFlowExecConfigure.getView());
        oneConfigure.setMatFlow(matFlow);
        oneConfigure.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        oneConfigure.setTaskType(matFlowTest.getType());

        //存在测试配置，更新或者创建
        if (matFlowTest.getTestId() != null){
            updateTest(matFlowTest);
            matFlowConfigureService.updateConfigure(oneConfigure);
        }else {
            String testId = createTest(matFlowTest);
            oneConfigure.setTaskId(testId);
            matFlowConfigureService.createConfigure(oneConfigure);

        }
        matFlowStructureService.updateTask(matFlowExecConfigure);

        //if (oneConfigure != null ){
        //    if (matFlowTest.getType() != 0){
        //        updateTest(matFlowTest);
        //        oneConfigure.setTaskSort(matFlowTest.getSort());
        //        oneConfigure.setTaskAlias(matFlowTest.getTestAlias());
        //        oneConfigure.setView(matFlowExecConfigure.getView());
        //        matFlowConfigureService.updateConfigure(oneConfigure);
        //        //动态
        //    }else {
        //        matFlowConfigureService.deleteTask(oneConfigure.getTaskId(),matFlow.getMatFlowId());
        //    }
        //}
        //
        //if (matFlowTest.getType() != 0 && oneConfigure == null){
        //    createConfigure(matFlow.getMatFlowId(),matFlowTest);
        //}
    }

    //查询单个
    @Override
    public MatFlowTest findOneTest(String testId) {
        return BeanMapper.map(matFlowTestDao.findOneTest(testId), MatFlowTest.class);
    }

    @Override
    public List<Object> findOneTask(MatFlowConfigure matFlowConfigure, List<Object> list) {
        if (matFlowConfigure.getTaskType() < 20 && matFlowConfigure.getTaskType() >10 ){
            MatFlowTest oneTest = findOneTest(matFlowConfigure.getTaskId());
            list.add(oneTest);
        }
        return matFlowStructureService.findOneTask(matFlowConfigure,list);
    }

    //查询所有
    @Override
    public List<MatFlowTest> findAllTest() {
        return  BeanMapper.mapList(matFlowTestDao.findAllTest(), MatFlowTest.class);
    }

    @Override
    public List<MatFlowTest> findAllTestList(List<String> idList) {
        return BeanMapper.mapList(matFlowTestDao.findAllCodeList(idList), MatFlowTest.class);
    }
}

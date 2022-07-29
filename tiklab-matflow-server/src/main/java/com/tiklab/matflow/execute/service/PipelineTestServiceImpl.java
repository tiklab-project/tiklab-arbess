package com.tiklab.matflow.execute.service;

import com.doublekit.beans.BeanMapper;
import com.tiklab.matflow.definition.model.Pipeline;
import com.tiklab.matflow.definition.model.PipelineConfigure;
import com.tiklab.matflow.definition.model.PipelineExecConfigure;
import com.tiklab.matflow.definition.service.PipelineConfigureService;
import com.tiklab.matflow.execute.dao.PipelineTestDao;
import com.tiklab.matflow.execute.entity.PipelineTestEntity;
import com.tiklab.matflow.execute.model.PipelineTest;
import com.doublekit.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Exporter
public class PipelineTestServiceImpl implements PipelineTestService {

    @Autowired
    PipelineTestDao pipelineTestDao;

    @Autowired
    PipelineStructureService pipelineStructureService;

    @Autowired
    PipelineConfigureService pipelineConfigureService;

    //创建
    @Override
    public String createTest(PipelineTest pipelineTest) {
        return pipelineTestDao.createTest(BeanMapper.map(pipelineTest, PipelineTestEntity.class));
    }

    //创建构建表
    @Override
    public  String createConfigure(String pipelineId, PipelineTest pipelineTest){
        String testId = createTest(pipelineTest);

        PipelineConfigure pipelineConfigure = new PipelineConfigure();
        pipelineConfigure.setTaskAlias("测试");
        pipelineConfigure.setTaskAlias(pipelineTest.getTestAlias());
        pipelineConfigure.setTaskType(pipelineTest.getSort());
        pipelineConfigure.setTaskId(testId);
        pipelineConfigure.setTaskSort(pipelineTest.getSort());

        pipelineConfigureService.createTask(pipelineConfigure,pipelineId);
        return testId;
    }

    //删除
    @Override
    public void deleteTest(String testId) {
        pipelineTestDao.deleteTest(testId);
    }

    @Override
    public void deleteTask(String taskId, int taskType) {
        if (10 < taskType && taskType <= 20){
            deleteTest(taskId);
            return;
        }
        pipelineStructureService.deleteTask(taskId,taskType);
    }


    //修改
    @Override
    public void updateTest(PipelineTest pipelineTest) {
        pipelineTestDao.updateTest(BeanMapper.map(pipelineTest,PipelineTestEntity.class));
    }

    @Override
    public void updateTask(PipelineExecConfigure pipelineExecConfigure) {
        PipelineTest pipelineTest = pipelineExecConfigure.getPipelineTest();
        Pipeline pipeline = pipelineExecConfigure.getPipeline();
        PipelineConfigure oneConfigure = pipelineConfigureService.findOneConfigure(pipeline.getPipelineId(), 20);

        //判断新配置是否删除了测试配置
        if (oneConfigure != null && pipelineTest.getType() == 0){
            deleteTest(oneConfigure.getTaskId());
            pipelineConfigureService.deleteConfigure(oneConfigure.getConfigureId());
            pipelineStructureService.updateTask(pipelineExecConfigure);
            return;
        }

        if (oneConfigure == null ){
            oneConfigure = new PipelineConfigure();
        }

        if (pipelineTest.getType() == 0){
            pipelineStructureService.updateTask(pipelineExecConfigure);
            return;
        }
        oneConfigure.setTaskSort(pipelineTest.getSort());
        oneConfigure.setTaskAlias(pipelineTest.getTestAlias());
        oneConfigure.setView(pipelineExecConfigure.getView());
        oneConfigure.setPipeline(pipeline);
        oneConfigure.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        oneConfigure.setTaskType(pipelineTest.getType());

        //存在测试配置，更新或者创建
        if (pipelineTest.getTestId() != null){
            updateTest(pipelineTest);
            pipelineConfigureService.updateConfigure(oneConfigure);
        }else {
            String testId = createTest(pipelineTest);
            oneConfigure.setTaskId(testId);
            pipelineConfigureService.createConfigure(oneConfigure);

        }
        pipelineStructureService.updateTask(pipelineExecConfigure);

        //if (oneConfigure != null ){
        //    if (pipelineTest.getType() != 0){
        //        updateTest(pipelineTest);
        //        oneConfigure.setTaskSort(pipelineTest.getSort());
        //        oneConfigure.setTaskAlias(pipelineTest.getTestAlias());
        //        oneConfigure.setView(pipelineExecConfigure.getView());
        //        pipelineConfigureService.updateConfigure(oneConfigure);
        //        //动态
        //    }else {
        //        pipelineConfigureService.deleteTask(oneConfigure.getTaskId(),pipeline.getPipelineId());
        //    }
        //}
        //
        //if (pipelineTest.getType() != 0 && oneConfigure == null){
        //    createConfigure(pipeline.getPipelineId(),pipelineTest);
        //}
    }

    //查询单个
    @Override
    public PipelineTest findOneTest(String testId) {
        return BeanMapper.map(pipelineTestDao.findOneTest(testId),PipelineTest.class);
    }

    @Override
    public List<Object> findOneTask( PipelineConfigure pipelineConfigure,List<Object> list) {
        if (pipelineConfigure.getTaskType() < 20 && pipelineConfigure.getTaskType() >10 ){
            PipelineTest oneTest = findOneTest(pipelineConfigure.getTaskId());
            list.add(oneTest);
        }
        return pipelineStructureService.findOneTask(pipelineConfigure,list);
    }

    //查询所有
    @Override
    public List<PipelineTest> findAllTest() {
        return  BeanMapper.mapList(pipelineTestDao.findAllTest(), PipelineTest.class);
    }

    @Override
    public List<PipelineTest> findAllTestList(List<String> idList) {
        return BeanMapper.mapList(pipelineTestDao.findAllCodeList(idList),PipelineTest.class);
    }
}

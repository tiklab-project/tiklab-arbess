package com.doublekit.pipeline.example.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.join.JoinTemplate;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.definition.service.PipelineConfigureService;
import com.doublekit.pipeline.example.dao.PipelineTestDao;
import com.doublekit.pipeline.example.entity.PipelineTestEntity;
import com.doublekit.pipeline.example.model.PipelineStructure;
import com.doublekit.pipeline.example.model.PipelineTest;
import com.doublekit.rpc.annotation.Exporter;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public  String createConfigure(String pipelineId,int taskType){
        PipelineTest pipelineTest = new PipelineTest();
        pipelineTest.setType(taskType);
        pipelineTest.setType(taskType);
        PipelineConfigure pipelineConfigure = new PipelineConfigure();
        pipelineConfigure.setTaskType(taskType);
        String testId = createTest(pipelineTest);
        pipelineConfigure.setTaskId(testId);
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
    public void updateTask(Map<String,Object> map) {
        PipelineTest pipelineTest = (PipelineTest) map.get("pipelineTest");
        if (pipelineTest.getTestId() != null){
            updateTest(pipelineTest);
        }
        if (pipelineTest.getType() == 0){
            List<PipelineConfigure> configureList = pipelineConfigureService.findAllConfigure(map.get("pipelineId").toString());
            if (configureList != null){
                for (PipelineConfigure pipelineConfigure : configureList) {
                    if (pipelineConfigure.getTaskType() > 10 && pipelineConfigure.getTaskType() < 20){
                        pipelineConfigureService.deleteConfigure(pipelineConfigure.getConfigureId());
                        deleteTest(pipelineConfigure.getTaskId());
                    }
                }
            }
        }
        pipelineStructureService.updateTask(map);
    }

    //查询单个
    @Override
    public PipelineTest findOneTest(String testId) {
        return BeanMapper.map(pipelineTestDao.findOneTest(testId),PipelineTest.class);
    }

    @Override
    public List<Object>  findOneTask( PipelineConfigure pipelineConfigure,List<Object> list) {
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

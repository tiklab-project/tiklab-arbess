package com.doublekit.pipeline.example.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.join.JoinTemplate;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.example.dao.PipelineTestDao;
import com.doublekit.pipeline.example.entity.PipelineTestEntity;
import com.doublekit.pipeline.example.model.PipelineStructure;
import com.doublekit.pipeline.example.model.PipelineTest;
import com.doublekit.rpc.annotation.Exporter;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Exporter
public class PipelineTestServiceImpl implements PipelineTestService {

    @Autowired
    PipelineTestDao pipelineTestDao;

    @Autowired
    PipelineStructureService pipelineStructureService;


    //创建
    @Override
    public String createTest(PipelineTest pipelineTest) {
        return pipelineTestDao.createTest(BeanMapper.map(pipelineTest, PipelineTestEntity.class));
    }

    //创建构建表
    @Override
    public Map<String,String> createStructure(PipelineConfigure pipelineConfigure){
        PipelineTest pipelineTest = new PipelineTest();
        if (pipelineConfigure.getPipelineTest() != null){
            pipelineTest = pipelineConfigure.getPipelineTest();
        }
        String test = createTest(pipelineTest);
        Map<String, String> map = pipelineStructureService.createDeploy(pipelineConfigure);
        map.put("testId",test);
        return map;
    }

    //删除
    @Override
    public void deleteTest(String testId) {
        pipelineTestDao.deleteTest(testId);
    }

    //删除构建表
    @Override
    public void deleteStructure(PipelineConfigure pipelineConfigure){
        deleteTest(pipelineConfigure.getPipelineTest().getTestId());
        pipelineStructureService.deleteDeploy(pipelineConfigure);
    }

    //修改
    @Override
    public void updateTest(PipelineTest pipelineTest) {
        pipelineTestDao.updateTest(BeanMapper.map(pipelineTest,PipelineTestEntity.class));
    }

    //更新构建表
    @Override
    public void updateStructure(PipelineConfigure pipelineConfigure){
        updateTest(pipelineConfigure.getPipelineTest());
        pipelineStructureService.updateDeploy(pipelineConfigure);
    }

    //查询单个
    @Override
    public PipelineTest findOneTest(String testId) {
        return BeanMapper.map(pipelineTestDao.findOneTest(testId),PipelineTest.class);
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

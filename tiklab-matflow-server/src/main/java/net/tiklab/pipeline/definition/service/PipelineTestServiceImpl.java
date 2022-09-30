package net.tiklab.pipeline.definition.service;


import net.tiklab.beans.BeanMapper;
import net.tiklab.join.JoinTemplate;
import net.tiklab.pipeline.definition.dao.PipelineTestDao;
import net.tiklab.pipeline.definition.entity.PipelineTestEntity;
import net.tiklab.pipeline.definition.model.PipelineTest;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class PipelineTestServiceImpl implements PipelineTestService {

    @Autowired
    PipelineTestDao pipelineTestDao;

    @Autowired
    JoinTemplate joinTemplate;

    //创建
    @Override
    public String createTest(PipelineTest pipelineTest) {
        return pipelineTestDao.createTest(BeanMapper.map(pipelineTest, PipelineTestEntity.class));
    }

    //删除
    @Override
    public void deleteTest(String testId) {
        pipelineTestDao.deleteTest(testId);
    }

    //根据流水线id查询配置
    @Override
    public PipelineTest findTest(String pipelineId) {
        List<PipelineTest> allBuild = findAllTest();
        if (allBuild != null){
            for (PipelineTest pipelineTest : allBuild) {
                if (pipelineTest.getPipeline() == null){
                    continue;
                }
                if (pipelineTest.getPipeline().getPipelineId().equals(pipelineId)){
                    joinTemplate.joinQuery(pipelineTest);
                    return pipelineTest;
                }
            }
        }
        return null;
    }

    @Override
    public void updateTest(PipelineTest pipelineTest, String pipelineId) {
        PipelineTest test = findTest(pipelineId);
        if (test == null){
            if (pipelineTest.getSort() != 0){
                createTest(pipelineTest);
            }
        }else {
            if (pipelineTest.getSort() == 0){
                deleteTest(test.getTestId());
            }else {
                pipelineTest.setTestId(test.getTestId());
                updateTest(pipelineTest);
            }
        }
    }

    //修改
    @Override
    public void updateTest(PipelineTest pipelineTest) {
        pipelineTestDao.updateTest(BeanMapper.map(pipelineTest, PipelineTestEntity.class));
    }


    //查询单个
    @Override
    public PipelineTest findOneTest(String testId) {
        return BeanMapper.map(pipelineTestDao.findOneTest(testId), PipelineTest.class);
    }

    //查询所有
    @Override
    public List<PipelineTest> findAllTest() {
        return  BeanMapper.mapList(pipelineTestDao.findAllTest(), PipelineTest.class);
    }

    @Override
    public List<PipelineTest> findAllTestList(List<String> idList) {
        return BeanMapper.mapList(pipelineTestDao.findAllCodeList(idList), PipelineTest.class);
    }
}

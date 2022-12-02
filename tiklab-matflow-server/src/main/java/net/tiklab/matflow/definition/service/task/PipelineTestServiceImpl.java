package net.tiklab.matflow.definition.service.task;


import net.tiklab.beans.BeanMapper;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.definition.dao.task.PipelineTestDao;
import net.tiklab.matflow.definition.entity.task.PipelineTestEntity;
import net.tiklab.matflow.definition.model.task.PipelineTest;
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


    /**
     * 根据配置id删除任务
     * @param configId 配置id
     */
    @Override
    public void deleteTestConfig(String configId){
        PipelineTest oneTestConfig = findOneTestConfig(configId);
        deleteTest(oneTestConfig.getTestId());
    }

    /**
     * 根据配置id查询任务
     * @param configId 配置id
     * @return 任务
     */
    @Override
    public PipelineTest findOneTestConfig(String configId){
        List<PipelineTest> allTest = findAllTest();
        if (allTest == null){
            return null;
        }
        for (PipelineTest pipelineTest : allTest) {
            if (pipelineTest.getConfigId().equals(configId)){
                return pipelineTest;
            }
        }
        return null;
    }
    

    //删除
    @Override
    public void deleteTest(String testId) {
        pipelineTestDao.deleteTest(testId);
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

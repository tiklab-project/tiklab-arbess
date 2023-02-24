package net.tiklab.matflow.task.test.service;


import net.tiklab.beans.BeanMapper;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.task.test.dao.TaskTestDao;
import net.tiklab.matflow.task.test.entity.TaskTestEntity;
import net.tiklab.matflow.task.test.model.TaskTest;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class TaskTestServiceImpl implements TaskTestService {

    @Autowired
    TaskTestDao taskTestDao;

    @Autowired
    JoinTemplate joinTemplate;

    //创建
    @Override
    public String createTest(TaskTest taskTest) {
        return taskTestDao.createTest(BeanMapper.map(taskTest, TaskTestEntity.class));
    }


    /**
     * 根据配置id删除任务
     * @param configId 配置id
     */
    @Override
    public void deleteTestConfig(String configId){
        TaskTest oneTestConfig = findOneTestConfig(configId);
        deleteTest(oneTestConfig.getTestId());
    }

    /**
     * 根据配置id查询任务
     * @param configId 配置id
     * @return 任务
     */
    @Override
    public TaskTest findOneTestConfig(String configId){
        List<TaskTest> allTest = findAllTest();
        if (allTest == null){
            return null;
        }
        for (TaskTest taskTest : allTest) {
            if (taskTest.getConfigId().equals(configId)){
                return taskTest;
            }
        }
        return null;
    }
    

    //删除
    @Override
    public void deleteTest(String testId) {
        taskTestDao.deleteTest(testId);
    }


    //修改
    @Override
    public void updateTest(TaskTest taskTest) {
        taskTestDao.updateTest(BeanMapper.map(taskTest, TaskTestEntity.class));
    }


    //查询单个
    @Override
    public TaskTest findOneTest(String testId) {
        return BeanMapper.map(taskTestDao.findOneTest(testId), TaskTest.class);
    }

    //查询所有
    @Override
    public List<TaskTest> findAllTest() {
        return  BeanMapper.mapList(taskTestDao.findAllTest(), TaskTest.class);
    }

    @Override
    public List<TaskTest> findAllTestList(List<String> idList) {
        return BeanMapper.mapList(taskTestDao.findAllCodeList(idList), TaskTest.class);
    }
}

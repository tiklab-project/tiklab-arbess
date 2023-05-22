package io.tiklab.matflow.task.test.service;


import io.tiklab.beans.BeanMapper;
import io.tiklab.join.JoinTemplate;
import io.tiklab.matflow.setting.model.AuthThird;
import io.tiklab.matflow.setting.service.AuthHostService;
import io.tiklab.matflow.setting.service.AuthThirdService;
import io.tiklab.matflow.task.test.dao.TaskTestDao;
import io.tiklab.matflow.task.test.entity.TaskTestEntity;
import io.tiklab.matflow.task.test.model.TaskTest;
import io.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Exporter
public class TaskTestServiceImpl implements TaskTestService {

    @Autowired
    private TaskTestDao taskTestDao;


    @Autowired
    private AuthThirdService thirdServer;

    @Autowired
    private AuthHostService hostServer;

    @Autowired
    private JoinTemplate joinTemplate;




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
        deleteTest(oneTestConfig.getTaskId());
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
            if (taskTest.getTaskId().equals(configId)){
                String authId = taskTest.getAuthId();
                if (Objects.isNull(authId)){
                    return taskTest;
                }
                AuthThird authServer = thirdServer.findOneAuthServer(authId);
                taskTest.setAuth(authServer);
                // joinTemplate.joinQuery(taskTest);
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
        List<TaskTest> taskTests = BeanMapper.mapList(taskTestDao.findAllTest(), TaskTest.class);
        // joinTemplate.joinQuery(taskTests);
        return taskTests;
    }

    @Override
    public List<TaskTest> findAllTestList(List<String> idList) {
        return BeanMapper.mapList(taskTestDao.findAllCodeList(idList), TaskTest.class);
    }
}

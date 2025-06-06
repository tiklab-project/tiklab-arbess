package io.tiklab.arbess.task.test.service;


import io.tiklab.arbess.setting.host.service.AuthHostService;
import io.tiklab.arbess.setting.third.service.AuthThirdService;
import io.tiklab.arbess.task.test.dao.TaskTestDao;
import io.tiklab.arbess.task.test.entity.TaskTestEntity;
import io.tiklab.arbess.task.test.model.*;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.rpc.annotation.Exporter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static io.tiklab.arbess.support.util.util.PipelineFinal.TASK_TEST_TESTON;

/**
 * @author zcamy
 */
@Service
@Exporter
public class TaskTestServiceImpl implements TaskTestService {

    @Autowired
    TaskTestDao taskTestDao;

    @Autowired
    AuthThirdService thirdServer;

    @Autowired
    AuthHostService hostServer;

    @Autowired
    TaskTestOnService taskTestOnService;



    @Override
    public String createTest(TaskTest taskTest) {
        return taskTestDao.createTest(BeanMapper.map(taskTest, TaskTestEntity.class));
    }

    @Override
    public Boolean testValid(String taskType,TaskTest taskTest){
        if (taskType.equals(TASK_TEST_TESTON)){
            if (Objects.isNull(taskTest.getTestSpace())){
                return false;
            }
            if (Objects.isNull(taskTest.getTestPlan())){
                return false;
            }
            boolean b  = Objects.isNull(taskTest.getTestEnv());
            return !b ;
        }
        return !StringUtils.isEmpty(taskTest.getAddress());
    }


    @Override
    public TaskTest findTestBuAuth (String taskId){

        TaskTest taskTest = findOneTest(taskId);

        if (Objects.isNull(taskTest)){
            return null;
        }
        String authId = taskTest.getAuthId();
        if (Objects.isNull(authId)){
            return taskTest;
        }

        try {
            if (!Objects.isNull(taskTest.getTestEnv())){
                String id = taskTest.getTestEnv().getId();
                TestHuboEnv env = taskTestOnService.findEnv(authId,id);
                taskTest.setTestEnv(env);
            }

            if (!Objects.isNull(taskTest.getTestSpace())){
                String id = taskTest.getTestSpace().getId();
                TestHuboRpy repository = taskTestOnService.findRepository(authId,id);
                taskTest.setTestSpace(repository);
            }
            if (!Objects.isNull(taskTest.getTestPlan())){
                String id = taskTest.getTestPlan().getId();
                TestHuboTestPlan testPlan = taskTestOnService.findTestPlan(authId,id);
                taskTest.setTestPlan(testPlan);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return taskTest;
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
        TaskTestEntity oneTest = taskTestDao.findOneTest(testId);
        return BeanMapper.map(oneTest, TaskTest.class);
    }

    //查询所有
    @Override
    public List<TaskTest> findAllTest() {
        return BeanMapper.mapList(taskTestDao.findAllTest(), TaskTest.class);
    }

    @Override
    public List<TaskTest> findAllTestList(List<String> idList) {
        return BeanMapper.mapList(taskTestDao.findAllCodeList(idList), TaskTest.class);
    }
}

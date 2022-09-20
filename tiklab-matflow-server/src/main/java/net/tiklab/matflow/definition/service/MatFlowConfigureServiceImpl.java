package net.tiklab.matflow.definition.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.definition.dao.MatFlowConfigureDao;
import net.tiklab.matflow.definition.entity.MatFlowConfigureEntity;
import net.tiklab.matflow.definition.model.MatFlow;
import net.tiklab.matflow.definition.model.MatFlowConfigure;
import net.tiklab.matflow.definition.model.MatFlowExecConfigure;
import net.tiklab.matflow.definition.model.MatFlowCode;
import net.tiklab.matflow.definition.model.MatFlowDeploy;
import net.tiklab.matflow.definition.model.MatFlowBuild;
import net.tiklab.matflow.definition.model.MatFlowTest;
import net.tiklab.matflow.orther.service.MatFlowActivityService;
import net.tiklab.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * 维护配置关联信息
 */

@Service
@Exporter
public class MatFlowConfigureServiceImpl implements MatFlowConfigureService {

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    MatFlowConfigureDao matFlowConfigureDao;

    @Autowired
    MatFlowTaskService matFlowTaskService;

    @Autowired
    MatFlowActivityService matFlowActivityService;

    private static final Logger logger = LoggerFactory.getLogger(MatFlowConfigureServiceImpl.class);

    //创建
    @Override
    public  String createConfigure(MatFlowConfigure matFlowConfigure) {
        return matFlowConfigureDao.createConfigure(BeanMapper.map(matFlowConfigure, MatFlowConfigureEntity.class));
    }

    //删除配置
    @Override
    public void deleteConfigure(String configureId) {
        matFlowConfigureDao.deleteConfigure(configureId);
    }

    @Override
    public MatFlowConfigure findOneConfigure(String matFlowId, int type) {
        List<MatFlowConfigure> allConfigure = findAllConfigure(matFlowId);
        if (allConfigure == null){
            return null;
        }
        for (MatFlowConfigure matFlowConfigure : allConfigure) {
            int taskType = matFlowConfigure.getTaskType();
            if ( type - 10 < taskType  && taskType < type  ){
                return matFlowConfigure;
            }
        }
        return null;
    }


    //更新配置
    @Override
    public void updateConfigure(MatFlowConfigure matFlowConfigure){
        matFlowConfigureDao.updateConfigure(BeanMapper.map(matFlowConfigure, MatFlowConfigureEntity.class));
    }

    //创建配置信息
    @Override
    public void createTask(MatFlowConfigure matFlowConfigure, String matFlowId){
        MatFlow matFlow = new MatFlow();
        matFlow.setMatflowId(matFlowId);
        matFlowConfigure.setMatFlow(matFlow);
        matFlowConfigure.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        matFlowActivityService.createActive(matFlowConfigure.getUserId(), matFlowConfigure.getMatFlow(), "用户创建了流水线/的配置");
        createConfigure(matFlowConfigure);
    }

    //删除任务
    @Override
    public void deleteTask(String matFlowId) {
        List<MatFlowConfigure> allConfigure = findAllConfigure(matFlowId);
        if (allConfigure == null){
            return;
        }
        for (MatFlowConfigure matFlowConfigure : allConfigure) {
            MatFlowConfigure oneConfigure = findOneConfigure(matFlowConfigure.getConfigureId());
            matFlowTaskService.deleteTaskConfig(oneConfigure.getTaskId(),oneConfigure.getTaskType());
            deleteConfigure(oneConfigure.getConfigureId());
            deleteConfigure(matFlowConfigure.getConfigureId());
        }
    }

    //更新任务
    @Override
    public void updateTask(MatFlowExecConfigure matFlowExecConfigure){
        matFlowActivityService.createActive(matFlowExecConfigure.getUser().getId(), matFlowExecConfigure.getMatFlow(), "更新了流水线/的配置");
        //获取源码配置
        MatFlowCode matFlowCode = matFlowExecConfigure.getMatFlowCode();
        updateTask(matFlowExecConfigure,matFlowCode,matFlowCode.getType(),10);

        //获取测试配置
        MatFlowTest matFlowTest = matFlowExecConfigure.getMatFlowTest();
        updateTask(matFlowExecConfigure,matFlowTest,matFlowTest.getType(),20);

        //获取构建配置
        MatFlowBuild matFlowBuild = matFlowExecConfigure.getMatFlowBuild();
        updateTask(matFlowExecConfigure,matFlowBuild,matFlowBuild.getType(),30);

        //获取部署配置
        MatFlowDeploy matFlowDeploy = matFlowExecConfigure.getMatFlowDeploy();
        updateTask(matFlowExecConfigure,matFlowDeploy,matFlowDeploy.getType(),40);

    }

    /**
     * 判断对配置的操作
     * @param execConfigure 配置信息
     * @param type 配置类型
     */
    public void updateTask(MatFlowExecConfigure execConfigure,Object o,Integer objectType,Integer type){
        MatFlow matFlow = execConfigure.getMatFlow();
         MatFlowConfigure configure = findOneConfigure(matFlow.getMatflowId(), type);
        //存在旧的配置
        if (configure != null){
            //没有新的配置
            if (objectType == 0){
                deleteTask(configure.getConfigureId());
                matFlowTaskService.deleteTaskConfig(configure.getTaskId(),type);
                deleteConfigure(configure.getConfigureId());
            }else {
                matFlowTaskService.updateTaskConfig(o,configure.getTaskId(),type);
            }
            //不存在旧的配置
        }else {
            //新的有配置
            if (objectType != 0){
                String taskId = matFlowTaskService.createTaskConfig(o, type);
                if (taskId == null){
                    return;
                }
                MatFlowConfigure matFlowConfigure = initTaskConfig(execConfigure, type);
                matFlowConfigure.setTaskId(taskId);
                createConfigure(matFlowConfigure);
            }
        }
    }

    /**
     * 初始化配置信息
     * @param configure 配置
     * @param type 类型
     * @return 配置
     */
    public MatFlowConfigure initTaskConfig(MatFlowExecConfigure configure,Integer type){
        MatFlow matFlow = configure.getMatFlow();
        MatFlowConfigure oneConfigure = new MatFlowConfigure();
        oneConfigure.setMatFlow(matFlow);
        oneConfigure.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        oneConfigure.setView(configure.getView());
        int sort = 0;
        int types = 0;
        String alias = null;
        switch (type) {
            case 10 -> {
                MatFlowCode matFlowCode = configure.getMatFlowCode();
                sort = 1;
                alias="源码管理";
                types = matFlowCode.getType();
            }
            case 20 -> {
                MatFlowTest matFlowTest = configure.getMatFlowTest();
                sort = matFlowTest.getSort();
                types = matFlowTest.getType();
                alias = matFlowTest.getTestAlias();
            }
            case 30 -> {
                MatFlowBuild matFlowBuild = configure.getMatFlowBuild();
                sort = matFlowBuild.getSort();
                types = matFlowBuild.getType();
                alias = matFlowBuild.getBuildAlias();
            }
            case 40 -> {
                MatFlowDeploy matFlowDeploy = configure.getMatFlowDeploy();
                sort = matFlowDeploy.getSort();
                types = matFlowDeploy.getType();
                alias = matFlowDeploy.getDeployAlias();
            }
        }
        oneConfigure.setTaskType(types);
        oneConfigure.setTaskSort(sort);
        oneConfigure.setTaskAlias(alias);
        return oneConfigure;
    }


    //查询
    @Override
    public MatFlowConfigure findOneConfigure(String configureId) {
        MatFlowConfigureEntity oneConfigure = matFlowConfigureDao.findOneConfigure(configureId);
        joinTemplate.joinQuery(oneConfigure);
        return BeanMapper.map(oneConfigure, MatFlowConfigure.class);
    }


    //查询所有
    @Override
    public List<MatFlowConfigure> findAllConfigure() {
        List<MatFlowConfigureEntity> allConfigure = matFlowConfigureDao.findAllConfigure();
        List<MatFlowConfigure> matFlowConfigureList = BeanMapper.mapList(allConfigure, MatFlowConfigure.class);
        joinTemplate.joinQuery(matFlowConfigureList);
        return matFlowConfigureList;
    }

    //查询配置
    @Override
    public  List<Object> findAll(String matFlowId){
        List<Object> list = new ArrayList<>();
        List<MatFlowConfigure> allConfigure = findAllConfigure(matFlowId);

        if (allConfigure != null){
            for (MatFlowConfigure matFlowConfigure : allConfigure) {
                matFlowTaskService.findOneTask(list, matFlowConfigure.getTaskId(), matFlowConfigure.getTaskType());
            }
        }
        return list;
    }

    //通过流水线id查询所有配置
    @Override
    public List<MatFlowConfigure> findAllConfigure(String matFlowId) {
        List<MatFlowConfigureEntity> allConfigure = matFlowConfigureDao.findAllConfigure(matFlowId);

        if (allConfigure ==null ){
            return null;
        }
        List<MatFlowConfigure> matFlowConfigures = BeanMapper.mapList(allConfigure, MatFlowConfigure.class);
        joinTemplate.joinQuery(matFlowConfigures);
        matFlowConfigures.sort(Comparator.comparing(MatFlowConfigure::getTaskSort));
        return matFlowConfigures;
    }

    @Override
    public List<MatFlowConfigure> findAllConfigureList(List<String> idList) {
        List<MatFlowConfigureEntity> matFlowConfigureEntityList = matFlowConfigureDao.findAllConfigureList(idList);
        return BeanMapper.mapList(matFlowConfigureEntityList, MatFlowConfigure.class);
    }


}

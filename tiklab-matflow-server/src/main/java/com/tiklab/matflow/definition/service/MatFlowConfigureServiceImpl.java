package com.tiklab.matflow.definition.service;



import com.ibm.icu.text.SimpleDateFormat;
import com.tiklab.beans.BeanMapper;
import com.tiklab.join.JoinTemplate;
import com.tiklab.matflow.definition.dao.MatFlowConfigureDao;
import com.tiklab.matflow.definition.entity.MatFlowConfigureEntity;
import com.tiklab.matflow.definition.model.MatFlow;
import com.tiklab.matflow.definition.model.MatFlowConfigure;
import com.tiklab.matflow.definition.model.MatFlowExecConfigure;
import com.tiklab.matflow.execute.service.MatFlowCodeService;
import com.tiklab.matflow.instance.service.MatFlowActionService;
import com.tiklab.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


@Service
@Exporter
public class MatFlowConfigureServiceImpl implements MatFlowConfigureService {

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    MatFlowConfigureDao matFlowConfigureDao;

    @Autowired
    MatFlowCodeService matFlowCodeService;

    @Autowired
    MatFlowActionService matFlowActionService;


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
            if (taskType < type && taskType > type - 10){
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
        matFlowActionService.createActive(matFlowConfigure.getUserId(), matFlowConfigure.getMatFlow(), "用户创建了流水线/的配置");
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
            matFlowCodeService.deleteTask(oneConfigure.getTaskId(),oneConfigure.getTaskType());
            deleteConfigure(matFlowConfigure.getConfigureId());
        }
    }

    //更新任务
    @Override
    public void updateTask(MatFlowExecConfigure matFlowExecConfigure){
        matFlowActionService.createActive(matFlowExecConfigure.getUser().getId(), matFlowExecConfigure.getMatFlow(), "更新了流水线/的配置");
        matFlowCodeService.updateTask(matFlowExecConfigure);
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
               matFlowCodeService.findOneTask(matFlowConfigure,list);
            }
        }
        return list;
    }

    //通过流水线id查询所有配置
    @Override
    public List<MatFlowConfigure> findAllConfigure(String matFlowId) {
        List<MatFlowConfigure> allConfigure = findAllConfigure();
        if (allConfigure.size() == 0){
            return null;
        }
        allConfigure.sort(Comparator.comparing(MatFlowConfigure::getTaskSort));
        List<MatFlowConfigure> matFlowConfigures = new ArrayList<>();
        for (MatFlowConfigure matFlowConfigure : allConfigure) {
            if (matFlowConfigure.getMatFlow() == null){
                continue;
            }
            if (matFlowConfigure.getMatFlow().getMatflowId().equals(matFlowId)){
                matFlowConfigures.add(matFlowConfigure);
            }
        }
        if (matFlowConfigures.size() != 0){
            allConfigure.sort(Comparator.comparing(MatFlowConfigure::getTaskSort));
        }
        return matFlowConfigures;
    }

    @Override
    public List<MatFlowConfigure> findAllConfigureList(List<String> idList) {
        List<MatFlowConfigureEntity> matFlowConfigureEntityList = matFlowConfigureDao.findAllConfigureList(idList);
        return BeanMapper.mapList(matFlowConfigureEntityList, MatFlowConfigure.class);
    }


}

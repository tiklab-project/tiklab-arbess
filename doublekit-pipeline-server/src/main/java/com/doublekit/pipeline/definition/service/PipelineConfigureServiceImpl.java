package com.doublekit.pipeline.definition.service;


import com.doublekit.beans.BeanMapper;
import com.doublekit.join.JoinTemplate;
import com.doublekit.pipeline.definition.dao.PipelineConfigureDao;
import com.doublekit.pipeline.definition.entity.PipelineConfigureEntity;
import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.definition.model.PipelineExecConfigure;
import com.doublekit.pipeline.example.service.PipelineCodeService;
import com.doublekit.rpc.annotation.Exporter;
import com.ibm.icu.text.SimpleDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
@Exporter
public class PipelineConfigureServiceImpl implements PipelineConfigureService {

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    PipelineConfigureDao pipelineConfigureDao;

    @Autowired
    PipelineCodeService pipelineCodeService;

    private static final Logger logger = LoggerFactory.getLogger(PipelineConfigureServiceImpl.class);

    //创建
    @Override
    public  String createConfigure(PipelineConfigure pipelineConfigure) {
        return pipelineConfigureDao.createConfigure(BeanMapper.map(pipelineConfigure, PipelineConfigureEntity.class));
    }

    //删除配置
    @Override
    public void deleteConfigure(String configureId) {
        pipelineConfigureDao.deleteConfigure(configureId);
    }

    @Override
    public PipelineConfigure findOneConfigure(String pipelineId, int type) {
        List<PipelineConfigure> allConfigure = findAllConfigure(pipelineId);
        if (allConfigure != null){
            for (PipelineConfigure pipelineConfigure : allConfigure) {
                int taskType = pipelineConfigure.getTaskType();
                if (taskType < type && taskType > type - 10){
                    return pipelineConfigure;
                }
            }
        }
        return null;
    }

    @Override
    public PipelineConfigure findOneTask(String pipelineId,String taskId) {
        List<PipelineConfigure> allConfigure = findAllConfigure(pipelineId);
        if (allConfigure != null){
            for (PipelineConfigure pipelineConfigure : allConfigure) {
                if (pipelineConfigure.getTaskId().equals(taskId)){
                    return pipelineConfigure;
                }
            }
        }
        return null;
    }

    //更新配置
    @Override
    public void updateConfigure(PipelineConfigure pipelineConfigure){
        pipelineConfigureDao.updateConfigure(BeanMapper.map(pipelineConfigure,PipelineConfigureEntity.class));
    }

    //创建配置信息
    @Override
    public void createTask(PipelineConfigure pipelineConfigure,String pipelineId){
        Pipeline pipeline = new Pipeline();
        pipeline.setPipelineId(pipelineId);
        pipelineConfigure.setPipeline(pipeline);
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        pipelineConfigure.setCreateTime(time);
        createConfigure(pipelineConfigure);
    }

    //删除任务
    @Override
    public void deleteTask(String taskId,String pipelineId) {
        List<PipelineConfigure> allConfigure = findAllConfigure(pipelineId);
        if (allConfigure != null){
            for (PipelineConfigure pipelineConfigure : allConfigure) {
                if (pipelineConfigure.getTaskId().equals(taskId)){
                    PipelineConfigure oneConfigure = findOneConfigure(pipelineConfigure.getConfigureId());
                    pipelineCodeService.deleteTask(oneConfigure.getTaskId(),oneConfigure.getTaskType());
                    deleteConfigure(pipelineConfigure.getConfigureId());
                }
            }
        }
    }

    //删除所有任务
    @Override
    public void deleteAllTask(String pipelineId) {
        List<PipelineConfigure> allConfigure = findAllConfigure(pipelineId);
        if(allConfigure != null){
            for (PipelineConfigure pipelineConfigure : allConfigure) {
                deleteTask(pipelineConfigure.getTaskId(),pipelineId);
            }
        }
    }

    //更新任务
    @Override
    public void updateTask(PipelineExecConfigure pipelineExecConfigure){
        pipelineCodeService.updateTask(pipelineExecConfigure);
    }

    //查询
    @Override
    public PipelineConfigure findOneConfigure(String configureId) {
        PipelineConfigureEntity oneConfigure = pipelineConfigureDao.findOneConfigure(configureId);
        joinTemplate.joinQuery(oneConfigure);
        return BeanMapper.map(oneConfigure,PipelineConfigure.class);
    }

    @Override
    public Pipeline findOnePipeline(String pipelineId) {
        return null;
    }

    //查询所有
    @Override
    public List<PipelineConfigure> findAllConfigure() {
        List<PipelineConfigureEntity> allConfigure = pipelineConfigureDao.findAllConfigure();
        List<PipelineConfigure> pipelineConfigureList = BeanMapper.mapList(allConfigure, PipelineConfigure.class);
        joinTemplate.joinQuery(pipelineConfigureList);
        return pipelineConfigureList;
    }

    //查询配置
    @Override
    public  List<Object> findAll(String pipelineId){
        List<Object> list = new ArrayList<>();
        List<PipelineConfigure> allConfigure = findAllConfigure(pipelineId);
        if (allConfigure != null){
            for (PipelineConfigure pipelineConfigure : allConfigure) {
               pipelineCodeService.findOneTask(pipelineConfigure,list);
            }
        }
        return list;
    }

    //通过流水线id查询所有配置
    @Override
    public List<PipelineConfigure> findAllConfigure(String pipelineId) {
        List<PipelineConfigure> allConfigure = findAllConfigure();
        if (allConfigure.size() == 0){
            return null;
        }
        allConfigure.sort(Comparator.comparing(PipelineConfigure::getTaskSort));
        List<PipelineConfigure> pipelineConfigures = new ArrayList<>();
        for (PipelineConfigure pipelineConfigure : allConfigure) {
            if (pipelineConfigure.getPipeline() == null){
                continue;
            }
            if (pipelineConfigure.getPipeline().getPipelineId().equals(pipelineId)){
                pipelineConfigures.add(pipelineConfigure);
            }
        }
        if (pipelineConfigures .size() != 0){
            allConfigure.sort(Comparator.comparing(PipelineConfigure::getTaskSort));
        }
        return pipelineConfigures;
    }

    @Override
    public List<PipelineConfigure> findAllConfigureList(List<String> idList) {
        List<PipelineConfigureEntity> pipelineConfigureEntityList = pipelineConfigureDao.findAllConfigureList(idList);
        return BeanMapper.mapList(pipelineConfigureEntityList, PipelineConfigure.class);
    }


}

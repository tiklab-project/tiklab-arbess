package com.doublekit.pipeline.instance.service;

import com.doublekit.pipeline.instance.dao.PipelineActionDao;
import com.doublekit.pipeline.instance.entity.PipelineActionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PipelineActionServiceImpl {

    public PipelineActionServiceImpl() {
    }

    @Autowired
     PipelineActionDao pipelineActionDao;

    //添加动态
    public  void createActive(PipelineActionEntity pipelineActionEntity){
        pipelineActionDao.createAction(pipelineActionEntity);
    }

    //删除
    public  void deleteAction(String activeId){
        pipelineActionDao.deleteAction(activeId);
    }

    //更改
    public  void updateActive(PipelineActionEntity pipelineActionEntity){
        pipelineActionDao.updateAction(pipelineActionEntity);
    }

    public  PipelineActionEntity findOneActive(String activeId){
        return pipelineActionDao.findOneAction(activeId);
    }

    public  List<PipelineActionEntity> findAllActive(){
        return pipelineActionDao.findAllAction();
    }
}

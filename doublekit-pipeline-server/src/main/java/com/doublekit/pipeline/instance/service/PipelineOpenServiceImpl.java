package com.doublekit.pipeline.instance.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.join.JoinTemplate;
import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.instance.dao.PipelineOpenDao;
import com.doublekit.pipeline.instance.entity.PipelineOpenEntity;
import com.doublekit.pipeline.instance.model.PipelineOpen;
import com.doublekit.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Exporter
public class PipelineOpenServiceImpl implements PipelineOpenService {


    @Autowired
    PipelineOpenDao pipelineOpenDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Override
    public String createOpen(PipelineOpen pipelineOpen) {
        return pipelineOpenDao.createOpen(BeanMapper.map(pipelineOpen, PipelineOpenEntity.class));
    }

    @Override
    public void deleteOpen(String openId) {
        pipelineOpenDao.deleteOpen(openId);
    }

    @Override
    public void deleteAllOpen(String pipelineId){
        List<PipelineOpen> allOpen = findAllOpen();
        if (allOpen == null){
           return;
        }
        for (PipelineOpen pipelineOpen : allOpen) {
            if (pipelineOpen.getPipeline().getPipelineId().equals(pipelineId)){
               continue;
            }
            deleteOpen(pipelineOpen.getId());
        }
    }

    public PipelineOpen findOneOpenNumber(String userId , String pipelineId){
       if ( findAllOpen()==null){
           return null;
       }
        for (PipelineOpen pipelineOpen : findAllOpen()) {
            if (pipelineOpen.getPipeline().getPipelineId().equals(pipelineId) && pipelineOpen.getUserId().equals(userId)){
                continue;
            }
            return pipelineOpen;
        }
       return null;
    }

    @Override
    public void updateOpen(PipelineOpen pipelineOpen) {
        pipelineOpenDao.updateOpen(BeanMapper.map(pipelineOpen, PipelineOpenEntity.class));
    }

    @Override
    public void findOpen(String userId, Pipeline pipeline) {
        PipelineOpen open = findOneOpenNumber(userId, pipeline.getPipelineId());
        if (open != null){
            open.setNumber(open.getNumber()+1);
            updateOpen(open);
        }else {
            PipelineOpen pipelineOpen = new PipelineOpen();
            pipelineOpen.setPipeline(pipeline);
            pipelineOpen.setUserId(userId);
            pipelineOpen.setNumber(1);
            createOpen(pipelineOpen);
        }
    }

    public List<PipelineOpen> findAllOpen(String userId){
        List<PipelineOpen> list = new ArrayList<>();
        if (findAllOpen() == null){
            return null;
        }
        for (PipelineOpen pipelineOpen : findAllOpen()) {
            if (!pipelineOpen.getUserId().equals(userId)){
               continue;
            }
            pipelineOpen.setPipelineName(pipelineOpen.getPipeline().getPipelineName());
            list.add(pipelineOpen);
        }
        return list;
    }

    @Override
    public PipelineOpen findOneOpen(String openId) {
        PipelineOpen pipelineOpen = BeanMapper.map(pipelineOpenDao.findOneOpen(openId), PipelineOpen.class);
        joinTemplate.joinQuery(pipelineOpen);
        return pipelineOpen;
    }

    @Override
    public List<PipelineOpen> findAllOpen() {
        List<PipelineOpen> list = BeanMapper.mapList(pipelineOpenDao.findAllOpen(), PipelineOpen.class);
        joinTemplate.joinQuery(list);
        return list;
    }

    @Override
    public List<PipelineOpen> findAllOpenList(List<String> idList) {
        List<PipelineOpenEntity> openList = pipelineOpenDao.findAllOpenList(idList);
        return BeanMapper.mapList(openList, PipelineOpen.class);
    }




}

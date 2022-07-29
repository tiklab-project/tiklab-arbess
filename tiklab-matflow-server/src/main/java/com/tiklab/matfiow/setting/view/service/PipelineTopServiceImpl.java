package com.tiklab.matfiow.setting.view.service;

import com.doublekit.beans.BeanMapper;
import com.tiklab.matfiow.setting.view.dao.PipelineTopDao;
import com.tiklab.matfiow.setting.view.entity.PipelineTopEntity;
import com.tiklab.matfiow.setting.view.model.PipelineTop;
import com.doublekit.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Exporter
public class PipelineTopServiceImpl implements PipelineTopService{

    @Autowired
    PipelineTopDao pipelineTopDao;


    @Override
    public String createTop(PipelineTop pipelineTop) {
        return pipelineTopDao.createTop(BeanMapper.map(pipelineTop, PipelineTopEntity.class));
    }

    @Override
    public void deleteTop(String topId) {
        pipelineTopDao.deleteTop(topId);
    }

    @Override
    public void updateTop(PipelineTop pipelineTop) {
        pipelineTopDao.updateTop(BeanMapper.map(pipelineTop, PipelineTopEntity.class));
    }

    @Override
    public PipelineTop findOneTop(String topId) {
        return BeanMapper.map(pipelineTopDao.findOneTop(topId), PipelineTop.class);
    }

    public List<PipelineTop>  findAllViewTop(String viewId){
        boolean empty = findAllTop().isEmpty();
        if (!empty){
            List<PipelineTop> list = new ArrayList<>();
            for (PipelineTop pipelineTop : findAllTop()) {
                if (pipelineTop.getViewId().equals(viewId)){
                    list.add(pipelineTop);
                }
            }
            return list;
        }
       return null;
    }

    @Override
    public List<PipelineTop> findAllTop() {
        return BeanMapper.mapList(pipelineTopDao.findAllTop(), PipelineTop.class);
    }
}

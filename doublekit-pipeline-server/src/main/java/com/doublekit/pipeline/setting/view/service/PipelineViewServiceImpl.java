package com.doublekit.pipeline.setting.view.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.pipeline.setting.view.dao.PipelineViewDao;
import com.doublekit.pipeline.setting.view.entity.PipelineViewEntity;
import com.doublekit.pipeline.setting.view.model.PipelineView;
import com.doublekit.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class PipelineViewServiceImpl implements  PipelineViewService{
    
    @Autowired
    PipelineViewDao pipelineViewDao;


    @Override
    public String createView(PipelineView pipelineView) {
        return pipelineViewDao.createView(BeanMapper.map(pipelineView, PipelineViewEntity.class));
    }

    @Override
    public void deleteView(String viewId) {
        pipelineViewDao.deleteView(viewId);
    }

    @Override
    public void updateView(PipelineView pipelineView) {
        pipelineViewDao.updateView(BeanMapper.map(pipelineView, PipelineViewEntity.class));
    }

    @Override
    public PipelineView findOneView(String viewId) {
        return BeanMapper.map(pipelineViewDao.findOneView(viewId), PipelineView.class);
    }

    @Override
    public List<PipelineView> findAllView() {
        return BeanMapper.mapList(pipelineViewDao.findAllView(), PipelineView.class);
    }
}

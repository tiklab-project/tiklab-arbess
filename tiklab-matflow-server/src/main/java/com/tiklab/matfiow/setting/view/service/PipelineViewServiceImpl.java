package com.tiklab.matfiow.setting.view.service;

import com.doublekit.beans.BeanMapper;
import com.tiklab.matfiow.setting.view.dao.PipelineViewDao;
import com.tiklab.matfiow.setting.view.entity.PipelineViewEntity;
import com.tiklab.matfiow.setting.view.model.PipelineTop;
import com.tiklab.matfiow.setting.view.model.PipelineView;
import com.doublekit.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class PipelineViewServiceImpl implements  PipelineViewService{
    
    @Autowired
    PipelineViewDao pipelineViewDao;

    @Autowired
    PipelineTopService pipelineTopService;


    @Override
    public String createView(PipelineView pipelineView) {
        return pipelineViewDao.createView(BeanMapper.map(pipelineView, PipelineViewEntity.class));
    }

    @Override
    public String createViewTop(PipelineView pipelineView){
        String viewId = createView(pipelineView);
        if (!pipelineView.getList().isEmpty()){
            for (String s : pipelineView.getList()) {
                PipelineTop pipelineTop = new PipelineTop();
                pipelineTop.setViewId(viewId);
                pipelineTop.setPipelineId(s);
                pipelineTopService.createTop(pipelineTop);
            }
        }
        return viewId;
    }

    @Override
    public void deleteView(String viewId) {
        pipelineViewDao.deleteView(viewId);
    }

    @Override
    public void deleteViewTop(String viewId) {
        List<PipelineTop> topList = pipelineTopService.findAllViewTop(viewId);
        if (!topList.isEmpty()){
            for (PipelineTop pipelineTop : topList) {
                pipelineTopService.deleteTop(pipelineTop.getId());
            }
        }
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

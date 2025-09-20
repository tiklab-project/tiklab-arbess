package io.tiklab.arbess.pipeline.definition.service;


import io.tiklab.arbess.pipeline.definition.dao.PipelineOpenDao;
import io.tiklab.arbess.pipeline.definition.entity.PipelineOpenEntity;
import io.tiklab.arbess.pipeline.definition.model.Pipeline;
import io.tiklab.arbess.pipeline.definition.model.PipelineOpen;
import io.tiklab.arbess.pipeline.definition.model.PipelineOpenQuery;
import io.tiklab.arbess.pipeline.overview.model.PipelineOverview;
import io.tiklab.arbess.pipeline.overview.service.PipelineOverviewService;
import io.tiklab.arbess.support.authority.service.PipelineAuthorityService;
import io.tiklab.arbess.support.util.util.PipelineUtil;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.eam.common.context.LoginContext;
import io.tiklab.rpc.annotation.Exporter;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.toolkit.join.JoinTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Exporter
public class PipelineOpenServiceImpl implements PipelineOpenService {

    @Autowired
    PipelineOpenDao pipelineOpenDao;

    @Autowired
    PipelineAuthorityService authorityService;

    @Autowired
    PipelineOverviewService overviewService;

    @Autowired
    JoinTemplate joinTemplate;

    @Override
    public void deleteAllOpen(String pipelineId){
        PipelineOpenQuery pipelineOpenQuery = new PipelineOpenQuery();
        pipelineOpenQuery.setPipelineId(pipelineId);
        List<PipelineOpen> allOpen = findOpenList(pipelineOpenQuery);
        if (allOpen.isEmpty()){
           return;
        }
        for (PipelineOpen pipelineOpen : allOpen) {
            deleteOpen(pipelineOpen.getOpenId());
        }
    }

    @Override
    public void updatePipelineOpen(String pipelineId) {

        String loginId = LoginContext.getLoginId();
        PipelineOpenQuery pipelineOpenQuery = new PipelineOpenQuery();
        pipelineOpenQuery.setPipelineId(pipelineId);
        pipelineOpenQuery.setUserId(loginId);
        List<PipelineOpen> openList = findOpenList(pipelineOpenQuery);

        if (openList.isEmpty()){
            PipelineOpen pipelineOpen = new PipelineOpen();
            pipelineOpen.setPipeline(new Pipeline(pipelineId));
            pipelineOpen.setCreateTime(PipelineUtil.date(1));
            pipelineOpen.setUserId(loginId);
            pipelineOpen.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            createOpen(pipelineOpen);
        }else {
            PipelineOpen pipelineOpen = openList.get(0);
            pipelineOpen.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            updateOpen(pipelineOpen);
        }
    }

    @Override
    public PipelineOpen findOneOpen(String openId) {
        PipelineOpenEntity openEntity = pipelineOpenDao.findOneOpen(openId);
        PipelineOpen pipelineOpen = BeanMapper.map(openEntity, PipelineOpen.class);
        joinTemplate.joinQuery(pipelineOpen,new String[]{"pipeline"});
        return pipelineOpen;
    }

    @Override
    public List<PipelineOpen> findAllOpen() {
        List<PipelineOpenEntity> allOpen = pipelineOpenDao.findAllOpen();
        if (Objects.isNull(allOpen)){
            return new ArrayList<>();
        }
        List<PipelineOpen> list = BeanMapper.mapList(allOpen, PipelineOpen.class);
        joinTemplate.joinQuery(list,new String[]{"pipeline"});
        return list;
    }

    public List<PipelineOpen> findOpenByQuery(PipelineOpenQuery query) {
        List<PipelineOpen> openList = findOpenList(query);
        joinTemplate.joinQuery(openList,new String[]{"pipeline"});
        return openList;
    }

    @Override
    public List<PipelineOpen> findOpenList(List<String> idList) {
        List<PipelineOpenEntity> openList = pipelineOpenDao.findAllOpenList(idList);
        return BeanMapper.mapList(openList, PipelineOpen.class);
    }

    @Override
    public List<PipelineOpen> findOpenList(PipelineOpenQuery query) {
        String userId = query.getUserId();
        if (!StringUtils.isEmpty(userId)){
            String[] userPipelineIdString = authorityService.findUserPipelineIdString(userId);
            query.setPipelineIds(userPipelineIdString);
        }
        List<PipelineOpenEntity> openList = pipelineOpenDao.findPipelineOpenList(query);
        return BeanMapper.mapList(openList, PipelineOpen.class);
    }

    @Override
    public Pagination<PipelineOpen> findOpenPage(PipelineOpenQuery query) {
        String userId = query.getUserId();
        if (!StringUtils.isEmpty(userId)){
            String[] userPipelineIdString = authorityService.findUserPipelineIdString(userId);
            query.setPipelineIds(userPipelineIdString);
        }

        Pagination<PipelineOpenEntity> pipelineOpenPage = pipelineOpenDao.findPipelineOpenPage(query);
        List<PipelineOpenEntity> dataList = pipelineOpenPage.getDataList();
        List<PipelineOpen> openList = BeanMapper.mapList(dataList, PipelineOpen.class);
        joinTemplate.joinQuery(openList,new String[]{"pipeline"});
        for (PipelineOpen pipelineOpen : openList) {
            String pipelineId = pipelineOpen.getPipeline().getId();
            PipelineOverview pipelineOverview = overviewService.pipelineOverview(pipelineId);
            pipelineOpen.setExecStatus(pipelineOverview);
        }
        return PaginationBuilder.build(pipelineOpenPage,openList);
    }

    //更新最近打开
    private void updateOpen(PipelineOpen pipelineOpen) {
        pipelineOpenDao.updateOpen(BeanMapper.map(pipelineOpen, PipelineOpenEntity.class));
    }

    //删除最近打开
    private void deleteOpen(String openId) {
        pipelineOpenDao.deleteOpen(openId);
    }

    //创建最近打开
    private void createOpen(PipelineOpen pipelineOpen) {
        pipelineOpenDao.createOpen(BeanMapper.map(pipelineOpen, PipelineOpenEntity.class));
    }


}
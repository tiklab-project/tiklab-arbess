package io.tiklab.arbess.pipeline.definition.service;


import io.tiklab.arbess.pipeline.definition.dao.PipelineDao;
import io.tiklab.arbess.pipeline.definition.entity.PipelineEntity;
import io.tiklab.arbess.support.authority.service.PipelineAuthorityService;
import io.tiklab.arbess.support.util.util.PipelineUtil;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.eam.common.context.LoginContext;
import io.tiklab.toolkit.join.JoinTemplate;
import io.tiklab.arbess.pipeline.definition.dao.PipelineOpenDao;
import io.tiklab.arbess.pipeline.definition.entity.PipelineOpenEntity;
import io.tiklab.arbess.pipeline.definition.model.Pipeline;
import io.tiklab.arbess.pipeline.definition.model.PipelineOpen;
import io.tiklab.arbess.pipeline.overview.model.PipelineOverview;
import io.tiklab.arbess.pipeline.overview.service.PipelineOverviewService;
import io.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Autowired
    PipelineDao pipelineDao;


    @Override
    public void deleteAllOpen(String pipelineId){
        List<PipelineOpen> allOpen = findAllOpenNoQuery();
        if (allOpen.isEmpty()){
           return;
        }
        for (PipelineOpen pipelineOpen : allOpen) {
            Pipeline pipeline = pipelineOpen.getPipeline();
            if (!pipeline.getId().equals(pipelineId)){
               continue;
            }
            deleteOpen(pipelineOpen.getOpenId());
        }
    }

    @Override
    public void updatePipelineOpen(String pipelineId) {
        if (pipelineId.equals("undefined")){
            return;
        }
        String userId = LoginContext.getLoginId();
        PipelineOpen pipelineOpen = new PipelineOpen();
        pipelineOpen.setPipeline(new Pipeline(pipelineId));
        pipelineOpen.setCreateTime(PipelineUtil.date(1));
        pipelineOpen.setUserId(userId);
        createOpen(pipelineOpen);
    }

    @Override
    public PipelineOpen findOneOpen(String openId) {
        PipelineOpenEntity openEntity = pipelineOpenDao.findOneOpen(openId);
        PipelineOpen pipelineOpen = BeanMapper.map(openEntity, PipelineOpen.class);
        joinTemplate.joinQuery(pipelineOpen);
        return pipelineOpen;
    }

    public PipelineOpen findOneOpenNoQuery(String openId) {
        PipelineOpenEntity openEntity = pipelineOpenDao.findOneOpen(openId);
        return BeanMapper.map(openEntity, PipelineOpen.class);
    }

    @Override
    public List<PipelineOpen> findAllOpen() {
        List<PipelineOpenEntity> allOpen = pipelineOpenDao.findAllOpen();
        if (Objects.isNull(allOpen)){
            return new ArrayList<>();
        }
        List<PipelineOpen> list = BeanMapper.mapList(allOpen, PipelineOpen.class);
        joinTemplate.joinQuery(list);
        return list;
    }

    public List<PipelineOpen> findAllOpenNoQuery() {
        List<PipelineOpenEntity> allOpen = pipelineOpenDao.findAllOpen();
        if (Objects.isNull(allOpen)){
            return new ArrayList<>();
        }
        return BeanMapper.mapList(allOpen, PipelineOpen.class);
    }

    @Override
    public List<PipelineOpen> findAllOpenList(List<String> idList) {
        List<PipelineOpenEntity> openList = pipelineOpenDao.findAllOpenList(idList);
        return BeanMapper.mapList(openList, PipelineOpen.class);
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

    public List<String> findUserOpen(int number){
        // 获取用户流水线
        String userId = LoginContext.getLoginId();
        String[] userPipeline = authorityService.findUserPipelineIdString(userId);
        if (userPipeline.length == 0){
            return new ArrayList<>();
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < userPipeline.length; i++) {
            builder.append("'").append(userPipeline[i]).append("'");
            if (i != userPipeline.length-1){
                builder.append(",");
            }
        }

        List<String> pipelineIds = pipelineOpenDao.findUserOpen(userId, number,builder.toString());
        if (pipelineIds.isEmpty()){
            return new ArrayList<>();
        }
        return pipelineIds;
    }

    /**
     * 查询流水线最近打开
     * @param number 查询数量
     * @return 最近打开的流水线
     */
    @Override
    public List<PipelineOpen> findUserAllOpen(int number) {

        // 获取用户流水线
        String userId = LoginContext.getLoginId();

        List<String> pipelineIds = findUserOpen(number);
        if (pipelineIds.isEmpty()){
            return new ArrayList<>();
        }

        List<PipelineOpen> openList = new ArrayList<>();

        for (String pipelineId : pipelineIds) {
            PipelineOpen pipelineOpen = new PipelineOpen();

            PipelineEntity pipelineEntity = pipelineDao.findPipelineById(pipelineId);
            Pipeline pipeline = BeanMapper.map(pipelineEntity, Pipeline.class);
            pipelineOpen.setPipeline(pipeline);

            Integer openNumber = pipelineOpenDao.findUserOpenNumber(userId, pipelineId);
            pipelineOpen.setNumber(openNumber);

            PipelineOverview pipelineOverview = overviewService.pipelineOverview(pipelineId);
            pipelineOpen.setPipelineExecState(pipelineOverview);

            if (Objects.isNull(pipeline) || Objects.isNull(pipeline.getName())){
                deleteOpen(pipelineOpen.getOpenId());
                continue;
            }

            openList.add(pipelineOpen);
        }
        return openList;
    }


}
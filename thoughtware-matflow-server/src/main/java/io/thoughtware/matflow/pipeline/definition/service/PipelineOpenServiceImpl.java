package io.thoughtware.matflow.pipeline.definition.service;


import io.thoughtware.matflow.pipeline.definition.dao.PipelineDao;
import io.thoughtware.matflow.pipeline.definition.entity.PipelineEntity;
import io.thoughtware.matflow.support.authority.service.PipelineAuthorityService;
import io.thoughtware.matflow.support.util.util.PipelineUtil;
import io.thoughtware.toolkit.beans.BeanMapper;
import io.thoughtware.eam.common.context.LoginContext;
import io.thoughtware.toolkit.join.JoinTemplate;
import io.thoughtware.matflow.pipeline.definition.dao.PipelineOpenDao;
import io.thoughtware.matflow.pipeline.definition.entity.PipelineOpenEntity;
import io.thoughtware.matflow.pipeline.definition.model.Pipeline;
import io.thoughtware.matflow.pipeline.definition.model.PipelineOpen;
import io.thoughtware.matflow.pipeline.overview.model.PipelineOverview;
import io.thoughtware.matflow.pipeline.overview.service.PipelineOverviewService;
import io.thoughtware.rpc.annotation.Exporter;
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
        if (allOpen == null){
           return;
        }
        for (PipelineOpen pipelineOpen : allOpen) {
            // joinTemplate.joinQuery(pipelineOpen);
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

    public List<PipelineOpen> findAllOpenNoQuery() {
        List<PipelineOpen> list = BeanMapper.mapList(pipelineOpenDao.findAllOpen(), PipelineOpen.class);
        // joinTemplate.joinQuery(list);
        return list;
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
            return Collections.emptyList();
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
            return Collections.emptyList();
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
            return Collections.emptyList();
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
package io.tiklab.matflow.pipeline.definition.service;


import io.tiklab.beans.BeanMapper;
import io.tiklab.eam.common.context.LoginContext;
import io.tiklab.join.JoinTemplate;
import io.tiklab.matflow.pipeline.definition.dao.PipelineOpenDao;
import io.tiklab.matflow.pipeline.definition.entity.PipelineEntity;
import io.tiklab.matflow.pipeline.definition.entity.PipelineOpenEntity;
import io.tiklab.matflow.pipeline.definition.model.Pipeline;
import io.tiklab.matflow.pipeline.definition.model.PipelineOpen;
import io.tiklab.matflow.pipeline.definition.model.PipelineOpenQuery;
import io.tiklab.matflow.pipeline.overview.model.PipelineOverview;
import io.tiklab.matflow.pipeline.overview.service.PipelineOverviewService;
import io.tiklab.matflow.support.authority.service.PipelineAuthorityService;
import io.tiklab.matflow.support.util.PipelineUtil;
import io.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
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


    @Override
    public void deleteAllOpen(String pipelineId){
        List<PipelineOpen> allOpen = findAllOpen();
        if (allOpen == null){
           return;
        }
        for (PipelineOpen pipelineOpen : allOpen) {
            joinTemplate.joinQuery(pipelineOpen);
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

        new Thread(() -> {
            List<String> pipelineIds = pipelineOpenDao.findUserPipelineOpen(userId, 100);
            if (pipelineIds.size() < 10){
                return;
            }
            for (int i = 9; i < pipelineIds.size(); i++) {
                PipelineOpenQuery pipelineOpenQuery = new PipelineOpenQuery();
                pipelineOpenQuery.setPipelineId(pipelineIds.get(i));
                List<PipelineOpenEntity> pipelineOpenList = pipelineOpenDao.findPipelineOpenList(pipelineOpenQuery);
                for (PipelineOpenEntity pipelineOpenEntity : pipelineOpenList) {
                    deleteOpen(pipelineOpenEntity.getOpenId());
                }
            }
        }).start();

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


    /**
     * 查询流水线最近打开
     * @param number 查询数量
     * @return 最近打开的流水线
     */
    @Override
    public List<PipelineOpen> findUserAllOpen(int number) {

        String userId = LoginContext.getLoginId();
        List<String> pipelineIds = pipelineOpenDao.findUserPipelineOpen(userId, number);
        if (pipelineIds.isEmpty()){
            return Collections.emptyList();
        }

        List<Pipeline> userPipeline = authorityService.findUserPipeline(userId);
        if (userPipeline.isEmpty()){
            return Collections.emptyList();
        }

        List<PipelineOpen> openList = new ArrayList<>();

        for (String pipelineId : pipelineIds) {
            PipelineOpen pipelineOpen = new PipelineOpen();
            Date date = PipelineUtil.findDate(Calendar.DATE, -7);

            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
            Integer openNumber = pipelineOpenDao.findUserOpenPipelineNumber(userId, pipelineId, format);
            pipelineOpen.setNumber(openNumber);

            pipelineOpen.setPipeline(new Pipeline(pipelineId));

            PipelineOverview pipelineOverview = overviewService.pipelineOverview(pipelineId);
            pipelineOpen.setPipelineExecState(pipelineOverview);

            joinTemplate.joinQuery(pipelineOpen);

            openList.add(pipelineOpen);
        }
        return openList;
    }


}

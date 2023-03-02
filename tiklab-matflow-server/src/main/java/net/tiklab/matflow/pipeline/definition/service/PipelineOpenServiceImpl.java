package net.tiklab.matflow.pipeline.definition.service;


import net.tiklab.beans.BeanMapper;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.pipeline.definition.model.Pipeline;
import net.tiklab.matflow.pipeline.overview.model.PipelineOverview;
import net.tiklab.matflow.pipeline.definition.dao.PipelineOpenDao;
import net.tiklab.matflow.pipeline.definition.entity.PipelineOpenEntity;
import net.tiklab.matflow.pipeline.definition.model.PipelineOpen;
import net.tiklab.matflow.pipeline.overview.service.PipelineOverviewService;
import net.tiklab.matflow.support.authority.service.PipelineAuthorityService;
import net.tiklab.matflow.support.util.PipelineUtil;
import net.tiklab.rpc.annotation.Exporter;
import net.tiklab.utils.context.LoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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


    //删除流水线所有最近打开信息
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
    public void updatePipelineOpen(String userId, String pipelineId) {
        PipelineOpen open = findPipelineOpenNumber(userId, pipelineId);
        if (open != null){
            open.setNumber(open.getNumber()+1);
            updateOpen(open);
        }else {
            PipelineOpen pipelineOpen = new PipelineOpen();
            pipelineOpen.setUserId(userId);
            pipelineOpen.setPipeline(new Pipeline(pipelineId));
            pipelineOpen.setNumber(1);
            pipelineOpen.setCreateTime(PipelineUtil.date(2));
            createOpen(pipelineOpen);
        }
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

    //获取流水线打开次数
    private PipelineOpen findPipelineOpenNumber(String userId , String pipelineId){
        if ( findAllOpen()==null){
            return null;
        }
        for (PipelineOpen pipelineOpen : findAllOpen()) {
            String user = pipelineOpen.getUserId();
            Pipeline pipeline = pipelineOpen.getPipeline();
            if (pipeline.getId().equals(pipelineId) && user.equals(userId)){
                return pipelineOpen;
            }
        }
        return null;
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
        StringBuilder builder = authorityService.findUserPipelineIdString(LoginContext.getLoginId());
        List<PipelineOpen> allOpen = findUserAllOpen(builder) ;
        if (allOpen == null){
            return Collections.emptyList() ;
        }
        //根据打开次数降序排列
        allOpen.sort(Comparator.comparing(PipelineOpen::getNumber).reversed()) ;
        // 指定返回数量
        int min = Math.min(number, allOpen.size()) ;
        List<PipelineOpen> pipelineOpens = allOpen.subList(0, min);
        //统计信息
        for (PipelineOpen pipelineOpen : pipelineOpens) {
            Pipeline pipeline = pipelineOpen.getPipeline();
            PipelineOverview pipelineOverview = overviewService.pipelineOverview(pipeline.getId());
            pipelineOpen.setPipelineExecState(pipelineOverview);
        }
        return pipelineOpens;
    }

    //查询流水线最近打开
    private List<PipelineOpen> findUserAllOpen(StringBuilder s){
        String loginId = LoginContext.getLoginId();
        if (s.toString().equals("")){
            return null;
        }
        List<PipelineOpenEntity> allOpen = pipelineOpenDao.findUserAllOpen(loginId,s);
        List<PipelineOpen> list = BeanMapper.mapList(allOpen, PipelineOpen.class);
        joinTemplate.joinQuery(list);
        List<PipelineOpen> openList = new ArrayList<>();
        for (PipelineOpen pipelineOpen : list) {
            Pipeline pipeline = pipelineOpen.getPipeline();
            String name = pipeline.getName();
            if (!PipelineUtil.isNoNull(name)){
                deleteOpen(pipelineOpen.getOpenId());
                continue;
            }
            openList.add(pipelineOpen);
        }
        return openList;
    }






}

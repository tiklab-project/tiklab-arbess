package io.tiklab.matflow.pipeline.definition.service;


import io.tiklab.beans.BeanMapper;
import io.tiklab.eam.common.context.LoginContext;
import io.tiklab.join.JoinTemplate;
import io.tiklab.matflow.pipeline.definition.dao.PipelineOpenDao;
import io.tiklab.matflow.pipeline.definition.entity.PipelineOpenEntity;
import io.tiklab.matflow.pipeline.definition.model.Pipeline;
import io.tiklab.matflow.pipeline.definition.model.PipelineOpen;
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
        String userId = LoginContext.getLoginId();

        PipelineOpen pipelineOpen = new PipelineOpen();
        pipelineOpen.setPipeline(new Pipeline(pipelineId));
        pipelineOpen.setCreateTime(PipelineUtil.date(1));
        pipelineOpen.setUserId(userId);
        createOpen(pipelineOpen);

        // PipelineOpen open = findPipelineOpenNumber(userId, pipelineId);
        // if (open != null){
        //     open.setNumber(open.getNumber()+1);
        //     updateOpen(open);
        // }else {
        //     PipelineOpen pipelineOpen = new PipelineOpen();
        //     pipelineOpen.setUserId(userId);
        //     pipelineOpen.setPipeline(new Pipeline(pipelineId));
        //     pipelineOpen.setNumber(1);
        //     pipelineOpen.setCreateTime(PipelineUtil.date(2));
        //     createOpen(pipelineOpen);
        // }
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
        String userId = LoginContext.getLoginId();
        List<Pipeline> userPipeline = authorityService.findUserPipeline(userId);
        if (userPipeline.isEmpty()){
            return Collections.emptyList();
        }

        List<PipelineOpen> openList = new ArrayList<>();
        for (Pipeline pipeline : userPipeline) {
            String pipelineId = pipeline.getId();
            // // 判断天数是否超过7天
            // String openTime = pipelineOpenDao.findUserLastOpenPipeline(userId, pipelineId);
            // if (openTime.length() < 11){
            //     openTime = openTime +" 00:00:00";
            // }
            PipelineOpen pipelineOpen = new PipelineOpen();

            Date date = PipelineUtil.findDate(Calendar.DATE, -7);

            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
            Integer openNumber = pipelineOpenDao.findUserOpenPipelineNumber(userId, pipelineId, format);
            pipelineOpen.setNumber(openNumber);

            PipelineOverview pipelineOverview = overviewService.pipelineOverview(pipelineId);
            pipelineOpen.setPipelineExecState(pipelineOverview);
            pipelineOpen.setPipeline(pipeline);

            openList.add(pipelineOpen);
        }
        openList.sort(Comparator.comparing(PipelineOpen::getNumber).reversed());

        List<PipelineOpen> list = new ArrayList<>();
        for (int i = 0; i < openList.size(); i++) {
            if (i >= number){
                continue;
            }
            list.add(openList.get(i));
        }
        return list;
    }

    //查询流水线最近打开
    private List<PipelineOpen> findUserAllOpen(){
        String loginId = LoginContext.getLoginId();
        List<PipelineOpenEntity> allOpen = pipelineOpenDao.findUserAllOpen(loginId);
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

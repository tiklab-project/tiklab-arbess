package net.tiklab.matflow.orther.service;


import net.tiklab.beans.BeanMapper;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.orther.dao.PipelineOpenDao;
import net.tiklab.matflow.orther.entity.PipelineOpenEntity;
import net.tiklab.matflow.orther.model.PipelineOpen;
import net.tiklab.matflow.orther.until.PipelineUntil;
import net.tiklab.rpc.annotation.Exporter;
import net.tiklab.utils.context.LoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class PipelineOpenServiceImpl implements PipelineOpenService {

    @Autowired
    PipelineOpenDao pipelineOpenDao;

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
            pipelineOpen.setCreateTime(PipelineUntil.date(2));
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

    //查询流水线最近打开
    @Override
    public List<PipelineOpen> findAllOpen(StringBuilder s){
        String loginId = LoginContext.getLoginId();
        if (s.toString().equals("")){
            return null;
        }
        List<PipelineOpenEntity> allOpen = pipelineOpenDao.findAllOpen(loginId,s);
        List<PipelineOpen> list = BeanMapper.mapList(allOpen, PipelineOpen.class);
        joinTemplate.joinQuery(list);
        return list;
    }


}

package net.tiklab.matflow.orther.service;


import net.tiklab.beans.BeanMapper;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.definition.service.PipelineService;
import net.tiklab.matflow.orther.dao.PipelineOpenDao;
import net.tiklab.matflow.orther.entity.PipelineOpenEntity;
import net.tiklab.matflow.orther.model.PipelineOpen;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@Exporter
public class PipelineOpenServiceImpl implements PipelineOpenService {

    @Autowired
    PipelineOpenDao pipelineOpenDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    PipelineService pipelineService;


    //查询用户最近打开的流水线
    @Override
    public List<PipelineOpen> findAllOpen(String userId){
        StringBuilder s = pipelineService.findUserPipelineId(userId);
        if (s == null){
            return null;
        }
        List<PipelineOpen> allOpen = findAllOpen(userId,s);
        if (allOpen == null){
            return null;
        }
        allOpen.sort(Comparator.comparing(PipelineOpen::getNumber,Comparator.reverseOrder()));
        int size = Math.min(allOpen.size(), 6);
        return allOpen.subList(0, size);
    }

    //删除流水线所有最近打开信息
    @Override
    public void deleteAllOpen(String pipelineId){
        List<PipelineOpen> allOpen = findAllOpen();
        if (allOpen == null){
           return;
        }
        for (PipelineOpen pipelineOpen : allOpen) {
            joinTemplate.joinQuery(pipelineOpen);
            if (!pipelineOpen.getPipeline().getPipelineId().equals(pipelineId)){
               continue;
            }
            deleteOpen(pipelineOpen.getId());
        }
    }

    @Override
    public void findOpen(String userId, Pipeline pipeline) {
        PipelineOpen open = findOneOpenNumber(userId, pipeline.getPipelineId());
        if (open != null){
            open.setNumber(open.getNumber()+1);
            updateOpen(open);
        }else {
            PipelineOpen pipelineOpen = new PipelineOpen();
            pipelineOpen.setPipeline(pipeline);
            pipelineOpen.setUserId(userId);
            pipelineOpen.setNumber(1);
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
    private PipelineOpen findOneOpenNumber(String userId , String pipelineId){
        if ( findAllOpen()==null){
            return null;
        }
        for (PipelineOpen pipelineOpen : findAllOpen()) {
            if (pipelineOpen.getPipeline().getPipelineId().equals(pipelineId) && pipelineOpen.getUserId().equals(userId)){
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
    private List<PipelineOpen> findAllOpen(String userId, StringBuilder s){
        List<PipelineOpenEntity> allOpen = pipelineOpenDao.findAllOpen(userId,s);
        List<PipelineOpen> list = BeanMapper.mapList(allOpen, PipelineOpen.class);
        joinTemplate.joinQuery(list);
        for (PipelineOpen pipelineOpen : list) {
            pipelineOpen.setPipelineName(pipelineOpen.getPipeline().getPipelineName());
            pipelineOpen.setPipelineId(pipelineOpen.getPipeline().getPipelineId());
        }
        return list;
    }


}

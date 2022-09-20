package net.tiklab.matflow.orther.service;


import net.tiklab.beans.BeanMapper;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.definition.model.MatFlow;
import net.tiklab.matflow.orther.dao.MatFlowOpenDao;
import net.tiklab.matflow.orther.entity.MatFlowOpenEntity;
import net.tiklab.matflow.orther.model.MatFlowOpen;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class MatFlowOpenServiceImpl implements MatFlowOpenService {


    @Autowired
    MatFlowOpenDao matFlowOpenDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Override
    public String createOpen(MatFlowOpen matFlowOpen) {
        return matFlowOpenDao.createOpen(BeanMapper.map(matFlowOpen, MatFlowOpenEntity.class));
    }

    @Override
    public void deleteOpen(String openId) {
        matFlowOpenDao.deleteOpen(openId);
    }

    @Override
    public void deleteAllOpen(String matFlowId){
        List<MatFlowOpen> allOpen = findAllOpen();
        if (allOpen == null){
           return;
        }
        for (MatFlowOpen matFlowOpen : allOpen) {
            joinTemplate.joinQuery(matFlowOpen);
            if (!matFlowOpen.getMatFlow().getMatflowId().equals(matFlowId)){
               continue;
            }
            deleteOpen(matFlowOpen.getId());
        }
    }

    public MatFlowOpen findOneOpenNumber(String userId , String matFlowId){
       if ( findAllOpen()==null){
           return null;
       }
        for (MatFlowOpen matFlowOpen : findAllOpen()) {
            if (matFlowOpen.getMatFlow().getMatflowId().equals(matFlowId) && matFlowOpen.getUserId().equals(userId)){
                return matFlowOpen;
            }
        }
       return null;
    }

    @Override
    public void updateOpen(MatFlowOpen matFlowOpen) {
        matFlowOpenDao.updateOpen(BeanMapper.map(matFlowOpen, MatFlowOpenEntity.class));
    }

    @Override
    public void findOpen(String userId, MatFlow matFlow) {
        MatFlowOpen open = findOneOpenNumber(userId, matFlow.getMatflowId());
        if (open != null){
            open.setNumber(open.getNumber()+1);
            updateOpen(open);
        }else {
            MatFlowOpen matFlowOpen = new MatFlowOpen();
            matFlowOpen.setMatFlow(matFlow);
            matFlowOpen.setUserId(userId);
            matFlowOpen.setNumber(1);
            createOpen(matFlowOpen);
        }
    }

    @Override
    public List<MatFlowOpen> findAllOpen(String userId, StringBuilder s){
        List<MatFlowOpenEntity> allOpen = matFlowOpenDao.findAllOpen(userId,s);
        List<MatFlowOpen> list = BeanMapper.mapList(allOpen, MatFlowOpen.class);
        joinTemplate.joinQuery(list);
        for (MatFlowOpen matFlowOpen : list) {
            matFlowOpen.setMatFlowName(matFlowOpen.getMatFlow().getMatflowName());
            matFlowOpen.setMatFlowId(matFlowOpen.getMatFlow().getMatflowId());
        }
        return list;
    }

    @Override
    public MatFlowOpen findOneOpen(String openId) {
        MatFlowOpen matFlowOpen = BeanMapper.map(matFlowOpenDao.findOneOpen(openId), MatFlowOpen.class);
        joinTemplate.joinQuery(matFlowOpen);
        return matFlowOpen;
    }

    @Override
    public List<MatFlowOpen> findAllOpen() {
        List<MatFlowOpen> list = BeanMapper.mapList(matFlowOpenDao.findAllOpen(), MatFlowOpen.class);
        joinTemplate.joinQuery(list);
        return list;
    }

    @Override
    public List<MatFlowOpen> findAllOpenList(List<String> idList) {
        List<MatFlowOpenEntity> openList = matFlowOpenDao.findAllOpenList(idList);
        return BeanMapper.mapList(openList, MatFlowOpen.class);
    }

}

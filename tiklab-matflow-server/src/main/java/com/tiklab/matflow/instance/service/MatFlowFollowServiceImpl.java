package com.tiklab.matflow.instance.service;


import com.tiklab.beans.BeanMapper;
import com.tiklab.matflow.definition.entity.MatFlowEntity;
import com.tiklab.matflow.definition.model.MatFlow;
import com.tiklab.matflow.instance.dao.MatFlowFollowDao;
import com.tiklab.matflow.instance.entity.MatFlowFollowEntity;
import com.tiklab.matflow.instance.model.MatFlowFollow;
import com.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;

/**
 * 收藏信息实现
 */

@Service
@Exporter
public class MatFlowFollowServiceImpl implements MatFlowFollowService {

    @Autowired
    MatFlowFollowDao matFlowFollowDao;

    @Override
    public String updateFollow(MatFlowFollow matFlowFollow) {
        String matFlowId = matFlowFollow.getMatFlow().getMatflowId();
        String userId = matFlowFollow.getUserId();
        List<MatFlowFollow> list = matFlowFollowDao.updateFollow(userId, matFlowId);
        if (list.size() == 0){
            String follow = matFlowFollowDao.createFollow(BeanMapper.map(matFlowFollow, MatFlowFollowEntity.class));
            if (follow == null){
                return null;
            }
        }else {
            deleteFollow(list.get(0).getId());
        }
        return "1";
    }

    @Override
    public void deleteFollow(String followId) {
        matFlowFollowDao.deleteFollow(followId);
    }

    @Override
    public  List<MatFlow> findAllFollow(String userId, StringBuilder s){
        List<MatFlowEntity> matFlowFollow = matFlowFollowDao.findMatFlowFollow(userId,s);
        List<MatFlow> list = BeanMapper.mapList(matFlowFollow, MatFlow.class);
        list.forEach(matFlow -> matFlow.setMatflowCollect(1));
        return list;
    }

    @Override
    public List<MatFlow> findUserMatFlow(String userId, StringBuilder s){

        List<MatFlowEntity> matFlowFollow = matFlowFollowDao.findMatFlowFollow(userId,s);
        List<MatFlowEntity> matFlowNotFollow = matFlowFollowDao.findMatFlowNotFollow(userId,s);

        if (matFlowFollow != null){
            matFlowFollow.forEach(matFlow -> matFlow.setMatflowCollect(1));
            matFlowNotFollow.addAll(matFlowFollow);
        }
        matFlowNotFollow.sort(Comparator.comparing(MatFlowEntity::getMatflowName));

        return BeanMapper.mapList(matFlowNotFollow, MatFlow.class);
        //List<MatFlow> matFlowList = new ArrayList<>();

        //if (matFlowFollow != null){
        //    matFlowFollow.forEach(matFlow -> matFlow.setMatflowCollect(1));
        //    matFlowList.addAll(matFlowFollow);
        //}
        //
        //if (matFlowNotFollow != null){
        //    matFlowList.addAll(matFlowNotFollow);
        //}

        //return matFlows;
    }

    @Override
    public MatFlowFollow findOneFollow(String followId) {
        return BeanMapper.map(matFlowFollowDao.findOneFollow(followId), MatFlowFollow.class);
    }

    @Override
    public List<MatFlowFollow> findAllFollow() {
        return BeanMapper.mapList(matFlowFollowDao.findAllFollow(), MatFlowFollow.class);
    }

    @Override
    public List<MatFlowFollow> findAllFollowList(List<String> idList) {
        List<MatFlowFollowEntity> followList = matFlowFollowDao.findAllFollowList(idList);
        return BeanMapper.mapList(followList, MatFlowFollow.class);
    }
}

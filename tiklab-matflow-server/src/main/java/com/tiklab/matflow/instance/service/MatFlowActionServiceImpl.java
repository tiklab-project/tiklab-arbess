package com.tiklab.matflow.instance.service;

import com.tiklab.beans.BeanMapper;
import com.tiklab.join.JoinTemplate;
import com.tiklab.matflow.definition.model.MatFlow;
import com.tiklab.matflow.instance.dao.MatFlowActionDao;
import com.tiklab.matflow.instance.entity.MatFlowActionEntity;
import com.tiklab.matflow.instance.model.MatFlowAction;
import com.tiklab.matflow.instance.model.MatFlowActionQuery;
import com.tiklab.rpc.annotation.Exporter;
import com.tiklab.user.user.model.User;
import com.tiklab.user.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * 动态
 */

@Exporter
@Service
public class MatFlowActionServiceImpl implements MatFlowActionService {

    @Autowired
    MatFlowActionDao matFlowActionDao;

    @Autowired
    UserService userService;

    @Autowired
    JoinTemplate joinTemplate;

    private static final Logger logger = LoggerFactory.getLogger(MatFlowActionServiceImpl.class);


    //添加动态
    @Override
    public  void createActive(MatFlowAction matFlowAction){
        matFlowActionDao.createAction(BeanMapper.map(matFlowAction, MatFlowActionEntity.class));
    }

    //对外创建动态
    @Override
    public void createActive(String userId, MatFlow matFlow, String massage){
        MatFlowAction matFlowAction = new MatFlowAction();
        User user = userService.findOne(userId);
        matFlowAction.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        matFlowAction.setUser(user);
        if (matFlow != null){
            matFlowAction.setMatFlow(matFlow);
        }
        String[] split = massage.split("/");
        matFlowAction.setMassage(""+split[0]);
        if (split.length == 2){
            matFlowAction.setNews(""+split[1]);
        }
        createActive(matFlowAction);
    }

    //删除
    @Override
    public  void deleteAction(String activeId){
        matFlowActionDao.deleteAction(activeId);
    }

    @Override
    public void deleteMatFlowAction(String matFlowId){
        matFlowActionDao.deleteAllAction(matFlowId);
    }

    @Override
    public  List<MatFlowAction> findAllActive(){
        List<MatFlowAction> list = BeanMapper.mapList(matFlowActionDao.findAllAction(), MatFlowAction.class);
        joinTemplate.joinQuery(list);
        return list;
    }

    @Override
    public List<MatFlowAction> findUserAction(MatFlowActionQuery matFlowActionQuery){
        ArrayList<MatFlowAction> list = new ArrayList<>();
        String sql = "";
        for (MatFlow matFlow : matFlowActionQuery.getMatFlowList()) {
            if (matFlowActionQuery.getPage()+ matFlowActionQuery.getPageSize() == 11){
                sql = sql.concat(" where matflow_action.matflow_id = '"+ matFlow.getMatflowId()+"' order by create_time desc limit 0,10");
            } else {
                sql = sql.concat(" where matflow_action.matflow_id = '"+ matFlow.getMatflowId()+"' order by create_time desc");
            }
            list.addAll(BeanMapper.mapList(matFlowActionDao.findUserAction(sql), MatFlowAction.class));
            sql = "";
        }
        joinTemplate.joinQuery(list);
        list.sort(Comparator.comparing(MatFlowAction::getCreateTime,Comparator.reverseOrder()));
        return list;
    }

}

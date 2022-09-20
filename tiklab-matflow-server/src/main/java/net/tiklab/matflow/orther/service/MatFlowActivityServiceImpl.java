package net.tiklab.matflow.orther.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.definition.model.MatFlow;
import net.tiklab.matflow.orther.dao.MatFlowActivityDao;
import net.tiklab.matflow.orther.entity.MatFlowActivityEntity;
import net.tiklab.matflow.orther.model.MatFlowActivity;
import net.tiklab.matflow.orther.model.MatFlowActivityQuery;
import net.tiklab.rpc.annotation.Exporter;
import net.tiklab.user.user.model.User;
import net.tiklab.user.user.service.UserService;
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
public class MatFlowActivityServiceImpl implements MatFlowActivityService {

    @Autowired
    MatFlowActivityDao matFlowActivityDao;

    @Autowired
    UserService userService;

    @Autowired
    JoinTemplate joinTemplate;

    private static final Logger logger = LoggerFactory.getLogger(MatFlowActivityServiceImpl.class);


    //添加动态
    @Override
    public  void createActive(MatFlowActivity matFlowActivity){
        matFlowActivityDao.createActivity(BeanMapper.map(matFlowActivity, MatFlowActivityEntity.class));
    }

    //对外创建动态
    @Override
    public void createActive(String userId, MatFlow matFlow, String massage){
        MatFlowActivity matFlowActivity = new MatFlowActivity();
        User user = userService.findOne(userId);
        matFlowActivity.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        matFlowActivity.setUser(user);
        if (matFlow != null){
            matFlowActivity.setMatFlow(matFlow);
        }
        String[] split = massage.split("/");
        matFlowActivity.setMassage(""+split[0]);
        if (split.length == 2){
            matFlowActivity.setNews(""+split[1]);
        }
        createActive(matFlowActivity);
    }

    //删除
    @Override
    public  void deleteActivity(String activeId){
        matFlowActivityDao.deleteActivity(activeId);
    }

    @Override
    public void deleteMatFlowActivity(String matFlowId){
        matFlowActivityDao.deleteAllActivity(matFlowId);
    }

    @Override
    public  List<MatFlowActivity> findAllActive(){
        List<MatFlowActivity> list = BeanMapper.mapList(matFlowActivityDao.findAllActivity(), MatFlowActivity.class);
        joinTemplate.joinQuery(list);
        return list;
    }

    @Override
    public List<MatFlowActivity> findUserActivity(MatFlowActivityQuery matFlowActivityQuery){
        ArrayList<MatFlowActivity> list = new ArrayList<>();
        String sql = "";
        for (MatFlow matFlow : matFlowActivityQuery.getMatFlowList()) {
            if (matFlowActivityQuery.getPage()+ matFlowActivityQuery.getPageSize() == 11){
                sql = sql.concat(" where matflow_activity.matflow_id = '"+ matFlow.getMatflowId()+"' order by create_time desc limit 0,10");
            } else {
                sql = sql.concat(" where matflow_activity.matflow_id = '"+ matFlow.getMatflowId()+"' order by create_time desc");
            }
            list.addAll(BeanMapper.mapList(matFlowActivityDao.findUserActivity(sql), MatFlowActivity.class));
            sql = "";
        }
        joinTemplate.joinQuery(list);
        list.sort(Comparator.comparing(MatFlowActivity::getCreateTime,Comparator.reverseOrder()));
        return list;
    }

}

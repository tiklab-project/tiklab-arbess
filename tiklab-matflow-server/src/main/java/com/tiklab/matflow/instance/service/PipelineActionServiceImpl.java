package com.tiklab.matflow.instance.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.join.JoinTemplate;
import com.tiklab.matflow.definition.model.Pipeline;
import com.tiklab.matflow.instance.dao.PipelineActionDao;
import com.tiklab.matflow.instance.entity.PipelineActionEntity;
import com.tiklab.matflow.instance.model.PipelineAction;
import com.tiklab.matflow.instance.model.PipelineActionQuery;
import com.doublekit.rpc.annotation.Exporter;
import com.doublekit.user.user.model.User;
import com.doublekit.user.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Exporter
@Service
public class PipelineActionServiceImpl implements PipelineActionService {

    @Autowired
    PipelineActionDao pipelineActionDao;

    @Autowired
    UserService userService;

    @Autowired
    JoinTemplate joinTemplate;

    private static final Logger logger = LoggerFactory.getLogger(PipelineActionServiceImpl.class);


    //添加动态
    @Override
    public  void createActive(PipelineAction pipelineAction){
        pipelineActionDao.createAction(BeanMapper.map(pipelineAction, PipelineActionEntity.class));
    }

    //对外创建动态
    @Override
    public void createActive(String userId, Pipeline pipeline,String massage){
        PipelineAction pipelineAction = new PipelineAction();
        User user = userService.findOne(userId);
        pipelineAction.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        pipelineAction.setUser(user);
        if (pipeline != null){
            pipelineAction.setPipeline(pipeline);
        }
        String[] split = massage.split("/");
        pipelineAction.setMassage(""+split[0]);
        if (split.length == 2){
            pipelineAction.setNews(""+split[1]);
        }
        createActive(pipelineAction);
    }

    //删除
    @Override
    public  void deleteAction(String activeId){
        pipelineActionDao.deleteAction(activeId);
    }

    @Override
    public void deletePipelineAction(String pipelineId){
        pipelineActionDao.deleteAllAction(pipelineId);
    }

    @Override
    public  List<PipelineAction> findAllActive(){
        List<PipelineAction> list = BeanMapper.mapList(pipelineActionDao.findAllAction(), PipelineAction.class);
        joinTemplate.joinQuery(list);
        return list;
    }

    @Override
    public List<PipelineAction> findUserAction(PipelineActionQuery pipelineActionQuery){
        ArrayList<PipelineAction> list = new ArrayList<>();
        String sql = "";
        for (Pipeline pipeline : pipelineActionQuery.getPipelineList()) {
            if (pipelineActionQuery.getPage()+pipelineActionQuery.getPageSize() == 11){
                sql = sql.concat(" where pipeline_action.pipeline_id = '"+ pipeline.getPipelineId()+"' order by create_time desc limit 0,10");
            } else {
                sql = sql.concat(" where pipeline_action.pipeline_id = '"+ pipeline.getPipelineId()+"' order by create_time desc");
            }
            list.addAll(BeanMapper.mapList(pipelineActionDao.findUserAction(sql), PipelineAction.class));
            sql = "";
        }
        joinTemplate.joinQuery(list);
        list.sort(Comparator.comparing(PipelineAction::getCreateTime,Comparator.reverseOrder()));
        return list;
    }

}

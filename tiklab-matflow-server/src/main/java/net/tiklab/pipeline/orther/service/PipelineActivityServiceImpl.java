package net.tiklab.pipeline.orther.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.join.JoinTemplate;
import net.tiklab.pipeline.definition.model.Pipeline;
import net.tiklab.pipeline.orther.dao.PipelineActivityDao;
import net.tiklab.pipeline.orther.entity.PipelineActivityEntity;
import net.tiklab.pipeline.orther.model.PipelineActivity;
import net.tiklab.pipeline.orther.model.PipelineActivityQuery;
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
public class PipelineActivityServiceImpl implements PipelineActivityService {

    @Autowired
    PipelineActivityDao pipelineActivityDao;

    @Autowired
    UserService userService;

    @Autowired
    JoinTemplate joinTemplate;

    private static final Logger logger = LoggerFactory.getLogger(PipelineActivityServiceImpl.class);


    //添加动态
    @Override
    public  void createActive(PipelineActivity pipelineActivity){
        pipelineActivityDao.createActivity(BeanMapper.map(pipelineActivity, PipelineActivityEntity.class));
    }

    //对外创建动态
    @Override
    public void createActive(String userId, Pipeline pipeline, String massage){
        PipelineActivity pipelineActivity = new PipelineActivity();
        User user = userService.findOne(userId);
        pipelineActivity.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        pipelineActivity.setUser(user);
        if (pipeline != null){
            pipelineActivity.setPipeline(pipeline);
        }
        String[] split = massage.split("/");
        pipelineActivity.setMassage(""+split[0]);
        if (split.length == 2){
            pipelineActivity.setNews(""+split[1]);
        }
        createActive(pipelineActivity);
    }

    //删除
    @Override
    public  void deleteActivity(String activeId){
        pipelineActivityDao.deleteActivity(activeId);
    }

    @Override
    public void deletePipelineActivity(String pipelineId){
        pipelineActivityDao.deleteAllActivity(pipelineId);
    }

    @Override
    public  List<PipelineActivity> findAllActive(){
        List<PipelineActivity> list = BeanMapper.mapList(pipelineActivityDao.findAllActivity(), PipelineActivity.class);
        joinTemplate.joinQuery(list);
        return list;
    }

    @Override
    public List<PipelineActivity> findUserActivity(PipelineActivityQuery pipelineActivityQuery){
        ArrayList<PipelineActivity> list = new ArrayList<>();
        String sql = "";
        for (Pipeline pipeline : pipelineActivityQuery.getPipelineList()) {
            if (pipelineActivityQuery.getPage()+ pipelineActivityQuery.getPageSize() == 11){
                sql = sql.concat(" where pipeline_activity.pipeline_id = '"+ pipeline.getPipelineId()+"' order by create_time desc limit 0,10");
            } else {
                sql = sql.concat(" where pipeline_activity.pipeline_id = '"+ pipeline.getPipelineId()+"' order by create_time desc");
            }
            list.addAll(BeanMapper.mapList(pipelineActivityDao.findUserActivity(sql), PipelineActivity.class));
            sql = "";
        }
        joinTemplate.joinQuery(list);
        list.sort(Comparator.comparing(PipelineActivity::getCreateTime,Comparator.reverseOrder()));
        return list;
    }

}

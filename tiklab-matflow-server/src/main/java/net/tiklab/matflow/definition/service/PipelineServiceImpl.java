package net.tiklab.matflow.definition.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.core.exception.ApplicationException;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.definition.dao.PipelineDao;
import net.tiklab.matflow.definition.entity.PipelineEntity;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.definition.model.PipelineMassage;
import net.tiklab.matflow.execute.model.PipelineExecHistory;
import net.tiklab.matflow.execute.model.PipelineExecState;
import net.tiklab.matflow.orther.service.PipelineActivityService;
import net.tiklab.rpc.annotation.Exporter;
import net.tiklab.user.user.model.DmUser;
import net.tiklab.user.user.model.User;
import net.tiklab.user.user.service.DmUserService;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * PipelineServiceImpl
 */

@Service
@Exporter
public class PipelineServiceImpl implements PipelineService {

    @Autowired
    PipelineDao pipelineDao;

    @Autowired
    PipelineConfigOrderService pipelineConfigOrderService;

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    PipelineCommonServer pipelineCommonServer;

    @Autowired
    PipelineActivityService pipelineActivityService;

    private static final Logger logger = LoggerFactory.getLogger(PipelineServiceImpl.class);

    //创建
    @Override
    public String createPipeline(Pipeline pipeline) {
        PipelineEntity pipelineEntity = BeanMapper.map(pipeline, PipelineEntity.class);
        String pipelineId = pipelineDao.createPipeline(pipelineEntity);
        //创建模板配置
        pipelineConfigOrderService.createTemplate(pipelineId, pipeline.getPipelineType());

        //动态
        HashMap<String, String> map = new HashMap<>();
        map.put("message", "");
        map.put("pipelineId", pipelineId);
        map.put("pipelineName", pipeline.getPipelineName());
        pipelineActivityService.log("create", "pipeline", map);
        DmUser dmUser = new DmUser();
        dmUser.setDomainId(pipelineId);
        User user = new User();
        user.setId(pipeline.getUser().getId());
        dmUser.setUser(user);
        pipelineCommonServer.updateDmUser(pipelineId,dmUser,true);
        return pipelineId;
    }

    //删除
    @Override
    public Integer deletePipeline(String pipelineId) {
        Pipeline pipeline = findOnePipeline(pipelineId);
        pipelineCommonServer.updateDmUser(pipelineId,new DmUser(),false);
        joinTemplate.joinQuery(pipeline);
        pipelineDao.deletePipeline(pipelineId);
        pipelineCommonServer.delete(pipeline);
        pipelineConfigOrderService.deleteConfig(pipelineId);
        //动态
        HashMap<String, String> map = new HashMap<>();
        map.put("message", "");
        map.put("pipelineId", pipelineId);
        map.put("pipelineName", pipeline.getPipelineName());
        pipelineActivityService.log("delete", "pipeline", map);
        return 1;
    }

    //更新
    @Override
    public int updatePipeline(Pipeline pipeline) {
        //更新名称
        Pipeline flow = findOnePipeline(pipeline.getPipelineId());
        if (!pipeline.getPipelineName().equals( flow.getPipelineName())){
            pipelineCommonServer.updatePipeline(pipeline.getPipelineName(), flow.getPipelineName());
            HashMap<String, String> map = new HashMap<>();
            map.put("message","名称为"+pipeline.getPipelineName());
            map.put("pipelineId", pipeline.getPipelineId());
            map.put("pipelineName", pipeline.getPipelineName());
            pipelineActivityService.log("update", "pipeline", map);
        }
        PipelineEntity pipelineEntity = BeanMapper.map(pipeline, PipelineEntity.class);
        pipelineDao.updatePipeline(pipelineEntity);
        return 1;
    }

    //查询
    @Override
    public Pipeline findOnePipeline(String pipelineId) {
        PipelineEntity pipelineEntity = pipelineDao.findPipeline(pipelineId);
        Pipeline pipeline = BeanMapper.map(pipelineEntity, Pipeline.class);
        joinTemplate.joinQuery(pipeline);
        return pipeline;
    }

    //查询所有
    @Override
    public List<Pipeline> findAllPipeline() {
        List<Pipeline> list = BeanMapper.mapList(pipelineDao.findAllPipeline(), Pipeline.class);
        joinTemplate.joinQuery(list);
        return list;
    }

    @Override
    public List<Pipeline> findAllPipelineList(List<String> idList) {
        List<PipelineEntity> pipelineEntityList = pipelineDao.findAllPipelineList(idList);
        return BeanMapper.mapList(pipelineEntityList, Pipeline.class);
    }

    /**
     * 获取用户名下的流水线id
     * @param userId 用户id
     * @return 所有流水线id
     */
    @Override
    public StringBuilder findUserPipelineId(String userId){
        List<PipelineEntity> allPipeline = pipelineDao.findUserPipeline();
        List<Pipeline> pipelineList = BeanMapper.mapList(allPipeline, Pipeline.class);
       return pipelineCommonServer.findUserPipelineId(userId, pipelineList);
    }

    /**
     * 获取用户所有流水线
     * @param userId 用户id
     * @return 流水线
     */
    @Override
    public List<Pipeline> findAllPipeline(String userId){
        StringBuilder userPipelineId = findUserPipelineId(userId);
        List<PipelineEntity> userPipeline = pipelineDao.findUserPipeline(userPipelineId);
        if (userPipeline != null){
            return BeanMapper.mapList(userPipeline, Pipeline.class);
        }
        return null;
    }

    //所有的流水线状态
    @Override
    public List<PipelineMassage> findUserPipeline(String userId){
        StringBuilder builder = findUserPipelineId(userId);
        if (builder == null){
            return null;
        }
        List<PipelineEntity> pipelineFollowEntity = pipelineDao.findPipelineFollow(userId,builder);
        List<Pipeline> pipelineFollow = BeanMapper.mapList(pipelineFollowEntity, Pipeline.class);

        List<Pipeline> pipelineNotFollow = new ArrayList<>();
        if (pipelineFollow != null){
            List<PipelineEntity> pipelineNotFollowEntity = pipelineDao.findPipelineNotFollow(userId,builder);
            pipelineNotFollow = BeanMapper.mapList(pipelineNotFollowEntity, Pipeline.class);
            pipelineFollow.forEach(pipeline -> pipeline.setPipelineCollect(1));
            pipelineNotFollow.addAll(pipelineFollow);
        }
        //排序
        pipelineNotFollow.sort(Comparator.comparing(Pipeline::getPipelineName));

        return pipelineCommonServer.findAllStatus(pipelineNotFollow);
    }

    //获取用户收藏的流水线
    @Override
    public List<PipelineMassage> findUserFollowPipeline(String userId) {
        StringBuilder builder = findUserPipelineId(userId);
        if (builder == null){
            return null;
        }
        List<PipelineEntity> pipelineFollowEntity = pipelineDao.findPipelineFollow(userId,builder);
        List<Pipeline> pipelineFollow = BeanMapper.mapList(pipelineFollowEntity, Pipeline.class);
        if (pipelineFollow == null){
          return null;
        }
        pipelineFollow.forEach(pipeline -> pipeline.setPipelineCollect(1));
        //排序
        pipelineFollow.sort(Comparator.comparing(Pipeline::getPipelineName));

        return pipelineCommonServer.findAllStatus(pipelineFollow);
    }

    //查询流水线最近运行状态
    @Override
    public List<PipelineExecState> findBuildStatus(String userId){
        StringBuilder builder = findUserPipelineId(userId);
        if (builder == null){
            return null;
        }
        List<PipelineExecState> list = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        int i = -6;
        List<PipelineExecHistory> allUserHistory = pipelineCommonServer.findRecentStatus(userId,builder);
        if (allUserHistory == null){
            return null;
        }
        while (i <= 0){
            Date lastTime = DateUtils.addDays(new Date(), i);
            PipelineExecState pipelineExecState = new PipelineExecState();
            pipelineExecState.setTime(formatter.format(lastTime));
            for (PipelineExecHistory pipelineExecHistory : allUserHistory) {
                try {
                    Date time = formatter.parse(pipelineExecHistory.getCreateTime());
                    if (!formatter.format(lastTime).equals(formatter.format(time))){
                        continue;
                    }
                    switch (pipelineExecHistory.getRunStatus()) {
                        case 30 -> pipelineExecState.setSuccessNumber(pipelineExecState.getSuccessNumber() + 1);
                        case 20 -> pipelineExecState.setRemoveNumber(pipelineExecState.getRemoveNumber() + 1);
                        case 1 -> pipelineExecState.setErrorNumber(pipelineExecState.getErrorNumber() + 1);
                    }
                } catch (ParseException e) {
                    logger.info("获取历史创建时间时日期转换异常。");
                    throw new RuntimeException(e);
                }
            }

            if (pipelineExecState.getTime() != null){
                pipelineExecState.setTime(pipelineExecState.getTime().substring(5));
                list.add(pipelineExecState);
            }
            i++;
        }
        return list;
    }

    //模糊查询
    @Override
    public List<PipelineMassage> findLikePipeline(String pipelineName, String userId) {
        List<PipelineEntity> pipelineEntityList = pipelineDao.findLikeName(pipelineName);
        List<Pipeline> list = BeanMapper.mapList(pipelineEntityList, Pipeline.class);

        StringBuilder s = findUserPipelineId(userId);

        if (s == null){
            return null;
        }
        List<PipelineEntity> allPipeline = pipelineDao.findAllPipeline(s);
        if (list == null || allPipeline ==null){
            return null;
        }
        List<Pipeline> userPipeline = BeanMapper.mapList(allPipeline, Pipeline.class);
        ArrayList<Pipeline> pipelines = new ArrayList<>();
        for (Pipeline pipeline : userPipeline) {
            List<Pipeline> collect = list.stream().filter(pipeline1 -> pipeline.getPipelineId().equals(pipeline1.getPipelineId())).toList();
            pipelines.addAll(collect);
        }
        if (pipelines.size() == 0){
            return null;
        }
        return pipelineCommonServer.findAllStatus(pipelines);
    }



}

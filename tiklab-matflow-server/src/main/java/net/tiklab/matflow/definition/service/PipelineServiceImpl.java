package net.tiklab.matflow.definition.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.core.exception.ApplicationException;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.definition.dao.PipelineDao;
import net.tiklab.matflow.definition.entity.PipelineEntity;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.definition.model.PipelineMassage;
import net.tiklab.matflow.execute.model.PipelineExecState;
import net.tiklab.matflow.orther.model.PipelineOpen;
import net.tiklab.matflow.orther.service.PipelineHomeService;
import net.tiklab.matflow.orther.service.PipelineOpenService;
import net.tiklab.matflow.orther.service.PipelineUntil;
import net.tiklab.rpc.annotation.Exporter;
import net.tiklab.utils.context.LoginContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
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
    PipelineConfigOrderService configOrderService;

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    PipelineCommonServer commonServer;

    @Autowired
    PipelineHomeService homeService;

    @Autowired
    PipelineOpenService openService;


    private static final Logger logger = LoggerFactory.getLogger(PipelineServiceImpl.class);

    //创建
    @Override
    public String createPipeline(Pipeline pipeline) {
        //随机颜色
        Random random ;
        try {
            random = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            throw new ApplicationException(e);
        }
        pipeline.setColor((random.nextInt(5) + 1));

        PipelineEntity pipelineEntity = BeanMapper.map(pipeline, PipelineEntity.class);
        pipelineEntity.setPipelineState(2);
        String pipelineId = pipelineDao.createPipeline(pipelineEntity);
        //判断是否使用流水线模板配置
        if (pipeline.getPipelineType() != 1){
            configOrderService.createTemplate(pipelineId, pipeline.getPipelineType());
        }

        //流水线关联角色，用户信息
        // commonServer.createDmUser(pipelineId);

        Map<String, String> map = PipelineUntil.initMap(pipeline);
        // 动态
        homeService.log("create", "pipelineCreate", map);
        // 消息
        homeService.message("pipelineCreate", map);

        return pipelineId;
    }

    //删除
    @Override
    public Integer deletePipeline(String pipelineId) {
        if (pipelineId == null){
            throw new ApplicationException(50001, "pipelineId为空。");
        }
        Pipeline pipeline = findOnePipeline(pipelineId);
        joinTemplate.joinQuery(pipeline);

        //删除关联信息
        pipelineDao.deletePipeline(pipelineId);//流水线
        commonServer.deleteDmUser(pipelineId);//关联用户
        commonServer.deleteHistory(pipeline);//历史，日志信息
        commonServer.deleteDmRole(pipelineId);//关联角色
        configOrderService.deleteConfig(pipelineId);//配置信息

        //动态，消息
        Map<String, String> map =  PipelineUntil.initMap(pipeline);
        homeService.log("delete", "pipelineDelete", map);
        //消息
        homeService.message("pipelineDelete", map);
        return 1;
    }

    //更新
    @Override
    public int updatePipeline(Pipeline pipeline) {
        //更新名称
        Pipeline flow = findOnePipeline(pipeline.getPipelineId());
        if (pipeline.getPipelineName() != null && !pipeline.getPipelineName().equals(flow.getPipelineName())){
            commonServer.updatePipeline(pipeline.getPipelineName(), flow.getPipelineName());
            HashMap<String, String> map = new HashMap<>();
            map.put("message","名称为"+pipeline.getPipelineName());
            map.put("pipelineId", pipeline.getPipelineId());
            map.put("pipelineName", flow.getPipelineName());
            homeService.log("update", "pipelineUpdate", map);
        }
        //更新权限信息
        updatePipeline(pipeline,flow);

        PipelineEntity pipelineEntity = BeanMapper.map(pipeline, PipelineEntity.class);
        pipelineDao.updatePipeline(pipelineEntity);
        return 1;
    }

    private void updatePipeline(Pipeline pipeline,Pipeline flow){
        if (pipeline.getPipelineState() == 0){
            pipeline.setPipelineState(flow.getPipelineState());
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("pipelineId", pipeline.getPipelineId());
        map.put("pipelineName", flow.getPipelineName());

        String pipelineId = pipeline.getPipelineId();
        int pipelinePower = pipeline.getPipelinePower();


        if (pipelinePower == 2 && flow.getPipelinePower() != 2) {
            commonServer.createDmUser(pipelineId);
            map.put("message","权限为私有");
            homeService.log("update", "pipelineUpdate", map);
        }
        if (pipelinePower == 1 && flow.getPipelinePower() != 1 ) {
            commonServer.deleteDmUser(pipelineId);
            map.put("message","权限为全局");
            homeService.log("update", "pipelineUpdate", map);
        }

        if (pipelinePower == 0){
            pipeline.setPipelinePower(flow.getPipelinePower());
        }

        if (pipeline.getPipelineType() == 0){
            pipeline.setPipelineType(flow.getPipelineType());
        }

        if (pipeline.getColor() == 0){
            pipeline.setColor(flow.getColor());
        }

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
       return commonServer.findUserPipelineId(userId, pipelineList);
    }

    /**
     * 获取用户所有流水线
     * @param userId 用户id
     * @return 流水线
     */
    public List<Pipeline> findAllPipeline(String userId){
        StringBuilder userPipelineId = findUserPipelineId(userId);
        List<PipelineEntity> userPipeline = pipelineDao.findUserPipeline(userPipelineId);
        if (userPipeline != null){
            return BeanMapper.mapList(userPipeline, Pipeline.class);
        }
        return Collections.emptyList();
    }

    //所有的流水线状态
    @Override
    public List<PipelineMassage> findUserPipeline(String userId){
        StringBuilder builder = findUserPipelineId(userId);
        if (builder.toString().equals("")){
            return Collections.emptyList();
        }
        List<PipelineEntity> pipelineNotFollowEntity = pipelineDao.findPipelineNotFollow(userId,builder);
        List<Pipeline> pipelineNotFollow = BeanMapper.mapList(pipelineNotFollowEntity, Pipeline.class);
        List<PipelineMassage> allStatus = commonServer.findAllStatus(pipelineNotFollow);
        joinTemplate.joinQuery(allStatus);
        List<PipelineMassage> userFollowPipeline = findUserFollowPipeline(userId);
        allStatus.addAll(userFollowPipeline);
        //排序
        allStatus.sort(Comparator.comparing(PipelineMassage::getPipelineName));

        return allStatus;
    }

    //获取用户收藏的流水线
    @Override
    public List<PipelineMassage> findUserFollowPipeline(String userId) {
        StringBuilder builder = findUserPipelineId(userId);
        if (builder.toString().equals("")){
            return Collections.emptyList();
        }
        List<PipelineEntity> pipelineFollowEntity = pipelineDao.findPipelineFollow(userId,builder);
        List<Pipeline> pipelineFollow = BeanMapper.mapList(pipelineFollowEntity, Pipeline.class);
        if (pipelineFollow == null){
          return Collections.emptyList();
        }
        pipelineFollow.forEach(pipeline -> pipeline.setPipelineCollect(1));
        //排序
        pipelineFollow.sort(Comparator.comparing(Pipeline::getPipelineName));
        List<PipelineMassage> allStatus = commonServer.findAllStatus(pipelineFollow);
        joinTemplate.joinQuery(allStatus);
        return allStatus;
    }
    
    //模糊查询
    @Override
    public List<PipelineMassage> findLikePipeline(String pipelineName, String userId) {
        List<PipelineEntity> pipelineEntityList = pipelineDao.findLikeName(pipelineName);
        List<Pipeline> list = BeanMapper.mapList(pipelineEntityList, Pipeline.class);

        StringBuilder s = findUserPipelineId(userId);

        if (s == null){
            return Collections.emptyList();
        }
        List<PipelineEntity> allPipeline = pipelineDao.findAllPipeline(s);
        if (list == null || allPipeline ==null){
            return Collections.emptyList();
        }
        List<Pipeline> userPipeline = BeanMapper.mapList(allPipeline, Pipeline.class);
        ArrayList<Pipeline> pipelines = new ArrayList<>();
        for (Pipeline pipeline : userPipeline) {
            List<Pipeline> collect = list.stream().filter(pipeline1 -> pipeline.getPipelineId().equals(pipeline1.getPipelineId())).toList();
            pipelines.addAll(collect);
        }
        if (pipelines.isEmpty()){
            return Collections.emptyList();
        }
        return commonServer.findAllStatus(pipelines);
    }

    /**
     * 查询最近打开的流水线
     * @return 流水线
     */
    @Override
    public List<PipelineOpen> findAllOpen() {
        StringBuilder s = findUserPipelineId(LoginContext.getLoginId());
        List<PipelineOpen> allOpen = openService.findAllOpen(s);
        if (allOpen == null){
            return Collections.emptyList();
        }
        allOpen.sort(Comparator.comparing(PipelineOpen::getNumber).reversed());
        int min = Math.min(5, allOpen.size());
        return allOpen.subList(0,min);
    }


    /**
     * 流水线执行信息统计
     * @param pipelineId 流水线id
     * @return 统计信息
     */
    @Override
     public PipelineExecState pipelineCensus(String pipelineId) {
        Pipeline pipeline = findOnePipeline(pipelineId);
        String loginId = LoginContext.getLoginId();
        openService.findOpen(loginId,pipeline);
        return commonServer.pipelineCensus(pipelineId);
    }



}





























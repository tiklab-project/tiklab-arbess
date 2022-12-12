package net.tiklab.matflow.definition.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.core.exception.ApplicationException;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.definition.dao.PipelineDao;
import net.tiklab.matflow.definition.entity.PipelineEntity;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.definition.model.PipelineExecMessage;
import net.tiklab.matflow.orther.model.PipelineOpen;
import net.tiklab.matflow.orther.service.PipelineHomeService;
import net.tiklab.matflow.orther.until.PipelineUntil;
import net.tiklab.rpc.annotation.Exporter;
import net.tiklab.utils.context.LoginContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

import static net.tiklab.matflow.orther.until.PipelineFinal.*;

/**
 * PipelineServiceImpl
 */

@Service
@Exporter
public class PipelineServiceImpl implements PipelineService {

    @Autowired
    private JoinTemplate joinTemplate;

    @Autowired
    private PipelineDao pipelineDao;

    @Autowired
    private PipelineRelationServer relationServer;

    @Autowired
    private PipelineConfigServer configServer;

    @Autowired
    private PipelineHomeService homeService;

    private static final Logger logger = LoggerFactory.getLogger(PipelineServiceImpl.class);

    /**
     * 创建流水线及关联信息
     * @param pipeline 流水线信息
     * @return 流水线id
     */
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
        pipeline.setCreateTime(PipelineUntil.date(1));
        PipelineEntity pipelineEntity = BeanMapper.map(pipeline, PipelineEntity.class);
        pipelineEntity.setState(1);
        String pipelineId = pipelineDao.createPipeline(pipelineEntity);
        joinTemplate.joinQuery(pipeline);
        pipeline.setId(pipelineId);
        Map<String, String> map = homeService.initMap(pipeline);

        //动态
        homeService.log(LOG_PIPELINE, LOG_MD_PIPELINE_CREATE, LOG_TEM_PIPELINE_CREATE, map);

        //创建对应流水线模板
        configServer.createTemplate(pipeline);

        //流水线关联角色，用户信息
        relationServer.createDmUser(pipelineId,pipeline.getUserList());

        //消息
        map.put("title","流水线创建消息");
        homeService.settingMessage("ed558817d1327167f4b26076c890cde8", map);

        return pipelineId;
    }

    /**
     * 删除流水线以及关联关系
     * @param pipelineId 流水线id
     */
    @Override
    public void deletePipeline(String pipelineId) {
        Pipeline pipeline = findOnePipeline(pipelineId);
        joinTemplate.joinQuery(pipeline);
        //删除关联信息
        pipelineDao.deletePipeline(pipelineId); //流水线
        relationServer.deleteDmUser(pipelineId); //关联用户
        relationServer.deleteHistory(pipeline); //历史，日志信息
        relationServer.deleteDmRole(pipelineId); //关联角色
        configServer.deleteAllTaskConfig(pipelineId,pipeline.getType()); //配置信息

        //动态
        Map<String, String> map = homeService.initMap(pipeline);
        homeService.log(LOG_PIPELINE, LOG_MD_PIPELINE_DELETE,LOG_TEM_PIPELINE_DELETE, map);
        //消息
        homeService.message(MES_TEM_PIPELINE_DELETE, MES_PIPELINE, map);

    }

    /**
     * 更新流水线及关联信息
     * @param pipeline 更新后流水线信息
     */
    @Override
    public void updatePipeline(Pipeline pipeline) {
        //更新名称
        Pipeline flow = findOnePipeline(pipeline.getId());
        joinTemplate.joinQuery(flow);
        //判断名称是否改变
        if (!pipeline.getName().equals(flow.getName())){

            //更改文件夹名称
            relationServer.updatePipeline(pipeline.getName(), flow.getName());
            flow.setName(pipeline.getName());

            Map<String, String> map = homeService.initMap(flow);
            map.put("message","名称为:"+ flow.getName());
            homeService.log(LOG_PIPELINE, LOG_MD_PIPELINE_UPDATE,LOG_TEM_PIPELINE_UPDATE, map);
        }

        int pipelinePower = pipeline.getPower();
        if (pipelinePower != flow.getPower() && pipelinePower != 0){
            flow.setPower(pipelinePower);
            Map<String, String> map = homeService.initMap(flow);
            if (pipelinePower == 1){
                map.put("message","权限为全局");
                homeService.log(LOG_PIPELINE,LOG_MD_PIPELINE_UPDATE, LOG_TEM_PIPELINE_UPDATE, map);
            }
            if (pipelinePower == 2){
                map.put("message","权限为私有");
                homeService.log(LOG_PIPELINE, LOG_MD_PIPELINE_UPDATE,LOG_TEM_PIPELINE_UPDATE, map);
            }
        }

        if (pipeline.getState() !=0 ){
            flow.setState(pipeline.getState());
        }

        PipelineEntity pipelineEntity = BeanMapper.map(flow, PipelineEntity.class);
        pipelineDao.updatePipeline(pipelineEntity);

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
        return relationServer.findUserPipelineId(userId, pipelineList);
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
    public List<PipelineExecMessage> findUserPipeline(String userId){
        StringBuilder builder = findUserPipelineId(userId);
        if (builder.toString().equals("")){
            return Collections.emptyList();
        }
        List<PipelineEntity> pipelineNotFollowEntity = pipelineDao.findPipelineNotFollow(userId,builder);
        List<Pipeline> pipelineNotFollow = BeanMapper.mapList(pipelineNotFollowEntity, Pipeline.class);
        List<PipelineExecMessage> allStatus = relationServer.findAllStatus(pipelineNotFollow);
        joinTemplate.joinQuery(allStatus);
        List<PipelineExecMessage> userFollowPipeline = findUserFollowPipeline(userId);
        allStatus.addAll(userFollowPipeline);
        //排序
        allStatus.sort(Comparator.comparing(PipelineExecMessage::getName));

        return allStatus;
    }

    //获取用户收藏的流水线
    @Override
    public List<PipelineExecMessage> findUserFollowPipeline(String userId) {
        StringBuilder builder = findUserPipelineId(userId);
        if (builder.toString().equals("")){
            return Collections.emptyList();
        }
        List<PipelineEntity> pipelineFollowEntity = pipelineDao.findPipelineFollow(userId,builder);
        List<Pipeline> pipelineFollow = BeanMapper.mapList(pipelineFollowEntity, Pipeline.class);
        if (pipelineFollow == null){
          return Collections.emptyList();
        }
        pipelineFollow.forEach(pipeline -> pipeline.setCollect(1));
        //排序
        pipelineFollow.sort(Comparator.comparing(Pipeline::getName));
        List<PipelineExecMessage> allStatus = relationServer.findAllStatus(pipelineFollow);
        joinTemplate.joinQuery(allStatus);
        return allStatus;
    }
    
    //模糊查询
    @Override
    public List<PipelineExecMessage> findLikePipeline(String pipelineName, String userId) {
        List<PipelineEntity> pipelineEntityList = pipelineDao.findLikeName(pipelineName);
        List<Pipeline> list = BeanMapper.mapList(pipelineEntityList, Pipeline.class);

        StringBuilder s = findUserPipelineId(userId);

        if (s == null){
            return Collections.emptyList();
        }
        List<PipelineEntity> allPipeline = pipelineDao.findAllPipeline(s);
        if (list == null || allPipeline == null){
            return Collections.emptyList();
        }
        List<Pipeline> userPipeline = BeanMapper.mapList(allPipeline, Pipeline.class);
        ArrayList<Pipeline> pipelines = new ArrayList<>();
        for (Pipeline pipeline : userPipeline) {
            List<Pipeline> collect = list.stream().
                    filter(pipeline1 -> pipeline.getId().equals(pipeline1.getId()))
                    .toList();
            pipelines.addAll(collect);
        }
        if (pipelines.isEmpty()){
            return Collections.emptyList();
        }
        return relationServer.findAllStatus(pipelines);
    }

    /**
     * 查询最近打开的流水线
     * @return 流水线
     */
    @Override
    public List<PipelineOpen> findAllOpen(int number) {
        StringBuilder s = findUserPipelineId(LoginContext.getLoginId()) ;
        if (!PipelineUntil.isNoNull(s.toString())){
            return null;
        }
        return relationServer.findAllOpen(s,number);
    }

}





























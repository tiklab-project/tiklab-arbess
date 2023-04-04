package io.tiklab.matflow.pipeline.definition.service;

import io.tiklab.beans.BeanMapper;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.core.page.Pagination;
import io.tiklab.eam.common.context.LoginContext;
import io.tiklab.join.JoinTemplate;
import io.tiklab.matflow.home.service.PipelineHomeService;
import io.tiklab.matflow.pipeline.definition.dao.PipelineDao;
import io.tiklab.matflow.pipeline.definition.entity.PipelineEntity;
import io.tiklab.matflow.pipeline.definition.model.Pipeline;
import io.tiklab.matflow.pipeline.definition.model.PipelineExecMessage;
import io.tiklab.matflow.pipeline.definition.model.PipelineFollow;
import io.tiklab.matflow.pipeline.definition.model.PipelineQuery;
import io.tiklab.matflow.pipeline.instance.model.PipelineInstance;
import io.tiklab.matflow.pipeline.instance.service.PipelineInstanceService;
import io.tiklab.matflow.stages.service.StageService;
import io.tiklab.matflow.support.authority.service.PipelineAuthorityService;
import io.tiklab.matflow.support.util.PipelineFinal;
import io.tiklab.matflow.support.util.PipelineUtil;
import io.tiklab.matflow.task.task.service.TasksService;
import io.tiklab.rpc.annotation.Exporter;
import io.tiklab.user.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

/**
 * 流水线服务
 */

@Service
@Exporter
public class PipelineServiceImpl implements PipelineService {

    @Autowired
    private JoinTemplate joinTemplate;

    @Autowired
    private PipelineDao pipelineDao;

    @Autowired
    private PipelineInstanceService instanceService;

    @Autowired
    private PipelineHomeService homeService;

    @Autowired
    private PipelineAuthorityService authorityService;

    @Autowired
    private TasksService tasksService;

    @Autowired
    private StageService stageService;

    @Autowired
    private PipelineFollowService followService;


    private static final Logger logger = LoggerFactory.getLogger(PipelineServiceImpl.class);

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
        pipeline.setCreateTime(PipelineUtil.date(1));
        String loginId = LoginContext.getLoginId();
        pipeline.setUser(new User(loginId));
        //创建流水线
        PipelineEntity pipelineEntity = BeanMapper.map(pipeline, PipelineEntity.class);
        pipelineEntity.setState(1);
        String pipelineId = pipelineDao.createPipeline(pipelineEntity);
        joinTemplate.joinQuery(pipeline);
        pipeline.setId(pipelineId);

        //创建对应流水线模板
        String template = pipeline.getTemplate();
        int[] ints;
        switch (template) {
            case "12131" -> ints = new int[]{1, 21, 31};
            case "1112131" -> ints = new int[]{1, 11, 21, 31};
            case "12231" -> ints = new int[]{1, 22, 31};
            default -> ints = new int[]{1};
        }
        if (pipeline.getType() == 1){
            tasksService.createTaskTemplate(pipelineId,ints);
        }
        if (pipeline.getType() == 2){
            stageService.createStageTemplate(pipelineId,ints);
        }

        //流水线关联角色，用户信息
        authorityService.createDmUser(pipelineId,pipeline.getUserList());

        //动态与消息
        HashMap<String,Object> map = homeService.initMap(pipeline);
        map.put("title","流水线创建消息");
        map.put("message","创建了");
        homeService.log(PipelineFinal.LOG_PIPELINE, PipelineFinal.LOG_TEM_CREATE, map);
        homeService.settingMessage(PipelineFinal.MES_CREATE, map);

        return pipelineId;
    }

    @Override
    public void deletePipeline(String pipelineId) {
        Pipeline pipeline = findPipelineById(pipelineId);
        joinTemplate.joinQuery(pipeline);
        //删除关联信息
        pipelineDao.deletePipeline(pipelineId); //删除流水线
        authorityService.deleteDmUser(pipelineId); //删除关联用户
        deleteHistory(pipelineId); //删除历史，日志信息
        //删除配置信息
        if (pipeline.getType() == 1){
            tasksService.deleteAllTasksOrTask(pipelineId,1);
        }
        if (pipeline.getType() == 2){
            stageService.deleteAllStagesOrTask(pipelineId);
        }
        //动态与消息
        HashMap<String,Object> map = homeService.initMap(pipeline);
        map.put("title","流水线删除消息");
        map.put("message","删除了");
        homeService.log(PipelineFinal.LOG_PIPELINE, PipelineFinal.LOG_TEM_DELETE, map);
        homeService.settingMessage(PipelineFinal.MES_DELETE, map);

    }

    @Override
    public void updatePipeline(Pipeline pipeline) {
        //更新名称
        Pipeline flow = findPipelineById(pipeline.getId());
        joinTemplate.joinQuery(flow);
        //判断名称是否改变
        if (!pipeline.getName().equals(flow.getName())){
            HashMap<String,Object> map = homeService.initMap(flow);
            map.put("message", flow.getName()+"名称为:"+pipeline.getName());
            flow.setName(pipeline.getName());
            homeService.log(PipelineFinal.LOG_PIPELINE, PipelineFinal.LOG_TEM_UPDATE, map);
            homeService.settingMessage(PipelineFinal.MES_UPDATE, map);
        }

        //判断权限是否改变
        int pipelinePower = pipeline.getPower();
        if (pipelinePower != flow.getPower() && pipelinePower != 0){
            flow.setPower(pipelinePower);
            HashMap<String,Object> map = homeService.initMap(flow);
            if (pipelinePower == 1){
                map.put("message",pipeline.getName()+"权限为全局");
                homeService.log(PipelineFinal.LOG_PIPELINE, PipelineFinal.LOG_TEM_UPDATE, map);
                homeService.settingMessage(PipelineFinal.MES_UPDATE, map);
            }
            if (pipelinePower == 2){
                map.put("message",pipeline.getName()+"权限为私有");
                homeService.log(PipelineFinal.LOG_PIPELINE, PipelineFinal.LOG_TEM_UPDATE, map);
                homeService.settingMessage(PipelineFinal.MES_UPDATE, map);
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
    public Pipeline findPipelineById(String pipelineId) {
        PipelineEntity pipelineEntity = pipelineDao.findPipelineById(pipelineId);
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

    @Override
    public List<Pipeline> findUserPipeline(String userId){
        StringBuilder userPipelineId = authorityService.findUserPipelineIdString(userId);
        List<PipelineEntity> userPipeline = pipelineDao.findUserPipeline(userPipelineId);
        if (userPipeline != null){
            return BeanMapper.mapList(userPipeline, Pipeline.class);
        }
        return Collections.emptyList();
    }

    @Override
    public List<PipelineExecMessage> findUserPipelineExecMessage(){
        String loginId = LoginContext.getLoginId();
        StringBuilder builder = authorityService.findUserPipelineIdString(loginId);
        if (builder.toString().equals("")){
            return Collections.emptyList();
        }
        //未收藏的流水线
        List<PipelineEntity> pipelineNotFollowEntity = pipelineDao.findPipelineNotFollow(loginId,builder);
        List<Pipeline> pipelineNotFollow = BeanMapper.mapList(pipelineNotFollowEntity, Pipeline.class);
        List<PipelineExecMessage> allStatus = findAllExecMessage(pipelineNotFollow);
        joinTemplate.joinQuery(allStatus);
        //收藏的流水线
        List<PipelineExecMessage> userFollowPipeline = findUserFollowPipeline();
        allStatus.addAll(userFollowPipeline);
        //排序
        allStatus.sort(Comparator.comparing(PipelineExecMessage::getName));

        return allStatus;
    }

    public Pagination<PipelineExecMessage> findUserPipelinePage(PipelineQuery query){
        String loginId = LoginContext.getLoginId();
        StringBuilder builder = authorityService.findUserPipelineIdString(loginId);
        if (builder.toString().equals("")){
            return null;
        }

        List<PipelineFollow> followPipeline = followService.findUserFollowPipeline(loginId);
        int size = followPipeline.size();
        Pagination<PipelineEntity> userPipelineQuery;
        List<Pipeline> list = new ArrayList<>();
        if (Objects.equals(query.getPipelineFollow(),1)){
            if (Objects.equals(followPipeline.size(),0)){
                return null;
            }
            String[] strings = new String[size];
            for (int i = 0; i < size; i++) {
                strings[i] = followPipeline.get(i).getPipeline().getId();
            }
            userPipelineQuery = pipelineDao.findUserPipelineQuery(strings, query);
            List<PipelineEntity> pipelineEntityList = userPipelineQuery.getDataList();
            List<Pipeline> pipelineList = BeanMapper.mapList(pipelineEntityList, Pipeline.class);
            for (Pipeline pipeline : pipelineList) {
                pipeline.setCollect(1);
                list.add(pipeline);
            }
        } else {
            String[] split = builder.toString().replace("'", "").split(",");
            userPipelineQuery =  pipelineDao.findUserPipelineQuery(split, query);
            List<PipelineEntity> pipelineEntityList = userPipelineQuery.getDataList();
            List<Pipeline> pipelineList = BeanMapper.mapList(pipelineEntityList, Pipeline.class);
            if (!Objects.equals(size,0)){
                for (Pipeline pipeline : pipelineList) {
                    String id = pipeline.getId();
                    for (PipelineFollow pipelineFollow : followPipeline) {
                        String s = pipelineFollow.getPipeline().getId();
                        if (!Objects.equals(id,s)){
                            continue;
                        }
                        pipeline.setCollect(1);
                    }
                    list.add(pipeline);
                }
            }else {
                list.addAll(pipelineList);
            }
        }

        if (Objects.isNull(userPipelineQuery.getDataList())){
            return null;
        }

        List<PipelineExecMessage> allStatus = findAllExecMessage(list);
        Pagination<PipelineExecMessage> pagination  = new Pagination<>();
        pagination.setBeginIndex(userPipelineQuery.getBeginIndex());
        pagination.setEndIndex(userPipelineQuery.getEndIndex());
        pagination.setCurrentPage(userPipelineQuery.getCurrentPage());
        pagination.setPageSize(userPipelineQuery.getPageSize());
        pagination.setTotalPage(userPipelineQuery.getTotalPage());
        pagination.setTotalRecord(userPipelineQuery.getTotalRecord());
        pagination.setDataList(allStatus);
        return pagination;
    }

    @Override
    public List<PipelineExecMessage> findUserFollowPipeline() {
        String userId = LoginContext.getLoginId();
        StringBuilder builder = authorityService.findUserPipelineIdString(userId);
        if (builder.toString().equals("")){
            return Collections.emptyList();
        }
        List<PipelineEntity> pipelineFollowEntity = pipelineDao.findPipelineFollow(userId,builder);
        List<Pipeline> pipelineFollow = BeanMapper.mapList(pipelineFollowEntity, Pipeline.class);
        if (pipelineFollow == null){
          return Collections.emptyList();
        }
        //更改状态
        pipelineFollow.forEach(pipeline -> pipeline.setCollect(1));
        //排序
        pipelineFollow.sort(Comparator.comparing(Pipeline::getName));
        List<PipelineExecMessage> allStatus = findAllExecMessage(pipelineFollow);
        joinTemplate.joinQuery(allStatus);
        return allStatus;
    }
    
    @Override
    public List<PipelineExecMessage> findPipelineByName(String pipelineName) {
        List<PipelineEntity> pipelineEntityList = pipelineDao.findPipelineByName(pipelineName);
        List<Pipeline> list = BeanMapper.mapList(pipelineEntityList, Pipeline.class);
        String loginId = LoginContext.getLoginId();
        StringBuilder s = authorityService.findUserPipelineIdString(loginId);
        if (s == null){
            return Collections.emptyList();
        }
        List<PipelineEntity> allPipeline = pipelineDao.findUserPipeline(s);
        if (list == null || allPipeline == null){
            return Collections.emptyList();
        }
        List<Pipeline> userPipeline = BeanMapper.mapList(allPipeline, Pipeline.class);
        List<Pipeline> pipelines = new ArrayList<>();
        //遍历获取id相同的
        for (Pipeline pipeline : userPipeline) {
            List<Pipeline> collect = list.stream().
                    filter(pipeline1 -> pipeline.getId().equals(pipeline1.getId()))
                    .toList();
            pipelines.addAll(collect);
        }
        if (pipelines.isEmpty()){
            return Collections.emptyList();
        }
        return findAllExecMessage(pipelines);
    }

    @Override
    public List<User> findPipelineUser(String pipelineId) {
        return authorityService.findPipelineUser(pipelineId);
    }

    /**
     * 删除关联信息
     * @param pipelineId 流水线Id
     */
    public void deleteHistory( String pipelineId){
        //删除对应的历史
        instanceService.deleteAllInstance(pipelineId);
        //删除对应源码文件
        String fileAddress = PipelineUtil.findFileAddress(pipelineId,1);
        PipelineUtil.deleteFile(new File(fileAddress));
        //删除对应日志
        String logAddress = PipelineUtil.findFileAddress(pipelineId,2);
        PipelineUtil.deleteFile(new File(logAddress+"/"+pipelineId));
    }

    /**
     * 查询流水线最近构建信息
     * @param allPipeline 流水线
     * @return 构建信息
     */
    public List<PipelineExecMessage> findAllExecMessage(List<Pipeline> allPipeline){
        List<PipelineExecMessage> pipelineExecMessageList = new ArrayList<>();
        for (Pipeline pipeline : allPipeline) {
            joinTemplate.joinQuery(pipeline);
            PipelineExecMessage pipelineExecMessage = new PipelineExecMessage();
            pipelineExecMessage.setId(pipeline.getId());
            pipelineExecMessage.setCollect(pipeline.getCollect());
            pipelineExecMessage.setName(pipeline.getName());
            pipelineExecMessage.setState(pipeline.getState());
            pipelineExecMessage.setColor(pipeline.getColor());
            pipelineExecMessage.setUser(pipeline.getUser());
            pipelineExecMessage.setPower(pipeline.getPower());
            pipelineExecMessage.setType(pipeline.getType());
            //成功和构建时间
            PipelineInstance latelyHistory = instanceService.findLatelyInstance(pipeline.getId());
            if (!Objects.isNull(latelyHistory)){
                pipelineExecMessage.setLastBuildTime(latelyHistory.getCreateTime());
                pipelineExecMessage.setBuildStatus(latelyHistory.getRunStatus());
                pipelineExecMessage.setExecUser(latelyHistory.getUser());
            }
            pipelineExecMessageList.add(pipelineExecMessage);
        }
        return pipelineExecMessageList;
    }




}





























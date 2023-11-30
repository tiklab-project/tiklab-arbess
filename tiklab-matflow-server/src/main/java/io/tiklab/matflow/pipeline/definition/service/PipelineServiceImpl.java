package io.tiklab.matflow.pipeline.definition.service;

import io.tiklab.beans.BeanMapper;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.eam.common.context.LoginContext;
import io.tiklab.join.JoinTemplate;
import io.tiklab.matflow.home.service.PipelineHomeService;
import io.tiklab.matflow.pipeline.definition.dao.PipelineDao;
import io.tiklab.matflow.pipeline.definition.entity.PipelineEntity;
import io.tiklab.matflow.pipeline.definition.model.*;
import io.tiklab.matflow.pipeline.instance.model.PipelineInstance;
import io.tiklab.matflow.pipeline.instance.service.PipelineInstanceService;
import io.tiklab.matflow.stages.service.StageService;
import io.tiklab.matflow.support.authority.service.PipelineAuthorityService;
import io.tiklab.matflow.support.condition.service.ConditionService;
import io.tiklab.matflow.support.postprocess.service.PostprocessService;
import io.tiklab.matflow.support.trigger.service.TriggerService;
import io.tiklab.matflow.support.util.PipelineFileUtil;
import io.tiklab.matflow.support.util.PipelineFinal;
import io.tiklab.matflow.support.util.PipelineUtil;
import io.tiklab.matflow.support.util.PipelineUtilService;
import io.tiklab.matflow.support.variable.service.VariableService;
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

import static io.tiklab.matflow.support.util.PipelineFinal.*;

/**
 * 流水线服务
 */

@Service
@Exporter
public class PipelineServiceImpl implements PipelineService {

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    PipelineDao pipelineDao;

    @Autowired
    PipelineInstanceService instanceService;

    @Autowired
    PipelineHomeService homeService;

    @Autowired
    PipelineAuthorityService authorityService;

    @Autowired
    TasksService tasksService;

    @Autowired
    StageService stageService;

    @Autowired
    PipelineFollowService followService;

    @Autowired
    PipelineUtilService utilService;

    @Autowired
    PipelineOpenService openService;

    @Autowired
    PostprocessService postprocessService;

    @Autowired
    TriggerService triggerService;

    @Autowired
    VariableService variableService;

    @Autowired
    ConditionService conditionService;


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
        String[] ints;
        switch (template) {
            case "2131" -> ints = new String[]{TASK_CODE_GIT, TASK_BUILD_MAVEN, TASK_DEPLOY_LINUX};
            case "112131" -> ints = new String[]{TASK_CODE_GIT, TASK_TEST_MAVENTEST,  TASK_BUILD_MAVEN, TASK_DEPLOY_LINUX};
            case "2231" -> ints = new String[]{TASK_CODE_GIT,  TASK_TEST_MAVENTEST, TASK_DEPLOY_LINUX};
            default -> ints = new String[]{TASK_CODE_GIT};
        }
        if (pipeline.getType() == 1) {
            tasksService.createTaskTemplate(pipelineId , ints);
        }
        if (pipeline.getType() == 2) {
            stageService.createStageTemplate(pipelineId , ints);
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

        new Thread(() -> {
            authorityService.deleteDmUser(pipelineId); //删除关联用户
            logger.warn("删除流水线历史信息....");
            deleteOther(pipelineId); //删除历史,日志,收藏,最近打开

            //删除配置信息
            logger.warn("删除流水线关联配置信息....");
            if (pipeline.getType() == 1){
                tasksService.deleteAllTasksOrTask(pipelineId,1);
            }
            if (pipeline.getType() == 2){
                stageService.deleteAllStagesOrTask(pipelineId);
            }
        }).start();

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

        flow.setEnv(pipeline.getEnv());
        flow.setGroup(pipeline.getGroup());

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

    @Override
    public Pipeline findOnePipeline(String pipelineId){
        List<Pipeline> pipelineList = findUserPipeline();
        if (pipelineList.isEmpty()){
            return null;
        }
        for (Pipeline pipeline : pipelineList) {
            if (!pipeline.getId().equals(pipelineId)){
                continue;
            }
            return pipeline;
        }
        return null;
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
    public List<Pipeline> findUserPipeline(){
        String userId = LoginContext.getLoginId();
        String[] builder = authorityService.findUserPipelineIdString(userId);
        PipelineQuery pipelineQuery = new PipelineQuery();
        pipelineQuery.setIdString(builder);
        List<PipelineEntity> userPipeline = pipelineDao.findPipelineList(pipelineQuery);
        if (userPipeline != null){
            return BeanMapper.mapList(userPipeline, Pipeline.class);
        }
        return Collections.emptyList();
    }

    @Override
    public Pagination<Pipeline> findUserPipelinePage(PipelineQuery query){
        String loginId = LoginContext.getLoginId();

        String[] builders = authorityService.findUserPipelineIdString(loginId);
        query.setIdString(builders);
        // 用户收藏的流水线
        Integer follow = query.getPipelineFollow();
        if (!Objects.isNull(follow) && follow == 1){
            query.setUserId(loginId);
            Pagination<PipelineEntity> pipelineListQuery = pipelineDao.findPipelineListQuery(query);
            List<PipelineEntity> dataList = pipelineListQuery.getDataList();
            List<Pipeline> pipelineList = BeanMapper.mapList(dataList, Pipeline.class);
            List<Pipeline> list = new ArrayList<>();
            for (Pipeline pipeline : pipelineList) {
                Pipeline pipelineMessage = findPipelineExecMessage(pipeline, 1);
                list.add(pipelineMessage);
            }
            joinTemplate.joinQuery(list);
            return PaginationBuilder.build(pipelineListQuery,list);
        }

        // 查询用户收藏的流水线
        PipelineFollowQuery followQuery = new PipelineFollowQuery();
        followQuery.setUserId(loginId);
        List<PipelineFollow> followPipeline = followService.findFollowQueryList(followQuery);
        Map<String,String> map = new HashMap<>();
        for (PipelineFollow pipelineFollow : followPipeline) {
            Pipeline pipeline = pipelineFollow.getPipeline();
            if (Objects.isNull(pipeline)){
                continue;
            }
            map.put(pipeline.getId(),pipeline.getId());
        }
        Pagination<PipelineEntity> pipelinePage = pipelineDao.findPipelinePage(query);
        List<PipelineEntity> dataList = pipelinePage.getDataList();
        List<Pipeline> pipelineList = BeanMapper.mapList(dataList, Pipeline.class);
        List<Pipeline> list = new ArrayList<>();
        for (Pipeline pipeline : pipelineList) {
            // 判断是否收藏
            String s = map.get(pipeline.getId());
            int i = 0;
            if (!Objects.isNull(s)){
                i = 1;
            }
            list.add(findPipelineExecMessage(pipeline, i));
        }
        joinTemplate.joinQuery(list);
        return PaginationBuilder.build(pipelinePage,list);
    }

    @Override
    public List<Pipeline> findUserPipelineList(PipelineQuery query){
        List<PipelineEntity> pipelineEntityList = pipelineDao.findPipelineList(query);
        if (pipelineEntityList == null){
            return Collections.emptyList();
        }
        return BeanMapper.mapList(pipelineEntityList,Pipeline.class);
    }

    @Override
    public List<User> findPipelineUser(String pipelineId) {
        return authorityService.findPipelineUser(pipelineId);
    }

    @Override
    public List<PipelineRecently> findPipelineRecently(int number){
        String userId = LoginContext.getLoginId();

        List<PipelineInstance> instanceList = instanceService.findUserPipelineInstance(userId,number);
        if (instanceList.isEmpty()){
            return Collections.emptyList();
        }

        // 筛选出用户拥有的流水线的历史
        String[] userPipeline = authorityService.findUserPipelineIdString(userId);
        if (userPipeline.length == 0){
            return Collections.emptyList();
        }
        List<PipelineInstance> pipelineInstanceList = new ArrayList<>();
        List<String> pipelineIdList = Arrays.stream(userPipeline).toList();
        for (PipelineInstance instance : instanceList) {
            String id = instance.getPipeline().getId();
            boolean containsElement = pipelineIdList.contains(id);
            if (!containsElement){
                continue;
            }
            pipelineInstanceList.add(instance);
        }

        List<PipelineRecently> list = new ArrayList<>();
        for (PipelineInstance lastInstance : pipelineInstanceList) {
            joinTemplate.joinQuery(lastInstance);
            Pipeline pipeline = lastInstance.getPipeline();
            String pipelineId = pipeline.getId();
            PipelineRecently recently = new PipelineRecently();
            recently.setPipelineId(pipelineId);
            recently.setPipelineName(pipeline.getName());
            recently.setLastRunState(lastInstance.getRunStatus());
            recently.setNumber(lastInstance.getFindNumber());
            String createTime = lastInstance.getCreateTime();
            recently.setCreateTime(createTime);
            Date date = PipelineUtil.StringChengeDate(createTime);
            String dateTime = PipelineUtil.findDateTime(date, 3000);
            recently.setExecTime(dateTime);
            String formatted = PipelineUtil.formatDateTime(lastInstance.getRunTime());
            recently.setLastRunTime(formatted);
            recently.setColor(pipeline.getColor());
            recently.setInstanceId(lastInstance.getInstanceId());
            recently.setLastRunType(lastInstance.getRunWay());
            list.add(recently);
        }
        return list;
    }

    public String findPipelineCloneName(String pipelineId){
        Pipeline pipeline = findPipelineById(pipelineId);

        if (Objects.isNull(pipeline)){
            logger.error("没有查询到当前流水线信息,pipelineId:{}",pipelineId);
            throw new ApplicationException("没有查询到当前流水线信息！");
        }
         String name = pipeline.getName() + "_copy";

         // 匹配流水线名称
         int i = 1;
         PipelineQuery pipelineQuery = new PipelineQuery();
         pipelineQuery.setPipelineName(name);
         pipelineQuery.setEqName(true);
         List<Pipeline> userPipelineList = findUserPipelineList(pipelineQuery);
         while (!userPipelineList.isEmpty() && i < 31){
            name = pipeline.getName() + "_copy_"+ i ;
            pipelineQuery.setPipelineName(name);
            userPipelineList = findUserPipelineList(pipelineQuery);
            i ++ ;
        }
         return name;
    }

    @Override
    public void pipelineClone(String pipelineId,String pipelineName) {
        Pipeline pipeline = findPipelineById(pipelineId);

        if (Objects.isNull(pipeline)){
            logger.error("没有查询到当前流水线信息,pipelineId:{}",pipelineId);
            throw new ApplicationException("没有查询到当前流水线信息！");
        }

        pipeline.setName(pipelineName);

        // 克隆流水线
        PipelineEntity pipelineEntity = BeanMapper.map(pipeline, PipelineEntity.class);
        String clonePipelineId = pipelineDao.createPipeline(pipelineEntity);

        // 克隆流水线成员以及权限信息
        authorityService.cloneDomainRole(pipelineId,clonePipelineId);

        // 克隆任务
        int type = pipeline.getType();
        if (type == 1){
            // 多任务
            tasksService.cloneTasks(pipelineId,clonePipelineId,"pipelineId");
        }else {
            // 多阶段
            stageService.cloneStage(pipelineId, clonePipelineId);
        }

        // 克隆后置任务
        postprocessService.clonePostTask(pipelineId,clonePipelineId);

        // 克隆触发器
        triggerService.cloneTrigger(pipelineId,clonePipelineId);

        // 克隆流水线变量
        variableService.cloneVariable(pipelineId,clonePipelineId);

    }


    @Override
    public List<Pipeline> findRecentlyPipeline(Integer number,String pipelineId){

        List<String> userOpen = openService.findUserOpen(number + 1);
        if (userOpen.isEmpty()){
            List<PipelineEntity> pipelineEntityList =
                    pipelineDao.findRecentlyPipeline("'"+pipelineId+"'",number+1);
            List<Pipeline> pipelineList = BeanMapper.mapList(pipelineEntityList, Pipeline.class);
            List<Pipeline> pipelines = pipelineList.subList(0, number);
            Pipeline pipeline = findPipelineById(pipelineId);
            pipelines.add(0,pipeline);
            return pipelines;
        }

        // 过滤出当前流水线
        List<String> list = userOpen.stream().filter(s -> !s.equals(pipelineId)).toList();

        StringBuilder ids =  new StringBuilder();
        // 查询流水线
        List<Pipeline> pipelineList = new ArrayList<>();
        for (String id : list) {
            Pipeline pipeline = findPipelineById(id);
            pipelineList.add(pipeline);
            ids.append("'").append(id).append("',");
        }

        ids.append("'").append(pipelineId).append("'");
        // 判断是否足够当前数量
        if (pipelineList.size() < number){
            int size = number -pipelineList.size();
            List<PipelineEntity> allPipeline = pipelineDao.findRecentlyPipeline(String.valueOf(ids),number+1);
            if (allPipeline != null){
                List<Pipeline> pipelineList1 = BeanMapper.mapList(allPipeline, Pipeline.class);
                if (pipelineList1.size() <= size){
                    pipelineList.addAll(pipelineList.size(),pipelineList1);
                }else {
                    pipelineList.addAll(pipelineList.size(),pipelineList1.subList(0, size));
                }
            }
        }

        // 当前流水线放在最前
        Pipeline pipeline = findPipelineById(pipelineId);
        pipelineList.add(0,pipeline);

        return pipelineList;
    }

    /**
     * 删除关联信息
     * @param pipelineId 流水线Id
     */
    private void deleteOther(String pipelineId){

        //删除对应的历史
        instanceService.deleteAllInstance(pipelineId);

        //删除对应源码文件
        String fileAddress = utilService.findPipelineDefaultAddress(pipelineId,1);
        PipelineFileUtil.deleteFile(new File(fileAddress));

        //删除对应日志
        String logAddress = utilService.findPipelineDefaultAddress(pipelineId,2);
        PipelineFileUtil.deleteFile(new File(logAddress));

        // 删除最近打开
        openService.deleteAllOpen(pipelineId);

        // 删除收藏
        followService.deletePipelineFollow(pipelineId);
    }


    public Pipeline findPipelineExecMessage(Pipeline pipeline ,Integer integer){
        PipelineInstance latelyHistory = instanceService.findLatelyInstance(pipeline.getId());
        if (!Objects.isNull(latelyHistory)){
            String createTime = latelyHistory.getCreateTime();
            Date date = PipelineUtil.StringChengeDate(createTime);
            String dateTime = PipelineUtil.findDateTime(date, 1000);
            if (!Objects.isNull(dateTime)){
                pipeline.setLastBuildTime(dateTime);
                pipeline.setBuildStatus(latelyHistory.getRunStatus());
                pipeline.setNumber(latelyHistory.getFindNumber());
                pipeline.setInstanceId(latelyHistory.getInstanceId());
            }
        }
        pipeline.setCollect(integer);
        return pipeline;
    }




}





























package io.tiklab.arbess.pipeline.definition.service;

import io.tiklab.arbess.home.service.PipelineHomeService;
import io.tiklab.arbess.pipeline.definition.model.*;
import io.tiklab.arbess.stages.service.StageService;
import io.tiklab.arbess.support.authority.service.PipelineAuthorityService;
import io.tiklab.arbess.support.condition.service.ConditionService;
import io.tiklab.arbess.support.postprocess.service.PostprocessService;
import io.tiklab.arbess.support.trigger.service.TriggerService;
import io.tiklab.arbess.support.util.util.PipelineFileUtil;
import io.tiklab.arbess.support.util.util.PipelineFinal;
import io.tiklab.arbess.support.util.util.PipelineUtil;
import io.tiklab.arbess.agent.support.util.service.PipelineUtilService;
import io.tiklab.arbess.support.variable.service.VariableService;
import io.tiklab.arbess.task.task.service.TasksCloneService;
import io.tiklab.arbess.task.task.service.TasksService;
import io.tiklab.message.message.model.MessageNoticePatch;
import io.tiklab.privilege.dmRole.model.DmRolePatch;
import io.tiklab.privilege.dmRole.service.DmRoleService;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.eam.common.context.LoginContext;
import io.tiklab.toolkit.join.JoinTemplate;
import io.tiklab.arbess.pipeline.definition.dao.PipelineDao;
import io.tiklab.arbess.pipeline.definition.entity.PipelineEntity;
import io.tiklab.arbess.pipeline.instance.model.PipelineInstance;
import io.tiklab.arbess.pipeline.instance.service.PipelineInstanceService;
import io.tiklab.message.message.service.MessageDmNoticeService;
import io.tiklab.rpc.annotation.Exporter;
import io.tiklab.user.user.model.User;
import io.tiklab.user.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.*;
import java.util.stream.Stream;

import static io.tiklab.arbess.support.util.util.PipelineFinal.*;

/**
 * 流水线服务
 */

@Service
@Exporter
public class PipelineServiceImpl implements PipelineService {

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    UserService userService;

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
    TasksCloneService tasksCloneService;

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

    @Autowired
    MessageDmNoticeService messageDmNoticeService;

    @Autowired
    DmRoleService dmRoleService;


    private static final Logger logger = LoggerFactory.getLogger(PipelineServiceImpl.class);

    @Override
    @Transactional
    public String createPipeline(Pipeline pipeline) {

        // 随机颜色
        int randomNumber = (int)(Math.random() * 5) + 1;
        pipeline.setColor(randomNumber);

        pipeline.setCreateTime(PipelineUtil.date(1));
        if (Objects.isNull(pipeline.getUser()) || StringUtils.isEmpty(pipeline.getUser().getId())){
            String loginId = LoginContext.getLoginId();
            pipeline.setUser(new User(loginId));
        }

        //创建流水线
        PipelineEntity pipelineEntity = BeanMapper.map(pipeline, PipelineEntity.class);
        pipelineEntity.setState(1);

        String pipelineId = pipelineDao.createPipeline(pipelineEntity);
        joinTemplate.joinQuery(pipeline);
        pipeline.setId(pipelineId);

        //创建对应流水线模板
        String template = StringUtils.isEmpty(pipeline.getTemplate()) ? "00" : pipeline.getTemplate();
        String[] ints;
        switch (template) {
            case "2131" -> ints =
                    new String[]{TASK_CODE_GIT, TASK_BUILD_MAVEN, TASK_DEPLOY_LINUX};
            case "112131" -> ints =
                    new String[]{TASK_CODE_GIT, TASK_TEST_MAVENTEST,  TASK_BUILD_MAVEN, TASK_DEPLOY_LINUX};
            case "2231" -> ints =
                    new String[]{TASK_CODE_GIT,  TASK_TEST_MAVENTEST, TASK_DEPLOY_LINUX};
            default -> ints = new String[]{TASK_CODE_GIT};
        }
        if (pipeline.getType() == 1) {
            tasksService.createTaskTemplate(pipelineId , ints);
        }
        if (pipeline.getType() == 2) {
            stageService.createStageTemplate(pipelineId , ints);
        }

        String userId = pipeline.getUser().getId();
        //流水线关联角色，用户信息
        authorityService.createDmUser(pipelineId,userId,pipeline.getUserList());

        // 消息通知方案
        MessageNoticePatch messageNoticePatch = new MessageNoticePatch();
        messageNoticePatch.setDomainId(pipelineId);
        messageNoticePatch.setUserList(List.of(userId));
        messageDmNoticeService.initMessageDmNotice(messageNoticePatch);

        //动态与消息
        Map<String,Object> map = homeService.initMap(pipeline);
        map.put("link",PipelineFinal.CREATE_LINK);
        map.put("pipelineName",pipeline.getName());
        homeService.log(PipelineFinal.LOG_TYPE_CREATE, map);
        homeService.settingMessage(PipelineFinal.MES_CREATE, map);

        return pipelineId;
    }

    @Override
    public void deletePipeline(String pipelineId) {
        Pipeline pipeline = findPipelineById(pipelineId);
        // joinTemplate.joinQuery(pipeline);
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
        Map<String,Object> map = homeService.initMap(pipeline);
        homeService.log(PipelineFinal.LOG_TYPE_DELETE,  map);
        homeService.settingMessage(PipelineFinal.MES_DELETE, map);

    }

    @Override
    public void updatePipeline(Pipeline pipeline) {
        //更新名称
        Pipeline flow = findPipelineById(pipeline.getId());
        joinTemplate.joinQuery(flow);
        //判断名称是否改变
        if (!pipeline.getName().equals(flow.getName())){
            Map<String,Object> map = homeService.initMap(pipeline);
            map.put("link",PipelineFinal.UPDATE_LINK);
            map.put("message", flow.getName() +"更改为："+pipeline.getName());
            map.put("lastName", flow.getName());
            flow.setName(pipeline.getName());
            map.put("dmMessage",true);
            homeService.log(PipelineFinal.LOG_TYPE_UPDATE,  map);
            homeService.settingMessage(PipelineFinal.MES_UPDATE, map);
        }

        //判断权限是否改变
        int pipelinePower = pipeline.getPower();
        if (pipelinePower != flow.getPower() && pipelinePower != 0){
            flow.setPower(pipelinePower);
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
    public Pipeline findPipelineByIdNoQuery(String pipelineId) {
        PipelineEntity pipelineEntity = pipelineDao.findPipelineById(pipelineId);
        return BeanMapper.map(pipelineEntity, Pipeline.class);
    }

    @Override
    public Pipeline findOnePipeline(String pipelineId){
       return findPipelineById(pipelineId);
    }

    @Override
    public Pipeline findPipelineNoQuery(String pipelineId){
        PipelineEntity pipelineEntity = pipelineDao.findPipelineById(pipelineId);
        User user = userService.findOne(pipelineEntity.getUserId());
        Pipeline pipeline = BeanMapper.map(pipelineEntity, Pipeline.class);
        pipeline.setUser(user);
        return pipeline;
    }

    @Override
    public void updatePipelineRootUser(DmRolePatch dmRolePatch){
        dmRoleService.updateDomainRootUser(dmRolePatch);

        String userId = dmRolePatch.getUserId();
        Pipeline pipeline = findPipelineById(dmRolePatch.getDomainId());
        pipeline.setUser(new User(userId));
        PipelineEntity pipelineEntity = BeanMapper.map(pipeline, PipelineEntity.class);
        pipelineDao.updatePipeline(pipelineEntity);
    }

    //查询所有
    @Override
    public List<Pipeline> findAllPipeline() {
        List<Pipeline> list = BeanMapper.mapList(pipelineDao.findAllPipeline(), Pipeline.class);
        joinTemplate.joinQuery(list);
        return list;
    }


    @Override
    public List<Pipeline> findAllPipelineNoQuery() {
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
    public List<Pipeline> findUserPipeline(PipelineQuery query){
        String[] builder = authorityService.findUserPipelineIdString(query.getUserId());
        query.setIdString(builder);
        List<Pipeline> userPipeline = findPipelineList(query);
        if (Objects.isNull(userPipeline)){
            return Collections.emptyList();
        }
        return userPipeline;
    }

    @Override
    public Pagination<Pipeline> findUserPipelinePage(PipelineQuery query){
        String userId = query.getUserId();

        if (Objects.isNull(query.getIdString()) || query.getIdString().length==0){
            String[] builders = authorityService.findUserPipelineIdString(userId);
            query.setIdString(builders);
        }

        // 用户收藏的流水线
        Integer follow = query.getPipelineFollow();
        if (!Objects.isNull(follow) && follow == 1){
            Pagination<PipelineEntity> pipelineListQuery = pipelineDao.findPipelineListQuery(query);
            List<PipelineEntity> dataList = pipelineListQuery.getDataList();
            if (dataList.isEmpty()){
                return PaginationBuilder.build(pipelineListQuery, Collections.emptyList());
            }
            List<Pipeline> pipelineList = BeanMapper.mapList(dataList, Pipeline.class);
            List<Pipeline> list = new ArrayList<>();

            List<String> userIdList = new ArrayList<>();
            for (Pipeline pipeline : pipelineList) {
                userIdList.add(pipeline.getUser().getId());
                pipeline.setCollect(1);
                Pipeline pipelineMessage = findPipelineExecMessage(pipeline);
                list.add(pipelineMessage);
            }


            // 查询用户信息
            Map<String, User> pipelineUser = findPipelineUser(userIdList);

            List<Pipeline> pipelines = list.stream()
                    .peek(pipeline -> pipeline.setUser(pipelineUser.get(pipeline.getUser().getId())))
                    .peek(pipeline -> pipeline.setExec(homeService.findPermissions(pipeline.getId(),PIPELINE_RUN_KEY)))
                    .toList();

            return PaginationBuilder.build(pipelineListQuery,pipelines);
        }

        // 查询用户流水线
        PipelineFollowQuery followQuery = new PipelineFollowQuery();
        followQuery.setUserId(userId);

        // 是否收藏
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
        if (pipelineList.isEmpty()){
            return PaginationBuilder.build(pipelinePage, Collections.emptyList());
        }
        List<Pipeline> list = new ArrayList<>();

        // 查询用户
        List<String> userIdList = new ArrayList<>();
        for (Pipeline pipeline : pipelineList) {
            // 判断是否收藏
            String s = map.get(pipeline.getId());
            pipeline.setCollect(0);
            if (!Objects.isNull(s)){
                pipeline.setCollect(1);
            }
            userIdList.add(pipeline.getUser().getId());
            list.add(findPipelineExecMessage(pipeline));
        }
        Map<String, User> pipelineUser = findPipelineUser(userIdList);

        // 是否执行
        List<Pipeline> pipelines = list.stream()
                .peek(pipeline -> pipeline.setUser(pipelineUser.get(pipeline.getUser().getId())))
                .peek(pipeline -> pipeline.setExec(homeService.findPermissions(pipeline.getId(),PIPELINE_RUN_KEY)))
                .toList();

        return PaginationBuilder.build(pipelinePage,pipelines);
    }

    @Override
    public List<Pipeline> findPipelineList(PipelineQuery query){
        List<PipelineEntity> pipelineEntityList = pipelineDao.findPipelineList(query);
        if (Objects.isNull(pipelineEntityList)){
            return Collections.emptyList();
        }
        return BeanMapper.mapList(pipelineEntityList,Pipeline.class);
    }

    @Override
    public List<User> findPipelineUser(String pipelineId) {
        return authorityService.findPipelineUser(pipelineId);
    }

    @Override
    public List<PipelineRecently> findPipelineRecently(String userId, int number){

        // 筛选出用户拥有的流水线的历史
        String[] userPipeline = authorityService.findUserPipelineIdString(userId);
        if (userPipeline.length == 0){
            return Collections.emptyList();
        }

        List<PipelineInstance> instanceList = instanceService.findUserPipelineInstance(userId,number);
        if (instanceList.isEmpty()){
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
            String pipelineId = lastInstance.getPipeline().getId();
            Pipeline pipeline = findPipelineByIdNoQuery(pipelineId);

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
            recently.setColor(pipeline.getColor());
            recently.setInstanceId(lastInstance.getInstanceId());
            recently.setLastRunType(lastInstance.getRunWay());
            list.add(recently);
        }
        return list;
    }

    @Override
    public String findPipelineCloneName(String pipelineId){
        Pipeline pipeline = findPipelineById(pipelineId);

        if (Objects.isNull(pipeline)){
            throw new ApplicationException("没有查询到当前流水线信息！");
        }
         String name = pipeline.getName() + "_copy";

         // 匹配流水线名称
         int i = 1;
         PipelineQuery pipelineQuery = new PipelineQuery();
         pipelineQuery.setPipelineName(name);
         pipelineQuery.setEqName(true);
         List<Pipeline> userPipelineList = findPipelineList(pipelineQuery);
         while (!userPipelineList.isEmpty() && i < 10){
            name = pipeline.getName() + "_copy_"+ i ;
            pipelineQuery.setPipelineName(name);
            userPipelineList = findPipelineList(pipelineQuery);
            i ++ ;
        }
         return name;
    }

    @Override
    public void pipelineClone(String pipelineId,String pipelineName) {
        Pipeline pipeline = findPipelineById(pipelineId);

        if (Objects.isNull(pipeline)){
            throw new ApplicationException("克隆失败，没有查询到当前流水线信息！");
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
            tasksCloneService.clonePipelineTasks(pipelineId,clonePipelineId);
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

        int i = number + 1;
        List<String> userOpenList = openService.findUserOpen(i);

        String loginId = LoginContext.getLoginId();
        String[] builders = authorityService.findUserPipelineIdString(loginId);

        Pipeline pipeline = findPipelineNoQuery(pipelineId);

        // 过滤出当前流水线
        List<String> strings = Stream.of(builders).filter(a -> !a.equals(pipelineId)).toList();
        if (strings.isEmpty()){
            List<Pipeline> objects = new ArrayList<>();
            objects.add(pipeline);
            return objects;
        }

        // 最近没有打开流水线
        if (userOpenList.isEmpty()){
            List<PipelineEntity> pipelineEntityList = pipelineDao.findAllPipelineList(strings);
            if (pipelineEntityList.size() > number){
                pipelineEntityList.subList(0, number);
            }
            List<Pipeline> pipelineList = BeanMapper.mapList(pipelineEntityList, Pipeline.class);
            pipelineList.add(0,pipeline);
            return pipelineList;
        }

        // 过滤出当前流水线
        List<String> pieplineIdList = userOpenList.stream().filter(s -> !s.equals(pipelineId))
                .toList();

        // 获取最近打开以及拥有权限的流水线
        List<String> collect = strings.stream()
                .filter(pieplineIdList::contains).distinct().toList();

        List<String> idStrings = new ArrayList<>(collect);

        // 判断是否超出数量
        if (collect.size() >= number){
            idStrings = idStrings.subList(0, number);
        }else {
            List<String> collect1 = strings.stream().filter(element -> !pieplineIdList.contains(element)).toList();
            if (collect1.size() >= number - collect.size()){
                idStrings.addAll(collect.size()-1,collect1.subList(0,number - collect.size()));
            }else {
                idStrings.addAll(collect.size()-1,collect1);
            }
        }
        // if (strings.isEmpty()){
        //     List<Pipeline> objects = new ArrayList<>();
        //     objects.add(pipeline);
        //     return objects;
        // }

        List<Pipeline> pipelineList = findAllPipelineList(idStrings);
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

    /**
     * 添加流水线执行信息
     * @param pipeline 流水线
     * @return 流水线
     */
    public Pipeline findPipelineExecMessage(Pipeline pipeline){
        PipelineInstance latelyHistory = instanceService.findLatelyInstance(pipeline.getId());
        if (!Objects.isNull(latelyHistory)){

            pipeline.setBuildStatus(latelyHistory.getRunStatus());
            pipeline.setNumber(latelyHistory.getFindNumber());
            pipeline.setInstanceId(latelyHistory.getInstanceId());

            String createTime = latelyHistory.getCreateTime();
            Date date = PipelineUtil.StringChengeDate(createTime);
            String dateTime = PipelineUtil.findDateTime(date, 1000);
            if (!Objects.isNull(dateTime)){
                pipeline.setLastBuildTime(dateTime);
            }
        }
        return pipeline;
    }

    /**
     * 根据用户Id查询用户
     * @param userIdString 用户Id
     * @return 用户
     */
    public Map<String,User> findPipelineUser(List<String> userIdString){

        // 使用Stream API去除重复元素
        List<String> uniqueList = userIdString.stream()
                .distinct()
                .toList();

        Map<String,User> map = new HashMap<>();

        List<User> list = userService.findList(uniqueList);
        for (User user : list) {
            if (Objects.isNull(user)){
                continue;
            }
            map.put(user.getId(),user);
        }
        return map;
    }


}





























package net.tiklab.matflow.pipeline.definition.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.core.exception.ApplicationException;
import net.tiklab.core.page.Pagination;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.home.service.PipelineHomeService;
import net.tiklab.matflow.pipeline.definition.dao.PipelineDao;
import net.tiklab.matflow.pipeline.definition.entity.PipelineEntity;
import net.tiklab.matflow.pipeline.definition.model.Pipeline;
import net.tiklab.matflow.pipeline.definition.model.PipelineMessageList;
import net.tiklab.matflow.pipeline.instance.model.PipelineAllInstanceQuery;
import net.tiklab.matflow.pipeline.instance.model.PipelineInstance;
import net.tiklab.matflow.pipeline.instance.service.PipelineInstanceService;
import net.tiklab.matflow.support.util.PipelineFinal;
import net.tiklab.matflow.support.util.PipelineUtil;
import net.tiklab.privilege.role.model.PatchUser;
import net.tiklab.privilege.roleDmRole.service.DmRoleService;
import net.tiklab.rpc.annotation.Exporter;
import net.tiklab.user.dmUser.model.DmUser;
import net.tiklab.user.dmUser.service.DmUserService;
import net.tiklab.user.user.model.User;
import net.tiklab.utils.context.LoginContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

import static net.tiklab.matflow.support.util.PipelineFinal.*;

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
    private PipelineConfigService configServer;

    @Autowired
    private PipelineInstanceService instanceService;

    @Autowired
    private PipelineHomeService homeService;

    @Autowired
    private DmUserService dmUserService;

    @Autowired
    private DmRoleService dmRoleService;


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
        pipeline.setCreateTime(PipelineUtil.date(1));
        PipelineEntity pipelineEntity = BeanMapper.map(pipeline, PipelineEntity.class);
        pipelineEntity.setState(1);
        String pipelineId = pipelineDao.createPipeline(pipelineEntity);
        joinTemplate.joinQuery(pipeline);
        pipeline.setId(pipelineId);
        HashMap<String,Object> map = homeService.initMap(pipeline);

        //创建对应流水线模板
        configServer.createTemplate(pipeline);

        //流水线关联角色，用户信息
        createDmUser(pipelineId,pipeline.getUserList());

        //消息
        map.put("title","流水线创建消息");
        map.put("message","创建了");

        //动态
        homeService.log(LOG_PIPELINE, LOG_TEM_CREATE, map);

        homeService.settingMessage(MES_CREATE, map);

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
        deleteDmUser(pipelineId); //关联用户
        deleteHistory(pipeline); //历史，日志信息
        configServer.deleteAllTaskConfig(pipelineId,pipeline.getType()); //配置信息

        //动态
        HashMap<String,Object> map = homeService.initMap(pipeline);
        map.put("title","流水线删除消息");
        map.put("message","删除了");

        homeService.log(LOG_PIPELINE,LOG_TEM_DELETE, map);

        homeService.settingMessage(MES_DELETE, map);

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
            HashMap<String,Object> map = homeService.initMap(flow);
            map.put("message", flow.getName()+"名称为:"+pipeline.getName());

            flow.setName(pipeline.getName());

            homeService.log(LOG_PIPELINE,LOG_TEM_UPDATE, map);

            homeService.settingMessage(MES_UPDATE, map);
        }

        int pipelinePower = pipeline.getPower();
        if (pipelinePower != flow.getPower() && pipelinePower != 0){
            flow.setPower(pipelinePower);
            HashMap<String,Object> map = homeService.initMap(flow);
            if (pipelinePower == 1){
                map.put("message",pipeline.getName()+"权限为全局");
                homeService.log(LOG_PIPELINE,LOG_TEM_UPDATE, map);
                homeService.settingMessage(MES_UPDATE, map);
            }
            if (pipelinePower == 2){
                map.put("message",pipeline.getName()+"权限为私有");
                homeService.log(LOG_PIPELINE,LOG_TEM_UPDATE, map);
                homeService.settingMessage(MES_UPDATE, map);
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
        return findUserPipelineId(userId, pipelineList);
    }

    /**
     * 获取用户所有流水线
     * @param userId 用户id
     * @return 流水线
     */
    @Override
    public List<Pipeline> findUserPipeline(String userId){
        StringBuilder userPipelineId = findUserPipelineId(userId);
        List<PipelineEntity> userPipeline = pipelineDao.findUserPipeline(userPipelineId);
        if (userPipeline != null){
            return BeanMapper.mapList(userPipeline, Pipeline.class);
        }
        return Collections.emptyList();
    }

    //所有的流水线状态
    @Override
    public List<PipelineMessageList> findUserPipelineExecMessage(String userId){
        StringBuilder builder = findUserPipelineId(userId);
        if (builder.toString().equals("")){
            return Collections.emptyList();
        }
        List<PipelineEntity> pipelineNotFollowEntity = pipelineDao.findPipelineNotFollow(userId,builder);
        List<Pipeline> pipelineNotFollow = BeanMapper.mapList(pipelineNotFollowEntity, Pipeline.class);
        List<PipelineMessageList> allStatus = findAllExecMessage(pipelineNotFollow);
        joinTemplate.joinQuery(allStatus);
        List<PipelineMessageList> userFollowPipeline = findUserFollowPipeline(userId);
        allStatus.addAll(userFollowPipeline);
        //排序
        allStatus.sort(Comparator.comparing(PipelineMessageList::getName));

        return allStatus;
    }

    //获取用户收藏的流水线
    @Override
    public List<PipelineMessageList> findUserFollowPipeline(String userId) {
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
        List<PipelineMessageList> allStatus = findAllExecMessage(pipelineFollow);
        joinTemplate.joinQuery(allStatus);
        return allStatus;
    }
    
    //模糊查询
    @Override
    public List<PipelineMessageList> findLikePipeline(String pipelineName, String userId) {
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
        return findAllExecMessage(pipelines);
    }

    /**
     * 查询用户所有流水线历史
     * @return 历史
     */
    @Override
    public Pagination<PipelineInstance> findUserAllHistory(PipelineAllInstanceQuery pipelineHistoryQuery){
        String id = LoginContext.getLoginId();
        List<Pipeline> allPipeline = findUserPipeline(id);
        if (allPipeline.isEmpty()){
            return null;
        }
        if (!PipelineUtil.isNoNull(pipelineHistoryQuery.getPipelineId())){
            pipelineHistoryQuery.setPipelineList(allPipeline);
        }
        return instanceService.findUserAllHistory(pipelineHistoryQuery);
    }

    /**
     * 删除关联信息
     * @param pipeline 流水线
     */
    public void deleteHistory(Pipeline pipeline){
        String pipelineId = pipeline.getId();
        //删除对应的历史
        instanceService.deleteAllHistory(pipelineId);
        //删除最近打开
        // openService.deleteAllOpen(pipelineId);
        //删除对应文件
        String fileAddress = PipelineUtil.findFileAddress(pipelineId,1);
        PipelineUtil.deleteFile(new File(fileAddress));
        //删除对应日志
        String logAddress = PipelineUtil.findFileAddress(pipelineId,2);
        PipelineUtil.deleteFile(new File(logAddress+"/"+pipelineId));
    }


    /**
     * 获取拥有此流水线的用户
     * @param pipelineId 流水线id
     * @return 用户信息
     */
    public List<User> findPipelineUser(String pipelineId) {
        List<DmUser> allDmUser = dmUserService.findAllDmUser();
        List<User> userList = new ArrayList<>();
        if (allDmUser == null){
            return null;
        }
        for (DmUser dmUser : allDmUser) {
            if (dmUser.getDomainId().equals(pipelineId)){
                userList.add(dmUser.getUser());
            }
        }
        return userList;
    }

    /**
     * 创建流水线关联用户
     * @param pipelineId 流水线id
     */
    public void createDmUser(String pipelineId,List<PatchUser> userList){
        //拉入创建人
        DmUser dmUser = new DmUser();
        dmUser.setDomainId(pipelineId);
        User user = new User();
        if (userList == null){
            user.setId(LoginContext.getLoginId());
            dmUser.setUser(user);
            dmUser.setType(1);
            dmRoleService.initDmRoles(pipelineId,LoginContext.getLoginId(), PipelineFinal.appName);
            return;
        }

        //多个用户
        for (PatchUser patchUser : userList) {
            dmUser.setType(0);
            user.setId(patchUser.getId());
            dmUser.setUser(user);
            if (patchUser.getId().equals(LoginContext.getLoginId())){
                dmUser.setType(1);
            }
        }

        //关联权限
        dmRoleService.initPatchDmRole(pipelineId,userList, PipelineFinal.appName);

    }

    /**
     * 更新项目域权限
     * @param pipelineId 流水线id
     */
    public void deleteDmUser(String pipelineId){
        dmRoleService.deleteDmRoleByDomainId(pipelineId);
    }

    /**
     * 拼接数据库查询条件
     * @param userId 用户id
     * @return 所有流水线id
     */
    public StringBuilder findUserPipelineId(String userId,List<Pipeline> pipelineList){
        List<DmUser> allDmUser = dmUserService.findAllDmUser();
        //获取项目域条件
        StringBuilder s = new StringBuilder();
        if (allDmUser != null && !allDmUser.isEmpty()){
            for (DmUser dmUser : allDmUser) {
                User user = dmUser.getUser();
                if (user == null  || !user.getId().equals(userId)){
                    continue;
                }
                if (s.toString().equals("") ) {
                    s.append("'");
                } else {
                    s.append(",'");
                }
                s.append(dmUser.getDomainId()).append("'");
            }
        }
        //获取流水线id
        StringBuilder j = new StringBuilder();
        if (s.toString().equals("") && pipelineList == null){
            return null;
        }
        for (Pipeline pipeline : pipelineList) {
            if (j.toString().equals("") ) {
                j.append("'");
            } else {
                j.append(",'");
            }
            j.append(pipeline.getId()).append("'");
        }
        if (s.toString().equals("")){
            return j;
        }
        if (j.toString().equals("")){
            return s;
        }
        return s.append(",").append(j);
    }

    /**
     * 查询流水线最近构建信息
     * @param allPipeline 流水线
     * @return 构建信息
     */
    public List<PipelineMessageList> findAllExecMessage(List<Pipeline> allPipeline){
        List<PipelineMessageList> pipelineMessageListList = new ArrayList<>();
        for (Pipeline pipeline : allPipeline) {
            joinTemplate.joinQuery(pipeline);
            PipelineMessageList pipelineMessageList = new PipelineMessageList();
            //成功和构建时间
            PipelineInstance latelyHistory = instanceService.findLatelyHistory(pipeline.getId());
            pipelineMessageList.setId(pipeline.getId());
            pipelineMessageList.setCollect(pipeline.getCollect());
            pipelineMessageList.setName(pipeline.getName());
            pipelineMessageList.setState(pipeline.getState());
            pipelineMessageList.setColor(pipeline.getColor());
            pipelineMessageList.setUser(pipeline.getUser());
            pipelineMessageList.setPower(pipeline.getPower());
            pipelineMessageList.setType(pipeline.getType());
            if (latelyHistory != null){
                pipelineMessageList.setLastBuildTime(latelyHistory.getCreateTime());
                pipelineMessageList.setBuildStatus(latelyHistory.getRunStatus());
                pipelineMessageList.setExecUser(latelyHistory.getUser());
            }
            pipelineMessageListList.add(pipelineMessageList);
        }
        return pipelineMessageListList;
    }




}





























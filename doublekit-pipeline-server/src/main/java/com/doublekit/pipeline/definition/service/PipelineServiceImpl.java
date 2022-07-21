package com.doublekit.pipeline.definition.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.join.JoinTemplate;
import com.doublekit.pipeline.definition.dao.PipelineDao;
import com.doublekit.pipeline.definition.entity.PipelineEntity;
import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.definition.model.PipelineStatus;
import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.pipeline.instance.service.PipelineActionService;
import com.doublekit.pipeline.instance.service.PipelineExecHistoryService;
import com.doublekit.pipeline.instance.service.PipelineOpenService;
import com.doublekit.rpc.annotation.Exporter;
import com.doublekit.user.user.model.DmUser;
import com.doublekit.user.user.model.User;
import com.doublekit.user.user.service.DmUserService;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * PipelineServiceImpl
 */

@Service
@Exporter
public class PipelineServiceImpl implements PipelineService{

    @Autowired
    PipelineDao pipelineDao;

    @Autowired
    DmUserService dmUserService;

    @Autowired
    PipelineOpenService pipelineOpenService;

    @Autowired
    PipelineActionService pipelineActionService;

    @Autowired
    PipelineExecHistoryService pipelineExecHistoryService;

    @Autowired
    PipelineConfigureService pipelineConfigureService;

    @Autowired
    PipelineCommonService pipelineCommonService;

    @Autowired
    JoinTemplate joinTemplate;


    private static final Logger logger = LoggerFactory.getLogger(PipelineServiceImpl.class);

    //创建
    @Override
    public String createPipeline(Pipeline pipeline) {
        PipelineEntity pipelineEntity = BeanMapper.map(pipeline, PipelineEntity.class);

        List<Pipeline> pipelineList = findAllPipeline();
        //判断是否存在相同名称
        for (Pipeline pipeline1 : pipelineList) {
            if (pipeline1.getPipelineName().equals(pipeline.getPipelineName())){
                return null;
            }
        }
        pipelineActionService.createActive(pipeline.getUser().getId(),pipeline,"创建了流水线"+pipeline.getPipelineName());
        String pipelineId = pipelineDao.createPipeline(pipelineEntity);
        DmUser dmUser = new DmUser();
        dmUser.setDomainId(pipelineId);
        dmUser.setUser(new User().setId(pipeline.getUser().getId()));
        dmUserService.createDmUser(dmUser);
        return  pipelineId;
    }

    //删除
    @Override
    public Integer deletePipeline(String pipelineId,String userId) {
        Pipeline pipeline = findPipeline(pipelineId);
        joinTemplate.joinQuery(pipeline);
        List<DmUser> allDmUser = dmUserService.findAllDmUser();
        if (allDmUser == null){
            return 1;
        }
        for (DmUser dmUser : allDmUser) {
            if (!dmUser.getDomainId().equals(pipelineId)){
                continue;
            }
            dmUserService.deleteDmUser(dmUser.getId());
        }
        pipelineDao.deletePipeline(pipelineId);
        //删除对应的流水线配置
        pipelineConfigureService.deleteTask(pipelineId);
        //删除对应的历史
        pipelineExecHistoryService.deleteHistory(pipelineId);
        //删除打开信息
        pipelineOpenService.deleteAllOpen(pipelineId);
        //删除动态
        pipelineActionService.deletePipelineAction(pipelineId);
        //删除对应文件
        String fileAddress = pipelineCommonService.getFileAddress();
        pipelineCommonService.deleteFile(new File(fileAddress+pipeline.getPipelineName()));
        //动态
        pipelineActionService.createActive(userId,null,"删除了流水线"+pipeline.getPipelineName());
        return 1;
    }

    //更新
    @Override
    public int updatePipeline(Pipeline pipeline) {
        //更改对应文件名
        String fileAddress = pipelineCommonService.getFileAddress();
        String lastName = fileAddress+findPipeline(pipeline.getPipelineId()).getPipelineName();
        String newName = fileAddress+pipeline.getPipelineName();
        File file = new File(lastName);
        if (file.exists()){
            boolean b = file.renameTo(new File(newName));
        }
        PipelineEntity pipelineEntity = BeanMapper.map(pipeline, PipelineEntity.class);
        pipelineDao.updatePipeline(pipelineEntity);
        pipelineActionService.createActive(pipeline.getUser().getId(),null,"删除了流水线"+pipeline.getPipelineName());
        return 1;
    }

    //查询
    @Override
    public Pipeline findPipeline(String pipelineId) {
        Pipeline pipeline = BeanMapper.map(pipelineDao.findPipeline(pipelineId), Pipeline.class);
        joinTemplate.joinQuery(pipeline);
        return pipeline;
    }

    //根据流水线获取配置信息
    @Override
    public  List<PipelineConfigure> findPipelineConfigure(String pipelineId){
        return pipelineConfigureService.findAllConfigure(pipelineId);
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

    //获取用户流水线
    @Override
    public List<Pipeline> findUserPipeline(String userId){
        StringBuilder s = findUserPipelineId(userId);
        if (s.toString().equals("")){
            return null;
        }
        List<PipelineEntity> userPipeline = pipelineDao.findUserPipeline(s);
        return BeanMapper.mapList(userPipeline, Pipeline.class);
    }

    //获取用户所有流水线id
    @Override
    public StringBuilder findUserPipelineId(String userId){
        StringBuilder s = new StringBuilder();
        List<DmUser> allDmUser = dmUserService.findAllDmUser();
        if (allDmUser != null && allDmUser.size()!=0){
            for (DmUser dmUser : allDmUser) {
                if (!dmUser.getUser().getId().equals(userId)){
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
        return s;
    }

    //模糊查询
    @Override
    public List<Pipeline> findLike(String pipelineName,String userId) {
        List<PipelineEntity> pipelineEntityList = pipelineDao.findName(pipelineName);
        List<Pipeline> list = BeanMapper.mapList(pipelineEntityList, Pipeline.class);
        List<Pipeline> userPipeline = findUserPipeline(userId);
        if (list == null || userPipeline==null){
            return null;
        }
        ArrayList<Pipeline> pipelines = new ArrayList<>();
        for (Pipeline pipeline : userPipeline) {
            List<Pipeline> collect = list.stream().filter(pipeline1 -> pipeline.getPipelineId().equals(pipeline1.getPipelineId())).toList();
            pipelines.addAll(collect);
        }
        return pipelines;
    }

    //获取流水线状态
    @Override
    public  List<PipelineStatus> findAllStatus(List<Pipeline> allPipeline){
        List<PipelineStatus> pipelineStatusList= new ArrayList<>();
        for (Pipeline pipeline : allPipeline) {
            PipelineStatus pipelineStatus = new PipelineStatus();
            //成功和构建时间
            PipelineExecHistory latelyHistory = pipelineExecHistoryService.findLatelyHistory(pipeline.getPipelineId());
            PipelineExecHistory latelySuccess = pipelineExecHistoryService.findLatelySuccess(pipeline.getPipelineId());

            pipelineStatus.setPipelineId(pipeline.getPipelineId());
            pipelineStatus.setPipelineCollect(pipeline.getPipelineCollect());
            pipelineStatus.setPipelineName(pipeline.getPipelineName());
            pipelineStatus.setPipelineState(pipeline.getPipelineState());
            if (latelyHistory != null){
                pipelineStatus.setLastStructureTime(latelyHistory.getCreateTime());
                pipelineStatus.setStructureStatus(latelyHistory.getRunStatus());
            }
            if (latelySuccess != null){
                pipelineStatus.setLastSuccessTime(latelySuccess.getCreateTime());
            }
            pipelineStatusList.add(pipelineStatus);
        }
        return pipelineStatusList;
    }

    //获取用户7天内的所有历史
    @Override
    public  List<PipelineExecHistory>  findAllUserHistory(String userId){
        //获取7天前的时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date lastTime = DateUtils.addDays(new Date(), -7);
        Date nowTime = DateUtils.addDays(new Date(), 1);
        StringBuilder s = findUserPipelineId(userId);
        if (s.toString().equals("")){
            return null;
        }
        return pipelineExecHistoryService.findAllUserHistory(formatter.format(lastTime),formatter.format(nowTime),s);
    }














}

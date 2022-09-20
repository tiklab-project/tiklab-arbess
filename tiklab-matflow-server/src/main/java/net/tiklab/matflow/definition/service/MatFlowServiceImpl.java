package net.tiklab.matflow.definition.service;




import net.tiklab.beans.BeanMapper;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.definition.dao.MatFlowDao;
import net.tiklab.matflow.definition.entity.MatFlowEntity;
import net.tiklab.matflow.definition.model.MatFlow;
import net.tiklab.matflow.definition.model.MatFlowConfigure;
import net.tiklab.matflow.definition.model.MatFlowStatus;
import net.tiklab.matflow.execute.model.MatFlowExecHistory;
import net.tiklab.matflow.orther.service.MatFlowActivityService;
import net.tiklab.matflow.execute.service.MatFlowExecHistoryService;
import net.tiklab.matflow.orther.service.MatFlowOpenService;
import net.tiklab.rpc.annotation.Exporter;
import net.tiklab.user.user.model.DmUser;
import net.tiklab.user.user.model.User;
import net.tiklab.user.user.service.DmUserService;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * MatFlowServiceImpl
 */

@Service
@Exporter
public class MatFlowServiceImpl implements MatFlowService {

    @Autowired
    MatFlowDao matFlowDao;

    @Autowired
    DmUserService dmUserService;

    @Autowired
    MatFlowOpenService matFlowOpenService;

    @Autowired
    MatFlowActivityService matFlowActivityService;

    @Autowired
    MatFlowExecHistoryService matFlowExecHistoryService;

    @Autowired
    MatFlowConfigureService matFlowConfigureService;

    @Autowired
    MatFlowCommonService matFlowCommonService;

    @Autowired
    JoinTemplate joinTemplate;


    private static final Logger logger = LoggerFactory.getLogger(MatFlowServiceImpl.class);

    //创建
    @Override
    public String createMatFlow(MatFlow matFlow) {
        MatFlowEntity matFlowEntity = BeanMapper.map(matFlow, MatFlowEntity.class);

        List<MatFlow> matFlowList = findAllMatFlow();
        //判断是否存在相同名称
        for (MatFlow matFlow1 : matFlowList) {
            if (matFlow1.getMatflowName().equals(matFlow.getMatflowName())){
                return null;
            }
        }
        matFlowActivityService.createActive(matFlow.getUser().getId(), matFlow,"创建了流水线"+ matFlow.getMatflowName());
        String matFlowId = matFlowDao.createMatFlow(matFlowEntity);
        DmUser dmUser = new DmUser();
        dmUser.setDomainId(matFlowId);
        User user = new User();
        user.setId(matFlow.getUser().getId());
        dmUser.setUser(user);
        dmUserService.createDmUser(dmUser);
        return  matFlowId;
    }

    //删除
    @Override
    public Integer deleteMatFlow(String matFlowId,String userId) {
        MatFlow matFlow = findMatFlow(matFlowId);
        joinTemplate.joinQuery(matFlow);
        List<DmUser> allDmUser = dmUserService.findAllDmUser();
        if (allDmUser == null){
            return 1;
        }
        for (DmUser dmUser : allDmUser) {
            if (!dmUser.getDomainId().equals(matFlowId)){
                continue;
            }
            dmUserService.deleteDmUser(dmUser.getId());
        }
        matFlowDao.deleteMatFlow(matFlowId);
        //删除对应的流水线配置
        matFlowConfigureService.deleteTask(matFlowId);
        //删除对应的历史
        matFlowExecHistoryService.deleteHistory(matFlowId);
        //删除打开信息
        matFlowOpenService.deleteAllOpen(matFlowId);
        //删除动态
        matFlowActivityService.deleteMatFlowActivity(matFlowId);
        //删除对应文件
        String fileAddress = matFlowCommonService.getFileAddress();
        matFlowCommonService.deleteFile(new File(fileAddress+ matFlow.getMatflowName()));
        //动态
        matFlowActivityService.createActive(userId,null,"删除了流水线"+ matFlow.getMatflowName());
        return 1;
    }

    //更新
    @Override
    public int updateMatFlow(MatFlow matFlow) {
        //更改对应文件名
        String fileAddress = matFlowCommonService.getFileAddress();
        MatFlow flow = findMatFlow(matFlow.getMatflowId());
        String lastName = fileAddress+flow.getMatflowName();
        String newName = fileAddress+ matFlow.getMatflowName();
        File file = new File(lastName);
        if (file.exists()){
            boolean b = file.renameTo(new File(newName));
        }
        MatFlowEntity matFlowEntity = BeanMapper.map(matFlow, MatFlowEntity.class);
        matFlowDao.updateMatFlow(matFlowEntity);
        matFlowActivityService.createActive(matFlow.getUser().getId(),null,"删除了流水线"+ matFlow.getMatflowName());
        return 1;
    }

    //查询
    @Override
    public MatFlow findMatFlow(String matFlowId) {
        MatFlowEntity matFlowEntity = matFlowDao.findMatFlow(matFlowId);
        MatFlow matFlow = BeanMapper.map(matFlowEntity, MatFlow.class);
        joinTemplate.joinQuery(matFlow);
        return matFlow;
    }

    //根据流水线获取配置信息
    @Override
    public  List<MatFlowConfigure> findMatFlowConfigure(String matFlowId){
        return matFlowConfigureService.findAllConfigure(matFlowId);
    }

    //查询所有
    @Override
    public List<MatFlow> findAllMatFlow() {
        List<MatFlow> list = BeanMapper.mapList(matFlowDao.findAllMatFlow(), MatFlow.class);
        joinTemplate.joinQuery(list);
        return list;
    }

    @Override
    public List<MatFlow> findAllMatFlowList(List<String> idList) {
        List<MatFlowEntity> matFlowEntityList = matFlowDao.findAllMatFlowList(idList);
        return BeanMapper.mapList(matFlowEntityList, MatFlow.class);
    }

    //获取用户流水线
    @Override
    public List<MatFlow> findUserMatFlow(String userId){
        StringBuilder s = findUserMatFlowId(userId);
        if (s.toString().equals("")){
            return null;
        }
        List<MatFlowEntity> userMatFlow = matFlowDao.findUserMatFlow(s);
        return BeanMapper.mapList(userMatFlow, MatFlow.class);
    }

    //获取用户所有流水线id
    @Override
    public StringBuilder findUserMatFlowId(String userId){
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
        StringBuilder findPowerMatFlow = findPowerMatFlow();
        if (s.toString().equals("")){
            return findPowerMatFlow;
        }
        return s.append(",").append(findPowerMatFlow);
    }

    public StringBuilder findPowerMatFlow(){
        List<MatFlowEntity> userMatFlow = matFlowDao.findUserMatFlow();
        StringBuilder s = new StringBuilder();
        if (userMatFlow == null){
            return null;
        }
        for (MatFlowEntity matFlowEntity : userMatFlow) {
            if (s.toString().equals("") ) {
                s.append("'");
            } else {
                s.append(",'");
            }
            s.append(matFlowEntity.getMatflowId()).append("'");
        }
        return s;
    }


    //模糊查询
    @Override
    public List<MatFlow> findLike(String matFlowName, String userId) {
        List<MatFlowEntity> matFlowEntityList = matFlowDao.findName(matFlowName);
        List<MatFlow> list = BeanMapper.mapList(matFlowEntityList, MatFlow.class);
        List<MatFlow> userMatFlow = findUserMatFlow(userId);
        if (list == null || userMatFlow ==null){
            return null;
        }
        ArrayList<MatFlow> matFlows = new ArrayList<>();
        for (MatFlow matFlow : userMatFlow) {
            List<MatFlow> collect = list.stream().filter(matFlow1 -> matFlow.getMatflowId().equals(matFlow1.getMatflowId())).toList();
            matFlows.addAll(collect);
        }
        return matFlows;
    }

    //获取流水线状态
    @Override
    public  List<MatFlowStatus> findAllStatus(List<MatFlow> allMatFlow){
        List<MatFlowStatus> matFlowStatusList = new ArrayList<>();
        for (MatFlow matFlow : allMatFlow) {
            MatFlowStatus matFlowStatus = new MatFlowStatus();
            //成功和构建时间
            MatFlowExecHistory latelyHistory = matFlowExecHistoryService.findLatelyHistory(matFlow.getMatflowId());
            MatFlowExecHistory latelySuccess = matFlowExecHistoryService.findLatelySuccess(matFlow.getMatflowId());

            matFlowStatus.setMatFlowId(matFlow.getMatflowId());
            matFlowStatus.setMatFlowCollect(matFlow.getMatflowCollect());
            matFlowStatus.setMatFlowName(matFlow.getMatflowName());
            matFlowStatus.setMatFlowState(matFlow.getMatflowState());
            if (latelyHistory != null){
                matFlowStatus.setLastBuildTime(latelyHistory.getCreateTime());
                matFlowStatus.setBuildStatus(latelyHistory.getRunStatus());
            }
            if (latelySuccess != null){
                matFlowStatus.setLastSuccessTime(latelySuccess.getCreateTime());
            }
            matFlowStatusList.add(matFlowStatus);
        }
        return matFlowStatusList;
    }

    //获取用户7天内的所有历史
    @Override
    public  List<MatFlowExecHistory>  findAllUserHistory(String userId){
        //获取7天前的时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date lastTime = DateUtils.addDays(new Date(), -7);
        Date nowTime = DateUtils.addDays(new Date(), 1);
        StringBuilder s = findUserMatFlowId(userId);
        if (s.toString().equals("")){
            return null;
        }
        return matFlowExecHistoryService.findAllUserHistory(formatter.format(lastTime),formatter.format(nowTime),s);
    }

    /**
     * 获取拥有此流水线的用户
     *
     * @param matFlowId 流水线id
     * @return 用户信息
     */
    @Override
    public List<DmUser> findMatFlowUser(String matFlowId) {
        List<DmUser> allDmUser = dmUserService.findAllDmUser();
        if (allDmUser == null){
            return null;
        }
        List<DmUser> dmUsers = new ArrayList<>();
        for (DmUser dmUser : allDmUser) {
            if (dmUser.getDomainId().equals(matFlowId)){
                dmUsers.add(dmUser);
            }
        }
        return dmUsers;
    }


}

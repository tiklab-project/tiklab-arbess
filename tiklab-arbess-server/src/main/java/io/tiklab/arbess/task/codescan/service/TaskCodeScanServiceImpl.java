package io.tiklab.arbess.task.codescan.service;

import io.tiklab.arbess.setting.host.service.AuthHostService;
import io.tiklab.arbess.setting.third.model.AuthThird;
import io.tiklab.arbess.setting.third.service.AuthThirdService;
import io.tiklab.arbess.support.util.util.PipelineUtil;
import io.tiklab.arbess.task.codescan.dao.TaskCodeScanDao;
import io.tiklab.arbess.task.codescan.entity.TaskCodeScanEntity;
import io.tiklab.arbess.task.codescan.model.TaskCodeScan;
import io.tiklab.rpc.annotation.Exporter;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.toolkit.join.JoinTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static io.tiklab.arbess.support.util.util.PipelineFinal.TASK_CODESCAN_SONAR;

@Service
@Exporter
public class TaskCodeScanServiceImpl implements TaskCodeScanService {


    @Autowired
    TaskCodeScanDao codeScanDao;

    @Autowired
    AuthThirdService thirdServer;

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    AuthHostService authHostService;

    /**
     * 创建流水线代码扫描
     * @param taskCodeScan 流水线代码扫描
     * @return 流水线代码扫描id
     */
    @Override
    public String createCodeScan(TaskCodeScan taskCodeScan) {
        TaskCodeScanEntity taskCodeScanEntity = BeanMapper.map(taskCodeScan, TaskCodeScanEntity.class);
        return codeScanDao.createCodeScan(taskCodeScanEntity);
    }


    @Override
    public Boolean codeScanValid(String taskType,TaskCodeScan taskCodeScan){

        if (taskType.equals(TASK_CODESCAN_SONAR)) {
            if (StringUtils.isEmpty(taskCodeScan.getAuthId())){
                return false;
            }
            String projectName = taskCodeScan.getProjectName();
            return !StringUtils.isEmpty(projectName);
        } else {
            return true;
        }
    }


    @Override
    public TaskCodeScan findCodeScanByAuth(String taskId){
        TaskCodeScan codeScan = findOneCodeScan(taskId);
        String authId = codeScan.getAuthId();
        if (!Objects.isNull(authId)){
            Object auth = authHostService.findOneAuthHost(authId);
            if (Objects.isNull(auth)){
                auth = thirdServer.findOneAuthServer(authId);
                codeScan.setAuth(auth);
            }
            codeScan.setAuth(auth);
        }
        return codeScan;
    }

    /**
     * 删除流水线代码扫描
     * @param CodeScanId 流水线代码扫描id
     */
    @Override
    public void deleteCodeScan(String CodeScanId) {
        codeScanDao.deleteCodeScan(CodeScanId);
    }

    /**
     * 更新代码扫描信息
     * @param taskCodeScan 信息
     */
    @Override
    public void updateCodeScan(TaskCodeScan taskCodeScan) {

        TaskCodeScanEntity codeScanEntity = BeanMapper.map(taskCodeScan, TaskCodeScanEntity.class);
        if (!StringUtils.isEmpty(taskCodeScan.getCodeType())){
            codeScanEntity.setToolOther("");
        }
        codeScanDao.updateCodeScan(codeScanEntity);
    }

    /**
     * 查询代码扫描信息
     * @param codeScanId id
     * @return 信息集合
     */
    @Override
    public TaskCodeScan findOneCodeScan(String codeScanId) {
        TaskCodeScanEntity oneCodeScan = codeScanDao.findOneCodeScan(codeScanId);
        TaskCodeScan codeScan = BeanMapper.map(oneCodeScan, TaskCodeScan.class);
        // if (PipelineUtil.isNoNull(codeScan.getAuthId())){
        //     AuthThird authServer = thirdServer.findOneAuthServer(codeScan.getAuthId());
        //     codeScan.setAuth(authServer);
        // }
        joinTemplate.joinQuery(codeScan,new String[]{"toolJdk","toolMaven","toolSonar","toolSourceFare","toolNodejs","toolGo"});
        return codeScan;
    }

    @Override
    public TaskCodeScan findOneCodeScanNoQuery(String codeScanId) {
        TaskCodeScanEntity oneCodeScan = codeScanDao.findOneCodeScan(codeScanId);
        TaskCodeScan codeScan = BeanMapper.map(oneCodeScan, TaskCodeScan.class);
        if (PipelineUtil.isNoNull(codeScan.getAuthId())){
            AuthThird authServer = thirdServer.findOneAuthServerNoQuery(codeScan.getAuthId());
            codeScan.setAuth(authServer);
        }
        joinTemplate.joinQuery(codeScan,new String[]{"toolJdk","toolMaven","toolSonar","toolSourceFare","toolNodejs","toolGo"});
        return codeScan;
    }

    /**
     * 查询所有流水线代码扫描
     * @return 流水线代码扫描列表
     */
    @Override
    public List<TaskCodeScan> findAllCodeScan() {
        List<TaskCodeScanEntity> allCodeScan = codeScanDao.findAllCodeScan();
        return BeanMapper.mapList(allCodeScan, TaskCodeScan.class);
    }

    @Override
    public List<TaskCodeScan> findAllCodeScanList(List<String> idList) {
        List<TaskCodeScanEntity> allCodeScanList = codeScanDao.findAllCodeScanList(idList);
        List<TaskCodeScan> taskCodeScans = BeanMapper.mapList(allCodeScanList, TaskCodeScan.class);
        joinTemplate.joinQuery(taskCodeScans,new String[]{"toolJdk","toolMaven","toolSonar","toolSourceFare","toolNodejs","toolGo"});
        return taskCodeScans;
    }


}

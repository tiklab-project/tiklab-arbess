package net.tiklab.matflow.task.codescan.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.setting.model.AuthThird;
import net.tiklab.matflow.setting.service.AuthThirdService;
import net.tiklab.matflow.support.until.PipelineUntil;
import net.tiklab.matflow.task.codescan.dao.TaskCodeScanDao;
import net.tiklab.matflow.task.codescan.entity.TaskCodeScanEntity;
import net.tiklab.matflow.task.codescan.model.TaskCodeScan;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class TaskCodeScanServiceImpl implements TaskCodeScanService {


    @Autowired
    TaskCodeScanDao codeScanDao;

    @Autowired
    AuthThirdService thirdServer;

    @Autowired
    JoinTemplate joinTemplate;

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


    /**
     * 根据配置id删除任务
     * @param configId 配置id
     */
    @Override
    public void deleteCodeScanConfig(String configId){
        TaskCodeScan oneCodeScanConfig = findOneCodeScanConfig(configId);
        deleteCodeScan(oneCodeScanConfig.getCodeScanId());
    }

    /**
     * 根据配置id查询任务
     * @param configId 配置id
     * @return 任务
     */
    @Override
    public TaskCodeScan findOneCodeScanConfig(String configId){
        List<TaskCodeScan> allCodeScan = findAllCodeScan();
        if (allCodeScan == null){
            return null;
        }
        for (TaskCodeScan taskCodeScan : allCodeScan) {
            if (taskCodeScan.getConfigId().equals(configId)){
                return findOneCodeScan(taskCodeScan.getCodeScanId());
            }
        }
        return null;
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
        if (PipelineUntil.isNoNull(codeScan.getAuthId())){
            AuthThird authServer = thirdServer.findOneAuthServer(codeScan.getAuthId());
            codeScan.setAuth(authServer);
        }
        joinTemplate.joinQuery(codeScan);
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
        joinTemplate.joinQuery(taskCodeScans);
        return taskCodeScans;
    }


}

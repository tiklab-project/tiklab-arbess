package com.tiklab.matflow.instance.service.execAchieveImpl;

import com.tiklab.matflow.definition.model.MatFlowConfigure;
import com.tiklab.matflow.definition.service.MatFlowCommonService;
import com.tiklab.matflow.execute.model.MatFlowStructure;
import com.tiklab.matflow.execute.service.MatFlowStructureService;
import com.tiklab.matflow.instance.model.MatFlowExecHistory;
import com.tiklab.matflow.instance.model.MatFlowExecLog;
import com.tiklab.matflow.instance.model.MatFlowProcess;
import com.tiklab.matflow.instance.service.execAchieveService.StructureAchieveService;
import com.tiklab.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

/**
 * 构建执行方法
 */

@Service
@Exporter
public class StructureAchieveServiceImpl implements StructureAchieveService {

    @Autowired
    MatFlowStructureService matFlowStructureService;

    @Autowired
    CommonAchieveServiceImpl commonAchieveServiceImpl;

    @Autowired
    MatFlowCommonService matFlowCommonService;

    private static final Logger logger = LoggerFactory.getLogger(StructureAchieveServiceImpl.class);

    // 构建
    public int structure(MatFlowProcess matFlowProcess, List<MatFlowExecHistory> matFlowExecHistoryList)  {

        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        //获取配置信息
        MatFlowExecHistory matFlowExecHistory = matFlowProcess.getMatFlowExecHistory();
        MatFlowConfigure matFlowConfigure = matFlowProcess.getMatFlowConfigure();
        //初始化日志
        MatFlowExecLog matFlowExecLog = commonAchieveServiceImpl.initializeLog(matFlowExecHistory, matFlowConfigure);
        matFlowProcess.setMatFlowExecLog(matFlowExecLog);
        MatFlowStructure matFlowStructure = matFlowStructureService.findOneStructure(matFlowConfigure.getTaskId());

        //执行信息
        String structureOrder = matFlowStructure.getStructureOrder();
        String structureAddress = matFlowStructure.getStructureAddress();

        //命令执行地址
        String path = matFlowCommonService.getFileAddress()+ matFlowConfigure.getMatFlow().getMatflowName();

        try {
            String a = "------------------------------------" + " \n"
                    +"开始构建" + " \n"
                    +"执行 : \""  + structureOrder + "\"\n";

            Process process = commonAchieveServiceImpl.process(path, structureOrder);

            matFlowExecHistory.setRunLog(matFlowExecHistory.getRunLog() + a);
            matFlowExecLog.setRunLog(matFlowExecLog.getRunLog()+a);
            //构建失败
            int state = commonAchieveServiceImpl.log(process.getInputStream(), matFlowProcess, matFlowExecHistoryList);
            process.destroy();
            commonAchieveServiceImpl.updateTime(matFlowProcess,beginTime);
            if (state == 0){
                commonAchieveServiceImpl.updateState(matFlowProcess,"构建失败。", matFlowExecHistoryList);
                return 0;
            }
        } catch (IOException e) {
            commonAchieveServiceImpl.updateState(matFlowProcess,e.getMessage(), matFlowExecHistoryList);
            return 0;
        }
        commonAchieveServiceImpl.updateState(matFlowProcess,null, matFlowExecHistoryList);
        return 1;
    }

    private String mavenOrder(String structureOrder,String path,String structureAddress ){
        String order;
        int systemType = commonAchieveServiceImpl.getSystemType();
        if (systemType == 1){
            order = " .\\mvn" + " " + structureOrder + " " + "-f" +path;
        }else {
            order = " ./mvn" + " " + structureOrder + " " + "-f" +path ;
        }
        if (structureAddress != null){
            order = order + structureAddress;
        }
        return order;
    }
}

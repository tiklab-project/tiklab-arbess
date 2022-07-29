package com.tiklab.matflow.execute.service;


import com.ibm.icu.text.SimpleDateFormat;
import com.tiklab.beans.BeanMapper;
import com.tiklab.matflow.definition.model.MatFlow;
import com.tiklab.matflow.definition.model.MatFlowConfigure;
import com.tiklab.matflow.definition.model.MatFlowExecConfigure;
import com.tiklab.matflow.definition.service.MatFlowConfigureService;
import com.tiklab.matflow.execute.dao.MatFlowCodeDao;
import com.tiklab.matflow.execute.entity.MatFlowCodeEntity;
import com.tiklab.matflow.execute.model.MatFlowCode;
import com.tiklab.matflow.execute.service.codeGit.CodeGitHubService;
import com.tiklab.matflow.execute.service.codeGit.CodeGiteeApiService;
import com.tiklab.matflow.setting.proof.model.Proof;
import com.tiklab.matflow.setting.proof.service.ProofService;
import com.tiklab.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Exporter
public class MatFlowCodeServiceImpl implements MatFlowCodeService {

    @Autowired
    MatFlowCodeDao matFlowCodeDao;

    @Autowired
    ProofService proofService;

    @Autowired
    MatFlowTestService matFlowTestService;

    @Autowired
    CodeGiteeApiService codeGiteeApiService;

    @Autowired
    CodeGitHubService codeGitHubService;

    @Autowired
    MatFlowConfigureService matFlowConfigureService;

    private static final Logger logger = LoggerFactory.getLogger(MatFlowCodeServiceImpl.class);

    //创建
    @Override
    public String createCode(MatFlowCode matFlowCode) {
        if (matFlowCode.getType() == 1){
            matFlowCode.setCodeAddress(matFlowCode.getCodeName());
        }
        return matFlowCodeDao.createCode(BeanMapper.map(matFlowCode, MatFlowCodeEntity.class));
    }

    //创建配置
    @Override
    public String createConfigure(String matFlowId, MatFlowCode matFlowCode){
        String codeId = createCode(matFlowCode);
        MatFlowConfigure matFlowConfigure = new MatFlowConfigure();
        matFlowConfigure.setTaskAlias("源码管理");
        matFlowConfigure.setTaskId(codeId);
        matFlowConfigure.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        matFlowConfigureService.createConfigure(matFlowConfigure);
        return codeId;
    }

    //删除
    @Override
    public void deleteCode(String codeId) {
        matFlowCodeDao.deleteCode(codeId);
    }

    @Override
    public void deleteTask(String taskId, int taskType) {
        if (taskType <= 10){
            deleteCode(taskId);
            return;
        }
        matFlowTestService.deleteTask(taskId,taskType);
    }

    //修改
    @Override
    public void updateCode(MatFlowCode matFlowCode) {
        matFlowCodeDao.updateCode(BeanMapper.map(matFlowCode, MatFlowCodeEntity.class));
    }

    //通过授权信息获取仓库url
    private MatFlowCode getUrl(MatFlowCode matFlowCode){
        if (matFlowCode.getProof() == null){
            return null;
        }
        if (matFlowCode.getType() == 2 ){
            String cloneUrl = codeGiteeApiService.getCloneUrl(matFlowCode.getProof(), matFlowCode.getCodeName());
            matFlowCode.setCodeAddress(cloneUrl);
        }else if (matFlowCode.getType() == 3){
            String cloneUrl = codeGitHubService.getOneHouse(matFlowCode.getProof(), matFlowCode.getCodeName());
            matFlowCode.setCodeAddress(cloneUrl);
        }
        return matFlowCode;
    }

    //修改任务
    @Override
    public void updateTask(MatFlowExecConfigure matFlowExecConfigure) {

        MatFlow matFlow = matFlowExecConfigure.getMatFlow();
        MatFlowCode matFlowCode = matFlowExecConfigure.getMatFlowCode();

        MatFlowConfigure oneConfigure = matFlowConfigureService.findOneConfigure(matFlow.getMatflowId(), 10);

        if (oneConfigure != null && matFlowCode.getType() == 0){
            deleteCode(oneConfigure.getTaskId());
            matFlowConfigureService.deleteConfigure(oneConfigure.getConfigureId());
            matFlowTestService.updateTask(matFlowExecConfigure);
            return;
        }

        if (oneConfigure == null){
            oneConfigure = new MatFlowConfigure();
        }

        if (matFlowCode.getType() == 0){
            matFlowTestService.updateTask(matFlowExecConfigure);
            return;
        }

        matFlowCode.setCodeAddress(matFlowCode.getCodeName());
        oneConfigure.setTaskSort(1);
        oneConfigure.setView(matFlowExecConfigure.getView());
        oneConfigure.setTaskType(matFlowCode.getType());
        oneConfigure.setMatFlow(matFlow);
        oneConfigure.setTaskAlias("源码管理");
        oneConfigure.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        //通过授权信息获取仓库url
        matFlowCode = getUrl(matFlowCode);
        if (matFlowCode == null){
            return;
        }

        if (matFlowCode.getCodeId() != null){
            updateCode(matFlowCode);
        }else {
            String codeId = createCode(matFlowCode);
            oneConfigure.setTaskId(codeId);
            matFlowConfigureService.createConfigure(oneConfigure);
        }
        matFlowTestService.updateTask(matFlowExecConfigure);
    }


    //查询单个
    @Override
    public MatFlowCode findOneCode(String codeId) {
        MatFlowCodeEntity oneCode = matFlowCodeDao.findOneCode(codeId);
        MatFlowCode matFlowCode = BeanMapper.map(oneCode, MatFlowCode.class);
        if (oneCode == null){
            return null;
        }
        if (oneCode.getProofId() != null){
            Proof proof = proofService.findOneProof(oneCode.getProofId());
            matFlowCode.setProof(proof);
        }
        return matFlowCode;
    }

    //获取配置
    @Override
    public List<Object> findOneTask(MatFlowConfigure matFlowConfigure, List<Object> list) {
            if (matFlowConfigure.getTaskType() < 10){
                MatFlowCode oneCode = findOneCode(matFlowConfigure.getTaskId());
                if (oneCode.getProof() != null){
                    Proof proof = proofService.findOneProof(oneCode.getProof().getProofId());
                    oneCode.setProof(proof);
                }
                list.add(oneCode);
            }
        return matFlowTestService.findOneTask(matFlowConfigure,list);
    }

    //查询所有
    @Override
    public List<MatFlowCode> findAllCode() {
        return BeanMapper.mapList(matFlowCodeDao.findAllCode(), MatFlowCode.class);
    }

    @Override
    public List<MatFlowCode> findAllCodeList(List<String> idList) {
        return BeanMapper.mapList(matFlowCodeDao.findAllCodeList(idList), MatFlowCode.class);
    }

}



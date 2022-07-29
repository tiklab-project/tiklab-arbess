package com.tiklab.matflow.execute.service;


import com.doublekit.beans.BeanMapper;
import com.tiklab.matflow.definition.model.Pipeline;
import com.tiklab.matflow.definition.model.PipelineConfigure;
import com.tiklab.matflow.definition.model.PipelineExecConfigure;
import com.tiklab.matflow.definition.service.PipelineConfigureService;
import com.tiklab.matflow.execute.dao.PipelineCodeDao;
import com.tiklab.matflow.execute.entity.PipelineCodeEntity;
import com.tiklab.matflow.execute.model.PipelineCode;
import com.tiklab.matflow.execute.service.codeGit.CodeGitHubService;
import com.tiklab.matflow.execute.service.codeGit.CodeGiteeApiService;
import com.tiklab.matflow.setting.proof.model.Proof;
import com.tiklab.matflow.setting.proof.service.ProofService;
import com.doublekit.rpc.annotation.Exporter;
import com.ibm.icu.text.SimpleDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Exporter
public class PipelineCodeServiceImpl implements PipelineCodeService {

    @Autowired
    PipelineCodeDao pipelineCodeDao;

    @Autowired
    ProofService proofService;

    @Autowired
    PipelineTestService pipelineTestService;

    @Autowired
    CodeGiteeApiService codeGiteeApiService;

    @Autowired
    CodeGitHubService codeGitHubService;

    @Autowired
    PipelineConfigureService pipelineConfigureService;

    private static final Logger logger = LoggerFactory.getLogger(PipelineCodeServiceImpl.class);

    //创建
    @Override
    public String createCode(PipelineCode pipelineCode) {
        if (pipelineCode.getType() == 1){
            pipelineCode.setCodeAddress(pipelineCode.getCodeName());
        }
        return pipelineCodeDao.createCode(BeanMapper.map(pipelineCode, PipelineCodeEntity.class));
    }

    //创建配置
    @Override
    public String createConfigure(String pipelineId,PipelineCode pipelineCode){
        String codeId = createCode(pipelineCode);
        PipelineConfigure pipelineConfigure = new PipelineConfigure();
        pipelineConfigure.setTaskAlias("源码管理");
        pipelineConfigure.setTaskId(codeId);
        pipelineConfigure.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        pipelineConfigureService.createConfigure(pipelineConfigure);
        return codeId;
    }

    //删除
    @Override
    public void deleteCode(String codeId) {
        pipelineCodeDao.deleteCode(codeId);
    }

    @Override
    public void deleteTask(String taskId, int taskType) {
        if (taskType <= 10){
            deleteCode(taskId);
            return;
        }
        pipelineTestService.deleteTask(taskId,taskType);
    }

    //修改
    @Override
    public void updateCode(PipelineCode pipelineCode) {
        pipelineCodeDao.updateCode(BeanMapper.map(pipelineCode,PipelineCodeEntity.class));
    }

    //通过授权信息获取仓库url
    private PipelineCode getUrl(PipelineCode pipelineCode){
        if (pipelineCode.getProof() == null){
            return null;
        }
        if (pipelineCode.getType() == 2 ){
            String cloneUrl = codeGiteeApiService.getCloneUrl(pipelineCode.getProof(),pipelineCode.getCodeName());
            pipelineCode.setCodeAddress(cloneUrl);
        }else if (pipelineCode.getType() == 3){
            String cloneUrl = codeGitHubService.getOneHouse(pipelineCode.getProof(),pipelineCode.getCodeName());
            pipelineCode.setCodeAddress(cloneUrl);
        }
        return pipelineCode;
    }

    //修改任务
    @Override
    public void updateTask(PipelineExecConfigure pipelineExecConfigure) {

        Pipeline pipeline = pipelineExecConfigure.getPipeline();
        PipelineCode pipelineCode =pipelineExecConfigure.getPipelineCode();

        PipelineConfigure oneConfigure = pipelineConfigureService.findOneConfigure(pipeline.getPipelineId(), 10);

        if (oneConfigure != null && pipelineCode.getType() == 0){
            deleteCode(oneConfigure.getTaskId());
            pipelineConfigureService.deleteConfigure(oneConfigure.getConfigureId());
            pipelineTestService.updateTask(pipelineExecConfigure);
            return;
        }

        if (oneConfigure == null){
            oneConfigure = new PipelineConfigure();
        }

        if (pipelineCode.getType() == 0){
            pipelineTestService.updateTask(pipelineExecConfigure);
            return;
        }

        pipelineCode.setCodeAddress(pipelineCode.getCodeName());
        oneConfigure.setTaskSort(1);
        oneConfigure.setView(pipelineExecConfigure.getView());
        oneConfigure.setTaskType(pipelineCode.getType());
        oneConfigure.setPipeline(pipeline);
        oneConfigure.setTaskAlias("源码管理");
        oneConfigure.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        //通过授权信息获取仓库url
        pipelineCode = getUrl(pipelineCode);
        if (pipelineCode == null){
            return;
        }

        if (pipelineCode.getCodeId() != null){
            updateCode(pipelineCode);
        }else {
            String codeId = createCode(pipelineCode);
            oneConfigure.setTaskId(codeId);
            pipelineConfigureService.createConfigure(oneConfigure);
        }
        pipelineTestService.updateTask(pipelineExecConfigure);
    }


    //查询单个
    @Override
    public PipelineCode findOneCode(String codeId) {
        PipelineCodeEntity oneCode = pipelineCodeDao.findOneCode(codeId);
        PipelineCode pipelineCode = BeanMapper.map(oneCode, PipelineCode.class);
        if (oneCode == null){
            return null;
        }
        if (oneCode.getProofId() != null){
            Proof proof = proofService.findOneProof(oneCode.getProofId());
            pipelineCode.setProof(proof);
        }
        return  pipelineCode;
    }

    //获取配置
    @Override
    public List<Object> findOneTask(PipelineConfigure pipelineConfigure ,List<Object> list) {
            if (pipelineConfigure.getTaskType() < 10){
                PipelineCode oneCode = findOneCode(pipelineConfigure.getTaskId());
                if (oneCode.getProof() != null){
                    Proof proof = proofService.findOneProof(oneCode.getProof().getProofId());
                    oneCode.setProof(proof);
                }
                list.add(oneCode);
            }
        return pipelineTestService.findOneTask(pipelineConfigure,list);
    }

    //查询所有
    @Override
    public List<PipelineCode> findAllCode() {
        return BeanMapper.mapList(pipelineCodeDao.findAllCode(), PipelineCode.class);
    }

    @Override
    public List<PipelineCode> findAllCodeList(List<String> idList) {
        return BeanMapper.mapList(pipelineCodeDao.findAllCodeList(idList), PipelineCode.class);
    }

}



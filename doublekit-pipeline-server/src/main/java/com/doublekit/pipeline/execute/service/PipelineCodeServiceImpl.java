package com.doublekit.pipeline.execute.service;


import com.doublekit.beans.BeanMapper;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.definition.model.PipelineExecConfigure;
import com.doublekit.pipeline.definition.service.PipelineConfigureService;
import com.doublekit.pipeline.execute.dao.PipelineCodeDao;
import com.doublekit.pipeline.execute.entity.PipelineCodeEntity;
import com.doublekit.pipeline.execute.model.PipelineCode;
import com.doublekit.pipeline.execute.service.codeGit.CodeGiteeApiService;
import com.doublekit.pipeline.setting.proof.model.Proof;
import com.doublekit.pipeline.setting.proof.service.ProofService;
import com.doublekit.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public String createConfigure(String pipelineId,int taskType,PipelineCode pipelineCode){
        PipelineConfigure pipelineConfigure = new PipelineConfigure();
        pipelineConfigure.setTaskAlias("源码管理");
        if (pipelineCode.getCodeAlias() != null){
            pipelineConfigure.setTaskAlias(pipelineCode.getCodeAlias());
        }
        pipelineCode.setType(taskType);
        pipelineConfigure.setTaskSort(1);
        pipelineCode.setCodeAddress(pipelineCode.getCodeName());
        if (pipelineCode.getType() == 2){
            String cloneUrl = codeGiteeApiService.getCloneUrl(pipelineCode.getProof().getProofId(),pipelineCode.getCodeName());
            pipelineCode.setCodeAddress(cloneUrl);
        }
        String codeId = createCode(pipelineCode);
        pipelineConfigure.setTaskId(codeId);
        pipelineConfigure.setTaskType(taskType);
        pipelineConfigure.setTaskSort(pipelineCode.getSort());
        pipelineConfigureService.createTask(pipelineConfigure,pipelineId);
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

    //修改任务
    @Override
    public void updateTask(PipelineExecConfigure pipelineExecConfigure) {
        String pipelineId = pipelineExecConfigure.getPipelineId();
        PipelineCode pipelineCode =pipelineExecConfigure.getPipelineCode();
        PipelineConfigure oneConfigure = pipelineConfigureService.findOneConfigure(pipelineId, 10);
        if (oneConfigure != null){
            //判断是否存在配置
            if (pipelineCode.getType() != 0){
                pipelineCode.setCodeAddress(pipelineCode.getCodeName());
                oneConfigure.setTaskSort(1);
                if (pipelineCode.getType() == 2){
                    String cloneUrl = codeGiteeApiService.getCloneUrl(pipelineCode.getProof().getProofId(),pipelineCode.getCodeName());
                    pipelineCode.setCodeAddress(cloneUrl);
                }
                updateCode(pipelineCode);
                oneConfigure.setTaskAlias(pipelineCode.getCodeAlias());
                pipelineConfigureService.updateConfigure(oneConfigure);
            }else {
                pipelineConfigureService.deleteTask(oneConfigure.getTaskId(),pipelineId);
            }
        }
        if (oneConfigure == null && pipelineCode.getType() != 0){
            createConfigure(pipelineId, pipelineCode.getType(),pipelineCode);
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



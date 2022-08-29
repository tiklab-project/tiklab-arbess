package com.tiklab.matflow.execute.service;


import com.tiklab.beans.BeanMapper;
import com.tiklab.join.JoinTemplate;
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
    JoinTemplate joinTemplate;

    @Autowired
    CodeGiteeApiService codeGiteeApiService;

    @Autowired
    CodeGitHubService codeGitHubService;

    private static final Logger logger = LoggerFactory.getLogger(MatFlowCodeServiceImpl.class);

    //创建
    @Override
    public String createCode(MatFlowCode matFlowCode) {
        if (matFlowCode.getType() == 1){
            matFlowCode.setCodeAddress(matFlowCode.getCodeName());
        }
        return matFlowCodeDao.createCode(BeanMapper.map(matFlowCode, MatFlowCodeEntity.class));
    }

    //删除
    @Override
    public void deleteCode(String codeId) {
        matFlowCodeDao.deleteCode(codeId);
    }

    //修改
    @Override
    public void updateCode(MatFlowCode matFlowCode) {
        matFlowCodeDao.updateCode(BeanMapper.map(matFlowCode, MatFlowCodeEntity.class));
    }

    //通过授权信息获取仓库url
    public MatFlowCode getUrl(MatFlowCode matFlowCode){
        if (matFlowCode.getProof() == null){
            return null;
        }
        if (matFlowCode.getType() == 2 ){
            String cloneUrl = codeGiteeApiService.getCloneUrl(matFlowCode.getProof(), matFlowCode.getCodeName());
            matFlowCode.setCodeAddress(cloneUrl);
        }else if (matFlowCode.getType() == 3){
            String cloneUrl = codeGitHubService.getOneHouse(matFlowCode.getProof(), matFlowCode.getCodeName());
            matFlowCode.setCodeAddress(cloneUrl);
        }else {
            matFlowCode.setCodeAddress(matFlowCode.getCodeName());
        }
        return matFlowCode;
    }

    //查询单个
    @Override
    public MatFlowCode findOneCode(String codeId) {
        MatFlowCodeEntity oneCode = matFlowCodeDao.findOneCode(codeId);
        MatFlowCode matFlowCode = BeanMapper.map(oneCode, MatFlowCode.class);
        if (oneCode == null){
            return null;
        }
        joinTemplate.joinQuery(matFlowCode);
        //if (oneCode.getProofId() != null){
        //    Proof proof = proofService.findOneProof(oneCode.getProofId());
        //    matFlowCode.setProof(proof);
        //}
        return matFlowCode;
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



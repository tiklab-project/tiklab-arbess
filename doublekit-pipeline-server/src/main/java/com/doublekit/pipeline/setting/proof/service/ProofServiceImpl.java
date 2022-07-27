package com.doublekit.pipeline.setting.proof.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.join.JoinTemplate;
import com.doublekit.pipeline.definition.service.PipelineService;
import com.doublekit.pipeline.execute.service.PipelineCodeServiceImpl;
import com.doublekit.pipeline.instance.service.PipelineActionService;
import com.doublekit.pipeline.setting.proof.dao.ProofDao;
import com.doublekit.pipeline.setting.proof.entity.ProofEntity;
import com.doublekit.pipeline.setting.proof.entity.ProofTaskEntity;
import com.doublekit.pipeline.setting.proof.model.Proof;
import com.doublekit.pipeline.setting.proof.model.ProofTask;
import com.doublekit.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@Service
@Exporter
public class ProofServiceImpl implements ProofService{

    @Autowired
    ProofDao proofDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    ProofTaskService proofTaskService;

    @Autowired
    PipelineService pipelineService;

    @Autowired
    PipelineActionService pipelineActionService;

    private static final Logger logger = LoggerFactory.getLogger(PipelineCodeServiceImpl.class);

    //创建
    @Override
    public String createProof(Proof proof) {
        //判断凭证类型
        if (proof.getProofType().equals("SSH")){
            try {
                //获取主机名
                proof.setProofUsername(InetAddress.getLocalHost().getHostName());
            } catch (UnknownHostException e) {
                //失败更改为凭证名称
                proof.setProofUsername(proof.getProofName());
            }
        }
        ProofEntity proofEntity = BeanMapper.map(proof, ProofEntity.class);
        //判断凭证作用域
        String proofId = proofDao.createProof(proofEntity);
        if (proof.getType() == 1){
            return proofId;
        }
        for (String s : proof.getProofList()) {
            proofTaskService.createProofTask(new ProofTask(proofId,s));
        }
        return proofId;
    }

    //删除
    @Override
    public void deleteProof(String proofId) {
        Proof proof = findOneProof(proofId);
        //判断是否存在关联信息
        if (proof.getType() == 2){
            proofDao.deleteProofTask(proofId);
        }
        proofDao.deleteProof(proofId);
        pipelineActionService.createActive(proof.getUser().getId(),null,"删除了凭证"+proof.getProofName());
    }


    //更新
    @Override
    public void updateProof(Proof proof) {
        String proofId = proof.getProofId();
        proofDao.deleteProofTask(proofId);
        if (proof.getType() == 2 && proof.getProofList() != null){
            for (String s : proof.getProofList()) {
                proofTaskService.createProofTask(new ProofTask(proofId,s));
            }
        }
        proofDao.updateProof(BeanMapper.map(proof, ProofEntity.class));

    }

    //查询
    @Override
    public Proof findOneProof(String proofId) {
        ProofEntity proofEntity = proofDao.findOneProof(proofId);
        Proof proof = BeanMapper.map(proofEntity, Proof.class);
        joinTemplate.joinQuery(proof);
        return proof;
    }

    //查询所有
    @Override
    public List<Proof> findAllProof() {
        List<ProofEntity> proofEntityList = proofDao.selectAllProof();
        return BeanMapper.mapList(proofEntityList, Proof.class);
        //joinTemplate.joinQuery(proofList);
        //return proofList;
    }

    @Override
    public List<Proof> findPipelineProof(String userId,String pipelineId,int type){
        List<Proof> allProof;

        StringBuilder s = pipelineService.findUserPipelineId(userId);
        //判断查询系统凭证还是项目凭证
        if (pipelineId.equals("")){
             allProof = BeanMapper.mapList(proofDao.findPipelineProof(s), Proof.class);
        }else {
            allProof =  BeanMapper.mapList(proofDao.findPipelineProof(pipelineId,type), Proof.class);
        }

        if (allProof == null){
            return null;
        }

        for (Proof proof : allProof) {
            List<String> arrayList = new ArrayList<>();
            List<ProofTaskEntity> list = proofDao.findAllProofTask(proof.getProofId());
            if (list.size() == 0){
                continue;
            }
            for (ProofTaskEntity proofTaskEntity : list) {
                arrayList.add(proofTaskEntity.getPipelineId());
                proof.setProofList(arrayList);
            }
        }
        return allProof;
    }

    @Override
    public List<Proof> selectProofList(List<String> idList) {
        List<ProofEntity> proofEntityList = proofDao.selectAllProofList(idList);
        return BeanMapper.mapList(proofEntityList, Proof.class);
    }

    //获取相同授权凭证
    @Override
    public Proof findAllAuthProof(int scope,String userName){
        List<Proof> allProof = findAllProof();
        if (allProof == null){
            return null;
        }
        for (Proof proof : allProof) {
            if (proof.getProofScope() != scope || !proof.getProofUsername().equals(userName)){
                continue;
            }
            return proof;
        }
        return null;
    }











}

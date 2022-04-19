package com.doublekit.pipeline.setting.proof.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.join.JoinTemplate;
import com.doublekit.pipeline.example.service.codeGit.CodeGiteeApiService;
import com.doublekit.pipeline.setting.proof.dao.ProofDao;
import com.doublekit.pipeline.setting.proof.entity.ProofEntity;
import com.doublekit.pipeline.setting.proof.model.Proof;
import com.doublekit.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Exporter
public class ProofServiceImpl implements ProofService{

    @Autowired
    ProofDao ProofDao;

    @Autowired
    JoinTemplate joinTemplate;

   @Autowired
   CodeGiteeApiService codeGiteeApiService;


    //创建
    @Override
    public String createProof(Proof proof) {
        List<Proof> allProof = findAllProof();
        if (allProof != null){
            for (Proof proof1 : allProof) {
                if (proof.getProofName().equals(proof1.getProofName())){
                    return null;
                }
            }
        }
        ProofEntity proofEntity = BeanMapper.map(proof, ProofEntity.class);
        return ProofDao.createProof(proofEntity);
    }

    //删除
    @Override
    public void deleteProof(String id) {
        ProofDao.deleteProof(id);
    }


    //更新
    @Override
    public void updateProof(Proof proof) {
        ProofDao.updateProof(BeanMapper.map(proof, ProofEntity.class));
    }

    //查询
    @Override
    public Proof findOneProof(String proofId) {
        ProofEntity proofEntity = ProofDao.findOneProof(proofId);
        Proof proof = BeanMapper.map(proofEntity, Proof.class);
        joinTemplate.joinQuery(proof);
        return proof;
    }

    //根据名称获取凭证
    public Proof fondOneName(String proofName){
        String[] s = proofName.split(" ");
        if (s.length ==2){
            List<Proof> allProof = findAllProof();
            for (Proof proof : allProof) {
                if (proof.getProofName().equals(s[0])){
                    return proof;
                }
            }
        }
        return null;
    }

    //查询所有
    public List<Proof> findAllProof() {
        List<ProofEntity> proofEntityList = ProofDao.selectAllProof();
        List<Proof> proofList = BeanMapper.mapList(proofEntityList, Proof.class);
        joinTemplate.joinQuery(proofList);
        return proofList;
    }

    //获取构建凭证
    public List<Proof> findAllGitProof(){
        List<ProofEntity> proofEntityList = ProofDao.selectAllProof();
        List<ProofEntity> proofs = new ArrayList<>();
        for (ProofEntity proofEntity : proofEntityList) {
            if (proofEntity.getProofScope() == 1){
                proofs.add(proofEntity);
            }
        }
        return BeanMapper.mapList(proofs, Proof.class);
    }

    //获取部署凭证
    public List<Proof> findAllDeployProof(){
        List<ProofEntity> proofEntityList = ProofDao.selectAllProof();
        List<ProofEntity> proofs = new ArrayList<>();
        for (ProofEntity proofEntity : proofEntityList) {
            //判断凭证类型
            if (proofEntity.getProofScope() == 2){
                proofs.add(proofEntity);
            }
        }
        return  BeanMapper.mapList(proofs, Proof.class);
    }

    public List<Proof> findAllGitee(){
        List<Proof> proofList = findAllProof();
        List<Proof> proofs = new ArrayList<>();
        for (Proof proof : proofList) {
            if (proof.getProofScope() == 3){
                proofs.add(proof);
            }
        }
        return proofs;
    }

    @Override
    public List<Proof> selectProofList(List<String> idList) {
        List<ProofEntity> proofEntityList = ProofDao.selectAllProofList(idList);
        return BeanMapper.mapList(proofEntityList, Proof.class);
    }


}

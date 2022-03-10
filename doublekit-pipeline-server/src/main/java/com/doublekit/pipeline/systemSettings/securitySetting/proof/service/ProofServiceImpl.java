package com.doublekit.pipeline.systemSettings.securitySetting.proof.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.join.JoinTemplate;
import com.doublekit.pipeline.systemSettings.securitySetting.proof.dao.ProofDao;
import com.doublekit.pipeline.systemSettings.securitySetting.proof.entity.ProofEntity;
import com.doublekit.pipeline.systemSettings.securitySetting.proof.model.Proof;
import com.doublekit.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.PseudoColumnUsage;
import java.util.ArrayList;
import java.util.List;

@Service
@Exporter
public class ProofServiceImpl implements ProofService{

    @Autowired
    ProofDao ProofDao;

    @Autowired
    JoinTemplate joinTemplate;

    //创建
    @Override
    public String createProof(Proof proof) {

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

        ProofEntity proofEntity = BeanMapper.map(proof, ProofEntity.class);

        ProofDao.updateProof(proofEntity);
    }

    //查询
    @Override
    public Proof findOneProof(String id) {

        ProofEntity proofEntity = ProofDao.findOneProof(id);

        Proof proof = BeanMapper.map(proofEntity, Proof.class);

        joinTemplate.joinQuery(proof);

        return proof;
    }

    //查询所有
    public List<Proof> selectAllProof() {

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
        List<Proof> proofList = BeanMapper.mapList(proofs, Proof.class);

        joinTemplate.joinQuery(proofList);

        return proofList;
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
        List<Proof> proofList = BeanMapper.mapList(proofs, Proof.class);

        joinTemplate.joinQuery(proofList);

        return proofList;
    }

    @Override
    public List<Proof> selectProofList(List<String> idList) {

        List<ProofEntity> proofEntityList = ProofDao.selectAllProofList(idList);

        return BeanMapper.mapList(proofEntityList, Proof.class);
    }


}

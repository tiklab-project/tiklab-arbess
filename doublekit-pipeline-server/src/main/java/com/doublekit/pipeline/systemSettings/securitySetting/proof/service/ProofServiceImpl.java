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

        List<Proof> proofs = selectAllProof();

        for (Proof proof1 : proofs) {

            if (proof.getProofName().equals(proof1.getProofName())){

                return null;
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

        ProofEntity proofEntity = BeanMapper.map(proof, ProofEntity.class);

        ProofDao.updateProof(proofEntity);
    }

    //查询
    @Override
    public Proof selectProof(String id) {

        ProofEntity proofEntity = ProofDao.selectProof(id);

        Proof proof = BeanMapper.map(proofEntity, Proof.class);

        joinTemplate.joinQuery(proof);

        return proof;
    }

    //查询所有
    @Override
    public List<Proof> selectAllProof() {

        List<ProofEntity> proofEntityList = ProofDao.selectAllProof();

        List<Proof> proofList = BeanMapper.mapList(proofEntityList, Proof.class);

        joinTemplate.joinQuery(proofList);

        return proofList;
    }

    //根据id查询名称
    @Override
    public String selectProofName(String proofId) {

        List<Proof> proofs = selectAllProof();
        if (proofs != null){
            for (Proof proof : proofs) {
                if (proof.getProofId().equals(proofId)){

                    return proof.getProofName();

                }
            }
        }
        return null;
    }

    @Override
    public List<Proof> selectProofList(List<String> idList) {

        List<ProofEntity> proofEntityList = ProofDao.selectAllProofList(idList);

        return BeanMapper.mapList(proofEntityList, Proof.class);
    }


}

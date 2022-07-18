package com.doublekit.pipeline.setting.proof.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.join.JoinTemplate;
import com.doublekit.pipeline.definition.service.PipelineService;
import com.doublekit.pipeline.execute.service.PipelineCodeServiceImpl;
import com.doublekit.pipeline.instance.service.PipelineActionService;
import com.doublekit.pipeline.instance.service.execAchieveService.CommonAchieveService;
import com.doublekit.pipeline.setting.proof.dao.ProofDao;
import com.doublekit.pipeline.setting.proof.entity.ProofEntity;
import com.doublekit.pipeline.setting.proof.model.Proof;
import com.doublekit.pipeline.setting.proof.model.ProofQuery;
import com.doublekit.pipeline.setting.proof.model.ProofTask;
import com.doublekit.rpc.annotation.Exporter;
import com.doublekit.user.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.List;

@Service
@Exporter
public class ProofServiceImpl implements ProofService{

    @Autowired
    ProofDao ProofDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    ProofTaskService proofTaskService;

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
        if (proof.getType() == 1){
            return ProofDao.createProof(proofEntity);
        }
        String proofId = ProofDao.createProof(proofEntity);
        for (String s : proof.getProofList()) {
            proofTaskService.createProofTask(new ProofTask(proofId,s));
        }
        return proofId;
    }

    //删除
    @Override
    public void deleteProof(String proofId) {
        Proof proof = findOneProof(proofId);
        ProofDao.deleteProof(proofId);
        pipelineActionService.createActive(proof.getUser().getId(),null,"删除了凭证"+proof.getProofName());
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

    //查询所有
    @Override
    public List<Proof> findAllProof() {
        List<ProofEntity> proofEntityList = ProofDao.selectAllProof();
        List<Proof> proofList = BeanMapper.mapList(proofEntityList, Proof.class);
        joinTemplate.joinQuery(proofList);
        for (Proof proof : proofList) {
            if (proof.getUser() != null){
                proof.setUsername(proof.getUser().getName());
            }
        }
        return proofList;
    }

    @Override
    public HashSet<Proof> findAllUserProof(String userId) {
        HashSet<Proof> proofSet = new HashSet<>();
        List<Proof> proofList = findAllProof();
        joinTemplate.joinQuery(proofList);
        for (Proof proof : proofList) {
            if (proof.getUser().getId().equals(userId)){
                proof.setUsername(proof.getUser().getName());
                proofSet.add(proof);
            }
        }
        return proofSet;
    }

    @Override
    public List<Proof> findPipelineProof(String pipelineId,int type){
        if (pipelineId.equals("")){
            return findAllProof();
        }
        return BeanMapper.mapList(ProofDao.findPipelineProof(pipelineId,type), Proof.class);
        //StringBuilder s = pipelineService.findUserPipelineId(proofQuery.getUserId());
        //
        //if (s == null){
        //    return null;
        //}
        //if (proofQuery.getType() == 0 ){
        //        if (proofQuery.getPipelineId() == null){
        //            List<ProofEntity> allProof = ProofDao.findAllProof(proofQuery.getUserId(),s);
        //            if (allProof == null){
        //                return null;
        //            }
        //            List<Proof> proofs = BeanMapper.mapList(allProof, Proof.class);
        //            joinTemplate.joinQuery(proofs);
        //            return proofs;
        //        }
        //        List<ProofEntity> allProof = ProofDao.findPipelineAllProof(proofQuery.getUserId(), proofQuery.getPipelineId());
        //        if (allProof == null){
        //            return null;
        //        }
        //        List<Proof> proofs = BeanMapper.mapList(allProof, Proof.class);
        //        joinTemplate.joinQuery(proofs);
        //        return proofs;
        //    }else {
        //        List<ProofEntity> allProof = ProofDao.findPipelineProof(proofQuery,s);
        //        if (allProof == null){
        //            return null;
        //        }
        //        List<Proof> proofs = BeanMapper.mapList(allProof, Proof.class);
        //        joinTemplate.joinQuery(proofs);
        //        return proofs;
        //    }
    }

    @Override
    public List<Proof> selectProofList(List<String> idList) {
        List<ProofEntity> proofEntityList = ProofDao.selectAllProofList(idList);
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

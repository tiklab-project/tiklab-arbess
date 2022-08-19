package com.tiklab.matflow.setting.proof.service;


import com.tiklab.beans.BeanMapper;
import com.tiklab.join.JoinTemplate;
import com.tiklab.matflow.definition.entity.MatFlowEntity;
import com.tiklab.matflow.definition.service.MatFlowService;
import com.tiklab.matflow.execute.service.MatFlowCodeServiceImpl;
import com.tiklab.matflow.instance.service.MatFlowActionService;
import com.tiklab.matflow.setting.proof.dao.ProofDao;
import com.tiklab.matflow.setting.proof.entity.ProofEntity;
import com.tiklab.matflow.setting.proof.entity.ProofTaskEntity;
import com.tiklab.matflow.setting.proof.model.Proof;
import com.tiklab.matflow.setting.proof.model.ProofTask;

import com.tiklab.rpc.annotation.Exporter;
import com.tiklab.user.user.model.DmUser;
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
    MatFlowActionService matFlowActionService;

    private static final Logger logger = LoggerFactory.getLogger(MatFlowCodeServiceImpl.class);

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
        matFlowActionService.createActive(proof.getUser().getId(),null,"删除了凭证"+proof.getProofName());
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
    }

    @Override
    public List<Proof> findMatFlowProof(String userId,String matFlowId,int type,StringBuilder s){
        List<Proof> allProof;
        //判断查询系统凭证还是项目凭证
        if (matFlowId.equals("")){
             allProof = BeanMapper.mapList(proofDao.findMatFlowProof(s), Proof.class);
        }else {
            allProof =  BeanMapper.mapList(proofDao.findMatFlowProof(matFlowId,type), Proof.class);
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
                arrayList.add(proofTaskEntity.getMatflowId());
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

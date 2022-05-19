package com.doublekit.pipeline.setting.proof.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.join.JoinTemplate;
import com.doublekit.pipeline.execute.service.PipelineCodeServiceImpl;
import com.doublekit.pipeline.setting.proof.dao.ProofDao;
import com.doublekit.pipeline.setting.proof.entity.ProofEntity;
import com.doublekit.pipeline.setting.proof.model.Proof;
import com.doublekit.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Exporter
public class ProofServiceImpl implements ProofService{

    @Autowired
    ProofDao ProofDao;

    @Autowired
    JoinTemplate joinTemplate;

    private static final Logger logger = LoggerFactory.getLogger(PipelineCodeServiceImpl.class);

    //创建
    @Override
    public String createProof(Proof proof) {
        List<Proof> allProof = findAllProof();
        if (allProof != null){
            for (Proof proof1 : allProof) {
                if (proof.getProofUsername().equals(proof1.getProofUsername())){
                    proof.setProofId(proof1.getProofId());
                    updateProof(proof);
                    return proof.getProofId();
                }
            }
        if (proof.getProofType().equals("SSH")){
                String path="D:\\clone\\key\\"+proof.getProofName();
                try {
                    writePrivateKeyPath(proof.getProofPassword(),path);
                    proof.setProofUsername(proof.getProofName());
                    proof.setProofPassword(path);
                } catch (IOException e) {
                    logger.info("私钥保存失败。");
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
    @Override
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

    @Override
    public List<Proof> findAllProof(int type){
        List<ProofEntity> proofEntityList = ProofDao.selectAllProof();
        List<ProofEntity> proofs = new ArrayList<>();
        for (ProofEntity proofEntity : proofEntityList) {
            //判断凭证类型
            if (proofEntity.getProofScope() == type){
                proofs.add(proofEntity);
            }
        }
        return  BeanMapper.mapList(proofs, Proof.class);
    }

    @Override
    public List<Proof> selectProofList(List<String> idList) {
        List<ProofEntity> proofEntityList = ProofDao.selectAllProofList(idList);
        return BeanMapper.mapList(proofEntityList, Proof.class);
    }

    public static Boolean writePrivateKeyPath(String Data, String filePath) throws IOException {
        BufferedReader bufferedReader ;
        BufferedWriter bufferedWriter;
        File distFile= new File(filePath);
        if (!distFile.getParentFile().exists()){
            boolean mkdirs = distFile.getParentFile().mkdirs();
        }
        bufferedReader = new BufferedReader(new StringReader(Data));
        bufferedWriter = new BufferedWriter(new FileWriter(distFile));
        char[] buf = new char[1024]; //字符缓冲区
        int len;
        while ( (len = bufferedReader.read(buf)) != -1) {
            bufferedWriter.write(buf, 0, len);
        }
        bufferedWriter.flush();
        bufferedReader.close();
        bufferedWriter.close();
        return true;
    }



}

package com.doublekit.pipeline.definition.service;


import com.doublekit.beans.BeanMapper;
import com.doublekit.join.JoinTemplate;
import com.doublekit.pipeline.definition.dao.ConfigureDao;
import com.doublekit.pipeline.definition.entity.ConfigureEntity;
import com.doublekit.pipeline.definition.model.Configure;
import com.doublekit.pipeline.instance.model.History;
import com.doublekit.pipeline.instance.service.git.GiteeService;
import com.doublekit.pipeline.systemSettings.securitySetting.proof.model.Proof;
import com.doublekit.pipeline.systemSettings.securitySetting.proof.service.ProofService;
import com.doublekit.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@Service
@Exporter
public class ConfigureServiceImpl implements ConfigureService {

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    ProofService proofService;

    @Autowired
    GiteeService giteeService;

    @Autowired
    ConfigureDao configureDao;

    //创建
    @Override
    public String createConfigure(Configure configure) {
        ConfigureEntity configureEntity = BeanMapper.map(configure, ConfigureEntity.class);
        return configureDao.createConfigure(configureEntity);
    }

    //删除
    @Override
    public void deleteConfig(String pipelineId) {
        //获取流水线id下的所有配置信息
        List<Configure> configureList = findAllConfigure(pipelineId);
        //判断是否有配置信息
        if (configureList != null){
            for (Configure configure : configureList) {
                //删除配置信息
                configureDao.deleteConfigure(configure.getConfigureId());
            }
        }
    }

    //删除配置信息
    public void deleteConfigure(String configureId) {
        configureDao.deleteConfigure(configureId);
    }

    //更新
    @Override
    public String updateConfigure(Configure configure) {
        if (configure.getConfigureCodeSource() == 3){
            String giteeUrl = giteeService.getGiteeUrl(configure.getConfigureCodeSourceAddress());
            configure.setConfigureCodeName(giteeUrl);
        }
        ConfigureEntity configureEntity = BeanMapper.map(configure, ConfigureEntity.class);
        configureDao.updateConfigure(configureEntity);

        return configureEntity.getConfigureId();

    }

    //查询
    @Override
    public Configure findConfigure(String configureId) {
        ConfigureEntity configureEntity = configureDao.findConfigure(configureId);
        Configure configure = BeanMapper.map(configureEntity, Configure.class);
        joinTemplate.joinQuery(configure);

        return configure;
    }

    //查询所有配置
    @Override
    public List<Configure> findAllConfigure() {
        List<ConfigureEntity> configureEntityList = configureDao.findAllConfigure();
        List<Configure> configureList = BeanMapper.mapList(configureEntityList, Configure.class);
        joinTemplate.joinQuery(configureList);

        return configureList;
    }


    @Override
    public List<Configure> findAllConfigureList(List<String> idList) {
        List<ConfigureEntity> pipelineConfigureList = configureDao.findAllConfigureList(idList);
        return BeanMapper.mapList(pipelineConfigureList, Configure.class);
    }


    /**
     * 根据最近配置信息
     * @param pipelineId 流水线id
     * @return 最近配置信息id
     */
    @Override
    public Configure findTimeId(String pipelineId) {
        List<Configure> configureList = findAllConfigure(pipelineId);
        if (configureList != null){
            //根据时间排序
            configureList.sort(Comparator.comparing(Configure::getConfigureCreateTime));
            //获取最近一次的配置id
            String configureId = configureList.get(configureList.size() - 1).getConfigureId();
           return findConfigure(configureId);
        }
        return null;
    }


    /**
     * 根据流水线id查询配置
     * @param pipelineId 流水线id
     * @return 配置集合
     */
    public List<Configure> findAllConfigure(String pipelineId) {
        List<Configure> configureList = findAllConfigure();
        List<Configure> configures = new ArrayList<>();
        if (configureList == null){
            return null;
        }
        //获取统一id下所有配置
        for (Configure configure : configureList) {
            if (configure.getPipeline().getPipelineId() == null){
                deleteConfigure(configure.getConfigureId());
                continue;
            }
            if (configure.getPipeline().getPipelineId().equals(pipelineId) ){
                configures.add(configure);
            }
        }
        return configures;
    }

    //向历史表添加凭证信息
    public History addHistoryOne(String pipelineId, History history){
        Configure configure = findTimeId(pipelineId);
        Proof proof = proofService.findOneProof(configure.getGitProof().getProofId());
        history.setConfigure(configure);
        history.setProof(proof);

        return history;
    }
    @Override
    //获取克隆凭证信息
    public Proof getProofIdGit(String pipelineId){ return proofService.findOneProof(findTimeId(pipelineId).getGitProof().getProofId()); }

    @Override
    //获取部署凭证信息
    public Proof getProofIdDeploy(String pipelineId){ return proofService.findOneProof(findTimeId(pipelineId).getDeployProof().getProofId()); }


}

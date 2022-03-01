package com.doublekit.pipeline.definition.service;


import com.doublekit.beans.BeanMapper;
import com.doublekit.join.JoinTemplate;
import com.doublekit.pipeline.definition.dao.PipelineConfigureDao;
import com.doublekit.pipeline.definition.entity.PipelineConfigureEntity;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.instance.model.PipelineHistory;
import com.doublekit.pipeline.systemSettings.securitySetting.proof.model.Proof;
import com.doublekit.pipeline.systemSettings.securitySetting.proof.service.ProofService;
import com.doublekit.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@Service
@Exporter
public class PipelineConfigureServiceImpl implements PipelineConfigureService{

    @Autowired
    PipelineConfigureDao pipelineConfigureDao;

    @Autowired
    ProofService proofService;

    @Autowired
    JoinTemplate joinTemplate;

    //创建
    @Override
    public String createPipelineConfigure(PipelineConfigure pipelineConfigure) {

        PipelineConfigureEntity pipelineConfigureEntity = BeanMapper.map(pipelineConfigure, PipelineConfigureEntity.class);

        return pipelineConfigureDao.createPipelineConfigure(pipelineConfigureEntity);
    }

    //删除
    @Override
    public void deletePipelineConfigure(String pipelineId) {

        //获取流水线id下的所有配置信息
        List<PipelineConfigure> pipelineConfigureList = selectAllPipelineConfigure(pipelineId);

        //判断是否有配置信息
        if (pipelineConfigureList != null){

            for (PipelineConfigure pipelineConfigure : pipelineConfigureList) {

                //删除配置信息
                pipelineConfigureDao.deletePipelineConfigure(pipelineConfigure.getConfigureId());

            }
        }
    }

    //更新
    @Override
    public String updatePipelineConfigure(PipelineConfigure pipelineConfigure) {

        String pipelineId = pipelineConfigure.getPipelineId();

        //获取最近一次的配置id
        PipelineConfigure configure = selectTimeId(pipelineId);

        //判断是否有过流水线配置
        if (configure == null){

            return createPipelineConfigure(pipelineConfigure);

        }

        //把配置id添加到最新的配置信息中
        pipelineConfigure.setConfigureId(configure.getConfigureId());

        PipelineConfigureEntity pipelineConfigureEntity = BeanMapper.map(pipelineConfigure, PipelineConfigureEntity.class);

        pipelineConfigureDao.updatePipelineConfigure(pipelineConfigureEntity);

        return pipelineConfigureEntity.getConfigureId();

    }

    //查询
    @Override
    public PipelineConfigure selectPipelineConfigure(String id) {

        PipelineConfigureEntity pipelineConfigureEntity = pipelineConfigureDao.selectPipelineConfigure(id);

        PipelineConfigure pipelineConfigure = BeanMapper.map(pipelineConfigureEntity, PipelineConfigure.class);

        joinTemplate.joinQuery(pipelineConfigure);

        return pipelineConfigure;
    }

    //查询所有
    @Override
    public List<PipelineConfigure> selectAllPipelineConfigure() {

        List<PipelineConfigureEntity> pipelineConfigureEntityList = pipelineConfigureDao.selectAllPipelineConfigure();

        List<PipelineConfigure> pipelineConfigureList = BeanMapper.mapList(pipelineConfigureEntityList, PipelineConfigure.class);

        joinTemplate.joinQuery(pipelineConfigureList);

        return pipelineConfigureList;
    }


    @Override
    public List<PipelineConfigure> selectAllPipelineConfigureList(List<String> idList) {

        List<PipelineConfigureEntity> pipelineConfigureList = pipelineConfigureDao.selectAllPipelineConfigureList(idList);

        return BeanMapper.mapList(pipelineConfigureList, PipelineConfigure.class);
    }


    /**
     * 根据最近配置信息
     * @param pipelineId 流水线id
     * @return 最近配置信息id
     */
    @Override
    public PipelineConfigure selectTimeId(String pipelineId) {

        List<PipelineConfigure> pipelineConfigureList = selectAllPipelineConfigure(pipelineId);

        if (pipelineConfigureList.size() != 0){

            //根据时间排序
            pipelineConfigureList.sort(Comparator.comparing(PipelineConfigure::getConfigureCreateTime));

            //获取最近一次的配置id
            String configureId = pipelineConfigureList.get(pipelineConfigureList.size() - 1).getConfigureId();

           return selectPipelineConfigure(configureId);
        }

        return null;
    }


    /**
     * 根据流水线id查询配置
     * @param pipelineId 流水线id
     * @return 配置集合
     */
    public List<PipelineConfigure> selectAllPipelineConfigure(String pipelineId) {

        List<PipelineConfigure> pipelineConfigureList = selectAllPipelineConfigure();

        List<PipelineConfigure> pipelineConfigures = new ArrayList<>();

        //获取统一id下所有配置
        for (PipelineConfigure pipelineConfigure : pipelineConfigureList) {

            if (pipelineConfigure.getPipelineId().equals(pipelineId) ){

                pipelineConfigures.add(pipelineConfigure);

            }
        }

        return pipelineConfigures;
    }


    public PipelineHistory pipelineHistoryOne(String pipelineId,PipelineHistory pipelineHistory){

        PipelineConfigure pipelineConfigure = selectTimeId(pipelineId);

        Proof proof = proofService.selectProof(pipelineConfigure.getProofId());

        pipelineHistory.setPipelineConfigure(pipelineConfigure);

        pipelineHistory.setProof(proof);

        return pipelineHistory;
    }

    public Proof getProof(String pipelineId){

        return proofService.selectProof(selectTimeId(pipelineId).getProofId());

    }


}

package com.doublekit.pipeline.definition.service;


import com.doublekit.beans.BeanMapper;
import com.doublekit.join.JoinTemplate;
import com.doublekit.pipeline.definition.dao.PipelineConfigureDao;
import com.doublekit.pipeline.definition.entity.PipelineConfigureEntity;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Exporter
public class PipelineConfigureServiceImpl implements PipelineConfigureService{

    @Autowired
    PipelineConfigureDao pipelineConfigureDao;

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
    public void deletePipelineConfigure(String id) {

        pipelineConfigureDao.deletePipelineConfigure(id);

    }

    //更新
    @Override
    public void updatePipelineConfigure(PipelineConfigure pipelineConfigure) {

        PipelineConfigureEntity pipelineConfigureEntity = BeanMapper.map(pipelineConfigure, PipelineConfigureEntity.class);

        pipelineConfigureDao.updatePipelineConfigure(pipelineConfigureEntity);
    }

    //查询配置
    @Override
    public PipelineConfigure updateListPipelineConfig() {

        List<PipelineConfigure> pipelineConfigureList = selectAllPipelineConfigure();

        //判断是否更改过配置
        List<String> createTime = new ArrayList<>();

        if (pipelineConfigureList.size() != 0){
            for (int i = pipelineConfigureList.size() - 1; i >= 0; i--) {

                String configureCreateTime = pipelineConfigureList.get(i).getConfigureCreateTime();

                createTime.add(configureCreateTime);

            }
            //排序
            Collections.sort(createTime);

            String id = selectTimeId(createTime.get(createTime.size() - 1));

            return selectPipelineConfigure(id);

        }

        return null;
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

        return BeanMapper.mapList(pipelineConfigureEntityList,PipelineConfigure.class);
    }



    //根据时间查询id
    public String selectTimeId(String time) {

        List<PipelineConfigure> pipelineConfigureList = selectAllPipelineConfigure();

        for (PipelineConfigure pipelineConfigure : pipelineConfigureList) {

            if (pipelineConfigure.getConfigureCreateTime().equals(time)){

                return pipelineConfigure.getConfigureId();
            }
        }
        return null;
    }
}

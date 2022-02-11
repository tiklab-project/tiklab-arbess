package com.doublekit.pipeline.definition.service;


import com.doublekit.beans.BeanMapper;
import com.doublekit.join.JoinTemplate;
import com.doublekit.pipeline.definition.dao.PipelineConfigureDao;
import com.doublekit.pipeline.definition.entity.PipelineConfigureEntity;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Comparator;
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

        String selectTimeId = selectTimeId();
        if (selectTimeId == null){
            return null;
        }

        return selectPipelineConfigure(selectTimeId);
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


    //根据时间查询id
    public String selectTimeId() {

        List<PipelineConfigure> pipelineConfigureList = selectAllPipelineConfigure();

        pipelineConfigureList.sort(new Comparator<PipelineConfigure>() {
            @Override
            public int compare(PipelineConfigure pipelineConfigure1, PipelineConfigure pipelineConfigure2) {

                return pipelineConfigure1.getConfigureCreateTime().compareTo(pipelineConfigure2.getConfigureCreateTime());
            }
        });

        String configureId = pipelineConfigureList.get(pipelineConfigureList.size() - 1).getConfigureId();

        // //获取配置中的时间
        // List<String> createTime = new ArrayList<>();
        //
        // if (pipelineConfigureList.size() != 0) {
        //     for (int i = pipelineConfigureList.size() - 1; i >= 0; i--) {
        //
        //         String configureCreateTime = pipelineConfigureList.get(i).getConfigureCreateTime();
        //
        //         createTime.add(configureCreateTime);
        //     }
        //     //对时间进行排序获得最近的修改
        //     Collections.sort(createTime);
        //
        //     String time = createTime.get(createTime.size()-1);
        //
        //     //获取对应时间的id
        //     for (PipelineConfigure pipelineConfigure : pipelineConfigureList) {
        //
        //         if (pipelineConfigure.getConfigureCreateTime().equals(time)){
        //
        //             return pipelineConfigure.getConfigureId();
        //         }
        //     }
        // }

        return configureId;
    }
}

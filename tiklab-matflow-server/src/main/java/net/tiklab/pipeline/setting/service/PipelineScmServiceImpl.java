package net.tiklab.pipeline.setting.service;


import net.tiklab.beans.BeanMapper;
import net.tiklab.pipeline.setting.dao.PipelineScmDao;
import net.tiklab.pipeline.setting.entity.PipelineScmEntity;
import net.tiklab.pipeline.setting.model.PipelineScm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PipelineScmServiceImpl implements net.tiklab.pipeline.setting.service.PipelineScmService {


    @Autowired
    PipelineScmDao pipelineScmDao;


    @Override
    public String createPipelineScm(PipelineScm pipelineScm) {
        PipelineScmEntity pipelineScmEntity = BeanMapper.map(pipelineScm, PipelineScmEntity.class);
        return pipelineScmDao.createPipelineScm(pipelineScmEntity);
    }

    //删除
    @Override
    public void deletePipelineScm(String pathId) {
        pipelineScmDao.deletePipelineScm(pathId);
    }

    //更新
    @Override
    public void updatePipelineScm(PipelineScm pipelineScm) {
        if (pipelineScm.getScmId()==null || findOnePipelineScm(pipelineScm.getScmId())==null){
            createPipelineScm(pipelineScm);
            return;
        }
        pipelineScmDao.updatePipelineScm(BeanMapper.map(pipelineScm, PipelineScmEntity.class));
    }

    //查询
    @Override
    public PipelineScm findOnePipelineScm(String pathId) {
        PipelineScmEntity pipelineScmEntity = pipelineScmDao.findOnePipelineScm(pathId);
        return BeanMapper.map(pipelineScmEntity, PipelineScm.class);
    }

    //查询所有
    @Override
    public List<PipelineScm> findAllPipelineScm() {
        List<PipelineScmEntity> pipelineScmEntityList = pipelineScmDao.selectAllPipelineScm();
        return BeanMapper.mapList(pipelineScmEntityList, PipelineScm.class);
    }

    /**
     * 获取配置
     * @param type 类型
     * @return 配置信息
     */
    @Override
    public PipelineScm findOnePipelineScm(int type) {
        List<PipelineScm> allPipelineScm = findAllPipelineScm();
        if (allPipelineScm == null){
            return null;
        }

        for (PipelineScm pipelineScm : allPipelineScm) {
            if (type != pipelineScm.getScmType()){
                continue;
            }
            return pipelineScm;
        }
        return null;
    }
    
    @Override
    public List<PipelineScm> selectPipelineScmList(List<String> idList) {
        List<PipelineScmEntity> pipelineScmEntityList = pipelineScmDao.selectAllPipelineScmList(idList);
        return BeanMapper.mapList(pipelineScmEntityList, PipelineScm.class);
    }

    
}

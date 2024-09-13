package io.thoughtware.arbess.setting.service;


import io.thoughtware.toolkit.beans.BeanMapper;
import io.thoughtware.arbess.setting.dao.ScmDao;
import io.thoughtware.arbess.setting.entity.ScmEntity;
import io.thoughtware.arbess.setting.model.Scm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ScmServiceImpl implements ScmService {


    @Autowired
    ScmDao scmDao;


    @Override
    public String createPipelineScm(Scm scm) {
        ScmEntity scmEntity = BeanMapper.map(scm, ScmEntity.class);
        return scmDao.createPipelineScm(scmEntity);
    }

    //删除
    @Override
    public void deletePipelineScm(String pathId) {
        scmDao.deletePipelineScm(pathId);
    }

    //更新
    @Override
    public void updatePipelineScm(Scm scm) {
        if (scm.getScmId()==null || findOnePipelineScm(scm.getScmId())==null){
            createPipelineScm(scm);
            return;
        }
        scmDao.updatePipelineScm(BeanMapper.map(scm, ScmEntity.class));
    }

    //查询
    @Override
    public Scm findOnePipelineScm(String pathId) {
        ScmEntity scmEntity = scmDao.findOnePipelineScm(pathId);
        return BeanMapper.map(scmEntity, Scm.class);
    }

    //查询所有
    @Override
    public List<Scm> findAllPipelineScm() {
        List<ScmEntity> scmEntityList = scmDao.selectAllPipelineScm();
        scmEntityList.sort(Comparator.comparing(ScmEntity::getCreateTime));
        return BeanMapper.mapList(scmEntityList, Scm.class);
    }

    /**
     * 获取配置
     * @param type 类型
     * @return 配置信息
     */
    @Override
    public Scm findOnePipelineScm(int type) {
        List<Scm> allScm = findAllPipelineScm();
        if (allScm == null){
            return null;
        }

        for (Scm scm : allScm) {
            if (type == 2 || type == 3){
                type = 1;
            }
            if (type != scm.getScmType()){
                continue;
            }
            return scm;
        }
        return null;
    }
    
    @Override
    public List<Scm> selectPipelineScmList(List<String> idList) {
        List<ScmEntity> scmEntityList = scmDao.selectAllPipelineScmList(idList);
        return BeanMapper.mapList(scmEntityList, Scm.class);
    }

    @Override
    public Integer findScmNumber() {
        return scmDao.findScmNumber();
    }
    
}

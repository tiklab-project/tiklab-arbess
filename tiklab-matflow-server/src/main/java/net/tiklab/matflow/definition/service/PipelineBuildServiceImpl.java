package net.tiklab.matflow.definition.service;


import net.tiklab.beans.BeanMapper;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.definition.dao.PipelineBuildDao;
import net.tiklab.matflow.definition.entity.PipelineBuildEntity;
import net.tiklab.matflow.definition.model.PipelineBuild;
import net.tiklab.matflow.orther.service.PipelineActivityService;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class PipelineBuildServiceImpl implements PipelineBuildService {

    @Autowired
    PipelineBuildDao pipelineBuildDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    PipelineActivityService activityService;

    //创建
    @Override
    public String createBuild(PipelineBuild pipelineBuild) {
        return pipelineBuildDao.createBuild(BeanMapper.map(pipelineBuild, PipelineBuildEntity.class));
    }

    //删除
    @Override
    public void deleteBuild(String buildId) {
        pipelineBuildDao.deleteBuild(buildId);
    }

    //修改
    @Override
    public void updateBuild(PipelineBuild pipelineBuild) {
        pipelineBuildDao.updateBuild(BeanMapper.map(pipelineBuild, PipelineBuildEntity.class));
    }

    //查询单个
    @Override
    public PipelineBuild findOneBuild(String buildId) {
        return BeanMapper.map(pipelineBuildDao.findOneBuild(buildId), PipelineBuild.class);
    }

    //查询所有
    @Override
    public List<PipelineBuild> findAllBuild() {
        return BeanMapper.mapList(pipelineBuildDao.findAllBuild(), PipelineBuild.class);
    }

    @Override
    public List<PipelineBuild> findAllBuildList(List<String> idList) {
        return BeanMapper.mapList(pipelineBuildDao.findAllCodeList(idList), PipelineBuild.class);
    }
}

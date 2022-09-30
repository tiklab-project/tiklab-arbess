package net.tiklab.pipeline.definition.service;


import net.tiklab.beans.BeanMapper;
import net.tiklab.join.JoinTemplate;
import net.tiklab.pipeline.definition.model.PipelineCode;
import net.tiklab.pipeline.definition.service.PipelineBuildService;
import net.tiklab.pipeline.definition.dao.PipelineBuildDao;
import net.tiklab.pipeline.definition.entity.PipelineBuildEntity;
import net.tiklab.pipeline.definition.model.PipelineBuild;
import net.tiklab.rpc.annotation.Exporter;
import org.apache.commons.codec.language.Nysiis;
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

    //根据流水线id查询配置
    @Override
    public PipelineBuild findBuild(String pipelineId) {
        List<PipelineBuild> allBuild = findAllBuild();
        if (allBuild != null){
            for (PipelineBuild pipelineBuild : allBuild) {
                if (pipelineBuild.getPipeline() == null){
                    continue;
                }
                if (pipelineBuild.getPipeline().getPipelineId().equals(pipelineId)){
                    joinTemplate.joinQuery(pipelineBuild);
                    return pipelineBuild;
                }
            }
        }
        return null;
    }

    @Override
    public void updateBuild(PipelineBuild pipelineBuild, String pipelineId) {
        PipelineBuild build = findBuild(pipelineId);
        if (build == null){
            if (pipelineBuild.getSort() != 0){
                createBuild(pipelineBuild);
            }
        }else {
            if (pipelineBuild.getSort() == 0){
                deleteBuild(build.getBuildId());
            }else {
                pipelineBuild.setBuildId(build.getBuildId());
                updateBuild(pipelineBuild);
            }
        }
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

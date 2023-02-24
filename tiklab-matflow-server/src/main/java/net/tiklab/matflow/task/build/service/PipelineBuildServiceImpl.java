package net.tiklab.matflow.task.build.service;


import net.tiklab.beans.BeanMapper;
import net.tiklab.matflow.task.build.dao.PipelineBuildDao;
import net.tiklab.matflow.task.build.entity.PipelineBuildEntity;
import net.tiklab.matflow.task.build.model.PipelineBuild;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class PipelineBuildServiceImpl implements PipelineBuildService {

    @Autowired
    PipelineBuildDao pipelineBuildDao;

    //创建
    @Override
    public String createBuild(PipelineBuild pipelineBuild) {
        return pipelineBuildDao.createBuild(BeanMapper.map(pipelineBuild, PipelineBuildEntity.class));
    }


    /**
     * 根据配置id删除任务
     * @param configId 配置id
     */
    @Override
    public void deleteBuildConfig(String configId){
        PipelineBuild oneBuildConfig = findOneBuildConfig(configId);
        deleteBuild(oneBuildConfig.getBuildId());
    }

    /**
     * 根据配置id查询任务
     * @param configId 配置id
     * @return 任务
     */
    @Override
    public PipelineBuild findOneBuildConfig(String configId){
        List<PipelineBuild> allBuild = findAllBuild();
        if (allBuild == null){
            return null;
        }
        for (PipelineBuild pipelineBuild : allBuild) {
            if (pipelineBuild.getConfigId().equals(configId)){
                return pipelineBuild;
            }
        }
        return null;
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

package net.tiklab.matflow.task.build.service;


import net.tiklab.beans.BeanMapper;
import net.tiklab.matflow.task.build.dao.TaskBuildDao;
import net.tiklab.matflow.task.build.entity.TaskBuildEntity;
import net.tiklab.matflow.task.build.model.TaskBuild;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class TaskBuildServiceImpl implements TaskBuildService {

    @Autowired
    TaskBuildDao taskBuildDao;

    //创建
    @Override
    public String createBuild(TaskBuild taskBuild) {
        return taskBuildDao.createBuild(BeanMapper.map(taskBuild, TaskBuildEntity.class));
    }


    /**
     * 根据配置id删除任务
     * @param configId 配置id
     */
    @Override
    public void deleteBuildConfig(String configId){
        TaskBuild oneBuildConfig = findOneBuildConfig(configId);
        deleteBuild(oneBuildConfig.getBuildId());
    }

    /**
     * 根据配置id查询任务
     * @param configId 配置id
     * @return 任务
     */
    @Override
    public TaskBuild findOneBuildConfig(String configId){
        List<TaskBuild> allBuild = findAllBuild();
        if (allBuild == null){
            return null;
        }
        for (TaskBuild taskBuild : allBuild) {
            if (taskBuild.getConfigId().equals(configId)){
                return taskBuild;
            }
        }
        return null;
    }
    

    //删除
    @Override
    public void deleteBuild(String buildId) {
        taskBuildDao.deleteBuild(buildId);
    }

    //修改
    @Override
    public void updateBuild(TaskBuild taskBuild) {
        taskBuildDao.updateBuild(BeanMapper.map(taskBuild, TaskBuildEntity.class));
    }

    //查询单个
    @Override
    public TaskBuild findOneBuild(String buildId) {
        return BeanMapper.map(taskBuildDao.findOneBuild(buildId), TaskBuild.class);
    }

    //查询所有
    @Override
    public List<TaskBuild> findAllBuild() {
        return BeanMapper.mapList(taskBuildDao.findAllBuild(), TaskBuild.class);
    }

    @Override
    public List<TaskBuild> findAllBuildList(List<String> idList) {
        return BeanMapper.mapList(taskBuildDao.findAllCodeList(idList), TaskBuild.class);
    }
}

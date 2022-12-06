package net.tiklab.matflow.definition.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.matflow.definition.dao.PipelineStagesDao;
import net.tiklab.matflow.definition.entity.PipelineStagesEntity;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.definition.model.PipelineCourseConfig;
import net.tiklab.matflow.definition.model.PipelineStages;
import net.tiklab.matflow.orther.service.PipelineUntil;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Exporter
public class PipelineStagesServerImpl implements PipelineStagesServer {


    @Autowired
    PipelineStagesDao stagesDao;

    @Autowired
    PipelineCourseConfigService courseConfigService;


    /**
     * 创建阶段及关联任务
     * @param stages 阶段信息
     * @return 阶段id
     */
    @Override
    public String createStagesConfig(PipelineStages stages){
        int taskStage = stages.getTaskStage();
        Pipeline pipeline = stages.getPipeline();
        String pipelineId = pipeline.getPipelineId();
        String id ;
        int taskSort = stages.getTaskSort();

        //判断是新阶段还是添加任务
        if (taskStage == 0){
            int i = insertStagesConfig(pipelineId, taskSort);

            stages.setTaskStage(i);
            stages.setTaskSort(1);
            id = createStages(stages);
        }else {
            String stagesId = stages.getStagesId();
            //判断是并行还是串行
            if (PipelineUntil.isNoNull(stagesId)){ //串行
                id = stages.getStagesId();
            }else {//并行
                List<PipelineStages> stagesConfig = findStagesConfig(pipelineId, taskStage);
                stages.setTaskSort(stagesConfig.size()+1);
                id = createStages(stages);
            }
        }

        PipelineCourseConfig courseConfig = new PipelineCourseConfig();

        courseConfig.setStagesId(id);
        courseConfig.setTaskType(stages.getTaskType());
        courseConfig.setPipeline(pipeline);
        courseConfig.setTaskSort(taskSort);
        return courseConfigService.createConfig(courseConfig);
    }

    /**
     * 根据流水线id查询所有阶段及任务
     * @param pipelineId 流水线id
     * @return 配置
     */
    @Override
    public List<PipelineStages> findAllStagesConfig(String pipelineId){
        List<PipelineStages> allStages = findAllStages();
        if (allStages == null || allStages.size() == 0){
            return null;
        }
        List<PipelineStages> list = new ArrayList<>();
        for (PipelineStages stages : allStages) {
            String id = stages.getPipeline().getPipelineId();
            if (!id.equals(pipelineId)){
                continue;
            }
            List<Object> allStagesConfig = courseConfigService.findAllStagesConfig(stages.getStagesId());
            stages.setTaskValues(allStagesConfig);
            list.add(stages);
        }
        list.sort(Comparator.comparing(PipelineStages::getTaskStage));
        return list;
    }

    /**
     * 更新阶段及任务配置
     * @param pipelineId 流水线id
     * @param taskSort 顺序
     */
    public int insertStagesConfig(String pipelineId,int taskSort){
        List<PipelineStages> allStagesConfig = findAllStagesConfig(pipelineId);
        if (allStagesConfig == null || allStagesConfig.size() ==0){
            return taskSort;
        }
        for (PipelineStages pipelineStages : allStagesConfig) {
            int sort = pipelineStages.getTaskStage();
            if (sort >= taskSort){
                pipelineStages.setTaskStage(pipelineStages.getTaskStage()+1);
                updateStages(pipelineStages);
            }
        }
        return taskSort;
    }

    /**
     * 删除阶段任务
     * @param configId 配置id
     */
    @Override
    public void deleteStagesConfig(String configId){
        PipelineCourseConfig courseConfig = courseConfigService.findOneCourseConfig(configId);
        String stagesId = courseConfig.getStagesId();
        courseConfigService.deleteConfig(configId);
        List<PipelineCourseConfig> allStagesCourseConfig = courseConfigService.findAllStagesCourseConfig(stagesId);
        if (allStagesCourseConfig == null || allStagesCourseConfig.size() == 0){
            PipelineStages oneStages = findOneStages(stagesId);
            deleteStages(stagesId);
            int stage = oneStages.getTaskStage();
            String pipelineId = oneStages.getPipeline().getPipelineId();
            List<PipelineStages> allStagesConfig = findAllStagesConfig(pipelineId);
            if (allStagesConfig == null || allStagesConfig.size() ==0){
                return;
            }
            for (PipelineStages pipelineStages : allStagesConfig) {
                int sort = pipelineStages.getTaskStage();
                if (sort >= stage){
                    pipelineStages.setTaskStage(pipelineStages.getTaskStage()-1);
                    updateStages(pipelineStages);
                }
            }
        }
    }

    /**
     * 查询流水线是否存在此阶段
     * @param pipelineId 流水线id
     * @param stages 阶段
     * @return 配置
     */
    public List<PipelineStages> findStagesConfig(String pipelineId ,int stages){
        List<PipelineStages> allStages = findAllStages();
        if (allStages == null || allStages.size() == 0){
            return null;
        }
        List<PipelineStages> list = new ArrayList<>();
        for (PipelineStages pipelineStages : allStages) {
            String id = pipelineStages.getPipeline().getPipelineId();
            if (id.equals(pipelineId) && pipelineStages.getTaskStage() == stages){
                list.add(pipelineStages);
            }
        }
        return list;
    }

    /**
     * 创建阶段
     * @param stages 阶段信息
     * @return 阶段id
     */
    @Override
    public String createStages(PipelineStages stages) {
        PipelineStagesEntity stagesEntity = BeanMapper.map(stages, PipelineStagesEntity.class);
        return stagesDao.createStages(stagesEntity);
    }

    /**
     * 更新阶段
     * @param stages 阶段信息
     */
    @Override
    public void updateStages(PipelineStages stages) {
        PipelineStagesEntity stagesEntity = BeanMapper.map(stages, PipelineStagesEntity.class);
        stagesDao.updateStages(stagesEntity);
    }

    /**
     * 删除阶段
     * @param stageId 阶段id
     */
    @Override
    public void deleteStages(String stageId) {
        stagesDao.deleteStages(stageId);
    }

    /**
     * 查询单个阶段
     * @param stageId 阶段id
     * @return 阶段信息
     */
    @Override
    public PipelineStages findOneStages(String stageId) {
        PipelineStagesEntity oneStages = stagesDao.findOneStages(stageId);
        return BeanMapper.map(oneStages, PipelineStages.class);
    }

    /**
     * 查询所有阶段
     * @return 阶段信息集合
     */
    @Override
    public List<PipelineStages> findAllStages() {
        List<PipelineStagesEntity> allStagesList = stagesDao.findAllStages();
        return BeanMapper.mapList(allStagesList, PipelineStages.class);
    }

    @Override
    public List<PipelineStages> findAllStagesList(List<String> idList) {
        List<PipelineStagesEntity> allStagesList = stagesDao.findAllStagesList(idList);
        return BeanMapper.mapList(allStagesList, PipelineStages.class);
    }
}

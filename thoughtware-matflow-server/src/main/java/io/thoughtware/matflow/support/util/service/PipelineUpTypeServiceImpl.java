package io.thoughtware.matflow.support.util.service;

import io.thoughtware.matflow.pipeline.definition.dao.PipelineDao;
import io.thoughtware.matflow.pipeline.definition.entity.PipelineEntity;
import io.thoughtware.matflow.pipeline.definition.model.Pipeline;
import io.thoughtware.matflow.pipeline.definition.model.PipelineQuery;
import io.thoughtware.matflow.pipeline.definition.service.PipelineService;
import io.thoughtware.matflow.stages.model.Stage;
import io.thoughtware.matflow.stages.service.StageService;
import io.thoughtware.matflow.support.util.util.PipelineFinal;
import io.thoughtware.matflow.task.task.model.Tasks;
import io.thoughtware.matflow.task.task.service.TasksService;
import io.thoughtware.toolkit.beans.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class PipelineUpTypeServiceImpl implements PipelineUpTypeService {

    @Autowired
    PipelineService pipelineService;

    @Autowired
    PipelineDao pipelineDao;

    @Autowired
    StageService stageService;

    @Autowired
    TasksService tasksService;


    @Override
    public void updatePipelineTypeList(){
        PipelineQuery pipelineQuery = new PipelineQuery();
        pipelineQuery.setPipelineType(1);
        List<Pipeline> pipelineList = pipelineService.findPipelineList(pipelineQuery);
        for (Pipeline pipeline : pipelineList) {
            String pipelineId = pipeline.getId();
            updatePipelineType(pipelineId);
        }
    }


    @Override
    public void updatePipelineType(String pipelineId) {

        Pipeline pipeline = pipelineService.findOnePipeline(pipelineId);
        if (pipeline.getType() == 2){
            throw new RuntimeException("不支持的流水线类型!");
        }
        List<Tasks> tasks = tasksService.finAllPipelineTask(pipelineId);
        for (int i = 0; i < tasks.size(); i++) {
            Tasks task = tasks.get(i);
            Stage parentStage = new Stage();
            parentStage.setPipelineId(pipelineId);
            parentStage.setStageName("阶段-" + (i + 1));
            parentStage.setStageSort(i + 1);
            if (i == 0) {
                String taskType = tasksService.findTaskType(task.getTaskType());
                if (taskType.equals(PipelineFinal.TASK_TYPE_CODE)){
                    parentStage.setCode(true);
                }
            }
            String parentStagesId = stageService.createStages(parentStage);

            Stage stage = new Stage();
            stage.setParentId(parentStagesId);
            stage.setStageName("并行阶段-" + (i + 1) + "-1");
            stage.setStageSort(i + 1);
            String stagesId = stageService.createStages(stage);

            task.setPipelineId(" ");
            task.setStageId(stagesId);
            tasksService.updateTasks(task);
        }

        pipeline.setType(2);
        PipelineEntity pipelineEntity = BeanMapper.map(pipeline, PipelineEntity.class);
        pipelineDao.updatePipeline(pipelineEntity);
    }




}

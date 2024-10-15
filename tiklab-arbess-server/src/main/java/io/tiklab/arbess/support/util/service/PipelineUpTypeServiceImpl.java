package io.tiklab.arbess.support.util.service;

import io.tiklab.arbess.pipeline.definition.dao.PipelineDao;
import io.tiklab.arbess.pipeline.definition.entity.PipelineEntity;
import io.tiklab.arbess.pipeline.definition.model.Pipeline;
import io.tiklab.arbess.pipeline.definition.model.PipelineQuery;
import io.tiklab.arbess.pipeline.definition.service.PipelineService;
import io.tiklab.arbess.pipeline.instance.service.PipelineInstanceService;
import io.tiklab.arbess.stages.model.Stage;
import io.tiklab.arbess.stages.service.StageService;
import io.tiklab.arbess.support.util.util.PipelineFileUtil;
import io.tiklab.arbess.support.util.util.PipelineFinal;
import io.tiklab.arbess.task.task.model.Tasks;
import io.tiklab.arbess.task.task.service.TasksService;
import io.tiklab.toolkit.beans.BeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


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

    @Autowired
    PipelineInstanceService instanceService;

    @Autowired
    PipelineUtilService utilService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public final ExecutorService executorService = Executors.newCachedThreadPool();


    @Override
    public void updatePipelineTypeList(){
        PipelineQuery pipelineQuery = new PipelineQuery();
        pipelineQuery.setPipelineType(1);
        List<Pipeline> pipelineList = pipelineService.findPipelineList(pipelineQuery);
        for (Pipeline pipeline : pipelineList) {
            String pipelineId = pipeline.getId();
            logger.warn(" 更新流水线：{}，{}：的类型",pipelineId,pipeline.getName());
            executorService.submit(() -> updatePipelineType(pipelineId));
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

        //删除对应的历史
        instanceService.deleteAllInstance(pipelineId);

        //删除对应源码文件
        String fileAddress = utilService.findPipelineDefaultAddress(pipelineId,1);
        PipelineFileUtil.deleteFile(new File(fileAddress));

        //删除对应日志
        String logAddress = utilService.findPipelineDefaultAddress(pipelineId,2);
        PipelineFileUtil.deleteFile(new File(logAddress));
    }




}

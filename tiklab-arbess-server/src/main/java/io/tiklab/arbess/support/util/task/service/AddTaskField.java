package io.tiklab.arbess.support.util.task.service;

import io.tiklab.arbess.pipeline.definition.model.Pipeline;
import io.tiklab.arbess.pipeline.definition.model.PipelineQuery;
import io.tiklab.arbess.pipeline.definition.service.PipelineService;
import io.tiklab.arbess.task.task.model.Tasks;
import io.tiklab.arbess.task.task.service.TasksService;
import io.tiklab.dsm.support.DsmProcessTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddTaskField implements DsmProcessTask {


    @Autowired
    TasksService tasksService;

    @Autowired
    PipelineService pipelineService;


    @Override
    public void execute() {
        run();
    }

    private void run(){
        List<Pipeline> allPipeline = pipelineService.findPipelineList(new PipelineQuery());
        allPipeline.forEach(pipeline -> {
            List<Tasks> taskList = tasksService.findTaskListByDetails(pipeline.getId());
            taskList.forEach(tasks -> {
                Boolean aBoolean = tasksService.validTaskMastField(tasks.getTaskType(), tasks.getTask());
                tasks.setFieldStatus(1);
                if (aBoolean){
                    tasks.setFieldStatus(2);
                }
                tasksService.updateTasks(tasks);
            });

        });
    }


}


















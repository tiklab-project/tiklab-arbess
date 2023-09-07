package io.tiklab.matflow.stages.service;

import io.tiklab.matflow.stages.dao.StageDao;
import io.tiklab.matflow.stages.entity.StageEntity;
import io.tiklab.matflow.stages.model.Stage;
import io.tiklab.beans.BeanMapper;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.matflow.support.util.PipelineUtil;
import io.tiklab.matflow.task.task.model.Tasks;
import io.tiklab.matflow.task.task.service.TasksService;
import io.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 流水线阶段服务
 */
@Service
@Exporter
public class StageServiceImpl implements StageService {

    @Autowired
    StageDao stageDao;

    @Autowired
    TasksService tasksService;

    public String createStagesOrTask(Stage stage){
        String stagesId = stage.getStageId();
        String pipelineId = stage.getPipelineId();

        String taskType = stage.getTaskType();
        int stageSort = stage.getStageSort();

        stage.setCreateTime(PipelineUtil.date(1));

        Tasks tasks = new Tasks();
        tasks.setTaskType(taskType);
        tasks.setTaskSort(1);
        boolean b = taskType.equals("git")
                || taskType.equals("gitlab")
                || taskType.equals("gitee")
                || taskType.equals("github")
                || taskType.equals("xcode");

        //是否为源码
        if (b){
            //判断是否存在代码源
            findTypeTasks(pipelineId);

            //创建根节点
            int initStage = initStage(pipelineId,1);
            stage.setCode(true);
            stage.setStageSort(1);
            stage.setPipelineId(pipelineId);
            stage.setStageName("阶段-"+initStage);
            String id = createStages(stage);

            //从节点
            Stage stages = new Stage();
            stages.setStageSort(1);
            stages.setParentId(id);
            stages.setStageName("源码");
            stagesId = createStages(stages);
            tasks.setStageId(stagesId);

            return tasksService.createTasksOrTask(tasks);
        }

        //新任务
        if (!PipelineUtil.isNoNull(stagesId) && stageSort != 0){
            //创建主节点
            int initStage = initStage(pipelineId, stageSort);
            stage.setStageSort(initStage);
            stage.setPipelineId(pipelineId);
            stage.setStageName("阶段-" + initStage);
            String id = createStages(stage);

            //创建从节点
            Stage stages = new Stage();
            stages.setParentId(id);
            stages.setStageSort(1);
            stages.setStageName("并行阶段-"+initStage+"-" + 1);
            stagesId = createStages(stages);
            tasks.setStageId(stagesId);
            return tasksService.createTasksOrTask(tasks);
        }

        //并行任务
        if (PipelineUtil.isNoNull(stagesId) && stageSort == 0){
            List<Stage> otherStage = findOtherStage(stagesId);
            Stage oneStages = findOneStages(stagesId);
            Stage stages = new Stage();
            stages.setParentId(stagesId);
            stages.setStageSort(otherStage.size() + 1);
            stages.setStageName("并行阶段-"+oneStages.getStageSort()+"-"+(otherStage.size()+1));
            stagesId = createStages(stages);
            tasks.setStageId(stagesId);
            return tasksService.createTasksOrTask(tasks);
        }

        //串行任务
        int sort = stage.getTaskSort();
        if (sort != 0){
            tasks.setTaskSort(sort);
            tasks.setStageId(stagesId);
            return tasksService.createTasksOrTask(tasks);
        }
        throw new ApplicationException(50001,"未知的操作类型");
    }

    @Override
    public void createStageTemplate(String pipelineId,String[] template){
        int j = 1;
        for (String i : template) {
            Stage stage = new Stage();
            stage.setPipelineId(pipelineId);
            stage.setStageSort(j);
            stage.setTaskType(i);
            createStagesOrTask(stage);
            j++;
        }
    }

    @Override
    public void cloneStage(String pipelineId,String clonePipelineId){
        List<Stage> allMainStage = findAllMainStage(pipelineId);
        for (Stage stage : allMainStage) {
            String stageId = stage.getStageId();
            StageEntity cloneStageEntity = BeanMapper.map(stage, StageEntity.class);
            cloneStageEntity.setPipelineId(clonePipelineId);
            String cloneMainStagesId = stageDao.createStages(cloneStageEntity);
            List<Stage> otherStageList = findOtherStage(stageId);
            for (Stage otherStage : otherStageList) {
                String otherStageId = otherStage.getStageId();
                otherStage.setParentId(cloneMainStagesId);
                StageEntity otherStageEntity = BeanMapper.map(otherStage, StageEntity.class);
                String cloneOtherStagesId = stageDao.createStages(otherStageEntity);
                tasksService.cloneTasks(otherStageId,cloneOtherStagesId,"stageId");
            }
        }
    }

    /**
     * 判断任务是否存在代码源
     * @param pipelineId 流水线id
     * @throws ApplicationException 代码源已存在
     */
    private void findTypeTasks(String pipelineId) throws ApplicationException {
        List<Stage> allStage = findAllMainStage(pipelineId);
        if ( allStage.size() == 0){
            return;
        }
        for (Stage stage : allStage) {
            if (stage.isCode()){
                throw new ApplicationException(50001,"代码源已存在，无法再次创建。");
            }
        }
    }

    @Override
    public List<Stage> findAllStagesOrTask(String pipelineId){
        //获取流水线主节点
        List<Stage> stageMainStage = findAllMainStage(pipelineId);
        if (stageMainStage.size() == 0){
            return null;
        }
        List<Stage> list = new ArrayList<>();
        for (Stage stage : stageMainStage) {
            String stagesId = stage.getStageId();
            //获取从节点
            List<Stage> allStageStage = findOtherStage(stagesId);
            stage.setStageList(allStageStage);
            list.add(stage);
        }
        list.sort(Comparator.comparing(Stage::getStageSort));
        return list;
    }

    @Override
    public void updateStageName(Stage stage){
        String stageName = stage.getStageName();
        String stageId = stage.getStageId();
        Stage stages = findOneStages(stageId);
        stages.setStageName(stageName);
        updateStages(stages);
    }

    @Override
    public List<Stage> findAllMainStage(String pipelineId){
        List<StageEntity> pipelineStage = stageDao.findPipelineStage(pipelineId);
        return BeanMapper.mapList(pipelineStage,Stage.class);
    }

    @Override
    public List<Stage> findOtherStage(String stagesId){
        List<StageEntity> otherStage = stageDao.findOtherStage(stagesId);
        List<Stage> stages = BeanMapper.mapList(otherStage, Stage.class);
        List<Stage> list = new ArrayList<>();
        for (Stage stage : stages) {
            //获取阶段配置及任务
            String otherId = stage.getStageId();
            List<Tasks> allStagesConfig =
                    tasksService.finAllStageTaskOrTask(otherId);
            stage.setTaskValues(allStagesConfig);
            list.add(stage);
        }
        return list;
    }

    /**
     * 更新主节点阶段顺序
     * @param pipelineId 流水线id
     * @param taskSort 顺序
     * @return 顺序
     */
    private Integer initStage(String pipelineId,int taskSort){
        List<Stage> allMainStage = findAllMainStage(pipelineId);
        if (allMainStage.size() == 0){
            return 1;
        }
        for (Stage stages : allMainStage) {
            int stage = stages.getStageSort();
            if (stage < taskSort){
                continue;
            }
            stages.setStageSort(stage+1);
            updateStages(stages);
        }
        return taskSort;
    }

    /**
     * 查询流水线所有阶段
     * @param pipelineId 流水线id
     * @return 阶段集合
     */
    private List<Stage> findAllPipelineStages(String pipelineId){
        List<Stage> allStages = findAllStages();
        if (allStages == null || allStages.size() == 0){
            return Collections.emptyList();
        }
        List<Stage> list = new ArrayList<>();
        for (Stage stage : allStages) {
            String id = stage.getPipelineId();
            if ( id == null || ! id.equals(pipelineId)){
                continue;
            }
            list.add(stage);
        }
        list.sort(Comparator.comparing(Stage::getStageSort));
        return list;
    }

    @Override
    public void deleteStagesOrTask(String taskId){
        //获取需要删除的任务信息
        Tasks taskTasks = tasksService.findOneTasks(taskId);
        //删除任务
        tasksService.deleteTasksOrTask(taskId);

        //阶段信息
        String taskStagesId = taskTasks.getStageId();
        Stage stages = findOneStages(taskStagesId);

        List<Tasks> tasks = tasksService.finAllStageTask(taskStagesId);
        //该阶段不存在其他任务
        if (tasks == null || tasks.size() == 0){
            deleteStages(taskStagesId);
            //主阶段id
            String mainStageId = stages.getParentId();
            List<Stage> otherStage = findOtherStage(mainStageId);
            //判断是否存在从节点，不存在删除主节点
            if (otherStage == null || otherStage.size() == 0){
                Stage mainStage = findOneStages(mainStageId);
                deleteStages(mainStageId);
                String pipelineId = mainStage.getPipelineId();
                //更新其他主节点顺序
                List<Stage> allMainStage = findAllMainStage(pipelineId);
                if (allMainStage == null || allMainStage.size() == 0){
                    return;
                }
                for (Stage stage : allMainStage) {
                    int sort = mainStage.getStageSort();
                    int stageSort = stage.getStageSort();
                    if (sort > stageSort){
                        continue;
                    }
                    stage.setStageSort(stageSort - 1 );
                    updateStages(stage);
                }
                return;
            }
            //存在则更新从节点顺序
            for (Stage stage : otherStage) {
                int sort = stage.getStageSort();
                if (stages.getStageSort() < sort){
                    stage.setStageSort(sort - 1 );
                    updateStages(stage);
                }
            }
        }
    }

    @Override
    public void updateStagesTask(Stage stage){
        Object values = stage.getValues();
        String taskId = stage.getTaskId();
        Tasks tasks = new Tasks();
        tasks.setTaskId(taskId);
        tasks.setTask(values);
        tasks.setValues(values);
        tasksService.updateTasksTask(tasks);
    }

    @Override
    public void deleteAllStagesOrTask(String pipelineId){
       List<Stage> allStage = findAllPipelineStages(pipelineId);
       if (allStage.size() == 0){
           return;
       }
       for (Stage stage : allStage) {
           String stagesId = stage.getStageId();
           tasksService.deleteAllTasksOrTask(stagesId,2);
           deleteStages(stagesId);
       }
   }

    @Override
    public List<String> validStagesMustField(String pipelineId){
        List<Stage> allStage = findAllMainStage(pipelineId);
        if (allStage.size() == 0){
            return null;
        }
        List<String> list = new ArrayList<>();
        for (Stage stage : allStage) {
            String stagesId = stage.getStageId();
            for (Stage pipelineStage : findOtherStage(stagesId)) {
                String id = pipelineStage.getStageId();
                //获取任务详情
                List<String> stringList = tasksService.validTasksMustField(id, 2);
                list.addAll(stringList);
            }
        }
        return list;
    }

    @Override
    public String createStages(Stage stage) {
        StageEntity stageEntity = BeanMapper.map(stage, StageEntity.class);
        return stageDao.createStages(stageEntity);
    }

    @Override
    public void updateStages(Stage stage) {
        StageEntity stageEntity = BeanMapper.map(stage, StageEntity.class);
        stageDao.updateStages(stageEntity);
    }

    @Override
    public void deleteStages(String stageId) {
        stageDao.deleteStages(stageId);
    }

    @Override
    public Stage findOneStages(String stageId) {
        StageEntity oneStages = stageDao.findOneStages(stageId);
        return BeanMapper.map(oneStages, Stage.class);
    }

    @Override
    public List<Stage> findAllStages() {
        List<StageEntity> allStagesList = stageDao.findAllStages();
        return BeanMapper.mapList(allStagesList, Stage.class);
    }

    @Override
    public List<Stage> findAllStagesList(List<String> idList) {
        List<StageEntity> allStagesList = stageDao.findAllStagesList(idList);
        return BeanMapper.mapList(allStagesList, Stage.class);
    }

}

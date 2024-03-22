package io.thoughtware.matflow.pipeline.definition.service;


import com.alibaba.fastjson.JSONObject;
import io.thoughtware.matflow.setting.service.AuthHostService;
import io.thoughtware.matflow.setting.service.AuthService;
import io.thoughtware.matflow.setting.service.AuthThirdService;
import io.thoughtware.matflow.stages.model.Stage;
import io.thoughtware.matflow.stages.service.StageService;
import io.thoughtware.matflow.support.util.util.PipelineFinal;
import io.thoughtware.matflow.task.artifact.model.TaskArtifact;
import io.thoughtware.matflow.task.artifact.service.TaskArtifactXpackService;
import io.thoughtware.matflow.task.build.model.TaskBuild;
import io.thoughtware.matflow.task.code.model.TaskCode;
import io.thoughtware.matflow.task.code.model.ThirdHouse;
import io.thoughtware.matflow.task.code.model.XcodeBranch;
import io.thoughtware.matflow.task.code.model.XcodeRepository;
import io.thoughtware.matflow.task.code.service.TaskCodeThirdService;
import io.thoughtware.matflow.task.code.service.TaskCodeGittokService;
import io.thoughtware.matflow.task.codescan.model.TaskCodeScan;
import io.thoughtware.matflow.task.deploy.model.TaskDeploy;
import io.thoughtware.matflow.task.task.model.Tasks;
import io.thoughtware.matflow.task.task.service.TasksService;
import io.thoughtware.matflow.task.test.model.TaskTest;
import io.thoughtware.matflow.task.test.service.TaskTestOnService;
import io.thoughtware.matflow.pipeline.definition.dao.PipelineDao;
import io.thoughtware.matflow.pipeline.definition.entity.PipelineEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.util.*;

@Service
public class PipelineYamlServiceImpl implements PipelineYamlService {

    @Autowired
    PipelineDao pipelineDao;

    @Autowired
    AuthHostService authHostService;

    @Autowired
    AuthThirdService authThirdService;

    @Autowired
    TaskTestOnService taskTestOnService;

    @Autowired
    TaskCodeThirdService taskCodeThirdService;

    @Autowired
    TaskCodeGittokService taskCodeGittokService;

    @Autowired
    TaskArtifactXpackService taskArtifactXpackService;

    @Autowired
    AuthService authService;

    @Autowired
    PipelineYamlService pipelineYamlService;

    @Autowired
    TaskCodeThirdService codeThirdService;

    @Autowired
    TasksService tasksService;

    @Autowired
    StageService stageService;

    private static final Logger logger = LoggerFactory.getLogger(PipelineYamlService.class);


    @Override
    public String importPipelineYaml(String pipelineId){

        PipelineEntity pipeline = pipelineDao.findPipelineById(pipelineId);

        Map<String, Object> properties = new HashMap<>();
        properties.put("pipeline", Map.of("name", pipeline.getName()));

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        Yaml yaml = new Yaml(options);

        // 将Java对象转换为YAML字符串
        String yamlString = yaml.dump(properties);

        int type = pipeline.getType();

        if (type == 1){
            return importTaskYaml(yamlString,pipelineId);
        }else {
            return importStageYaml(yamlString,pipelineId);
        }
    }

    /**
     * 导出多任务配置为Yaml格式
     * @param yamlString yaml
     * @param pipelineId 流水线
     * @return Yaml格式支付串
     */
    private String importTaskYaml(String yamlString,String pipelineId){

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        Yaml yaml = new Yaml(options);
        Map<String, Object> data = yaml.load(yamlString);

        List<Map<String, Object>> taskList =  new ArrayList<>();

        List<Tasks> tasksList = tasksService.finAllPipelineTaskOrTask(pipelineId);
        for (Tasks tasks : tasksList) {
            LinkedHashMap<String, Object> tasksMap = new LinkedHashMap<>();

            LinkedHashMap<String, Object> taskDetails = new LinkedHashMap<>();

            taskDetails.put("taskId",tasks.getTaskId());
            taskDetails.put("taskName",tasks.getTaskName());
            findTaskDetails(tasks.getTaskType(), tasks.getTask(), taskDetails);

            tasksMap.put("task",taskDetails);
            taskList.add(tasksMap);
        }

        Map<String, Object> pipelineMap = (Map<String, Object>) data.get("pipeline");
        pipelineMap.put("tasks",taskList);
        return yaml.dump(data);
    }

    /**
     * 导出多阶段配置为Yaml格式
     * @param yamlString yaml
     * @param pipelineId 流水线
     * @return Yaml格式支付串
     */
    private String importStageYaml(String yamlString,String pipelineId){

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        Yaml yaml = new Yaml(options);
        Map<String, Object> data = yaml.load(yamlString);

        List<Map<String, Object>> stageMapList =  new ArrayList<>();

        List<Stage> stageList = stageService.findAllStagesOrTask(pipelineId);

        for (Stage stage : stageList) {
            LinkedHashMap<String, Object> stageMap = new LinkedHashMap<>();

            List<Stage> parallelStageList = stage.getStageList();

            List<Map<String, Object>> parallelMapList =  new ArrayList<>();
            LinkedHashMap<String, Object> parallelsMap = new LinkedHashMap<>();
            for (Stage parallelStage : parallelStageList) {

                LinkedHashMap<String, Object> parallelMap = new LinkedHashMap<>();

                List<Map<String, Object>> taskMapList =  new ArrayList<>();
                List<Tasks> taskList = parallelStage.getTaskValues();
                LinkedHashMap<String, Object> tasksMap = new LinkedHashMap<>();
                for (Tasks tasks : taskList) {

                    LinkedHashMap<String, Object> taskMap = new LinkedHashMap<>();
                    LinkedHashMap<String, Object> taskDetails = new LinkedHashMap<>();

                    taskDetails.put("taskId",tasks.getTaskId());
                    taskDetails.put("taskName",tasks.getTaskName());
                    findTaskDetails(tasks.getTaskType(), tasks.getTask(), taskDetails);

                    taskMap.put("task",taskDetails);
                    taskMapList.add(taskMap);

                    tasksMap.put("parallelId",parallelStage.getStageId());
                    tasksMap.put("parallelName",parallelStage.getStageName());
                    tasksMap.put("tasks",taskMapList);
                }

                parallelMap.put("parallel",tasksMap);
                parallelMapList.add(parallelMap);
            }
            parallelsMap.put("stageId",stage.getStageId());
            parallelsMap.put("stageName",stage.getStageName());
            parallelsMap.put("parallels",parallelMapList);

            stageMap.put("stage",parallelsMap);
            stageMapList.add(stageMap);
        }
        Map<String, Object> pipelineMap = (Map<String, Object>) data.get("pipeline");
        pipelineMap.put("stages",stageMapList);

        return yaml.dump(data);
    }


    private LinkedHashMap<String, Object> findTaskDetails(String taskType,Object object,LinkedHashMap<String, Object> taskDetails){
        if (Objects.isNull(object)){
            return null;
        }
        switch (taskType){
            case PipelineFinal.TASK_CODE_GIT , PipelineFinal.TASK_CODE_GITEE , PipelineFinal.TASK_CODE_GITHUB ,
                    PipelineFinal.TASK_CODE_GITLAB, PipelineFinal.TASK_CODE_XCODE, PipelineFinal.TASK_CODE_SVN ->{
                return taskCodeDetails(taskType,object,taskDetails);
            }
            case PipelineFinal.TASK_BUILD_MAVEN, PipelineFinal.TASK_BUILD_NODEJS ->{
                return taskBuildDetails(taskType,object,taskDetails);
            }
            case PipelineFinal.TASK_TEST_MAVENTEST, PipelineFinal.TASK_TEST_TESTON  ->{
                return taskTestDetails(taskType,object,taskDetails);
            }
            case PipelineFinal.TASK_DEPLOY_LINUX , PipelineFinal.TASK_DEPLOY_DOCKER ->{
                return taskDeployDetails(taskType,object,taskDetails);
            }
            case PipelineFinal.TASK_ARTIFACT_NEXUS , PipelineFinal.TASK_ARTIFACT_SSH , PipelineFinal.TASK_ARTIFACT_XPACK ->{
                return taskArtifactDetails(taskType,object,taskDetails);
            }
            case  PipelineFinal.TASK_CODESCAN_SONAR ->{
                return taskCodeScanDetails(taskType,object,taskDetails);
            }
        }
        return null;
    }


    private LinkedHashMap<String, Object> taskCodeDetails(String taskType,Object object,LinkedHashMap<String, Object> taskDetailsMap){
        String string = JSONObject.toJSONString(object);

        TaskCode taskCode = JSONObject.parseObject(string, TaskCode.class);
        if (!Objects.isNull(taskCode.getAuthId())){
            taskDetailsMap.put("authId",taskCode.getAuthId());
        }
        if (!taskType.equals(PipelineFinal.TASK_CODE_SVN)){
            if (!Objects.isNull(taskCode.getCodeName())){
                taskDetailsMap.put("url",taskCode.getCodeName());
            }
            if (!Objects.isNull(taskCode.getCodeBranch())){
                taskDetailsMap.put("branch",taskCode.getCodeBranch());
            }else {
                taskDetailsMap.put("branch", PipelineFinal.TASK_CODE_DEFAULT_BRANCH);
            }
        }else {
            if (!Objects.isNull(taskCode.getCodeName())){
                taskDetailsMap.put("detection_url",taskCode.getCodeName());
            }

            if (!Objects.isNull(taskCode.getSvnFile())){
                taskDetailsMap.put("detection_file",taskCode.getSvnFile());
            }
        }

        // switch (taskType){
        //     case PipelineFinal.TASK_CODE_GIT, PipelineFinal.TASK_CODE_GITLAB ->{
        //         if (!Objects.isNull(taskCode.getAuthId())){
        //             taskDetailsMap.put("authId",taskCode.getAuthId());
        //         }
        //         if (!Objects.isNull(taskCode.getCodeName())){
        //             taskDetailsMap.put("url",taskCode.getCodeName());
        //         }
        //
        //         if (!Objects.isNull(taskCode.getCodeBranch())){
        //             taskDetailsMap.put("branch",taskCode.getCodeBranch());
        //         }else {
        //             taskDetailsMap.put("branch", PipelineFinal.TASK_CODE_DEFAULT_BRANCH);
        //         }
        //     }
        //     case PipelineFinal.TASK_CODE_GITEE, PipelineFinal.TASK_CODE_GITHUB ->{
        //         if (!Objects.isNull(taskCode.getAuthId())){
        //             taskDetailsMap.put("authId",taskCode.getAuthId());
        //         }
        //         if (!Objects.isNull(taskCode.getCodeName())){
        //             taskDetailsMap.put("repository_name",taskCode.getCodeName());
        //         }
        //         if (!Objects.isNull(taskCode.getCodeAddress())){
        //             taskDetailsMap.put("repository_url",taskCode.getCodeAddress());
        //         }
        //
        //         if (!Objects.isNull(taskDetailsMap.get("authId"))){
        //             String authId = (String) taskDetailsMap.get("authId");
        //             String repositoryName = (String) taskDetailsMap.get("repository_name");
        //             String houseUrl = codeThirdService.getHouseUrl(authId, repositoryName, taskType);
        //             taskCode.setCodeAddress(houseUrl);
        //             taskDetailsMap.put("repository_url",houseUrl);
        //         }
        //
        //         if (!Objects.isNull(taskCode.getCodeBranch())){
        //             taskDetailsMap.put("branch",taskCode.getCodeBranch());
        //         }else {
        //             taskDetailsMap.put("branch", PipelineFinal.TASK_CODE_DEFAULT_BRANCH);
        //         }
        //     }
        //     case PipelineFinal.TASK_CODE_XCODE ->{
        //         String authId = taskCode.getAuthId();
        //         if (!Objects.isNull(authId)){
        //             taskDetailsMap.put("authId",taskCode.getAuthId());
        //         }
        //         if (!Objects.isNull(taskCode.getRepository())){
        //             String rpyId = taskCode.getRepository().getRpyId();
        //             taskDetailsMap.put("repository_id",rpyId);
        //             ThirdHouse thirdHouse = taskCodeGittokService.
        //                     findStoreHouse((String) taskDetailsMap.get("authId"), rpyId);
        //             if (!Objects.isNull(thirdHouse)) {
        //                 // taskCode.setRepository(thirdHouse);
        //                 taskDetailsMap.put("repository_url",thirdHouse.getHouseWebUrl());
        //                 taskDetailsMap.put("repository_name",thirdHouse.getName());
        //             }
        //         }
        //         taskDetailsMap.put("branch", PipelineFinal.TASK_CODE_DEFAULT_BRANCH);
        //         if (!Objects.isNull(taskCode.getBranch())){
        //             String branchId = taskCode.getBranch().getBranchId();
        //             String authId1 = (String) taskDetailsMap.get("authId");
        //             String rpyId1 = (String) taskDetailsMap.get("repository_id");
        //             XcodeBranch xcodeBranch = taskCodeGittokService.findOneBranch(authId1, rpyId1,branchId);
        //             if (!Objects.isNull(xcodeBranch)){
        //                 taskCode.setBranch(xcodeBranch);
        //                 taskDetailsMap.put("branch_name",xcodeBranch.getBranchName());
        //             }
        //         }else {
        //             taskDetailsMap.put("branch", PipelineFinal.TASK_CODE_DEFAULT_BRANCH);
        //         }
        //     }
        //     case PipelineFinal.TASK_CODE_SVN ->{
        //         if (!Objects.isNull(taskCode.getAuthId())){
        //             taskDetailsMap.put("authId",taskCode.getAuthId());
        //         }
        //         if (!Objects.isNull(taskCode.getCodeName())){
        //             taskDetailsMap.put("detection_url",taskCode.getCodeName());
        //         }
        //
        //         if (!Objects.isNull(taskCode.getSvnFile())){
        //             taskDetailsMap.put("detection_file",taskCode.getSvnFile());
        //         }
        //     }
        // }
        return taskDetailsMap;
    }


    private LinkedHashMap<String, Object> taskCodeScanDetails(String taskType,Object object,LinkedHashMap<String, Object> taskDetailsMap){
        String string = JSONObject.toJSONString(object);

        TaskCodeScan taskCodeScan = JSONObject.parseObject(string, TaskCodeScan.class);

        switch (taskType){
            case PipelineFinal.TASK_CODESCAN_SONAR ->{
                if (!Objects.isNull(taskCodeScan.getAuthId())){
                    taskDetailsMap.put("authId",taskCodeScan.getAuthId());
                }
                if (!Objects.isNull(taskCodeScan.getProjectName())){
                    taskDetailsMap.put("project_name",taskCodeScan.getProjectName());
                }
            }
        }
        return taskDetailsMap;
    }


    private LinkedHashMap<String, Object> taskTestDetails(String taskType,Object object,LinkedHashMap<String, Object> taskDetailsMap){
        String string = JSONObject.toJSONString(object);

        TaskTest taskTest = JSONObject.parseObject(string, TaskTest.class);

        switch (taskType){
            case PipelineFinal.TASK_TEST_MAVENTEST ->{
                if (!Objects.isNull(taskTest.getTestOrder())){
                    taskDetailsMap.put("test_order",taskTest.getTestOrder());
                }
                if (!Objects.isNull(taskTest.getAddress())){
                    taskDetailsMap.put("pom_address",taskTest.getAddress());
                }
            }
            case PipelineFinal.TASK_TEST_TESTON ->{

                if (!Objects.isNull(taskTest.getAuthId())){
                    taskDetailsMap.put("authId",taskTest.getAuthId());
                }

                if (!Objects.isNull(taskTest.getWebEnv())){
                    taskDetailsMap.put("webEnv",taskTest.getWebEnv().getId());
                }

                if (!Objects.isNull(taskTest.getApiEnv())){
                    taskDetailsMap.put("apiEnv",taskTest.getApiEnv().getId());
                }
                if (!Objects.isNull(taskTest.getAppEnv())){
                    taskDetailsMap.put("appEnv",taskTest.getAppEnv().getId());
                }
                if (!Objects.isNull(taskTest.getTestPlan())){
                    taskDetailsMap.put("testPlan",taskTest.getTestPlan().getId());
                }
                if (!Objects.isNull(taskTest.getTestSpace())){
                    taskDetailsMap.put("testSpace",taskTest.getTestSpace().getId());
                }
            }
        }
        return taskDetailsMap;
    }


    private LinkedHashMap<String, Object> taskBuildDetails(String taskType,Object object,LinkedHashMap<String, Object> taskDetailsMap){
        String string = JSONObject.toJSONString(object);

        TaskBuild taskBuilder = JSONObject.parseObject(string, TaskBuild.class);

        switch (taskType){
            case PipelineFinal.TASK_BUILD_MAVEN ->{
                if (!Objects.isNull(taskBuilder.getBuildOrder())){
                    taskDetailsMap.put("mvn_order",taskBuilder.getBuildOrder());
                }
                if (!Objects.isNull(taskBuilder.getBuildAddress())){
                    taskDetailsMap.put("pom_address",taskBuilder.getBuildAddress());
                }
                if (!Objects.isNull(taskBuilder.getProductRule())){
                    taskDetailsMap.put("artifact_role",taskBuilder.getProductRule());
                }
            }
            case PipelineFinal.TASK_BUILD_NODEJS ->{
                if (!Objects.isNull(taskBuilder.getBuildOrder())){
                    taskDetailsMap.put("npm_order",taskBuilder.getBuildOrder());
                }
                if (!Objects.isNull(taskBuilder.getBuildAddress())){
                    taskDetailsMap.put("model_address",taskBuilder.getBuildAddress());
                }
                if (!Objects.isNull(taskBuilder.getProductRule())){
                    taskDetailsMap.put("artifact_role",taskBuilder.getProductRule());
                }
            }
        }
        return taskDetailsMap;
    }


    private LinkedHashMap<String, Object> taskDeployDetails(String taskType,Object object,LinkedHashMap<String, Object> taskDetailsMap){
        String string = JSONObject.toJSONString(object);

        TaskDeploy taskDeploy = JSONObject.parseObject(string, TaskDeploy.class);

        switch (taskType){
            case PipelineFinal.TASK_DEPLOY_LINUX ->{
                if (taskDeploy.getAuthType() != 0){
                    taskDetailsMap.put("deploy_type",taskDeploy.getAuthType());
                }

                if (taskDeploy.getAuthType() == 1){
                    if (!Objects.isNull(taskDeploy.getAuthId())){
                        taskDetailsMap.put("authId",taskDeploy.getAuthId());
                    }
                    if (!Objects.isNull(taskDeploy.getDeployAddress())){
                        taskDetailsMap.put("deploy_address",taskDeploy.getDeployAddress());
                    }
                }
                if (!Objects.isNull(taskDeploy.getDeployOrder())){
                    taskDetailsMap.put("deploy_order",taskDeploy.getDeployOrder());
                }
            }
            case PipelineFinal.TASK_DEPLOY_DOCKER->{
                if (!Objects.isNull(taskDeploy.getAuthId())){
                    taskDetailsMap.put("authId",taskDeploy.getAuthId());
                }
                if (!Objects.isNull(taskDeploy.getDockerImage())){
                    taskDetailsMap.put("image",taskDeploy.getDockerImage());
                }
                if (!Objects.isNull(taskDeploy.getDeployAddress())){
                    taskDetailsMap.put("deploy_address",taskDeploy.getDeployAddress());
                }
                if (!Objects.isNull(taskDeploy.getDeployOrder())){
                    taskDetailsMap.put("deploy_order",taskDeploy.getDeployOrder());
                }
            }
        }
        return taskDetailsMap;
    }


    private LinkedHashMap<String, Object> taskArtifactDetails(String taskType,Object object,LinkedHashMap<String, Object> taskDetailsMap){
        String string = JSONObject.toJSONString(object);

        TaskArtifact taskArtifact = JSONObject.parseObject(string, TaskArtifact.class);

        String artifactType = taskArtifact.getArtifactType();
        taskDetailsMap.put("artifact_type",artifactType);
        switch (taskType){
            case PipelineFinal.TASK_ARTIFACT_MAVEN ->{
                switch (artifactType){
                    case PipelineFinal.TASK_ARTIFACT_NEXUS ->{
                        if (!Objects.isNull(taskArtifact.getArtifactId())){
                            taskDetailsMap.put("artifact_id",taskArtifact.getArtifactId());
                        }
                        if (!Objects.isNull(taskArtifact.getAuthId())){
                            taskDetailsMap.put("auth_id",taskArtifact.getAuthId());
                        }
                        if (!Objects.isNull(taskArtifact.getGroupId())){
                            taskDetailsMap.put("group_id",taskArtifact.getGroupId());
                        }
                        if (!Objects.isNull(taskArtifact.getVersion())){
                            taskDetailsMap.put("version",taskArtifact.getVersion());
                        }
                        if (!Objects.isNull(taskArtifact.getFileAddress())){
                            taskDetailsMap.put("file_address",taskArtifact.getFileAddress());
                        }
                        if (!Objects.isNull(taskArtifact.getRule())){
                            taskDetailsMap.put("rule",taskArtifact.getRule());
                        }
                    }
                    case PipelineFinal.TASK_ARTIFACT_SSH ->{
                        if (!Objects.isNull(taskArtifact.getAuthId())){
                            taskDetailsMap.put("auth_id",taskArtifact.getAuthId());
                        }
                        if (!Objects.isNull(taskArtifact.getPutAddress())){
                            taskDetailsMap.put("remote_address",taskArtifact.getPutAddress());
                        }
                        if (!Objects.isNull(taskArtifact.getFileAddress())){
                            taskDetailsMap.put("fileAddress",taskArtifact.getFileAddress());
                        }
                        if (!Objects.isNull(taskArtifact.getFileAddress())){
                            taskDetailsMap.put("put_address",taskArtifact.getPutAddress());
                        }
                        if (!Objects.isNull(taskArtifact.getRule())){
                            taskDetailsMap.put("rule",taskArtifact.getRule());
                        }
                    }
                    case PipelineFinal.TASK_ARTIFACT_XPACK ->{
                        if (!Objects.isNull(taskArtifact.getArtifactId())){
                            taskDetailsMap.put("artifactId",taskArtifact.getArtifactId());
                        }
                        if (!Objects.isNull(taskArtifact.getAuthId())){
                            taskDetailsMap.put("authId",taskArtifact.getAuthId());
                        }
                        if (!Objects.isNull(taskArtifact.getGroupId())){
                            taskDetailsMap.put("groupId",taskArtifact.getGroupId());
                        }
                        if (!Objects.isNull(taskArtifact.getVersion())){
                            taskDetailsMap.put("version",taskArtifact.getVersion());
                        }
                        if (!Objects.isNull(taskArtifact.getRepository())){
                            taskDetailsMap.put("repository_id",taskArtifact.getRepository().getId());
                        }
                    }
                }
            }
            case PipelineFinal.TASK_ARTIFACT_NODEJS ->{

            }
            case PipelineFinal.TASK_ARTIFACT_DOCKER ->{
                switch (artifactType){
                    case PipelineFinal.TASK_ARTIFACT_NEXUS ->{
                        if (!Objects.isNull(taskArtifact.getDockerImage())){
                            taskDetailsMap.put("image",taskArtifact.getDockerImage());
                        }
                        if (!Objects.isNull(taskArtifact.getAuthId())){
                            taskDetailsMap.put("authId",taskArtifact.getAuthId());
                        }
                    }
                }
            }
        }
        return taskDetailsMap;
    }

    private LinkedHashMap<String, Object> taskPullArtifactDetails(String taskType,Object object,LinkedHashMap<String, Object> taskDetailsMap){
        String string = JSONObject.toJSONString(object);

        TaskArtifact taskArtifact = JSONObject.parseObject(string, TaskArtifact.class);

        String artifactType = taskArtifact.getArtifactType();
        taskDetailsMap.put("artifact_type",artifactType);
        switch (taskType){
            case PipelineFinal.TASK_PULL_MAVEN ->{
                switch (artifactType){
                    case PipelineFinal.TASK_ARTIFACT_NEXUS ->{
                        if (!Objects.isNull(taskArtifact.getArtifactId())){
                            taskDetailsMap.put("artifact_id",taskArtifact.getArtifactId());
                        }
                        if (!Objects.isNull(taskArtifact.getAuthId())){
                            taskDetailsMap.put("auth_id",taskArtifact.getAuthId());
                        }
                        if (!Objects.isNull(taskArtifact.getGroupId())){
                            taskDetailsMap.put("group_id",taskArtifact.getGroupId());
                        }
                        if (!Objects.isNull(taskArtifact.getVersion())){
                            taskDetailsMap.put("version",taskArtifact.getVersion());
                        }
                        if (!Objects.isNull(taskArtifact.getFileAddress())){
                            taskDetailsMap.put("file_address",taskArtifact.getFileAddress());
                        }
                        if (!Objects.isNull(taskArtifact.getRule())){
                            taskDetailsMap.put("rule",taskArtifact.getRule());
                        }
                    }
                    case PipelineFinal.TASK_ARTIFACT_SSH ->{
                        if (!Objects.isNull(taskArtifact.getAuthId())){
                            taskDetailsMap.put("auth_id",taskArtifact.getAuthId());
                        }
                        if (!Objects.isNull(taskArtifact.getPutAddress())){
                            taskDetailsMap.put("remote_address",taskArtifact.getPutAddress());
                        }
                        if (!Objects.isNull(taskArtifact.getFileAddress())){
                            taskDetailsMap.put("fileAddress",taskArtifact.getFileAddress());
                        }
                        if (!Objects.isNull(taskArtifact.getFileAddress())){
                            taskDetailsMap.put("put_address",taskArtifact.getPutAddress());
                        }
                        if (!Objects.isNull(taskArtifact.getRule())){
                            taskDetailsMap.put("rule",taskArtifact.getRule());
                        }
                    }
                    case PipelineFinal.TASK_ARTIFACT_XPACK ->{
                        if (!Objects.isNull(taskArtifact.getArtifactId())){
                            taskDetailsMap.put("artifactId",taskArtifact.getArtifactId());
                        }
                        if (!Objects.isNull(taskArtifact.getAuthId())){
                            taskDetailsMap.put("authId",taskArtifact.getAuthId());
                        }
                        if (!Objects.isNull(taskArtifact.getGroupId())){
                            taskDetailsMap.put("groupId",taskArtifact.getGroupId());
                        }
                        if (!Objects.isNull(taskArtifact.getVersion())){
                            taskDetailsMap.put("version",taskArtifact.getVersion());
                        }
                        if (!Objects.isNull(taskArtifact.getRepository())){
                            taskDetailsMap.put("repository_id",taskArtifact.getRepository().getId());
                        }
                    }
                }
            }
            case PipelineFinal.TASK_PULL_NODEJS ->{

            }
            case PipelineFinal.TASK_PULL_DOCKER ->{
                switch (artifactType){
                    case PipelineFinal.TASK_ARTIFACT_NEXUS ->{
                        if (!Objects.isNull(taskArtifact.getDockerImage())){
                            taskDetailsMap.put("image",taskArtifact.getDockerImage());
                        }
                        if (!Objects.isNull(taskArtifact.getAuthId())){
                            taskDetailsMap.put("authId",taskArtifact.getAuthId());
                        }
                    }
                }
            }
        }
        return taskDetailsMap;
    }





}

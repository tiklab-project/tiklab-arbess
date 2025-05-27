package io.tiklab.arbess.starter.task;

import io.tiklab.arbess.setting.tool.model.Scm;
import io.tiklab.arbess.setting.tool.model.ScmQuery;
import io.tiklab.arbess.setting.tool.service.ScmService;
import io.tiklab.arbess.agent.support.util.service.PipelineUtilService;
import io.tiklab.arbess.support.util.util.PipelineFinal;
import io.tiklab.arbess.task.artifact.service.TaskArtifactService;
import io.tiklab.arbess.task.build.model.TaskBuild;
import io.tiklab.arbess.task.build.service.TaskBuildService;
import io.tiklab.arbess.task.code.model.TaskCode;
import io.tiklab.arbess.task.code.service.TaskCodeService;
import io.tiklab.arbess.task.codescan.model.TaskCodeScan;
import io.tiklab.arbess.task.codescan.service.TaskCodeScanService;
import io.tiklab.arbess.task.pullArtifact.service.TaskPullArtifactService;
import io.tiklab.arbess.task.task.model.Tasks;
import io.tiklab.arbess.task.task.service.TasksService;
import io.tiklab.arbess.task.test.model.TaskTest;
import io.tiklab.arbess.task.test.service.TaskTestService;
import io.tiklab.eam.client.author.config.TiklabApplicationRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static io.tiklab.arbess.support.util.util.PipelineFinal.*;

@Component
public class TaskInitTaskScm implements TiklabApplicationRunner {


    @Autowired
    TasksService tasksService;

    @Autowired
    ScmService scmService;

    @Autowired
    PipelineUtilService utilService;

    @Autowired
    TaskCodeService codeService;

    @Autowired
    TaskCodeScanService codeScanService;

    @Autowired
    TaskTestService testService;

    @Autowired
    TaskBuildService buildService;

    @Autowired
    TaskArtifactService artifactService;

    @Autowired
    TaskPullArtifactService pullArtifactService;

    public final static Map<String,Scm> scmAddress  = new HashMap<>();

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run() {

        logger.info("Load init scm tasks......");
        scmAddress();

        initTasks();
        logger.info("Load init scm success!");
    }

    /**
     * init scm address
     */
    public void initTasks(){

        List<Tasks> allTasks = tasksService.findAllTasks();
        try {
            List<Tasks> noCodeList = allTasks.stream()
                    .filter(tasks -> !tasksService.findTaskType(tasks.getTaskType()).equals(PipelineFinal.TASK_TYPE_CODE))
                    .toList();

            List<Tasks> codeList = allTasks.stream()
                    .filter(tasks -> tasksService.findTaskType(tasks.getTaskType()).equals(PipelineFinal.TASK_TYPE_CODE))
                    .toList();

            if (!codeList.isEmpty()){
                for (Tasks tasks : codeList) {
                    String taskId = tasks.getTaskId();
                    TaskCode code = codeService.findOneCode(taskId);
                    Scm gitScm = scmAddress.get(TASK_TOOL_TYPE_GIT);
                    Scm svnScm = scmAddress.get(TASK_TOOL_TYPE_SVN);
                    code.setType(tasks.getTaskType());
                    if (Objects.isNull(code.getToolGit()) && !Objects.isNull(gitScm)){
                        code.setToolGit(gitScm);
                        codeService.updateOneCode(code);
                    }
                    if (Objects.isNull(code.getToolSvn()) && !Objects.isNull(svnScm)){
                        code.setToolSvn(svnScm);
                        codeService.updateOneCode(code);
                    }
                }
            }

            List<Tasks> codeScanList = noCodeList.stream()
                    .filter(tasks -> tasksService.findTaskType(tasks.getTaskType()).equals(PipelineFinal.TASK_TYPE_CODESCAN))
                    .toList();

            for (Tasks tasks : codeScanList) {
                String taskId = tasks.getTaskId();
                TaskCodeScan codeScan = codeScanService.findOneCodeScan(taskId);
                if (Objects.isNull(codeScan.getToolJdk())){
                    Scm scm = scmAddress.get(TASK_TOOL_TYPE_JDK);
                    if (!Objects.isNull(scm)){
                        codeScan.setToolJdk(scm);
                        codeScanService.updateCodeScan(codeScan);
                    }
                }
                if (Objects.isNull(codeScan.getToolMaven())){
                    Scm scm = scmAddress.get(TASK_TOOL_TYPE_MAVEN);
                    if (!Objects.isNull(scm)){
                        codeScan.setToolMaven(scm);
                        codeScanService.updateCodeScan(codeScan);
                    }
                }
            }


            List<Tasks> testList = noCodeList.stream()
                    .filter(tasks -> tasksService.findTaskType(tasks.getTaskType()).equals(PipelineFinal.TASK_TYPE_TEST))
                    .toList();
            for (Tasks tasks : testList) {
                String taskId = tasks.getTaskId();
                TaskTest taskTest = testService.findOneTest(taskId);
                if (Objects.isNull(taskTest.getToolJdk())){
                    Scm scm = scmAddress.get(TASK_TOOL_TYPE_JDK);
                    if (!Objects.isNull(scm)){
                        taskTest.setToolJdk(scm);
                        testService.updateTest(taskTest);
                    }
                }
                if (Objects.isNull(taskTest.getToolMaven())){
                    Scm scm = scmAddress.get(TASK_TOOL_TYPE_MAVEN);
                    if (!Objects.isNull(scm)){
                        taskTest.setToolMaven(scm);
                        testService.updateTest(taskTest);
                    }
                }

            }

            List<Tasks> buildList = noCodeList.stream()
                    .filter(tasks -> tasksService.findTaskType(tasks.getTaskType()).equals(PipelineFinal.TASK_TYPE_BUILD))
                    .toList();
            for (Tasks tasks : buildList) {
                String taskId = tasks.getTaskId();
                TaskBuild build = buildService.findOneBuild(taskId);
                if (Objects.isNull(build.getToolJdk())){
                    Scm scm = scmAddress.get(TASK_TOOL_TYPE_JDK);
                    if (!Objects.isNull(scm)){
                        build.setToolJdk(scm);
                        buildService.updateBuild(build);
                    }
                }
                if (Objects.isNull(build.getToolMaven())){
                    Scm scm = scmAddress.get(TASK_TOOL_TYPE_MAVEN);
                    if (!Objects.isNull(scm)){
                        build.setToolMaven(scm);
                        buildService.updateBuild(build);
                    }
                }
                if (Objects.isNull(build.getToolNodejs())){
                    Scm scm = scmAddress.get(TASK_TOOL_TYPE_NODEJS);
                    if (!Objects.isNull(scm)){
                        build.setToolNodejs(scm);
                        buildService.updateBuild(build);
                    }
                }

            }
        }catch (Exception e){
            logger.warn(e.getMessage());
        }


        // List<Tasks> artifactList = noCodeList.stream()
        //         .filter(tasks -> tasksService.findTaskType(tasks.getTaskType()).equals(TASK_TYPE_UPLOAD))
        //         .toList();
        // for (Tasks tasks : artifactList) {
        //     String taskId = tasks.getTaskId();
        //     TaskArtifact artifact = artifactService.findOneProduct(taskId);
        //     if (Objects.isNull(artifact.getToolJdk())){
        //         Scm scm = scmAddress.get(TASK_TOOL_TYPE_JDK);
        //         if (!Objects.isNull(scm)){
        //             artifact.setToolJdk(scm);
        //             artifactService.updateProduct(artifact);
        //         }
        //     }
        //     if (Objects.isNull(artifact.getToolMaven())){
        //         Scm scm = scmAddress.get(TASK_TOOL_TYPE_MAVEN);
        //         if (!Objects.isNull(scm)){
        //             artifact.setToolMaven(scm);
        //             artifactService.updateProduct(artifact);
        //         }
        //     }
        //     if (Objects.isNull(artifact.getToolNodejs())){
        //         Scm scm = scmAddress.get(TASK_TOOL_TYPE_NODEJS);
        //         if (!Objects.isNull(scm)){
        //             artifact.setToolNodejs(scm);
        //             artifactService.updateProduct(artifact);
        //         }
        //     }
        //
        // }
        //
        //
        // List<Tasks> pullArtifactList = noCodeList.stream()
        //         .filter(tasks -> tasksService.findTaskType(tasks.getTaskType()).equals(TASK_TYPE_DOWNLOAD))
        //         .toList();
        // if (!pullArtifactList.isEmpty()){
        //     for (Tasks tasks : pullArtifactList) {
        //         String taskId = tasks.getTaskId();
        //         TaskPullArtifact artifact = pullArtifactService.findOnePullArtifact(taskId);
        //         if (Objects.isNull(artifact.getToolJdk())){
        //             Scm scm = scmAddress.get(TASK_TOOL_TYPE_JDK);
        //             if (!Objects.isNull(scm)){
        //                 artifact.setToolJdk(scm);
        //                 pullArtifactService.updatePullArtifact(artifact);
        //             }
        //         }
        //         if (Objects.isNull(artifact.getToolMaven())){
        //             Scm scm = scmAddress.get(TASK_TOOL_TYPE_MAVEN);
        //             if (!Objects.isNull(scm)){
        //                 artifact.setToolMaven(scm);
        //                 pullArtifactService.updatePullArtifact(artifact);
        //             }
        //         }
        //         if (Objects.isNull(artifact.getToolNodejs())){
        //             Scm scm = scmAddress.get(TASK_TOOL_TYPE_NODEJS);
        //             if (!Objects.isNull(scm)){
        //                 artifact.setToolNodejs(scm);
        //                 pullArtifactService.updatePullArtifact(artifact);
        //             }
        //         }
        //
        //     }
        // }


    }


    /**
     * 初始化scm地址
     */
    public void scmAddress(){

        // 获取JDk
        ScmQuery scmJdkQuery = new ScmQuery();
        scmJdkQuery.setScmType(PipelineFinal.TASK_TOOL_TYPE_JDK);
        List<Scm> scmJdkList = scmService.findPipelineScmList(scmJdkQuery);
        if (Objects.isNull(scmJdkList) || scmJdkList.isEmpty()){
            String javaPath = utilService.findJavaPath();
            Scm scm = new Scm();
            scm.setScmType(PipelineFinal.TASK_TOOL_TYPE_JDK);
            scm.setScmAddress(javaPath);
            scm.setScmName("default-jdk");
            String scmId = scmService.createPipelineScm(scm);
            scm.setScmId(scmId);
            scmAddress.put(PipelineFinal.TASK_TOOL_TYPE_JDK,scm);
        }else {
            scmAddress.put(PipelineFinal.TASK_TOOL_TYPE_JDK,scmJdkList.get(0));
        }


        List<Scm> allScm = scmService.findAllPipelineScm();

        List<Scm> nodeJsList = allScm.stream().filter(scm -> scm.getScmType().equals(TASK_TOOL_TYPE_NODEJS)).toList();
        if ( !nodeJsList.isEmpty()){
            scmAddress.put(PipelineFinal.TASK_TOOL_TYPE_NODEJS,nodeJsList.get(0));
        }

        List<Scm> mavenList = allScm.stream().filter(scm -> scm.getScmType().equals(TASK_TOOL_TYPE_MAVEN)).toList();
        if ( !mavenList.isEmpty()){
            scmAddress.put(PipelineFinal.TASK_TOOL_TYPE_MAVEN,mavenList.get(0));
        }

        List<Scm> gitList = allScm.stream().filter(scm -> scm.getScmType().equals(TASK_TOOL_TYPE_GIT)).toList();
        if ( !gitList.isEmpty()){
            scmAddress.put(PipelineFinal.TASK_TOOL_TYPE_GIT,gitList.get(0));
        }

        List<Scm> svnList = allScm.stream().filter(scm -> scm.getScmType().equals(TASK_TOOL_TYPE_SVN)).toList();
        if ( !svnList.isEmpty()){
            scmAddress.put(PipelineFinal.TASK_TOOL_TYPE_SVN,svnList.get(0));
        }

    }


}














package com.doublekit.pipeline.integration.project.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.common.page.Pagination;
import com.doublekit.common.Result;
import com.doublekit.project.project.model.Project;
import com.doublekit.project.project.model.ProjectQuery;
import com.doublekit.project.project.service.ProjectService;
import com.doublekit.rpc.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * ManagerController
 * Created by Zhangzhihua on 2017/9/25.
 */
@RestController
@RequestMapping("/project")
@Api(name = "ProjectController",desc = "项目管理")
public class ProjectController {

    private static Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    @Reference(address = "${rpc.project.address}")
    private ProjectService projectService;

    @RequestMapping(path="/createProject",method = RequestMethod.POST)
    @ApiMethod(name = "createProject",desc = "创建项目")
    @ApiParam(name = "project",desc = "项目DTO",required = true)
    public Result<String> createProject(@Validated(Project.Insert.class) @RequestBody Project project){
        //设置初始项目状态
        project.setProjectState("1");
        String id = projectService.createProject(project);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateProject",method = RequestMethod.POST)
    @ApiMethod(name = "updateProject",desc = "更新项目")
    @ApiParam(name = "project",desc = "项目DTO",required = true)
    public Result<Void> updateProject(@Validated(Project.Update.class) @RequestBody Project project){
        projectService.updateProject(project);

        return Result.ok();
    }


    @RequestMapping(path="/deleteProject",method = RequestMethod.POST)
    @ApiMethod(name = "deleteProject",desc = "根据项目ID删除项目")
    @ApiParam(name = "id",desc = "项目ID",required = true)
    public Result<Void> deleteProject(@NotNull String id){
        projectService.deleteProject(id);

        return Result.ok();
    }


    @RequestMapping(path="/findProject",method = RequestMethod.POST)
    @ApiMethod(name = "findProject",desc = "根据项目ID查找项目")
    @ApiParam(name = "id",desc = "项目ID",required = true)
       public Result<Project> findProject(@NotNull String id){
        Project project = projectService.findProject(id);

        return Result.ok(project);
    }

    @RequestMapping(path="/findAllProject",method = RequestMethod.POST)
    @ApiMethod(name = "findAllProject",desc = "查找所有项目")
    public Result<List<Project>> findAllProject(){
        List<Project> projectList = projectService.findAllProject();

        return Result.ok(projectList);
    }


    @RequestMapping(path = "/findProjectList",method = RequestMethod.POST)
    @ApiMethod(name = "findProjectList",desc = "根据查询对象查询项目列表")
    @ApiParam(name = "projectQuery",desc = "项目查询对象",required = true)
    public Result<List<Project>> findProjectList(@RequestBody ProjectQuery projectQuery){
        List<Project> projectList = projectService.findProjectList(projectQuery);

        return Result.ok(projectList);
    }


    @RequestMapping(path = "/findProjectPage",method = RequestMethod.POST)
    @ApiMethod(name = "findProjectPage",desc = "根据查询对象按分页查询项目列表")
    @ApiParam(name = "projectQuery",desc = "项目查询对象",required = true)
    public Result<Pagination<Project>> findProjectPage(@RequestBody ProjectQuery projectQuery){
        Pagination<Project> pagination = projectService.findProjectPage(projectQuery);

        return Result.ok(pagination);
    }

}

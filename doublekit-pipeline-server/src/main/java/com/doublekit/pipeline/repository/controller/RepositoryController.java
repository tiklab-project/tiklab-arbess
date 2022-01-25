package com.doublekit.pipeline.repository.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.common.page.Pagination;
import com.doublekit.common.Result;
import com.doublekit.pipeline.repository.model.Repository;
import com.doublekit.pipeline.repository.model.RepositoryQuery;
import com.doublekit.pipeline.repository.service.RepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * RepositoryController
 */
@RestController
@RequestMapping("/repository")
@Api(name = "RepositoryController",desc = "")
public class RepositoryController {
    //
    private static Logger logger = LoggerFactory.getLogger(RepositoryController.class);

    @Autowired
    private RepositoryService repositoryService;

    @RequestMapping(path="/createRepository",method = RequestMethod.POST)
    @ApiMethod(name = "createRepository",desc = "createRepository")
    @ApiParam(name = "repository",desc = "repository",required = true)
    public Result<String> createRepository(@RequestBody @NotNull @Valid Repository repository){
        String id = repositoryService.createRepository(repository);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateRepository",method = RequestMethod.POST)
    @ApiMethod(name = "updateRepository",desc = "updateRepository")
    @ApiParam(name = "repository",desc = "repository",required = true)
    public Result<Void> updateRepository(@RequestBody @NotNull @Valid Repository repository){
        repositoryService.updateRepository(repository);


        return Result.ok();
    }

    @RequestMapping(path="/deleteRepository",method = RequestMethod.POST)
    @ApiMethod(name = "deleteRepository",desc = "deleteRepository")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteRepository(@NotNull String id){
        repositoryService.deleteRepository(id);

        return Result.ok();
    }

    @RequestMapping(path="/findRepository",method = RequestMethod.POST)
    @ApiMethod(name = "findRepository",desc = "findRepository")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Repository> findRepository(@NotNull String id){
        Repository repository = repositoryService.findRepository(id);

        return Result.ok(repository);
    }

    @RequestMapping(path="/findAllRepository",method = RequestMethod.POST)
    @ApiMethod(name = "findAllRepository",desc = "findAllRepository")
    public Result<List<Repository>> findAllRepository(){
        List<Repository> repositoryList = repositoryService.findAllRepository();

        return Result.ok(repositoryList);
    }

    @RequestMapping(path = "/findRepositoryList",method = RequestMethod.POST)
    @ApiMethod(name = "findRepositoryList",desc = "findRepositoryList")
    @ApiParam(name = "repositoryQuery",desc = "repositoryQuery",required = true)
    public Result<List<Repository>> findRepositoryList(@RequestBody @Valid @NotNull RepositoryQuery repositoryQuery){
        List<Repository> repositoryList = repositoryService.findRepositoryList(repositoryQuery);

        return Result.ok(repositoryList);
    }

    @RequestMapping(path = "/findRepositoryPage",method = RequestMethod.POST)
    @ApiMethod(name = "findRepositoryPage",desc = "findRepositoryPage")
    @ApiParam(name = "repositoryQuery",desc = "repositoryQuery",required = true)
    public Result<Pagination<Repository>> findRepositoryPage(@RequestBody @Valid @NotNull RepositoryQuery repositoryQuery){
        Pagination<Repository> pagination = repositoryService.findRepositoryPage(repositoryQuery);

        return Result.ok(pagination);
    }

}

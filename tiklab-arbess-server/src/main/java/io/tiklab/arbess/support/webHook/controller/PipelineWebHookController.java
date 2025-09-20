package io.tiklab.arbess.support.webHook.controller;

import io.tiklab.arbess.support.webHook.model.WebHook;
import io.tiklab.arbess.support.webHook.model.WebHookQuery;
import io.tiklab.arbess.support.webHook.service.PipelineWebHookServer;
import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/pipeline/webhook")
public class PipelineWebHookController {

    @Autowired
    PipelineWebHookServer webHookServer;

    @RequestMapping(path="/*",method = RequestMethod.POST)
    public Result<String> execWebHook(HttpServletRequest request,HttpServletRequest response){
        String requestURI = request.getRequestURI();
        String key = requestURI.replace("/pipeline/webhook/", "");
        webHookServer.execWebHook(key);
        return Result.ok();
    }


    @RequestMapping(path="/createWebHook",method = RequestMethod.POST)
    public Result<String> createWebHook(@RequestBody @NotNull @Valid WebHook webHook){
        String webHookId = webHookServer.createWebHook(webHook);
        return Result.ok(webHookId);
    }

   
    @RequestMapping(path="/updateWebHook",method = RequestMethod.POST)
    public Result<Void> updateWebHook(@RequestBody @NotNull @Valid WebHook webHook){
        webHookServer.updateWebHook(webHook);
        return Result.ok();
    }

    
    @RequestMapping(path="/deleteWebHook",method = RequestMethod.POST)
    public Result<Void> deleteWebHook(@NotNull String webHookId){
        webHookServer.deleteWebHook(webHookId);
        return Result.ok();
    }


    @RequestMapping(path="/findPipelineWebHook",method = RequestMethod.POST)
    public Result<WebHook> findPipelineWebHook(@NotNull String pipelineId){
        WebHook webHook = webHookServer.findPipelineWebHook(pipelineId);
        return Result.ok(webHook);
    }


    @RequestMapping(path="/findWebHookList",method = RequestMethod.POST)
    public Result<List<WebHook>> findWebHookList(@RequestBody @NotNull @Valid WebHookQuery webHookQuery){
        List<WebHook> webHookList = webHookServer.findWebHookList(webHookQuery);
        return Result.ok(webHookList);
    }

    @RequestMapping(path="/findWebHookPage",method = RequestMethod.POST)
    public Result<Pagination<WebHook>> findWebHookPage(@RequestBody @NotNull @Valid WebHookQuery webHookQuery){
        Pagination<WebHook> webHookPage = webHookServer.findWebHookPage(webHookQuery);
        return Result.ok(webHookPage);
    }



}

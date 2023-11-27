package io.tiklab.matflow.pipeline.definition.controller;

import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.matflow.pipeline.definition.model.Pipeline;
import io.tiklab.matflow.pipeline.definition.model.PipelineQuery;
import io.tiklab.matflow.pipeline.definition.model.PipelineRecently;
import io.tiklab.matflow.pipeline.definition.service.PipelineService;
import io.tiklab.matflow.pipeline.definition.service.PipelineYamlService;
import io.tiklab.matflow.support.util.PipelineFileUtil;
import io.tiklab.postin.annotation.ApiMethod;
import io.tiklab.user.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @pi.protocol: http
 * @pi.groupName: pipeline
 */
@RestController
@RequestMapping("/pipeline")
public class PipelineController {

    @Autowired
    PipelineService pipelineService;

    @Autowired
    PipelineYamlService yamlService;

    /**
     * @pi.name:createPipeline
     * @pi.path:/pipeline/createPipeline
     * @pi.method:post
     * @pi.request-type:json
     * @pi.param: model=pipeline
     */
    @RequestMapping(path="/createPipeline",method = RequestMethod.POST)
    public Result<String> createPipeline(@RequestBody @NotNull @Valid Pipeline pipeline){

        String pipelineId = pipelineService.createPipeline(pipeline);

        return Result.ok(pipelineId);
    }

    /**
     * @pi.name:findAllPipeline
     * @pi.path:/pipeline/findAllPipeline
     * @pi.method:post
     * @pi.request-type:none
     */
    @RequestMapping(path="/findAllPipeline",method = RequestMethod.POST)
    public Result<List<Pipeline>> findAllPipeline(){

        List<Pipeline> selectAllPipeline = pipelineService.findAllPipeline();

        return Result.ok(selectAllPipeline);
    }

    /**
     * @pi.name:deletePipeline
     * @pi.path:/pipeline/deletePipeline
     * @pi.method:post
     * @pi.request-type:formdata
     * @pi.param: name=pipelineId;dataType=string;value=pipelineId;
     */
    @RequestMapping(path="/deletePipeline",method = RequestMethod.POST)
    public Result<Void> deletePipeline(@NotNull String pipelineId){

        pipelineService.deletePipeline(pipelineId);

        return Result.ok();
    }

    /**
     * @pi.name:findOnePipeline
     * @pi.path:/pipeline/findOnePipeline
     * @pi.method:post
     * @pi.request-type:formdata
     * @pi.param: name=pipelineId;dataType=string;value=pipelineId;
     */
    @RequestMapping(path="/findOnePipeline",method = RequestMethod.POST)
    public Result<Pipeline> findOnePipeline(@NotNull String pipelineId){

        Pipeline pipeline = pipelineService.findOnePipeline(pipelineId);

        return Result.ok(pipeline);
    }

    /**
     * @pi.name:updatePipeline
     * @pi.path:/pipeline/updatePipeline
     * @pi.method:post
     * @pi.request-type:json
     * @pi.param: model=pipeline
     */
    @RequestMapping(path="/updatePipeline",method = RequestMethod.POST)
    public Result<Void> updatePipeline(@RequestBody @NotNull @Valid Pipeline pipeline){

         pipelineService.updatePipeline(pipeline);

        return Result.ok();
    }


    /**
     * @pi.name:findUserPipelinePage
     * @pi.path:/pipeline/findUserPipelinePage
     * @pi.method:post
     * @pi.request-type:json
     * @pi.param: model=query
     */
    @RequestMapping(path="/findUserPipelinePage",method = RequestMethod.POST)
    public Result< Pagination<Pipeline>> findUserPipelinePage(@RequestBody @NotNull @Valid PipelineQuery query){

        Pagination<Pipeline> userPipeline = pipelineService.findUserPipelinePage(query);

        return Result.ok(userPipeline);
    }

    /**
     * @pi.name:findUserPipeline
     * @pi.path:/pipeline/findUserPipeline
     * @pi.method:post
     * @pi.request-type:none
     */
    @RequestMapping(path="/findUserPipeline",method = RequestMethod.POST)
    public Result< List<Pipeline>> findAllUserPipeline(){
        List<Pipeline> userPipeline = pipelineService.findUserPipeline();

        return Result.ok(userPipeline);
    }

    /**
     * @pi.name:findPipelineUser
     * @pi.path:/pipeline/findPipelineUser
     * @pi.method:post
     * @pi.request-type: formdata
     * @pi.param: name=pipelineId;dataType=string;value=pipelineId;
     */
    @RequestMapping(path="/findPipelineUser",method = RequestMethod.POST)
    public Result<List<User>> findPipelineUser(@NotNull String pipelineId){

        List<User>  dmUser = pipelineService.findPipelineUser(pipelineId);

        return Result.ok(dmUser);
    }

    /**
     * @pi.name:findPipelineRecently
     * @pi.path:/pipeline/findPipelineRecently
     * @pi.method:post
     * @pi.request-type: formdata;
     * @pi.param: name=number;dataType=int;value=5;
     */
    @RequestMapping(path="/findPipelineRecently",method = RequestMethod.POST)
    public Result<List<PipelineRecently>> findPipelineRecently(Integer number){

        List<PipelineRecently> userPipeline = pipelineService.findPipelineRecently(number);

        return Result.ok(userPipeline);
    }


    /**
     * @pi.name:pipelineClone
     * @pi.path:/pipeline/pipelineClone
     * @pi.method:post
     * @pi.request-type: formdata;
     * @pi.param: name=pipelineId;dataType=string;value=pipelineId;
     */
    @RequestMapping(path="/pipelineClone",method = RequestMethod.POST)
    public Result<Void> pipelineClone(@NotNull String pipelineId,@NotNull String pipelineName){

        pipelineService.pipelineClone(pipelineId,pipelineName);

        return Result.ok();
    }


    /**
     * @pi.name:findPipelineCloneName
     * @pi.path:/pipeline/findPipelineCloneName
     * @pi.method:post
     * @pi.request-type: formdata;
     * @pi.param: name=pipelineId;dataType=string;value=pipelineId;
     */
    @RequestMapping(path="/findPipelineCloneName",method = RequestMethod.POST)
    public Result<String> findPipelineCloneName(@NotNull String pipelineId){

        String name = pipelineService.findPipelineCloneName(pipelineId);

        return Result.ok(name);
    }

    /**
     * 获取流水线最近打开的流水线
     * @param number 数量
     * @param pipelineId 流水线id
     * @return 流水线
     */
    @RequestMapping(path="/findRecentlyPipeline",method = RequestMethod.POST)
    public Result<String> findRecentlyPipeline(@NotNull Integer number,@NotNull String pipelineId){

        List<Pipeline> pipelineList = pipelineService.findRecentlyPipeline(number,pipelineId);

        return Result.ok(pipelineList);
    }


    /**
     * @pi.name:findPipelineCloneName
     * @pi.path:/pipeline/importPipelineYaml
     * @pi.method:post
     * @pi.request-type: formdata;
     * @pi.param: name=pipelineId;dataType=string;value=pipelineId;
     */
    @RequestMapping(path="/importPipelineYaml",method = RequestMethod.POST)
    public ResponseEntity<Object> importPipelineYaml(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();

            String pipelineId = Arrays.toString(parameterMap.get("pipelineId")).replace("[","").replace("]","");

            String yamlString = yamlService.importPipelineYaml(pipelineId);

            Pipeline pipeline = pipelineService.findPipelineById(pipelineId);

            String tempFile = PipelineFileUtil.createTempFile(yamlString, ".yaml");
            File file = new File(tempFile);
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));

            ServletOutputStream outputStream = response.getOutputStream();
            response.setHeader("Content-Disposition", "attachment; filename="+pipeline.getName());
            response.setContentLength((int) file.length());

            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                outputStream.write(buffer,0,len);
            }
            in.close();
            outputStream.close();

            file.delete();

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.error(1000,"下载失败"));
        }
    }

}



















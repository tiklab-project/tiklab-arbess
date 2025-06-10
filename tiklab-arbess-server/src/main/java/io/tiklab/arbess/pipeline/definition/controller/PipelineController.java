package io.tiklab.arbess.pipeline.definition.controller;

import io.tiklab.arbess.support.util.util.PipelineFileUtil;
import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.arbess.pipeline.definition.model.Pipeline;
import io.tiklab.arbess.pipeline.definition.model.PipelineQuery;
import io.tiklab.arbess.pipeline.definition.model.PipelineRecently;
import io.tiklab.arbess.pipeline.definition.service.PipelineService;
import io.tiklab.arbess.pipeline.definition.service.PipelineYamlService;
import io.tiklab.postin.annotation.Api;
import io.tiklab.postin.annotation.ApiMethod;
import io.tiklab.postin.annotation.ApiParam;
import io.tiklab.privilege.dmRole.model.DmRolePatch;
import io.tiklab.user.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @pi.protocol: http
 * @pi.groupName: 流水线控制器
 * @pi.url:/pipeline
 */
@RestController
@RequestMapping("/pipeline")
@Api(name = "流水线控制器",desc = "流水线管理")
public class PipelineController {

    public Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PipelineService pipelineService;

    @Autowired
    PipelineYamlService yamlService;

    /**
     * @pi.name:创建流水线
     * @pi.url:/createPipeline
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param: model=io.tiklab.arbess.pipeline.definition.model.pipeline
     */
    @RequestMapping(path="/createPipeline",method = RequestMethod.POST)
    @ApiMethod(name = "创建流水线",desc = "创建流水线")
    @ApiParam(name = "pipeline",desc = "流水线信息",required = true)
    public Result<String> createPipeline(@RequestBody @NotNull @Valid Pipeline pipeline){

        String pipelineId = pipelineService.createPipeline(pipeline);

        return Result.ok(pipelineId);
    }

    /**
     * @pi.name:查询所有流水线
     * @pi.url:/findAllPipeline
     * @pi.methodType:post
     * @pi.request-type:none
     */
    @RequestMapping(path="/findAllPipeline",method = RequestMethod.POST)
    @ApiMethod(name = "查询所有流水线",desc = "查询所有流水线")
    public Result<List<Pipeline>> findAllPipeline(){

        List<Pipeline> selectAllPipeline = pipelineService.findAllPipeline();

        return Result.ok(selectAllPipeline);
    }

    /**
     * @pi.name:删除流水线
     * @pi.url:/deletePipeline
     * @pi.methodType:post
     * @pi.request-type:formdata
     * @pi.param: name=pipelineId;dataType=string;value=流水线id;
     */
    @RequestMapping(path="/deletePipeline",method = RequestMethod.POST)
    @ApiMethod(name = "删除流水线",desc = "删除流水线")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<Void> deletePipeline(@NotNull String pipelineId){

        pipelineService.deletePipeline(pipelineId);

        return Result.ok();
    }

    /**
     * @pi.name:查询流水线
     * @pi.url:/findOnePipeline
     * @pi.methodType:post
     * @pi.request-type:formdata
     * @pi.param: name=pipelineId;dataType=string;value=流水线id;
     */
    @RequestMapping(path="/findOnePipeline",method = RequestMethod.POST)
    @ApiMethod(name = "根据id查询流水线",desc = "根据id查询流水线")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<Pipeline> findOnePipeline(@NotNull String pipelineId){

        Pipeline pipeline = pipelineService.findOnePipeline(pipelineId);

        return Result.ok(pipeline);
    }

    @RequestMapping(path="/findPipelineNoQuery",method = RequestMethod.POST)
    @ApiMethod(name = "根据id查询流水线,不查询关联模型",desc = "根据id查询流水线,不查询关联模型")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<Pipeline> findPipelineNoQuery(@NotNull String pipelineId){

        Pipeline pipeline = pipelineService.findPipelineNoQuery(pipelineId);

        return Result.ok(pipeline);
    }

    @RequestMapping(path="/updatePipelineRootUser",method = RequestMethod.POST)
    // @ApiMethod(name = "updatePipelineRootUser",desc = "更新流水线负责人")
    // @ApiParam(name = "dmRolePatch",desc = "流水线负责人信息",required = true)
    public Result<Void> updatePipelineRootUser(@RequestBody @NotNull @Valid DmRolePatch dmRolePatch){

        pipelineService.updatePipelineRootUser(dmRolePatch);

        return Result.ok();
    }

    /**
     * @pi.name:更新流水线
     * @pi.url:/updatePipeline
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param:model=pipeline
     */
    @RequestMapping(path="/updatePipeline",method = RequestMethod.POST)
    //@ApiMethod(name = "updatePipeline",desc = "更新流水线信息")
    //@ApiParam(name = "pipeline",desc = "流水线",required = true)
    public Result<Void> updatePipeline(@RequestBody @NotNull @Valid Pipeline pipeline){

         pipelineService.updatePipeline(pipeline);

        return Result.ok();
    }


    /**
     * @pi.name:条件分页查询流水线
     * @pi.url:/findUserPipelinePage
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param: model=query
     */
    @RequestMapping(path="/findUserPipelinePage",method = RequestMethod.POST)
    @ApiMethod(name = "分页查询用户流水线",desc = "分页查询用户流水线")
    @ApiParam(name = "query",desc = "流水线查询条件",required = true)
    public Result<Pagination<Pipeline>> findUserPipelinePage(@RequestBody @NotNull @Valid PipelineQuery query){

        Pagination<Pipeline> userPipeline = pipelineService.findUserPipelinePage(query);

        return Result.ok(userPipeline);
    }


    /**
     * @pi.name:根据用户名密码分页查询流水线
     * @pi.url:/findUserPipelinePage
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param: model=query
     */
    @RequestMapping(path="/findUserPipelinePageByUser",method = RequestMethod.POST)
    @ApiMethod(name = "分页查询用户流水线",desc = "分页查询用户流水线")
    @ApiParam(name = "query",desc = "流水线查询条件",required = true)
    public Result<Pagination<Pipeline>> findUserPipelinePageByUser(@RequestBody @NotNull @Valid PipelineQuery query){

        Pagination<Pipeline> userPipeline = pipelineService.findUserPipelinePageByUser(query);

        return Result.ok(userPipeline);
    }

    /**
     * @pi.name:查询用户流水线
     * @pi.url:/findUserPipeline
     * @pi.methodType:post
     * @pi.request-type:none
     */
    @RequestMapping(path="/findUserPipeline",method = RequestMethod.POST)
    @ApiMethod(name = "查询用户流水线",desc = "查询用户流水线")
    @ApiParam(name = "query",desc = "流水线查询条件",required = true)
    public Result<List<Pipeline>> findUserPipeline(@RequestBody @NotNull @Valid PipelineQuery query){
        List<Pipeline> userPipeline = pipelineService.findUserPipeline(query);

        return Result.ok(userPipeline);
    }

    /**
     * @pi.name:查询流水线用户
     * @pi.url:/findPipelineUser
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=pipelineId;dataType=string;value=流水线id;
     */
    @RequestMapping(path="/findPipelineUser",method = RequestMethod.POST)
    @ApiMethod(name = "查询流水线用户",desc = "查询流水线用户")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<List<User>> findPipelineUser(@NotNull String pipelineId){

        List<User> dmUser = pipelineService.findPipelineUser(pipelineId);

        return Result.ok(dmUser);
    }

    /**
     * @pi.name:查询最近构建的流水线
     * @pi.url:/findPipelineRecently
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=number;dataType=int;value=查询数量;
     */
    @RequestMapping(path="/findPipelineRecently",method = RequestMethod.POST)
    public Result<List<PipelineRecently>> findPipelineRecently(@NotNull String userId,@NotNull Integer number){

        List<PipelineRecently> userPipeline = pipelineService.findPipelineRecently(userId,number);

        return Result.ok(userPipeline);
    }


    /**
     * @pi.name:克隆流水线
     * @pi.url:/pipelineClone
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param:name=pipelineId;dataType=string;value=流水线Id;
     * @pi.param:name=pipelineName;dataType=string;value=流水线名称;
     */
    @RequestMapping(path="/pipelineClone",method = RequestMethod.POST)
    public Result<Void> pipelineClone(@NotNull String pipelineId,@NotNull String pipelineName){

        pipelineService.pipelineClone(pipelineId,pipelineName);

        return Result.ok();
    }


    /**
     * @pi.name:获取克隆流水线默认名称
     * @pi.url:/findPipelineCloneName
     * @pi.methodType:post
     * @pi.request-type:formdata
     * @pi.param:name=pipelineId;dataType=string;value=pipelineId;
     */
    @RequestMapping(path="/findPipelineCloneName",method = RequestMethod.POST)
    public Result<String> findPipelineCloneName(@NotNull String pipelineId){

        String name = pipelineService.findPipelineCloneName(pipelineId);

        return Result.ok(name);
    }


    /**
     * @pi.name:获取最近打开的流水线
     * @pi.url:/findRecentlyPipeline
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=number;dataType=Integer;value=查询数量;
     * @pi.param: name=pipelineId;dataType=String;value=流水线id;
     */
    @RequestMapping(path="/findRecentlyPipeline",method = RequestMethod.POST)
    public Result<String> findRecentlyPipeline(@NotNull Integer number,@NotNull String pipelineId){

        List<Pipeline> pipelineList = pipelineService.findRecentlyPipeline(number,pipelineId);

        return Result.ok(pipelineList);
    }


    /**
     * @pi.name:流水线导出为Yaml格式
     * @pi.url:/importPipelineYaml
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=request;dataType=HttpServletResponse;value=请求;
     * @pi.param: name=response;dataType=HttpServletRequest;value=请求体;
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



















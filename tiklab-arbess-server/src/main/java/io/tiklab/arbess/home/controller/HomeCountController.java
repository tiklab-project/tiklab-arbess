package io.tiklab.arbess.home.controller;

import io.tiklab.core.Result;
import io.tiklab.arbess.home.service.HomeCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * 导入数据控制器
 * @author admin
 */

@RestController
@RequestMapping("/count")
public class HomeCountController {

    @Autowired
    HomeCountService homeCountService;

    @RequestMapping(path="/findCount",method = RequestMethod.POST)
    public Result<Map<String, Object>> findCount() {
        Map<String, Object> count = homeCountService.findCount();
        return Result.ok(count);
    }

    @RequestMapping(path="/findPipelineCount",method = RequestMethod.POST)
    public Result<Map<String, Object>> findPipelineCount(@NotNull @Valid String pipelineId) {
        Map<String, Object> count = homeCountService.findPipelineCount(pipelineId);
        return Result.ok(count);
    }

    @RequestMapping(path="/findTaskCount",method = RequestMethod.POST)
    public Result<Map<String, Object>> findTaskCount(@NotNull @Valid String pipelineId,@NotNull @Valid String taskId) {
        Map<String, Object> count = homeCountService.findTaskCount(pipelineId,taskId);
        return Result.ok(count);
    }


}

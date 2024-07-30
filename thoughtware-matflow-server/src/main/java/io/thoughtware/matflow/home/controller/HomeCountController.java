package io.thoughtware.matflow.home.controller;

import io.thoughtware.core.Result;
import io.thoughtware.matflow.home.service.HomeCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public Result<Map<String, Object>> createWorkWidget() {
        Map<String, Object> count = homeCountService.findCount();
        return Result.ok(count);
    }


}
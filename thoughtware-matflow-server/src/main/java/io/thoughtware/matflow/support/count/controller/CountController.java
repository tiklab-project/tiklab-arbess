package io.thoughtware.matflow.support.count.controller;

import io.thoughtware.core.Result;
import io.thoughtware.matflow.support.count.CountService;
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
public class CountController {

    @Autowired
    CountService countService;

    @RequestMapping(path="/findCount",method = RequestMethod.POST)
    public Result<Map<String, Object>> createWorkWidget() {
        Map<String, Object> count = countService.findCount();
        return Result.ok(count);
    }


}

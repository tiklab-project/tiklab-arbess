package io.tiklab.arbess.home.controller;

import io.tiklab.core.Result;
import io.tiklab.arbess.home.service.HomeCountService;
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

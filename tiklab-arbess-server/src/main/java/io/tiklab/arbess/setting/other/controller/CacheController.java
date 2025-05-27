package io.tiklab.arbess.setting.other.controller;

import io.tiklab.core.Result;
import io.tiklab.arbess.setting.other.model.Cache;
import io.tiklab.arbess.setting.other.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/cache")
public class CacheController {


    @Autowired
    CacheService cacheService;


    @RequestMapping(path="/findAllCathe",method = RequestMethod.POST)
    public Result<List<Cache>> findAllCathe() {
        List<Cache> allCathe = cacheService.findAllCathe();
        return Result.ok(allCathe);
    }


    @RequestMapping(path="/updateCathe",method = RequestMethod.POST)
    public Result<Void> findPipelineScm(@RequestBody @Valid @NotNull Cache cache) {
        cacheService.updateCathe(cache);
        return Result.ok();
    }


}

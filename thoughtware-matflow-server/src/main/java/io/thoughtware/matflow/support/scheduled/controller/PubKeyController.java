package io.thoughtware.matflow.support.scheduled.controller;

import io.thoughtware.core.Result;
import io.thoughtware.matflow.support.postprocess.model.Postprocess;
import io.thoughtware.matflow.support.postprocess.service.PostprocessService;
import io.thoughtware.matflow.support.scheduled.service.PubKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


@RestController
@RequestMapping("/pubkey")
public class PubKeyController {

    @Autowired
    PubKeyService pubKeyService;


    @RequestMapping(path="/updatePviKey",method = RequestMethod.POST)
    public Result<Void> updatePviKey() {
        pubKeyService.updatePviKey();
        return Result.ok();
    }



}

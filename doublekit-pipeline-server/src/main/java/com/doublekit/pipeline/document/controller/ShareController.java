package com.doublekit.pipeline.document.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.pipeline.document.model.Share;
import com.doublekit.pipeline.document.model.ShareQuery;
import com.doublekit.pipeline.document.service.ShareService;
import com.doublekit.common.page.Pagination;
import com.doublekit.common.Result;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * ShareController
 */
@RestController
@RequestMapping("/share")
@Api(name = "ShareController",desc = "文档分享管理")
public class ShareController {

    private static Logger logger = LoggerFactory.getLogger(ShareController.class);

    @Autowired
    private ShareService shareService;

    @RequestMapping(path="/createShare",method = RequestMethod.POST)
    @ApiMethod(name = "createShare",desc = "createShare")
    @ApiParam(name = "share",desc = "share",required = true)
    public Result<String> createShare(@RequestBody @NotNull @Valid Share share){
        String id = shareService.createShare(share);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateShare",method = RequestMethod.POST)
    @ApiMethod(name = "updateShare",desc = "updateShare")
    @ApiParam(name = "share",desc = "share",required = true)
    public Result<Void> updateShare(@RequestBody @NotNull @Valid Share share){
        shareService.updateShare(share);

        return Result.ok();
    }

    @RequestMapping(path="/deleteShare",method = RequestMethod.POST)
    @ApiMethod(name = "deleteShare",desc = "deleteShare")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteShare(@NotNull String id){
        shareService.deleteShare(id);

        return Result.ok();
    }

    @RequestMapping(path="/findShare",method = RequestMethod.POST)
    @ApiMethod(name = "findShare",desc = "findShare")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Share> findShare(@NotNull String id){
        Share share = shareService.findShare(id);

        return Result.ok(share);
    }

    @RequestMapping(path="/findAllShare",method = RequestMethod.POST)
    @ApiMethod(name = "findAllShare",desc = "findAllShare")
    public Result<List<Share>> findAllShare(){
        List<Share> shareList = shareService.findAllShare();

        return Result.ok(shareList);
    }

    @RequestMapping(path = "/findShareList",method = RequestMethod.POST)
    @ApiMethod(name = "findShareList",desc = "findShareList")
    @ApiParam(name = "shareQuery",desc = "shareQuery",required = true)
    public Result<List<Share>> findShareList(@RequestBody @Valid @NotNull ShareQuery shareQuery){
        List<Share> shareList = shareService.findShareList(shareQuery);

        return Result.ok(shareList);
    }

    @RequestMapping(path = "/findSharePage",method = RequestMethod.POST)
    @ApiMethod(name = "findSharePage",desc = "findSharePage")
    @ApiParam(name = "shareQuery",desc = "shareQuery",required = true)
    public Result<Pagination<Share>> findSharePage(@RequestBody @Valid @NotNull ShareQuery shareQuery){
        Pagination<Share> pagination = shareService.findSharePage(shareQuery);

        return Result.ok(pagination);
    }

    @RequestMapping(path="/addShare",method = RequestMethod.POST)
    @ApiMethod(name = "addShare",desc = "创建分享并返回验证码和链接")
    @ApiParam(name = "share",desc = "share",required = true)
    public Result<Share> addShare(@RequestBody @NotNull @Valid Share share){
        Share share1= shareService.addShare(share);

        return Result.ok(share1);
    }

    @RequestMapping(path="/cutHaveOrNotAuthCode",method = RequestMethod.POST)
    @ApiMethod(name = "cutHaveOrNotAuthCode",desc = "切换有无验证码")
    @ApiParam(name = "shareQuery",desc = "shareQuery",required = true)
    public Result<Share> cutHaveOrNotAuthCode( @RequestBody @NotNull @Valid ShareQuery shareQuery){
        Share share= shareService.cutHaveOrNotAuthCode(shareQuery);

        return Result.ok(share);
    }

    @RequestMapping(path="/verifyAuthCode",method = RequestMethod.POST)
    @ApiMethod(name = "verifyAuthCode",desc = "验证验证码是否正确")
    @ApiParam(name = "shareQuery",desc = "shareQuery",required = true)
    public Result<String> verifyAuthCode(@RequestBody @NotNull @Valid ShareQuery shareQuery){
        String share= shareService.verifyAuthCode(shareQuery);

        return Result.ok(share);
    }

    @RequestMapping(path="/judgeAuthCode",method = RequestMethod.POST)
    @ApiMethod(name = "judgeAuthCode",desc = "判断是否需要验证码")
    @ApiParam(name = "shareLink",desc = "shareLink",required = true)
    public Result<String> judgeAuthCode(@NotNull String shareLink){
        String share= shareService.judgeAuthCode(shareLink);

        return Result.ok(share);
    }
}

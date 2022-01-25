package com.doublekit.pipeline.document.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.pipeline.document.model.DocumentAttach;
import com.doublekit.pipeline.document.model.DocumentAttachQuery;
import com.doublekit.pipeline.document.service.DocumentAttachService;
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
 * DocumentAttachController
 */
@RestController
@RequestMapping("/documentAttach")
@Api(name = "DocumentAttachController",desc = "文档附件管理")
public class DocumentAttachController {

    private static Logger logger = LoggerFactory.getLogger(DocumentAttachController.class);

    @Autowired
    private DocumentAttachService documentAttachService;

    @RequestMapping(path="/createDocumentAttach",method = RequestMethod.POST)
    @ApiMethod(name = "createDocumentAttach",desc = "创建文档附件")
    @ApiParam(name = "documentAttach",desc = "documentAttach",required = true)
    public Result<String> createDocumentAttach(@RequestBody @NotNull @Valid DocumentAttach documentAttach){
        String id = documentAttachService.createDocumentAttach(documentAttach);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateDocumentAttach",method = RequestMethod.POST)
    @ApiMethod(name = "updateDocumentAttach",desc = "修改文档附件")
    @ApiParam(name = "documentAttach",desc = "documentAttach",required = true)
    public Result<Void> updateDocumentAttach(@RequestBody @NotNull @Valid DocumentAttach documentAttach){
        documentAttachService.updateDocumentAttach(documentAttach);

        return Result.ok();
    }

    @RequestMapping(path="/deleteDocumentAttach",method = RequestMethod.POST)
    @ApiMethod(name = "deleteDocumentAttach",desc = "删除文档附件")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteDocumentAttach(@NotNull String id){
        documentAttachService.deleteDocumentAttach(id);

        return Result.ok();
    }

    @RequestMapping(path="/findDocumentAttach",method = RequestMethod.POST)
    @ApiMethod(name = "findDocumentAttach",desc = "通过id查询文档附件")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<DocumentAttach> findDocumentAttach(@NotNull String id){
        DocumentAttach documentAttach = documentAttachService.findDocumentAttach(id);

        return Result.ok(documentAttach);
    }

    @RequestMapping(path="/findAllDocumentAttach",method = RequestMethod.POST)
    @ApiMethod(name = "findAllDocumentAttach",desc = "查询所有文档附件")
    public Result<List<DocumentAttach>> findAllDocumentAttach(){
        List<DocumentAttach> documentAttachList = documentAttachService.findAllDocumentAttach();

        return Result.ok(documentAttachList);
    }

    @RequestMapping(path = "/findDocumentAttachList",method = RequestMethod.POST)
    @ApiMethod(name = "findDocumentAttachList",desc = "通过查询条件查询文档附件")
    @ApiParam(name = "documentAttachQuery",desc = "documentAttachQuery",required = true)
    public Result<List<DocumentAttach>> findDocumentAttachList(@RequestBody @Valid @NotNull DocumentAttachQuery documentAttachQuery){
        List<DocumentAttach> documentAttachList = documentAttachService.findDocumentAttachList(documentAttachQuery);

        return Result.ok(documentAttachList);
    }

    @RequestMapping(path = "/findDocumentAttachPage",method = RequestMethod.POST)
    @ApiMethod(name = "findDocumentAttachPage",desc = "通过查询条件分页查询附件")
    @ApiParam(name = "documentAttachQuery",desc = "documentAttachQuery",required = true)
    public Result<Pagination<DocumentAttach>> findDocumentAttachPage(@RequestBody @Valid @NotNull DocumentAttachQuery documentAttachQuery){
        Pagination<DocumentAttach> pagination = documentAttachService.findDocumentAttachPage(documentAttachQuery);

        return Result.ok(pagination);
    }

}

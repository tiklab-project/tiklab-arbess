package com.doublekit.pipeline.document.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.pipeline.document.model.DocumentTemplate;
import com.doublekit.pipeline.document.model.DocumentTemplateQuery;
import com.doublekit.pipeline.document.service.DocumentTemplateService;
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
 * DocumentTemplateController
 */
@RestController
@RequestMapping("/documentTemplate")
@Api(name = "DocumentTemplateController",desc = "文档模板管理")
public class DocumentTemplateController {

    private static Logger logger = LoggerFactory.getLogger(DocumentTemplateController.class);

    @Autowired
    private DocumentTemplateService documentTemplateService;

    @RequestMapping(path="/createDocumentTemplate",method = RequestMethod.POST)
    @ApiMethod(name = "createDocumentTemplate",desc = "创建文档模板")
    @ApiParam(name = "documentTemplate",desc = "documentTemplate",required = true)
    public Result<String> createDocumentTemplate(@RequestBody @NotNull @Valid DocumentTemplate documentTemplate){
        String id = documentTemplateService.createDocumentTemplate(documentTemplate);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateDocumentTemplate",method = RequestMethod.POST)
    @ApiMethod(name = "updateDocumentTemplate",desc = "修改文档模板")
    @ApiParam(name = "documentTemplate",desc = "documentTemplate",required = true)
    public Result<Void> updateDocumentTemplate(@RequestBody @NotNull @Valid DocumentTemplate documentTemplate){
        documentTemplateService.updateDocumentTemplate(documentTemplate);

        return Result.ok();
    }

    @RequestMapping(path="/deleteDocumentTemplate",method = RequestMethod.POST)
    @ApiMethod(name = "deleteDocumentTemplate",desc = "删除文档模板")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteDocumentTemplate(@NotNull String id){
        documentTemplateService.deleteDocumentTemplate(id);

        return Result.ok();
    }

    @RequestMapping(path="/findDocumentTemplate",method = RequestMethod.POST)
    @ApiMethod(name = "findDocumentTemplate",desc = "通过id查询文档模板")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<DocumentTemplate> findDocumentTemplate(@NotNull String id){
        DocumentTemplate documentTemplate = documentTemplateService.findDocumentTemplate(id);

        return Result.ok(documentTemplate);
    }

    @RequestMapping(path="/findAllDocumentTemplate",method = RequestMethod.POST)
    @ApiMethod(name = "findAllDocumentTemplate",desc = "查询所有文档模板")
    public Result<List<DocumentTemplate>> findAllDocumentTemplate(){
        List<DocumentTemplate> documentTemplateList = documentTemplateService.findAllDocumentTemplate();

        return Result.ok(documentTemplateList);
    }

    @RequestMapping(path = "/findDocumentTemplateList",method = RequestMethod.POST)
    @ApiMethod(name = "findDocumentTemplateList",desc = "通过查询对象查询文档模板")
    @ApiParam(name = "documentTemplateQuery",desc = "documentTemplateQuery",required = true)
    public Result<List<DocumentTemplate>> findDocumentTemplateList(@RequestBody @Valid @NotNull DocumentTemplateQuery documentTemplateQuery){
        List<DocumentTemplate> documentTemplateList = documentTemplateService.findDocumentTemplateList(documentTemplateQuery);

        return Result.ok(documentTemplateList);
    }

    @RequestMapping(path = "/findDocumentTemplatePage",method = RequestMethod.POST)
    @ApiMethod(name = "findDocumentTemplatePage",desc = "通过查询对象分页查询文档目模板")
    @ApiParam(name = "documentTemplateQuery",desc = "documentTemplateQuery",required = true)
    public Result<Pagination<DocumentTemplate>> findDocumentTemplatePage(@RequestBody @Valid @NotNull DocumentTemplateQuery documentTemplateQuery){
        Pagination<DocumentTemplate> pagination = documentTemplateService.findDocumentTemplatePage(documentTemplateQuery);

        return Result.ok(pagination);
    }

}

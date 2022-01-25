package com.doublekit.pipeline.document.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.pipeline.document.model.Document;
import com.doublekit.pipeline.document.model.DocumentQuery;
import com.doublekit.pipeline.document.service.DocumentService;
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
 * DocumentController
 */
@RestController
@RequestMapping("/document")
@Api(name = "DocumentController",desc = "文档管理")
public class DocumentController {

    private static Logger logger = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    private DocumentService documentService;


    @RequestMapping(path="/createDocument",method = RequestMethod.POST)
    @ApiMethod(name = "createDocument",desc = "创建文档")
    @ApiParam(name = "document",desc = "document",required = true)
    public Result<String> createDocument(@RequestBody @NotNull @Valid Document document){
        String id = documentService.createDocument(document);

        return Result.ok(id);
    }


    @RequestMapping(path="/updateDocument",method = RequestMethod.POST)
    @ApiMethod(name = "updateDocument",desc = "修改文档")
    @ApiParam(name = "document",desc = "document",required = true)
    public Result<Void> updateDocument(@RequestBody @NotNull @Valid Document document){
        documentService.updateDocument(document);

        return Result.ok();
    }


    @RequestMapping(path="/deleteDocument",method = RequestMethod.POST)
    @ApiMethod(name = "deleteDocument",desc = "通过id删除文档")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteDocument(@NotNull String id){
        documentService.deleteDocument(id);

        return Result.ok();
    }


    @RequestMapping(path="/findDocument",method = RequestMethod.POST)
    @ApiMethod(name = "findDocument",desc = "通过id查询文档")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Document> findDocument(@NotNull String id){
        Document document = documentService.findDocument(id,null);

        return Result.ok(document);
    }

    @RequestMapping(path="/findAllDocument",method = RequestMethod.POST)
    @ApiMethod(name = "findAllDocument",desc = "findAllDocument")
    public Result<List<Document>> findAllDocument(){
        List<Document> documentList = documentService.findAllDocument();

        return Result.ok(documentList);
    }


    @RequestMapping(path = "/findDocumentList",method = RequestMethod.POST)
    @ApiMethod(name = "findDocumentList",desc = "findDocumentList")
    @ApiParam(name = "documentQuery",desc = "documentQuery",required = true)
    public Result<List<Document>> findDocumentList(@RequestBody @Valid @NotNull DocumentQuery documentQuery){
        List<Document> documentList = documentService.findDocumentList(documentQuery);

        return Result.ok(documentList);
    }


    @RequestMapping(path = "/findDocumentPage",method = RequestMethod.POST)
    @ApiMethod(name = "findDocumentPage",desc = "findDocumentPage")
    @ApiParam(name = "documentQuery",desc = "documentQuery",required = true)
    public Result<Pagination<Document>> findDocumentPage(@RequestBody @Valid @NotNull DocumentQuery documentQuery){
        Pagination<Document> pagination = documentService.findDocumentPage(documentQuery);

        return Result.ok(pagination);
    }

    @RequestMapping(path="/findDocumentById",method = RequestMethod.POST)
    @ApiMethod(name = "findDocumentById",desc = "通过id查询")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Document> findDocumentById(@NotNull String id){
        Document document = documentService.findDocumentById(id);

        return Result.ok(document);
    }


}

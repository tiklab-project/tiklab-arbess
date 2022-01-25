package com.doublekit.pipeline.integration.cf.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.common.Result;
import com.doublekit.common.exception.ApplicationException;
import com.doublekit.pipeline.integration.cf.service.CfImportDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/importDate")
@Api(name = "ImportDateController",desc = "导入第三方数据数据")
public class ImportDataController {
    @Autowired
    CfImportDataService cfImportDataService;


    @RequestMapping(path="/importConfluenceDate",method = RequestMethod.POST)
    @ApiMethod(name = "importConfluenceDate",desc = "导入jire数据")
    @ApiParam(name = "uploadFile",desc = "压缩包文件",required = true)
    public Result imporatConfluenceDate(@RequestParam("uploadFile")MultipartFile uploadFile){
       // importDateService.importConfluenceDate(null);
        if (uploadFile == null) {
            throw new ApplicationException("uploadFile must not be null.");
        }else {
            try {
                InputStream inputStream = uploadFile.getInputStream();
                String date = cfImportDataService.importConfluenceData(inputStream);
                return Result.ok(date);
            } catch (IOException e) {
                throw new ApplicationException(e);
            }
        }
    }
}

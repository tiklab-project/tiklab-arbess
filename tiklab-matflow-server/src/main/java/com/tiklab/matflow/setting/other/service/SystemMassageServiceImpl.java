package com.tiklab.matflow.setting.other.service;

import com.tiklab.matflow.definition.service.PipelineCommonService;
import com.tiklab.matflow.setting.other.model.SystemMassage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemMassageServiceImpl implements  SystemMassageService{


    @Autowired
    PipelineCommonService pipelineCommonService;

    @Autowired
    public SystemMassage getSystemMassage(){
        return new SystemMassage();
    }


    public List<String> getSystemLog(){
        String address = System.getProperty("user.dir");
        String files = address+ "/LOG_PATH_IS_UNDEFINED/doublekit/doublekit-pipeline/logs/app.pipelineExecLog";
        String property = System.getProperty("os.name");
        String[] s = property.split(" ");
        if (s[0].equals("Windows")){
            files = address+ "\\LOG_PATH_IS_UNDEFINED\\doublekit\\doublekit-pipeline\\logs\\app.pipelineExecLog";
        }
        List<String> list = pipelineCommonService.readFile(files);
        if (list.size() < 1000){
            return list;
        }
        return list.subList(list.size()-1000, list.size());
    }
}

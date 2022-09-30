package net.tiklab.pipeline.setting.service;


import net.tiklab.pipeline.orther.service.PipelineFileService;
import net.tiklab.pipeline.setting.model.SystemMassage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemMassageServiceImpl implements SystemMassageService {


    @Autowired
    PipelineFileService pipelineFileService;

    @Autowired
    public SystemMassage getSystemMassage(){
        return new SystemMassage();
    }


    //public List<String> getSystemLog(){
    //    String address = System.getProperty("user.dir");
    //    String files = address+ "/LOG_PATH_IS_UNDEFINED/doublekit/doublekit-pipeline/logs/app.pipelineExecLog";
    //    String property = System.getProperty("os.name");
    //    String[] s = property.split(" ");
    //    if (s[0].equals("Windows")){
    //        files = address+ "\\LOG_PATH_IS_UNDEFINED\\doublekit\\doublekit-pipeline\\logs\\app.pipelineExecLog";
    //    }
    //    List<String> list = pipelineCommonService.readFile(files);
    //    if (list.size() < 1000){
    //        return list;
    //    }
    //    return list.subList(list.size()-1000, list.size());
    //}
}

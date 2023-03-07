package io.tiklab.matflow.setting.service;


import io.tiklab.matflow.support.util.PipelineUtil;
import io.tiklab.matflow.setting.model.SystemMassage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemMassageServiceImpl implements SystemMassageService {


    @Autowired
    public SystemMassage getSystemMassage(){
        SystemMassage systemMassage = new SystemMassage();
        systemMassage.setWorkspace(PipelineUtil.findFileAddress(null,1));
        return systemMassage;
    }


}

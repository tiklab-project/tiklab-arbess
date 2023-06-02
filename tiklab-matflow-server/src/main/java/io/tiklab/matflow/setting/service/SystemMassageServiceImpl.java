package io.tiklab.matflow.setting.service;


import io.tiklab.matflow.setting.model.SystemMassage;
import io.tiklab.matflow.support.util.PipelineUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SystemMassageServiceImpl implements SystemMassageService {


    @Autowired
    private PipelineUtilService utilService;


    @Autowired
    public SystemMassage getSystemMassage(){
        SystemMassage systemMassage = new SystemMassage();
        String s = utilService.instanceAddress(1);
        systemMassage.setWorkspace(s);
        return systemMassage;
    }

    @Value("${matflow.cloud:true}")
    boolean idCe;



}

package io.thoughtware.matflow.setting.service;


import io.thoughtware.matflow.support.util.PipelineUtilService;
import io.thoughtware.matflow.setting.model.SystemMassage;
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

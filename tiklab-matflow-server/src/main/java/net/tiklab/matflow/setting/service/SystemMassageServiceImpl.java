package net.tiklab.matflow.setting.service;


import net.tiklab.matflow.setting.model.SystemMassage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemMassageServiceImpl implements SystemMassageService {


    @Autowired
    public SystemMassage getSystemMassage(){
        return new SystemMassage();
    }


}

package io.tiklab.matflow.support.backups.service;


import io.tiklab.matflow.support.backups.model.MatFlowBackups;

import java.io.InputStream;

public interface MatFlowDbRestoreService {


    String uploadBackups(String fileName, InputStream inputStream);


    void execRestore(String filePath);


    MatFlowBackups findRestoreResult();





}

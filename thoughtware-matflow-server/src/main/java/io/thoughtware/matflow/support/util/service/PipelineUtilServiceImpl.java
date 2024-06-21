package io.thoughtware.matflow.support.util.service;

import io.thoughtware.core.context.AppHomeContext;
import io.thoughtware.core.exception.ApplicationException;
import io.thoughtware.matflow.support.util.util.PipelineFileUtil;
import io.thoughtware.matflow.support.util.util.PipelineFinal;
import io.thoughtware.matflow.support.util.util.PipelineUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.*;

/**
 * @author zcamy
 */
@Service
public class PipelineUtilServiceImpl implements PipelineUtilService {

    @Value("${DATA_HOME:null}")
    String dataHome;

    @Value("${jdk.address:null}")
    String jdkPath;

    @Override
    public String instanceAddress(int type) {
        if (Objects.isNull(dataHome) || "null".equals(dataHome)){
            dataHome = "/opt/tiklab/matflow";
        }
        if (type == 1){
            return dataHome + PipelineFinal.MATFLOW_WORKSPACE;
        }else {
            return dataHome + PipelineFinal.MATFLOW_LOGS;
        }
    }

    @Override
    public String findPipelineDefaultAddress(String pipelineId, int type) {
        String path = instanceAddress(type);
        int systemType = PipelineUtil.findSystemType();
        if (systemType == 1){
            if (!PipelineUtil.isNoNull(pipelineId)){
                return path + "\\";
            }else {
                return path + "\\" + pipelineId + "\\";
            }
        }else {
            if (!PipelineUtil.isNoNull(pipelineId)){
                return path + "/";
            }else {
                return path + "/" + pipelineId + "/" ;
            }
        }
    }


    @Override
    public  String findFile(String pipelineId,String fileDir, String regex) {
        List<String> list = new ArrayList<>();

        File file3 = new File(fileDir);
        if (file3.exists() && file3.isFile()){
            return fileDir;
        }

        File file2 = new File(fileDir + "/" + regex);
        if (file2.exists() && file2.isFile()){
            return file2.getAbsolutePath();
        }

        List<String> filePath = PipelineFileUtil.getFilePath(new File(fileDir),new ArrayList<>());
        for (String s : filePath) {
            File file = new File(s);

            //拼装正则匹配
            boolean matches = file.getName().matches("^(.*" + regex + ".*)");

            //正则匹配
            boolean matches1 = file.getName().matches(regex);

            File file1 = new File(s + "/" + regex);
            if (file1.exists()){
                return file1.getAbsolutePath();
            }

            if (matches || matches1){
                list.add(s);
            }
        }

        if (list.size() > 1){
            StringBuilder s  = new StringBuilder("匹配到多个文件，请重新输入文件信息。");
            for (String s1 : list) {
                s.append("\n").append(s1);
            }
            throw new ApplicationException(s.toString());
        }

        if (list.size()== 1){
            return list.get(0);
        }
        throw new ApplicationException("没有匹配到文件。");
    }


    @Override
    public String findJavaPath(){

        if (!"null".equals(jdkPath)){
            return jdkPath;
        }

        String appHome = AppHomeContext.getAppHome();
        String applyRootDir = new File(appHome).getParentFile().getParent();

        return applyRootDir+"/embbed/jdk-16.0.2";

    }


}

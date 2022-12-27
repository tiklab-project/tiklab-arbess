package net.tiklab.matflow.achieve.server;

import com.jcraft.jsch.*;
import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.definition.service.PipelineStagesTaskServer;
import net.tiklab.matflow.definition.service.PipelineTasksService;
import net.tiklab.matflow.execute.model.PipelineProcess;
import net.tiklab.matflow.execute.service.PipelineExecCommonService;
import net.tiklab.matflow.orther.until.PipelineFinal;
import net.tiklab.matflow.orther.until.PipelineUntil;
import net.tiklab.matflow.setting.model.PipelineAuthHost;
import net.tiklab.matflow.setting.model.PipelineAuthThird;
import net.tiklab.matflow.task.model.PipelineProduct;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
@Exporter
public class ProductServiceImpl implements ProductService {

    @Autowired
    PipelineExecCommonService commonService;

    @Autowired
    PipelineTasksService tasksService;

    @Autowired
    PipelineStagesTaskServer stagesTaskServer;

    /**
     * 推送制品代码
     * @param pipelineProcess 执行信息
     * @return 执行状态
     */
    @Override
    public boolean product(PipelineProcess pipelineProcess, String configId ,int taskType) {

        Pipeline pipeline = pipelineProcess.getPipeline();
        Object o;
        if (pipeline.getType() == 1){
            o = tasksService.findOneTasksTask(configId);
        }else {
            o = stagesTaskServer.findOneStagesTasksTask(configId);
        }

        PipelineProduct product = (PipelineProduct) o;
        String name = product.getName();

        commonService.execHistory(pipelineProcess, "\n"+ PipelineUntil.date(4)+"执行任务："+name);

        product.setType(taskType);

        String fileAddress = product.getFileAddress();
        String path;
        try {
             path = PipelineUntil.getFile(pipeline.getId(),fileAddress);
        }catch (ApplicationException e){
            commonService.execHistory(pipelineProcess,PipelineUntil.date(4)+e);
            return false;
        }

        if (path == null){
            commonService.execHistory(pipelineProcess,PipelineUntil.date(4)+"匹配不到制品");
            return false;
        }

        commonService.execHistory(pipelineProcess,
                PipelineUntil.date(4)+"制品匹配成功\n"+
                    PipelineUntil.date(4)+"制品名称："+ new File(path).getName() + "\n"+
                    PipelineUntil.date(4)+"制品地址："+path);
        try {
            if (product.getType() == 51){

                Process process = getProductOrder(product,path);
                pipelineProcess.setError(error(product.getType()));
                pipelineProcess.setEnCode("UTF-8");
                commonService.execState(pipelineProcess,process,name);
                process.destroy();

            }else {
                commonService.execHistory(pipelineProcess, PipelineUntil.date(4)+"连接制品服务器。");
                Session session = createSession(product);
                commonService.execHistory(pipelineProcess, PipelineUntil.date(4)+"制品服务器连接成功。");
                String putAddress = product.getPutAddress();
                commonService.execHistory(pipelineProcess, PipelineUntil.date(4)+"开始推送制品。");
                sshPut(session,path,putAddress);
            }
        } catch (IOException | ApplicationException e) {
            commonService.execHistory(pipelineProcess, PipelineUntil.date(4)+"推送制品执行错误\n"+ PipelineUntil.date(4)+e.getMessage());
            return false;
        } catch (JSchException e) {
            commonService.execHistory(pipelineProcess, PipelineUntil.date(4)+"无法连接到服务器\n" +PipelineUntil.date(4)+e.getMessage());
            return false;
        } catch (SftpException e) {
            commonService.execHistory(pipelineProcess, PipelineUntil.date(4)+"文件发送失败\n"+ PipelineUntil.date(4)+e.getMessage());
            return false;
        }
        commonService.execHistory(pipelineProcess, PipelineUntil.date(4)+"推送制品完成");
        return true;
    }

    //推送到nexus
    private Process getProductOrder(PipelineProduct product, String path) throws ApplicationException, IOException {
        String order ;
        String execOrder =  "mvn deploy:deploy-file ";

        String mavenAddress = commonService.getScm(21);
        if (mavenAddress == null) {
            throw new ApplicationException("不存在maven配置");
        }

        PipelineUntil.validFile(mavenAddress, 21);

        PipelineAuthThird authThird = (PipelineAuthThird)product.getAuth();
        if (authThird == null){
            order = mavenOrder(execOrder, path);
            return PipelineUntil.process(mavenAddress, order);
        }

        execOrder = execOrder +
                " -DgroupId="+product.getGroupId() +
                " -DartifactId="+product.getArtifactId() +
                " -Dversion="+product.getVersion()+
                " -Dpackaging="+product.getFileType() +
                " -Dfile="+path +
                " -Durl="+authThird.getServerAddress() ;
        if (authThird.getAuthType() == 1){
            String id = PipelineFinal.appName;

            if (!PipelineUntil.isNoNull(settingAddress)){
                settingAddress = "conf/settings.xml";
            }

            String s = System.getProperty("user.dir") + "/" + settingAddress ;
            execOrder = execOrder +
                    " -Dusername="+authThird.getUsername()+
                    " -Dpassword="+authThird.getPassword()+
                    " -Did="+id+
                    " -DrepositoryId="+id+
                    " -s"+" "+s;
        }else {
            execOrder = execOrder +
                    " -DrepositoryId="+authThird.getPrivateKey();
        }

        System.out.println("命令为："+execOrder);

        order = mavenOrder(execOrder, path);
        return PipelineUntil.process(mavenAddress, order);
    }

    @Value("${setting.address:null}")
    private String settingAddress;

    /**
     * 创建连接实例
     * @param product 连接配置信息
     * @return 实例
     * @throws JSchException 连接失败
     */
    private Session createSession(PipelineProduct product) throws JSchException {
        PipelineAuthHost authHost = (PipelineAuthHost) product.getAuth();
        String sshIp = authHost.getIp();
        int sshPort = authHost.getPort();
        String username = authHost.getUsername();
        String password = authHost.getPassword();
        JSch jsch = new JSch();
        if (!PipelineUntil.isNoNull(username)){
            username = "root";
        }
        Session session = jsch.getSession(username, sshIp, sshPort);
        if (authHost.getAuthType() == 2){
            String tempFile = PipelineUntil.createTempFile(authHost.getPrivateKey());
            if (!PipelineUntil.isNoNull(tempFile)){
                throw new ApplicationException("写入私钥失败。");
            }
            jsch.addIdentity(tempFile);
            PipelineUntil.deleteFile(new File(tempFile));
        }else {
            session.setPassword(password);
        }
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();

        return session;
    }

    /**
     * 发送文件
     * @param session 连接实例
     * @param localFile 文件
     * @throws JSchException 连接失败
     */
    private void sshPut(Session session, String localFile, String uploadAddress) throws JSchException, SftpException {
        ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");
        sftp.connect();
        //判断目录是否存在
        try {
            sftp.lstat(uploadAddress);
        }catch (SftpException e){
            sftp.mkdir(uploadAddress);
        }
        //ChannelSftp.OVERWRITE 覆盖上传
        sftp.put(localFile,uploadAddress,ChannelSftp.OVERWRITE);
        sftp.disconnect();
    }

    //拼装maven命令
    private String mavenOrder(String buildOrder,String path){
        String order = " ./" + buildOrder  ;
        if (PipelineUntil.findSystemType() == 1){
            order = " .\\" + buildOrder ;
        }
        return order;
    }

    private String[] error(int type){
        String[] strings;
        if (type == 5){
            strings = new String[]{
                    "svn: E170000:",
                    "invalid option",
                    "Error executing Maven",
                    "The specified user settings file does not exist",
                    "405 HTTP method PUT is not supported by this URL"
            };
            return strings;
        }
        strings = new String[]{

        };
        return strings;
    }

}

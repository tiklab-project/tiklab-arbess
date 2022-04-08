package com.doublekit.pipeline.instance.service.execAchieve;

import ch.ethz.ssh2.Connection;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.pipeline.instance.service.PipelineExecLogService;
import com.doublekit.pipeline.setting.proof.model.Proof;
import com.doublekit.rpc.annotation.Exporter;
import com.jcraft.jsch.*;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Exporter
public class CommonAchieve {

    @Autowired
    PipelineExecLogService pipelineExecLogService;

    //存放过程状态
    List<PipelineExecLog> pipelineExecLogList = new ArrayList<>();

    //存放构建流水线id
    List<String> pipelineIdList = new ArrayList<>();

    /**
     *  执行ssh命令
     * @param proof 凭证信息
     * @param order 执行命令
     * @param pipelineExecLog 日志信息
     * @return 执行状态
     * @throws IOException 日志读写异常
     */
    public Map<String, String> sshOrder(Proof proof, String order, PipelineExecLog pipelineExecLog) throws IOException {
        Connection conn = new Connection(proof.getProofIp(),proof.getProofPort());
        conn.connect();
        conn.authenticateWithPassword(proof.getProofUsername(), proof.getProofPassword());
        ch.ethz.ssh2.Session session = conn.openSession();
        pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog() + "\n" + order);
        session.execCommand(order);
        InputStreamReader inputStreamReader = new InputStreamReader(session.getStdout());
        Map<String, String> map = log(inputStreamReader, pipelineExecLog);
        session.close();
        inputStreamReader.close();
        return map;
    }

    /**
     * 执行产生的日志
     * @param inputStreamReader 执行信息
     * @param pipelineExecLog 日志信息
     * @throws IOException 字符流装换异常
     */
    public Map<String, String> log(InputStreamReader inputStreamReader , PipelineExecLog pipelineExecLog) throws IOException {
        Map<String, String> map = new HashMap<>();
        String s;
        //InputStreamReader  inputStreamReader = new InputStreamReader(process.getInputStream());
        BufferedReader  bufferedReader = new BufferedReader(inputStreamReader);
        String logRunLog = "";
        //更新日志信息
        while ((s = bufferedReader.readLine()) != null) {
            logRunLog = logRunLog + s + "\n";
            pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog()+s+"\n");
            pipelineExecLogList.add(pipelineExecLog);
            if (logRunLog.contains("BUILD FAILURE")){
                pipelineExecLogList.add(pipelineExecLog);
                map.put("state","0");
                map.put("log",logRunLog);
                return map;
            }
        }
        map.put("state","1");
        map.put("log",logRunLog);
        inputStreamReader.close();
        bufferedReader.close();
        return map;
    }

    /**
     * ssh 连接发送文件
     * @param proof 凭证信息
     * @param nowPath 部署文件地址
     * @param lastPath 本机文件地址
     */
    public void sshSftp(Proof proof, String nowPath, String lastPath) throws JSchException, SftpException, IOException {

        //采用指定的端口连接服务器
        Session session = new JSch().getSession(proof.getProofUsername(), proof.getProofIp() ,proof.getProofPort());
        //如果服务器连接不上，则抛出异常
        if (session == null) {
            throw new JSchException(proof.getProofIp() + "连接异常。。。。");
        }
        //设置第一次登陆的时候提示，可选值：(ask | yes | no)
        session.setConfig("StrictHostKeyChecking", "no");
        //设置登陆主机的密码
        session.setPassword(proof.getProofPassword());
        //设置登陆超时时间 10s
        session.connect(10000);
        //调用发送方法
        sshSending(session,nowPath,lastPath);

        session.disconnect();

    }

    /**
     * 发送文件
     * @param session 连接
     * @param nowPath 部署文件地址
     * @param lastPath 本机文件地址
     */
    public void sshSending(Session session,String nowPath,String lastPath) throws JSchException, IOException, SftpException {

        ChannelSftp channel;
        //创建sftp通信通道
        channel = (ChannelSftp) session.openChannel("sftp");
        channel.connect();
        ChannelSftp sftp = channel;

        //发送
        OutputStream outputStream  = sftp.put(nowPath);
        InputStream inputStream =  new FileInputStream(new File(lastPath));

        byte[] b = new byte[1024];
        int n;
        while ((n = inputStream.read(b)) != -1) {
            outputStream.write(b, 0, n);
        }
        //关闭流
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }


    /**
     * 获取符合条件的文件名
     * @param path 文件地址
     * @param s 文件后缀名
     * @return 文件名
     */
    public  String zipName(String path,String s) {

        File f = new File(path);
        List<String> list = new ArrayList<>();
        File[] fa = f.listFiles();
        String regex = ".*."+s;
        if (fa != null){
            for (File fs : fa) {
                //判断符合条件的文件
                if (fs.getName().matches(regex)) {
                    if (!fs.isDirectory()) {
                        list.add(fs.getName());
                    }
                }
            }
            //获取符合条件的
            if (list.size() == 1){
                return list.get(0);
            }
        }
        return null;
    }


    /**
     * 调用cmd执行命令
     * @param path 构建地址
     * @param order 构建命令
     * @return 构建信息
     * @throws IOException 构建命令执行异常
     */
    public Process process(String path,String order,String sourceAddress) throws IOException {
        Runtime runtime=Runtime.getRuntime();
        Process process;
        if (sourceAddress != null){
            process = runtime.exec("cmd.exe /c cd " + path + "\\"+sourceAddress + " &&" + " " + order);
            return process;
        }
        //执行命令
        process = runtime.exec("cmd.exe /c cd " + path + " &&" + " " + order);
        return process;
    }


    /**
     * 凭证信息（UsernamePassword）方式
     * @param gitUser 用户名
     * @param gitPasswd 密码
     * @return 验证信息
     */
    public  UsernamePasswordCredentialsProvider usernamePassword(String gitUser, String gitPasswd) {

        UsernamePasswordCredentialsProvider credentialsProvider = null;
        if (StringUtils.isNotEmpty(gitUser) && StringUtils.isNotEmpty(gitPasswd)) {
            credentialsProvider = new UsernamePasswordCredentialsProvider(gitUser, gitPasswd);
        }
        return credentialsProvider;
    }

    /**
     * 删除旧的代码
     * @param dir 文件地址
     */
    public boolean deleteFile(File dir){
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            if (children != null) {
                for (String child : children) {
                    boolean success = deleteFile(new File(dir, child));
                    if (!success) {
                        return false;
                    }
                }
            }
            // 目录此时为空，可以删除
        }
        return dir.delete();
    }


    /**
     * 输出错误信息
     * @param pipelineExecLog 日志
     * @param pipelineId 流水线id
     * @param e 错误信息
     */
    public  void  error(PipelineExecLog pipelineExecLog, String e, String pipelineId){
        if (pipelineExecLog.getLogRunLog() != null){
            pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog()+ "\n" + e + "\n" + " RUN RESULT : FAIL");
        }else {
            pipelineExecLog.setLogRunLog("\n" + e + "\n"+ " RUN RESULT : FAIL");
        }
        pipelineExecLog.setLogRunStatus(1);
        // 清空缓存
        clean(pipelineExecLog,pipelineId);
    }

    /**
     * 输出成功信息
     * @param pipelineExecLog 日志
     * @param pipelineId 流水线id
     */
    public  void  success(PipelineExecLog pipelineExecLog, String pipelineId) {
        if (pipelineExecLog.getLogRunLog() != null){
            pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog()+ "\n"  + "\n" + " RUN RESULT : SUCCESS");
        }else {
            pipelineExecLog.setLogRunLog( "\n"+ " RUN RESULT : SUCCESS");
        }
        pipelineExecLog.setLogRunStatus(30);
        //清空缓存
        clean(pipelineExecLog,pipelineId);

    }


    //清空缓存
    public  void clean(PipelineExecLog pipelineExecLog, String pipelineId){
        pipelineExecLogList.add(pipelineExecLog);

        //执行完成移除构建id
        if (pipelineIdList != null) {
            pipelineIdList.removeIf(id -> id.equals(pipelineId));
        }
        pipelineExecLogService.updateLog(pipelineExecLog);
        //恢复中断状态
        try {
            Thread.sleep(1000);
        } catch (InterruptedException s) {
            Thread.currentThread().interrupt();
        }
        // 清除集合缓存
        pipelineExecLogList.removeIf(log -> log.getPipelineId().equals(pipelineId));
    }

}

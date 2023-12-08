package io.tiklab.matflow.home.service;

import com.alibaba.fastjson.JSONObject;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.eam.common.context.LoginContext;
import io.tiklab.matflow.pipeline.definition.model.Pipeline;
import io.tiklab.matflow.support.util.PipelineFileUtil;
import io.tiklab.matflow.support.util.PipelineFinal;
import io.tiklab.matflow.support.util.PipelineUtil;
import io.tiklab.message.message.model.Message;
import io.tiklab.message.message.model.MessageReceiver;
import io.tiklab.message.message.model.SendMessageNotice;
import io.tiklab.message.message.service.SendMessageNoticeService;
import io.tiklab.message.message.service.SingleSendMessageService;
import io.tiklab.message.setting.model.MessageType;
import io.tiklab.message.sms.modal.Sms;
import io.tiklab.rpc.annotation.Exporter;
import io.tiklab.security.logging.model.Logging;
import io.tiklab.security.logging.model.LoggingType;
import io.tiklab.security.logging.service.LoggingByTemplService;
import io.tiklab.user.user.model.User;
import io.tiklab.user.user.service.UserService;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Timestamp;
import java.util.*;
import java.util.zip.GZIPOutputStream;

@Service
public class PipelineHomeServiceImpl implements PipelineHomeService {

    @Autowired
    UserService userService;

    @Autowired
    SingleSendMessageService sendMessage;

    @Autowired
    LoggingByTemplService logService;

    @Autowired
    SendMessageNoticeService dispatchNoticeService;


    @Value("${base.url:null}")
    String baseUrl;

    String appName = PipelineFinal.appName;

    /**
     * 初始化消息，日志信息
     * @param pipeline 流水线
     * @return 信息
     */
    @Override
    public HashMap<String,Object> initMap(Pipeline pipeline){
        HashMap<String,Object> map = new HashMap<>();
        String userId = LoginContext.getLoginId();
        if (Objects.isNull(userId)){
            userId = pipeline.getUser().getId();
        }
        User user = userService.findOne(userId);
        map.put("pipelineId", pipeline.getId());
        map.put("pipelineName", pipeline.getName());
        map.put("name", pipeline.getName().substring(0,1).toUpperCase());
        map.put("userName", user.getName());
        map.put("rootId",user.getId());
        if (user.getNickname() != null){
            map.put("userName", user.getNickname());
        }
        map.put("color", pipeline.getColor());
        map.put("date", PipelineUtil.date(1));
        return map;
    }


    /**
     * 创建日志
     * @param logType 日志类型
     * @param templateId 模板code
     * @param map 日志信息
     */
    @Override
    public void log(String logType, String templateId,HashMap<String, Object> map){

        Logging log = new Logging();

        //消息类型
        LoggingType opLogType = new LoggingType();
        opLogType.setId(logType);
        log.setActionType(opLogType);
        String[] s = templateId.split("_");
        map.put("img","pip_config.svg");
        map.put("createTime",PipelineUtil.date(1));

        if (logType.contains("RUN")){
            map.put("img","/images/pip_run.svg");
        }
        if (logType.contains("CONFIG")){
            map.put("img","/images/pip_config.svg");
        }
        if (logType.contains("PIPELINE")){
            map.put("img","/images/pip_pipeline.svg");
        }

        log.setModule(s[s.length-1]);

        log.setLoggingTemplateId(templateId);
        log.setCreateTime(new Timestamp(System.currentTimeMillis()));

        //用户信息
        String userId = LoginContext.getLoginId();
        User user = userService.findOne(userId);

        map.put("title",user.getName().substring(0,1));
        log.setUser(user);
        log.setBaseUrl(baseUrl);
        log.setBgroup(appName);
        log.setContent(JSONObject.toJSONString(map));

        logService.createLog(log);

    }

    /**
     * 配置全局消息
     * @param templateId 方案id
     * @param map 信息
     */
    @Override
    public void settingMessage(String templateId,HashMap<String, Object> map){
        SendMessageNotice dispatchNotice = new SendMessageNotice();
        dispatchNotice.setId(templateId);
        String jsonString = JSONObject.toJSONString(map);
        dispatchNotice.setEmailData(jsonString);
        dispatchNotice.setDingdingData(jsonString);
        dispatchNotice.setSiteData(jsonString);
        dispatchNotice.setQywechatData(jsonString);
        dispatchNotice.setBaseUrl(baseUrl);
        dispatchNoticeService.createMessageItem(dispatchNotice);
    }


    /**
     * 发送消息（站内信）
     * @param receiver 接收信息
     * @param map 信息
     */
    @Override
    public void message(HashMap<String, Object> map,List<String> receiver){

        Message message = new Message();

        String sendWay = (String)map.get("sendWay");
        String mesType = (String)map.get("mesType");

        //消息类型
        MessageType messageType = new MessageType();
        messageType.setId(mesType);

        message.setMessageType(messageType);
        //发送方式
        message.setMessageSendTypeId(sendWay);
        message.setData(map);
        message.setBaseUrl(baseUrl);

        List<MessageReceiver> list = new ArrayList<>();
        for (String s : receiver) {
            MessageReceiver messageReceiver = new MessageReceiver();
            messageReceiver.setUserId(s);
            messageReceiver.setPhone(s);
            messageReceiver.setEmail(s);
            list.add(messageReceiver);
        }
        message.setMessageReceiverList(list);

        sendMessage.sendMessage(message);
    }

    /**
     * 发送短信
     * @param map 短信内容
     */
    @Override
    public void smsMessage(Map<String,String> map) throws  ApplicationException{

        String rootId = map.get("rootId");
        List<User> allUser = findAllUser(rootId);
        String phones = findUserPhone(allUser);
        if (!PipelineUtil.isNoNull(phones)){
           throw  new ApplicationException("当前用户与项目负责人都未配置手机号，消息发送失败。");
        }
        Map<String,String> stringMap = new HashMap<>();
        stringMap.put("projectName",map.get("pipelineName"));
        stringMap.put("userName",map.get("userName"));

        Sms sms = new Sms();
        sms.setPhones(phones);
        sms.setTemplateCode("SMS_261110255");
        sms.setSignName("字节加速");
        JSONObject jsonObject = new JSONObject();
        jsonObject.putAll(stringMap);
        sms.setTemplateParam(jsonObject);
        try {
            // smsSignCfgService.generalSms(sms);
        } catch (Exception e) {
            throw new ApplicationException("消息发送失败:"+e.getMessage());
        }
    }


    /**
     * 获取发送短信的用户手机号
     * @param list 用户集合
     * @return 手机号
     */
    private String findUserPhone(List<User> list){
        StringBuilder phones = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            String phone = list.get(i).getPhone();
            if (!PipelineUtil.isNoNull(phone)){
                continue;
            }
            if (i == list.size()-1){
                phones.append(phone);
                return phones.toString();
            }
            phones.append(",").append(phone);
        }
        return phones.toString();
    }


    /**
     * 获取流水线执行人和负责人
     * @param userId 负责人id
     * @return 流水线执行人和负责人
     */
    private List<User> findAllUser(String userId){
        List<User> list = new ArrayList<>();
        String loginId = LoginContext.getLoginId();
        if (!loginId.equals(userId) ){
            User user = userService.findOne(userId);
            list.add(user);
        }
        User user = userService.findOne(loginId);
        list.add(user);
        return list;
    }


    public static void main(String[] args) throws IOException {
        String path = "/Users/zcamy/tmp/clone";
        File file = new File(path);
        boolean exists = file.exists();
        if (!exists){
            file.getParentFile().mkdirs();
            file.createNewFile();
        }

        List<File> objects = new ArrayList<>();
        objects.add(file);

        compress(path,"/Users/zcamy/tmp/aa.tar.gz");
        // compressList(objects,"/Users/zcamy/tmp/aa.tar.gz",true);


    }

    /**
     * 压缩目录
     * @param srcFolder  需要压缩的目录
     * @param zipPath 压缩后的位置
     * @throws IOException 压缩失败
     */
    public static void compress(String srcFolder, String zipPath) throws IOException {

        FileOutputStream fos = new FileOutputStream(zipPath);
        GzipCompressorOutputStream gzOut = new GzipCompressorOutputStream(fos);
        TarArchiveOutputStream tarOut = new TarArchiveOutputStream(gzOut);
        tarOut.setLongFileMode(TarArchiveOutputStream.LONGFILE_GNU);
        Path sourcePath = Paths.get(srcFolder);

        Files.walk(sourcePath)
                .forEach(file -> {
                    if (Files.isRegularFile(file)) {
                        String entryName = sourcePath.relativize(file).toString();
                        TarArchiveEntry tarEntry = new TarArchiveEntry(file.toFile(), entryName);
                        try {

                            tarOut.putArchiveEntry(tarEntry);
                            FileInputStream fis = new FileInputStream(file.toFile());
                            byte[] buffer = new byte[1024];
                            int bytesRead;
                            while ((bytesRead = fis.read(buffer)) != -1) {
                                tarOut.write(buffer, 0, bytesRead);
                            }
                            fis.close();
                            tarOut.closeArchiveEntry();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

        tarOut.finish();
        tarOut.close();
        gzOut.close();
        fos.close();
    }


    /**
     * 压缩多个目录
     * @param fileList 目录名称
     * @param outputTarGzFile 输出地址
     * @param b  是否包含目录顶级路径 /user/aa压缩是否包含aa路径
     * @throws IOException 压缩失败
     */
    public static void compressList(List<File> fileList, String outputTarGzFile, Boolean b) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputTarGzFile);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
             GZIPOutputStream gzipOutputStream = new GZIPOutputStream(bufferedOutputStream);
             TarArchiveOutputStream tarArchiveOutputStream = new TarArchiveOutputStream(gzipOutputStream)) {

            for (File inputDirectory : fileList) {
                Path dirPath = Paths.get(inputDirectory.toURI());

                Files.walkFileTree(dirPath, new SimpleFileVisitor<>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        // 添加文件到tar归档，包括相对路径
                        Path relativePath;
                        if (b){
                            Path parent = dirPath.getParent();
                            relativePath = parent.relativize(file);
                        }else {
                            relativePath = dirPath.relativize(file);
                        }
                        TarArchiveEntry entry = new TarArchiveEntry(file.toFile(), relativePath.toString());
                        tarArchiveOutputStream.putArchiveEntry(entry);
                        Files.copy(file, tarArchiveOutputStream);
                        tarArchiveOutputStream.closeArchiveEntry();
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                        // 添加目录到tar归档，包括相对路径
                        Path relativePath;
                        if (b){
                            Path parent = dirPath.getParent();
                            relativePath = parent.relativize(dir);
                        }else {
                            relativePath = dirPath.relativize(dir);
                        }
                        String dirName = relativePath.toString();
                        TarArchiveEntry entry = new TarArchiveEntry(dir.toFile(), dirName + "/");
                        tarArchiveOutputStream.putArchiveEntry(entry);
                        tarArchiveOutputStream.closeArchiveEntry();
                        return FileVisitResult.CONTINUE;
                    }
                });
            }
        }
    }


}



































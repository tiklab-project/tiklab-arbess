package io.tiklab.arbess.support.webHook.service;

import io.tiklab.arbess.pipeline.execute.model.PipelineRunMsg;
import io.tiklab.arbess.pipeline.execute.service.PipelineExecService;
import io.tiklab.arbess.support.util.util.PipelineUtil;
import io.tiklab.arbess.support.webHook.dao.PipelineWebHookDao;
import io.tiklab.arbess.support.webHook.entity.WebHookEntity;
import io.tiklab.arbess.support.webHook.model.WebHook;
import io.tiklab.arbess.support.webHook.model.WebHookQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.toolkit.beans.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PipelineWebHookServerImpl implements PipelineWebHookServer {


    @Autowired
    PipelineExecService execService;

    @Autowired
    PipelineWebHookDao pipelineWebHookDao;

    @Value("${server.port:8090}")
    private String port;


    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 18;
    private static final SecureRandom random = new SecureRandom();

    @Override
    public void execWebHook(String key) {
        WebHookQuery webHookQuery = new WebHookQuery();
        webHookQuery.setKey(key);
        List<WebHook> webHookList = findWebHookList(webHookQuery);
        if (webHookList.isEmpty()){
            throw new RuntimeException("没有可用的WebHook！");
        }
        WebHook webHook = webHookList.get(0);
        if (webHook.getStatus() == 2){
            throw new RuntimeException("当前WebHook在流水线中未启用！");
        }

        String pipelineId = webHook.getPipelineId();
        PipelineRunMsg pipelineRunMsg = new PipelineRunMsg(pipelineId,"webhook",4);
        // 执行流水线

        execService.validWailExecPipeline(pipelineRunMsg);
        execService.start(pipelineRunMsg);
    }

    @Override
    public String createWebHook(WebHook webHook) {

        String key = generate();

        webHook.setKey(key);

        String url =  "/pipeline/webhook/" + key;

        webHook.setUrl(url);
        if (Objects.isNull(webHook.getStatus())){
            webHook.setStatus(1);
        }
        WebHookEntity webHookEntity = BeanMapper.map(webHook, WebHookEntity.class);

        return pipelineWebHookDao.createWebHook(webHookEntity);
    }

    @Override
    public void deleteWebHook(String id) {
        pipelineWebHookDao.deleteWebHook(id);
    }

    @Override
    public void updateWebHook(WebHook webHook) {
        WebHookEntity webHookEntity = BeanMapper.map(webHook, WebHookEntity.class);
        String url =  "/pipeline/webhook/" + webHook.getKey();
        webHookEntity.setUrl(url);
        pipelineWebHookDao.updateWebHook(webHookEntity);
    }


    @Override
    public WebHook findPipelineWebHook(String pipelineId) {
        String webHookId;
        WebHookQuery webHookQuery = new WebHookQuery();
        webHookQuery.setPipelineId(pipelineId);
        List<WebHook> webHookList = findWebHookList(webHookQuery);
        if (webHookList.isEmpty()){
            WebHook webHook = new WebHook();
            webHook.setPipelineId(pipelineId);
            webHook.setStatus(2);
            webHookId = createWebHook(webHook);
        }else {
            WebHook webHook = webHookList.get(0);
            webHookId = webHook.getId();
        }
        WebHook webHook = findWebHook(webHookId);
        String localIp = PipelineUtil.findLocalIp();
        String url = "http://" + localIp + ":" + port + webHook.getUrl();
        webHook.setUrl(url);
        return webHook;
    }

    @Override
    public WebHook findWebHook(String id) {
        WebHookEntity webHookEntity = pipelineWebHookDao.findWebHook(id);
        return BeanMapper.map(webHookEntity, WebHook.class);
    }

    @Override
    public List<WebHook> findAllWebHook() {
        List<WebHookEntity> webHookEntityList = pipelineWebHookDao.findAllWebHook();
        if (webHookEntityList.isEmpty()){
            return new ArrayList<>();
        }
        return BeanMapper.mapList(webHookEntityList, WebHook.class);
    }

    @Override
    public List<WebHook> findWebHookList(WebHookQuery webHookQuery) {
        List<WebHookEntity> webHookEntityList = pipelineWebHookDao.findWebHookList(webHookQuery);
        if (webHookEntityList.isEmpty()){
            return List.of();
        }
        return BeanMapper.mapList(webHookEntityList, WebHook.class);
    }

    @Override
    public List<WebHook> findWebHookList(List<String> idList) {
        List<WebHookEntity> webHookEntityList = pipelineWebHookDao.findWebHookList(idList);
        if (webHookEntityList.isEmpty()){
            return List.of();
        }
        return BeanMapper.mapList(webHookEntityList, WebHook.class);
    }

    @Override
    public Pagination<WebHook> findWebHookPage(WebHookQuery webHookQuery) {

        Pagination<WebHookEntity> webHookEntityPagination = pipelineWebHookDao.findWebHookPage(webHookQuery);
        List<WebHookEntity> dataList = webHookEntityPagination.getDataList();
        if (dataList.isEmpty()){
            return PaginationBuilder.build(webHookEntityPagination,new ArrayList<>());
        }
        List<WebHook> webHooks = BeanMapper.mapList(dataList, WebHook.class);
        return PaginationBuilder.build(webHookEntityPagination,webHooks);
    }

    public static String generate() {
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }
}

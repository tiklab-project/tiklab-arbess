package io.tiklab.arbess.task.code.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.tiklab.core.exception.SystemException;
import io.tiklab.arbess.setting.model.AuthThird;
import io.tiklab.arbess.setting.service.AuthThirdService;
import io.tiklab.arbess.support.util.util.PipelineRequestUtil;
import io.tiklab.arbess.task.code.model.*;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.user.user.service.UserService;
import io.tiklab.arbess.task.code.model.ThirdBranch;
import io.tiklab.arbess.task.code.model.ThirdHouse;
import io.tiklab.arbess.task.code.model.ThirdQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class TaskCodeGittokServiceImpl implements TaskCodeGittokService {

    private static final Logger logger = LoggerFactory.getLogger(TaskCodeGittokServiceImpl.class);

    @Autowired
    AuthThirdService serverServer;

    @Autowired
    PipelineRequestUtil requestUtil;

    @Autowired
    UserService userService;


    @Override
    public List<ThirdHouse> findStoreHouseList(ThirdQuery thirdQuery){
        String authId = thirdQuery.getAuthId();
        AuthThird authServer = serverServer.findOneAuthServer(authId);

        if (Objects.isNull(authServer)){
            return null;
        }

        String serverAddress =  authServer.getServerAddress();
        try {

            String username = authServer.getUsername();
            String password = authServer.getPassword();

            HttpHeaders headers = requestUtil.initHeaders(MediaType.APPLICATION_JSON, new HashMap<>());
            MultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<>();
            valueMap.add("account",username);
            valueMap.add("password",password);
            valueMap.add("dirId","1");
            String requestUrl = serverAddress+"/api/rpy/findRepositoryByUser";

            HttpEntity<Object> requestEntity = new HttpEntity<>(valueMap, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<JSONObject> response = restTemplate.exchange(requestUrl, HttpMethod.POST, requestEntity, JSONObject.class);
            JSONObject jsonObject = response.getBody();
            JSONArray jsonArray = validBody(jsonObject, JSONArray.class);

            List<ThirdHouse> houseList = new ArrayList<>();

            for (Object o : jsonArray) {
                JSONObject parseObject = JSONObject.parseObject(String.valueOf(o));
                ThirdHouse thirdHouse = bindHouse(parseObject);
                houseList.add(thirdHouse);
            }
            return houseList;
        }catch (Throwable throwable){
            String message = throwable.getMessage();
            logger.error(" 连接异常：{}", message);
            if (message.contains("timed out") ){
                throw new ApplicationException(58001,"请求超时！");
            }
            if (message.contains("未订阅")){
                throw new ApplicationException("当前企业未订阅gittok");
            }
            if (throwable instanceof ApplicationException){
                throw new ApplicationException(message);
            }
            throw new ApplicationException("无法连接到：" + serverAddress);
        }
    }

    @Override
    public List<ThirdBranch> findHouseBranchList(ThirdQuery thirdQuery){
        String authId = thirdQuery.getAuthId();
        String houseId = thirdQuery.getHouseId();
        AuthThird authServer = serverServer.findOneAuthServer(authId);
        String  serverAddress = authServer.getServerAddress();
        try {
            HttpHeaders headers = requestUtil.initHeaders(MediaType.APPLICATION_JSON, new HashMap<>());
            MultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<>();
            valueMap.add("rpyId",houseId);
            String requestUrl = serverAddress+"/api/branch/findAllBranch";

            HttpEntity<Object> requestEntity = new HttpEntity<>(valueMap, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<JSONObject> response = restTemplate.exchange(requestUrl, HttpMethod.POST, requestEntity, JSONObject.class);
            JSONObject jsonObject = response.getBody();
            List<ThirdBranch> branchList = new ArrayList<>();
            JSONArray jsonArray = validBody(jsonObject, JSONArray.class);
            for (Object o : jsonArray) {
                JSONObject parseObject = JSONObject.parseObject(String.valueOf(o));
                ThirdBranch thirdBranch = bindBranch(parseObject);
                branchList.add(thirdBranch);
            }
            return branchList;

            // return requestUtil.requestPostList(headers, requestUrl, valueMap, XcodeBranch.class);
        }catch (Throwable throwable){
            String message = throwable.getMessage();
            logger.error(message);
            if (message.contains("未订阅")){
                throw new ApplicationException("当前企业未订阅Xcode");
            }
            if (throwable instanceof ApplicationException){
                throw new ApplicationException(message);
            }
            throw new ApplicationException("无法连接到："+serverAddress);
        }
    }

    @Override
    public ThirdBranch findOneBranch(String authId,String rpyId,String branchId){
        if (Objects.isNull(authId) || Objects.isNull(branchId)){
            return null;
        }

        AuthThird authServer = serverServer.findOneAuthServer(authId);
        String  serverAddress = authServer.getServerAddress();

        HttpHeaders headers = requestUtil.initHeaders(MediaType.APPLICATION_JSON, new HashMap<>());
        MultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<>();
        valueMap.add("rpyId",rpyId);
        valueMap.add("commitId",branchId);
        String requestUrl = serverAddress+"/api/branch/findBranch";

        HttpEntity<Object> requestEntity = new HttpEntity<>(valueMap, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JSONObject> response = restTemplate.exchange(requestUrl, HttpMethod.POST, requestEntity, JSONObject.class);
        JSONObject object = validBody(response.getBody(), JSONObject.class);
        ThirdBranch thirdBranch = bindBranch(object);
        if (Objects.isNull(thirdBranch)){
            throw new ApplicationException("找不到"+ rpyId +"仓库！");
        }
        return thirdBranch;

        // return requestUtil.requestPost(headers, requestUrl, valueMap, XcodeBranch.class);
    }

    @Override
    public ThirdHouse findStoreHouse(ThirdQuery thirdQuery){
        String authId = thirdQuery.getAuthId();
        String houseId = thirdQuery.getHouseId();
        if (Objects.isNull(authId) || Objects.isNull(houseId)){
            return null;
        }
        AuthThird authServer = serverServer.findOneAuthServer(authId);
        String  serverAddress = authServer.getServerAddress();
        try {
            HttpHeaders headers = requestUtil.initHeaders(MediaType.APPLICATION_JSON, new HashMap<>());
            MultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<>();
            valueMap.add("id",houseId);
            String requestUrl = serverAddress+"/api/rpy/findRepository";

            HttpEntity<Object> requestEntity = new HttpEntity<>(valueMap, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<JSONObject> response = restTemplate.exchange(requestUrl, HttpMethod.POST, requestEntity, JSONObject.class);
            JSONObject object = validBody(response.getBody(), JSONObject.class);
            ThirdHouse thirdHouse = bindHouse(object);
            if (Objects.isNull(thirdHouse)){
                throw new ApplicationException("找不到"+ houseId +"仓库！");
            }
            return thirdHouse;
        }catch (Throwable throwable){
            String message = throwable.getMessage();
            logger.error(message);
            if (message.contains("未订阅")){
                throw new ApplicationException("当前企业未订阅Xcode");
            }
            if (throwable instanceof ApplicationException){
                throw new ApplicationException(message);
            }
            throw new ApplicationException("无法连接到："+serverAddress);
        }
    }


    private <T> T validBody(JSONObject jsonObject,Class<T> tClass){
        if (Objects.isNull( jsonObject)){
            throw new SystemException("获取返回值为空！");
        }
        Integer code = jsonObject.getInteger("code");
        if (code != 0){
            String msg = jsonObject.getString("msg");
            if (code == 5000){
                throw new ApplicationException("用户效验失败！");
            }
            throw new ApplicationException("获取仓库列表失败！,Message："+msg);
        }
        return jsonObject.getObject("data",tClass);
    }


    private ThirdHouse bindHouse(JSONObject jsonObject){
        if (Objects.isNull(jsonObject)){
            return null;
        }
        String id = jsonObject.getString("rpyId");
        String fullPath = jsonObject.getString("fullPath");
        String path = jsonObject.getString("address");
        String defaultBranch = jsonObject.getString("defaultBranch");
        String name = jsonObject.getString("name");
        return new ThirdHouse().setId(id)
                .setHouseWebUrl(fullPath)
                .setPath(path)
                .setNameWithSpace(name)
                .setPathWithSpace(path)
                .setName(name)
                .setDefaultBranch(defaultBranch);
    }

    private ThirdBranch bindBranch(JSONObject jsonObject){
        if (Objects.isNull(jsonObject)){
            return null;
        }
        String id = jsonObject.getString("branchId");
        Boolean defaultBranch = jsonObject.getBoolean("defaultBranch");
        String branchName = jsonObject.getString("branchName");

        return new ThirdBranch().
                setId(branchName)
                .setDefault(defaultBranch)
                .setName(branchName);
    }

}






















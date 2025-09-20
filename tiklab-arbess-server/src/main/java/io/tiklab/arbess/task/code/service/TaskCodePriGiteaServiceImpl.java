package io.tiklab.arbess.task.code.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.tiklab.arbess.setting.third.model.AuthThird;
import io.tiklab.arbess.setting.third.service.AuthThirdService;
import io.tiklab.arbess.task.code.model.ThirdBranch;
import io.tiklab.arbess.task.code.model.ThirdHouse;
import io.tiklab.arbess.task.code.model.ThirdQuery;
import io.tiklab.arbess.task.code.model.ThirdUser;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.core.exception.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TaskCodePriGiteaServiceImpl implements TaskCodePriGiteaService {

    @Autowired
    AuthThirdService authThirdService;

    /**
     * 获取accessToken
     * @param authId 认证ID
     * @return 凭证
     */
    private AuthThird findAccessToken(String authId){
        AuthThird authServer = authThirdService.findOneAuthServer(authId);
        if (Objects.isNull(authServer)){
            throw new ApplicationException("没有查询到当前凭证授权码！");
        }
        return authServer;
    }

    @Override
    public List<ThirdHouse> findStoreHouseList(ThirdQuery thirdQuery){
        AuthThird authServer = findAccessToken(thirdQuery.getAuthId());
        String accessToken = authServer.getAccessToken();
        String serverAddress = authServer.getServerAddress();
        List<ThirdHouse> houseList = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();

        String url = String.format("%s/api/v1/repos/search?page=%s&limit=%s&access_token=%s",
                serverAddress,thirdQuery.getPage(),thirdQuery.getPageNumber(),accessToken);
        try {
            HttpHeaders headers = new HttpHeaders();
            MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
            HttpEntity<MultiValueMap<String, Object>>  entity = new HttpEntity<>(paramMap, headers);
            ResponseEntity<String> forEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            String body = forEntity.getBody();
            if (Objects.isNull(body)){
                throw new SystemException("获取仓库信息失败，获取信息为空！");
            }

            String validBody = validBody(body);

            JSONArray projectList = JSONObject.parseArray(validBody);
            for (Object o : projectList) {
                JSONObject jsonObject = JSONObject.parseObject(String.valueOf(o));
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                String path = jsonObject.getString("full_name");
                String sshUrl = jsonObject.getString("ssh_url");
                String webUrl = jsonObject.getString("clone_url");
                String defaultBranch = jsonObject.getString("default_branch");
                ThirdHouse thirdHouse = new ThirdHouse()
                        .setId(path)
                        .setName(name)
                        .setNameWithSpace(path)
                        .setPathWithSpace(path)
                        .setPath(path).setHouseSshUrl(sshUrl)
                        .setHouseWebUrl(webUrl)
                        .setDefaultBranch(defaultBranch);
                houseList.add(thirdHouse);
            }
        }catch (HttpClientErrorException e) {
            findGitlabErrorRequest(e.getMessage());
            throw new SystemException(e);
        }
        return houseList;
    }


    @Override
    public ThirdHouse findStoreHouse(ThirdQuery thirdQuery) {
        AuthThird authServer = findAccessToken(thirdQuery.getAuthId());
        String accessToken = authServer.getAccessToken();
        String serverAddress = authServer.getServerAddress();
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("%s/api/v1/repos/%s?access_token=%s",serverAddress,thirdQuery.getHouseId(),accessToken);
        ResponseEntity<String> forEntity;
        try {
            HttpHeaders headers = new HttpHeaders();
            // headers.set("Authorization", "Bearer"+" "+accessToken);
            MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
            HttpEntity<MultiValueMap<String, Object>>  entity = new HttpEntity<>(paramMap, headers);
            forEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            String body = forEntity.getBody();
            if (Objects.isNull(body)){
                throw new SystemException("获取仓库信息失败，获取信息为空！");
            }
            JSONObject jsonObject = JSONObject.parseObject(body);
            String id = jsonObject.getString("id");
            String name = jsonObject.getString("name");
            String path = jsonObject.getString("full_name");
            String sshUrl = jsonObject.getString("ssh_url");
            String webUrl = jsonObject.getString("clone_url");
            String defaultBranch = jsonObject.getString("default_branch");
            return new ThirdHouse().setId(path).setName(name)
                            .setPath(path).setHouseSshUrl(sshUrl)
                    .setNameWithSpace(path)
                    .setHouseWebUrl(webUrl)
                    .setDefaultBranch(defaultBranch);
        }catch (HttpClientErrorException e) {
            findGitlabErrorRequest(e.getMessage());
            throw new SystemException(e);
        }
    }

    @Override
    public ThirdUser findAuthUser(ThirdQuery thirdQuery){
        AuthThird authServer = findAccessToken(thirdQuery.getAuthId());
        String accessToken = authServer.getAccessToken();
        String serverAddress = authServer.getServerAddress();
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("%s/api/v1/user?access_token=%s",serverAddress,accessToken);
        ResponseEntity<String> forEntity;
        try {

            HttpHeaders headers = new HttpHeaders();
            // headers.set("Authorization", "Bearer"+" "+accessToken);
            MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
            HttpEntity<MultiValueMap<String, Object>>  entity = new HttpEntity<>(paramMap, headers);
            forEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            String body = forEntity.getBody();

            JSONObject jsonObject = JSONObject.parseObject(body);
            String id = jsonObject.getString("id");
            String login = jsonObject.getString("username");
            String name = jsonObject.getString("name");
            String avatar = jsonObject.getString("avatar_url");
            return new ThirdUser().setId(id).setName(name).setPath(login).setHead(avatar);
        }catch (HttpClientErrorException e) {
            findGitlabErrorRequest(e.getMessage());
            throw new SystemException(e);
        }
    }

    @Override
    public List<ThirdBranch> findStoreBranchList(ThirdQuery thirdQuery){
        AuthThird authServer = findAccessToken(thirdQuery.getAuthId());
        String accessToken = authServer.getAccessToken();
        String serverAddress = authServer.getServerAddress();
        List<ThirdBranch> branchList= new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("%s/api/v1/repos/%s/branches?access_token=%s",serverAddress,
                thirdQuery.getHouseId(),accessToken);
        ResponseEntity<String> forEntity;
        try {
            HttpHeaders headers = new HttpHeaders();
            // headers.set("Authorization", "Bearer"+" "+accessToken);
            MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
            HttpEntity<MultiValueMap<String, Object>>  entity = new HttpEntity<>(paramMap, headers);
            forEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            String body = forEntity.getBody();
            if (Objects.isNull(body)){
                throw new SystemException("获取分支信息失败，获取信息为空！");
            }
            JSONArray projectList = JSONObject.parseArray(body);
            if (projectList.isEmpty()){
                return new ArrayList<>();
            }
            for (Object o : projectList) {
                JSONObject jsonObject = JSONObject.parseObject(String.valueOf(o));
                String id = jsonObject.getString("commit");
                String name = jsonObject.getString("name");
                Boolean isDefault = jsonObject.getBoolean("default");
                Boolean idProtected = jsonObject.getBoolean("protected");
                ThirdBranch thirdBranch = new ThirdBranch(name, name, idProtected, isDefault);
                branchList.add(thirdBranch);
            }
        }catch (HttpClientErrorException e) {
            findGitlabErrorRequest(e.getMessage());
            throw new SystemException(e);
        }
        return branchList;
    }

    private void findGitlabErrorRequest(String message){
        throw new ApplicationException(message);
    }

    String validBody(String body){
        JSONObject jsonObject = JSONObject.parseObject(body);
        Boolean ok = jsonObject.getBoolean("ok");
        if (Objects.isNull(ok) || !ok){
            String message = jsonObject.getString("message");
            throw new SystemException("请求失败:"+message);
        }
        return jsonObject.getString("data");
    }


}


























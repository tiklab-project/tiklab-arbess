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
public class TaskCodePriGitLabServiceImpl implements TaskCodePriGitLabService {

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

        String url = String.format("%s/api/v4/projects?min_access_level=10&simple=true&page=%s&per_page=%s",
                serverAddress,thirdQuery.getPage(),thirdQuery.getPageNumber());
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer"+" "+accessToken);
            MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
            HttpEntity<MultiValueMap<String, Object>>  entity = new HttpEntity<>(paramMap, headers);
            ResponseEntity<String> forEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            String body = forEntity.getBody();
            if (Objects.isNull(body)){
                throw new SystemException("获取分支信息失败，获取信息为空！");
            }
            JSONArray projectList = JSONObject.parseArray(body);
            for (Object o : projectList) {
                JSONObject jsonObject = JSONObject.parseObject(String.valueOf(o));
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                String path = jsonObject.getString("path");
                String nameSpace = jsonObject.getString("name_with_namespace");
                String pathSpace = jsonObject.getString("path_with_namespace");
                String sshUrl = jsonObject.getString("ssh_url_to_repo");
                String webUrl = jsonObject.getString("http_url_to_repo");
                String defaultBranch = jsonObject.getString("default_branch");
                ThirdHouse thirdHouse = new ThirdHouse().setId(id).setName(name).
                        setPath(path).setNameWithSpace(nameSpace)
                        .setPathWithSpace(pathSpace).setHouseSshUrl(sshUrl)
                        .setHouseWebUrl(webUrl)
                        .setDefaultBranch(defaultBranch);
                houseList.add(thirdHouse);
            }
        }catch (HttpClientErrorException e) {
            findGitlabErrorRequest(e.getRawStatusCode());
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
        String url = String.format("%s/api/v4/projects/%s",serverAddress,thirdQuery.getHouseId());
        ResponseEntity<String> forEntity;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer"+" "+accessToken);
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
            String path = jsonObject.getString("path");
            String nameSpace = jsonObject.getString("name_with_namespace");
            String pathSpace = jsonObject.getString("path_with_namespace");
            String sshUrl = jsonObject.getString("ssh_url_to_repo");
            String webUrl = jsonObject.getString("http_url_to_repo");
            String defaultBranch = jsonObject.getString("default_branch");
            return new ThirdHouse().setId(id).setName(name).
                    setPath(path).setNameWithSpace(nameSpace)
                    .setPathWithSpace(pathSpace).setHouseSshUrl(sshUrl)
                    .setHouseWebUrl(webUrl)
                    .setDefaultBranch(defaultBranch);
        }catch (HttpClientErrorException e) {
            findGitlabErrorRequest(e.getRawStatusCode());
            throw new SystemException(e);
        }
    }

    @Override
    public ThirdUser findAuthUser(ThirdQuery thirdQuery){
        AuthThird authServer = findAccessToken(thirdQuery.getAuthId());
        String accessToken = authServer.getAccessToken();
        String serverAddress = authServer.getServerAddress();
        RestTemplate restTemplate = new RestTemplate();
        String url = serverAddress+ "/api/v4/user";
        ResponseEntity<String> forEntity;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer"+" "+accessToken);
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
            findGitlabErrorRequest(e.getRawStatusCode());
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
        String url = String.format("%s/api/v4/projects/%s/repository/branches",serverAddress,thirdQuery.getHouseId());
        ResponseEntity<String> forEntity;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer"+" "+accessToken);
            MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
            HttpEntity<MultiValueMap<String, Object>>  entity = new HttpEntity<>(paramMap, headers);
            forEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            String body = forEntity.getBody();
            if (Objects.isNull(body)){
                throw new SystemException("获取分支信息失败，获取信息为空！");
            }
            JSONArray projectList = JSONObject.parseArray(body);
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
            findGitlabErrorRequest(e.getRawStatusCode());
            throw new SystemException(e);
        }
        return branchList;
    }

    private void findGitlabErrorRequest(int code){
        switch (code){
            case 401 -> { throw new ApplicationException("令牌无效或已过期 ！");}
            case 403 -> { throw new ApplicationException("令牌权限不足！");}
            case 404 -> { throw new ApplicationException("请求失败，接口不存在！");}
            case 405 -> { throw new ApplicationException("不支持该请求！");}
            case 429 -> { throw new ApplicationException("请求次数过多！");}
            case 503 -> { throw new ApplicationException("服务器暂时超载，无法处理该请求！");}
        }
    }


}


























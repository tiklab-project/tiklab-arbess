package io.tiklab.arbess.task.code.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.core.exception.SystemException;
import io.tiklab.arbess.setting.third.model.AuthThird;
import io.tiklab.arbess.setting.third.service.AuthThirdService;
import io.tiklab.arbess.task.code.model.ThirdBranch;
import io.tiklab.arbess.task.code.model.ThirdHouse;
import io.tiklab.arbess.task.code.model.ThirdQuery;
import io.tiklab.arbess.task.code.model.ThirdUser;
import org.apache.commons.lang3.StringUtils;
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
public class TaskCodeGiteeServiceImpl implements TaskCodeGiteeService {

    @Autowired
    AuthThirdService authThirdService;

    /**
     * 获取accessToken
     * @param authId 认证ID
     * @return 凭证
     */
    private String findAccessToken(String authId){
        AuthThird authServer = authThirdService.findOneAuthServer(authId);
        if (Objects.isNull(authServer)){
           throw new ApplicationException("没有查询到当前凭证授权码！");
        }
        return authServer.getAccessToken();
    }

    @Override
    public List<ThirdHouse> findStoreHouseList(ThirdQuery thirdQuery){
        List<ThirdHouse> houseList = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        String accessToken = findAccessToken(thirdQuery.getAuthId());
        String url;
        if (StringUtils.isEmpty(thirdQuery.getQuery())){
            url = String.format("https://gitee.com/api/v5/user/repos?access_token=%s&sort=full_name&page=%s&per_page=%s",
                    accessToken,thirdQuery.getPage(),thirdQuery.getPageNumber());
        }else {
            url = String.format("https://gitee.com/api/v5/user/repos?access_token=%s&sort=full_name&q=%s&page=%s&per_page=%s",
                    accessToken,thirdQuery.getQuery(),thirdQuery.getPage(),thirdQuery.getPageNumber());
        }

        try {
            HttpHeaders headers = new HttpHeaders();
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
                String id = jsonObject.getString("full_name");
                String name = jsonObject.getString("name");
                String path = jsonObject.getString("path");
                String nameSpace = jsonObject.getString("full_name");
                String pathSpace = jsonObject.getString("human_name");
                String sshUrl = jsonObject.getString("ssh_url");
                String webUrl = jsonObject.getString("html_url");
                String defaultBranch = jsonObject.getString("default_branch");
                ThirdHouse thirdHouse = new ThirdHouse().setId(id).setName(name).
                        setPath(path).setNameWithSpace(nameSpace)
                        .setPathWithSpace(pathSpace).setHouseSshUrl(sshUrl)
                        .setHouseWebUrl(webUrl)
                        .setDefaultBranch(defaultBranch);
                houseList.add(thirdHouse);
            }
        }catch (Exception e) {
            if (e instanceof HttpClientErrorException){
                int rawStatusCode = ((HttpClientErrorException) e).getRawStatusCode();
                if (rawStatusCode == 401){
                    throw new ApplicationException("授权信息认证失败！");
                }
            }
            throw new SystemException(e);
        }
        return houseList;
    }

    @Override
    public ThirdHouse findStoreHouse(ThirdQuery thirdQuery){
        String accessToken = findAccessToken(thirdQuery.getAuthId());
        String[] split = thirdQuery.getHouseId().split("/");
        String url = String.format("https://gitee.com/api/v5/repos/%s/%s?access_token=%s",
                split[0],split[1],accessToken);
        RestTemplate restTemplate = new RestTemplate();

        try {
            HttpHeaders headers = new HttpHeaders();
            MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
            HttpEntity<MultiValueMap<String, Object>>  entity = new HttpEntity<>(paramMap, headers);
            ResponseEntity<String> forEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            String body = forEntity.getBody();
            if (Objects.isNull(body)){
                throw new SystemException("获取仓库信息失败，获取信息为空！");
            }
            JSONObject jsonObject = JSONObject.parseObject(body);
            String id = jsonObject.getString("full_name");
            String name = jsonObject.getString("name");
            String path = jsonObject.getString("path");
            String nameSpace = jsonObject.getString("full_name");
            String pathSpace = jsonObject.getString("human_name");
            String sshUrl = jsonObject.getString("ssh_url");
            String webUrl = jsonObject.getString("html_url");
            String defaultBranch = jsonObject.getString("default_branch");
            return new ThirdHouse().setId(id).setName(name).
                    setPath(path).setNameWithSpace(nameSpace)
                    .setPathWithSpace(pathSpace).setHouseSshUrl(sshUrl)
                    .setHouseWebUrl(webUrl)
                    .setDefaultBranch(defaultBranch);
        } catch (Exception e) {
            throw new SystemException(e);
        }
    }

    @Override
    public ThirdUser findAuthUser(ThirdQuery thirdQuery){
        RestTemplate restTemplate = new RestTemplate();
        String accessToken = findAccessToken(thirdQuery.getAuthId());
        String url = String.format("https://gitee.com/api/v5/user?access_token=%s",accessToken);
        try {
            HttpHeaders headers = new HttpHeaders();
            MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
            HttpEntity<MultiValueMap<String, Object>>  entity = new HttpEntity<>(paramMap, headers);
            ResponseEntity<String> forEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            String body = forEntity.getBody();

            JSONObject jsonObject = JSONObject.parseObject(body);
            String id = jsonObject.getString("id");
            String login = jsonObject.getString("login");
            String name = jsonObject.getString("name");
            String avatar = jsonObject.getString("avatar_url");
            return new ThirdUser().setId(id).setName(name).setPath(login).setHead(avatar);
        }catch (Exception e) {
            throw new SystemException(e);
        }
    }

    @Override
    public List<ThirdBranch> findStoreBranchList(ThirdQuery thirdQuery){
        List<ThirdBranch> branchList= new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        String[] split = thirdQuery.getHouseId().split("/");
        String accessToken = findAccessToken(thirdQuery.getAuthId());
        String url = String.format("https://gitee.com/api/v5/repos/%s/%s/branches?access_token=%s&sort=name&direction=asc&page=%s&per_page=%s",
                split[0],split[1],accessToken,thirdQuery.getPage(),thirdQuery.getPageNumber());
        ResponseEntity<String> forEntity;
        try {
            HttpHeaders headers = new HttpHeaders();
            MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
            HttpEntity<MultiValueMap<String, Object>>  entity = new HttpEntity<>(paramMap, headers);
            forEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            String body = forEntity.getBody();
            if (Objects.isNull(body)){
                throw new SystemException("获取分支信息失败，获取信息为空！");
            }
            JSONArray projectList = JSONObject.parseArray(body);
            if (projectList.isEmpty()){
                throw new SystemException("获取分支信息失败，当前仓库为空仓库！");
            }
            for (Object o : projectList) {
                JSONObject jsonObject = JSONObject.parseObject(String.valueOf(o));
                String name = jsonObject.getString("name");
                Boolean isProtected = jsonObject.getBoolean("protected");
                ThirdBranch thirdBranch = new ThirdBranch(name, name, isProtected, false);
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
            case 401 -> { throw new ApplicationException("AccessToken无效或已过期 ！");}
            case 403 -> { throw new ApplicationException("AccessToken权限不足！");}
            case 404 -> { throw new ApplicationException("请求失败，接口不存在！");}
            case 405 -> { throw new ApplicationException("不支持该请求！");}
            case 429 -> { throw new ApplicationException("请求次数过多！");}
            case 503 -> { throw new ApplicationException("服务器暂时超载，无法处理该请求！");}
        }
    }


}


























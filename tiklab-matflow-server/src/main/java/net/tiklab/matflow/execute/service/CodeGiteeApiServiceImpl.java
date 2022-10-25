package net.tiklab.matflow.execute.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.tiklab.matflow.execute.model.CodeGiteeApi;
import net.tiklab.matflow.setting.model.Proof;
import net.tiklab.matflow.setting.service.ProofService;
import net.tiklab.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@Exporter
public class CodeGiteeApiServiceImpl implements CodeGiteeApiService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ProofService proofService;

    @Autowired
    CodeGiteeApi codeGiteeApi;

    private static final Logger logger = LoggerFactory.getLogger(CodeGiteeApiServiceImpl.class);


    public String getCode(String callbackUri){
        return  codeGiteeApi.getCode();
    }

    //获取accessToken
    @Override
    public Map<String, String>  getAccessToken(String code,String callbackUri) throws IOException {

        logger.info("获取code凭证 ：" +code);
        if (code == null || code.equals("undefined")){
            return null;
        }

        String url = codeGiteeApi.getAccessToken(code);
        String post = request(url, "POST");
        Map<String, String> map = new HashMap<>();
        if (post == null){
            return null;
        }

        ResponseEntity<String> returnBody = restTemplate.postForEntity(url, JSONObject.class, String.class);

        System.out.println(returnBody);

        JSONObject jsonObject = JSONObject.parseObject(post);
        String access_token = jsonObject.getString("access_token");
        String refresh_token = jsonObject.getString("refresh_token");
        logger.info("Gitee的token值 : "+ access_token);
        map.put("accessToken", access_token);
        map.put("refreshToken", refresh_token);
        return map;

    }

    public  String getURLEncoderString(String str, String encode) {
        String result = null;
        try {
            result = URLEncoder.encode(str, encode);
        } catch (UnsupportedEncodingException e) {
           return null;
        }
        return result;
    }


    //列出授权用户的所有仓库
    @Override
    public List<String> getAllStorehouse(String ProofId) {
        Proof proof = proofService.findOneProof(ProofId);
        if (proof == null){
            return null;
        }
        ArrayList<String> storehouseList = new ArrayList<>();
        String allStorehouseAddress = codeGiteeApi.getAllStorehouse(proof.getProofPassword());
        logger.info("allStorehouseAddress : "+allStorehouseAddress);
        //消息转换器列表
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        //配置消息转换器StringHttpMessageConverter，并设置utf-8
        messageConverters.set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        ResponseEntity<String> returnBody = restTemplate.getForEntity(allStorehouseAddress, String.class, JSONObject.class);

        HttpStatus statusCode = returnBody.getStatusCode();
        if (statusCode.isError()){
            return null;
        }

        String body = returnBody.getBody();
        if (body == null){
            return null;
        }
        logger.info("仓库信息 ： "+body);
        JSONArray allStorehouseJson = JSONArray.parseArray(body);
        for (int i = 0; i < allStorehouseJson.size(); i++) {
            JSONObject storehouse=allStorehouseJson.getJSONObject(i);
            storehouseList.add(storehouse.getString("human_name"));
        }
        return storehouseList;
    }

    //获取登录信息
    @Override
    public String getUserMessage(String accessToken){
        if (accessToken == null){
            return null;
        }
        String userMessage = codeGiteeApi.getUserMessage(accessToken);
        ResponseEntity<JSONObject> jsonObject = restTemplate.getForEntity(userMessage, JSONObject.class, String.class);
        JSONObject body = jsonObject.getBody();
        if (body == null) {
            return null;
        }
        return body.getString("login");

    }

    //凭证信息
    @Override
    public String getProof(Proof proof) {
        if (proof.getProofPassword() == null){
            return null;
        }
        String userName = getUserMessage(proof.getProofPassword());
        //判断是否存在相同的授权
        Proof proofs = proofService.findAllAuthProof(2, userName);
        proof.setProofUsername(userName);
        //不存在相同授权创建新的凭证
        if (proofs == null){
            proof.setProofCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            return proofService.createProof(proof);
        }
        proof.setProofId(proofs.getProofId());
        proofService.updateProof(proof);
        return proof.getProofId();
    }

    //获取一个仓库的所有分支
    @Override
    public List<String> getBranch(String proofId,String projectName) {
        if (proofId == null || projectName == null){
            return null;
        }
        logger.info("projectName:"+projectName);
        Proof proof = proofService.findOneProof(proofId);
        String[] split = projectName.split("/");
        String name = split[1];
        if (split.length != 2 && proof == null){
            return null;
        }
        ArrayList<String> branchList = new ArrayList<>();
        logger.info("loginName :"+name);
        assert false;
        String branchAddress = codeGiteeApi.getWarehouseBranch(proof.getProofUsername(),name,proof.getProofPassword());
        ResponseEntity<String> forEntity = restTemplate.getForEntity(branchAddress, String.class, JSONObject.class);
        HttpStatus statusCode = forEntity.getStatusCode();
        if (statusCode.isError()){
            return null;
        }
        JSONArray branchS = JSONArray.parseArray(forEntity.getBody());
        if (branchS != null) {
            for (int i = 0; i < branchS.size(); i++) {
                JSONObject jsonArray = branchS.getJSONObject(i);
                branchList.add(jsonArray.getString("name"));
            }
        }
        return branchList;

    }

    //获取仓库的url
    @Override
    public String getCloneUrl(Proof proof,String projectName){
        Proof oneProof = proofService.findOneProof(proof.getProofId());
        if (projectName == null){
            return null;
        }
        String[] split = projectName.split("/");
        if (split.length != 2){
            return null;
        }
        String name = split[1];
        //获取仓库URl
        String oneStorehouse = codeGiteeApi.getOneStorehouse(oneProof.getProofUsername(),name,oneProof.getProofPassword());
        ResponseEntity<String> forEntity1 = restTemplate.getForEntity(oneStorehouse, String.class, JSONObject.class);
        JSONObject jsonObject = JSONObject.parseObject(forEntity1.getBody());
        return jsonObject.getString("html_url");

    }

    int s = 0;

    @Override
    public int getState(String code,int state) {
        if (state == 1){
            if (!code.equals("access_denied")){
                s = 1;
            }else {
                s = 2;
            }
        }
        if (state == 0){
            switch (s) {
                case 1 -> {
                    s = 0;
                    return 1;
                }
                case 2 -> {
                    s = 0;
                    return 2;
                }
            }
        }
        return 0;
    }

    /**
     * 访问url
     * @param url 访问地址
     * @param type 请求方式
     * @return 访问结果
     * @throws IOException 转换异常
     */
    public String request(String url,String type) throws IOException {
        URL serverUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();
        conn.setRequestMethod(type);
        conn.setRequestProperty("Content-type", "application/json");
        //必须设置false，否则会自动redirect到重定向后的地址
        conn.setInstanceFollowRedirects(false);
        conn.connect();

        StringBuilder result = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        String line;
        while ((line = in.readLine()) != null) {
            result.append(line);
        }
        in.close();
        return result.toString();
    }

}

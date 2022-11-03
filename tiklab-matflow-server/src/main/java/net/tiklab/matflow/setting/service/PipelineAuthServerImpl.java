package net.tiklab.matflow.setting.service;

import com.alibaba.fastjson.JSON;
import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.execute.service.CodeAuthorizeServiceImpl;
import net.tiklab.matflow.setting.model.*;
import net.tiklab.rpc.annotation.Exporter;
import net.tiklab.utils.context.LoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Exporter
public class PipelineAuthServerImpl implements  PipelineAuthServer {
    
    @Autowired
    PipelineAuthThirdServer thirdServer;

    @Autowired
    PipelineAuthBasicServer basicServer;

    @Autowired
    PipelineAuthOtherServer ootherServer;

    @Autowired
    PipelineAuthHostServer hostServer;


    /**
     * 创建认证
     * @param pipelineAuth 认证内容
     * @return 认证id
     */
    @Override
    public String createAuth(PipelineAuth pipelineAuth) {
        String object = JSON.toJSONString( pipelineAuth.getValues());
        switch (pipelineAuth.getType()){
            case 1:
                PipelineAuthBasic authBasic = JSON.parseObject(object, PipelineAuthBasic.class);
                return basicServer.createAuthBasic(authBasic);
            case 2:
                PipelineAuthOther authOther = JSON.parseObject(object, PipelineAuthOther.class);
                return ootherServer.createAuthOther(authOther);
            case 3:
                PipelineAuthHost authHost = JSON.parseObject(object, PipelineAuthHost.class);
                return hostServer.createAuthHost(authHost);
            case 4:
                PipelineAuthThird authAuthorize = JSON.parseObject(object, PipelineAuthThird.class);
                Map<String, String> userMap = CodeAuthorizeServiceImpl.userMap;
                String thirdId = userMap.get(LoginContext.getLoginId());
                authAuthorize.setThirdId(thirdId);
                thirdServer.updateAuthThird(authAuthorize);
                return thirdId;
            default:return null;
        }
    }

    /**
     * 删除认证
     * @param type   类型
     * @param authId 认证is
     */
    @Override
    public void deleteAuth(int type, String authId) {
        switch (type) {
            case 1 -> basicServer.deleteAuthBasic(authId);
            case 2 -> ootherServer.deleteAuthOther(authId);
            case 3 -> hostServer.deleteAuthHost(authId);
            case 4 -> thirdServer.deleteAuthThird(authId);
        }
    }

    /**
     * 更新认证
     * @param pipelineAuth 更新内容
     */
    @Override
    public void updateAuth(PipelineAuth pipelineAuth) {
        String id = pipelineAuth.getId();
        if (id == null){
            throw new ApplicationException(50001,"id不能为空");
        }
        String object = JSON.toJSONString( pipelineAuth.getValues());
        switch (pipelineAuth.getType()){
            case 1 -> {
                PipelineAuthBasic authBasic = JSON.parseObject(object, PipelineAuthBasic.class);
                authBasic.setBasicId(id);
                basicServer.updateAuthBasic(authBasic);
            }
            case 2 -> {
                PipelineAuthOther authOther = JSON.parseObject(object, PipelineAuthOther.class);
                authOther.setOtherId(id);
                ootherServer.updateAuthOther(authOther);
            }
            case 3 -> {
                PipelineAuthHost authHost = JSON.parseObject(object, PipelineAuthHost.class);
                authHost.setHostId(id);
                hostServer.updateAuthHost(authHost);
            }
            case 4 -> {
                PipelineAuthThird authAuthorize =JSON.parseObject(object, PipelineAuthThird.class);
                authAuthorize.setThirdId(id);
                thirdServer.updateAuthThird(authAuthorize);
            }
        }
    }

    /**
     * 根据类型查询认证信息
     * @param type 类型
     * @param authId 认证id
     * @return 认证信息
     */
    public Object findOneAuth(int type,String authId){
        return switch (type) {
            case 1, 4, 5 -> basicServer.findOneAuthBasic(authId);
            case 2, 3 -> thirdServer.findOneAuthThird(authId);
            case 42 -> hostServer.findOneAuthHost(authId);
            case 41, 51 -> ootherServer.findOneAuthOther(authId);
            default -> null;
        };
    }

    /**
     * 根据不同配置类型查询凭证
     * @param type 类型
     * @return 凭证列表
     */
    public List<Object> findAllConfigAuth(int type){
         switch (type) {
            case 1, 4, 5 -> {
                return new ArrayList<>(basicServer.findAllAuthBasic());
            }
            case 2, 3 -> {
                return  new ArrayList<>(ootherServer.findAllAuthOther());
            }
            case 42 -> {
                return  new ArrayList<>(hostServer.findAllAuthHost());
            }
            case 41, 51 -> {
                return  new ArrayList<>(thirdServer.findAllAuthThird());
            }
             default -> {
                 return null;
             }
        }
    }

    /**
     * 查询认证
     * @param type 类型
     * @return 认证集合
     */
    @Override
    public List<Object> findAllAuth(int type) {
        switch (type) {
            case 1 ->{
               return new ArrayList<>(basicServer.findAllAuthBasic());
            }
            case 2 -> {
                return  new ArrayList<>(ootherServer.findAllAuthOther());
            }
            case 3 -> {
                return  new ArrayList<>(hostServer.findAllAuthHost());
            }
            case 4 -> {
                return  new ArrayList<>(thirdServer.findAllAuthThird());
            }
            default -> {
                return null;
            }
        }
    }
}

package net.tiklab.matflow.execute.service.achieve;


import net.tiklab.matflow.setting.model.PipelineAuthThird;

import java.io.IOException;
import java.util.List;

/**
 * 码云API
 */
public interface CodeThirdService {

    /**
     * 获取code
     * @return 获取code的Url
     */
    String findCode(PipelineAuthThird authThird);

    /**
     * 根据code获取AccessToken
     * @param authThird code
     */
    String findAccessToken(PipelineAuthThird authThird)  throws IOException ;
    /**
     * 获取用户所有仓库
     * @param authId 凭证
     * @return 厂库信息
     */
    List<String> findAllStorehouse(String authId,int type);

    /**
     * 获取仓库所有分支
     * @param authId 凭证ID
     * @param houseName 仓库名
     * @return 所有分支
     */
    List<String> findBranch(String authId,String houseName,int type);

    /**
     * 获取仓库克隆地址
     * @param proofId 凭证ID
     * @param houseName 仓库名称
     * @return 克隆地址
     */
    String getHouseUrl(String proofId,String houseName,int type);


    /**
     * 获取回调地址
     * @param callbackUrl 地址
     * @return 回调地址
     */
    String callbackUrl(String callbackUrl);



}



















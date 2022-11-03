package net.tiklab.matflow.execute.service;


import java.io.IOException;
import java.util.List;

/**
 * 码云API
 */
public interface CodeAuthorizeService {

    /**
     * 获取code
     * @return 获取code的Url
     */
    String findCode(int type);

    /**
     * 根据code获取AccessToken
     * @param code code
     */
    String findAccessToken(String code,int type)  throws IOException ;
    /**
     * 获取用户所有仓库
     * @param proofId 凭证
     * @return 厂库信息
     */
    List<String> findAllStorehouse(String proofId,int type);


    /**
     * 获取仓库所有分支
     * @param proofId 凭证ID
     * @param houseName 仓库名
     * @return 所有分支
     */
    List<String> findBranch(String proofId,String houseName,int type);

    /**
     * 获取仓库克隆地址
     * @param proofId 凭证ID
     * @param houseName 仓库名称
     * @return 克隆地址
     */
    String getHouseUrl(String proofId,String houseName,int type);

    /**
     * 获取授权状态
     * @return 0.未授权 1.成功授权 2.取消授权
     */
    int findState();

    /**
     * 获取授权账户名
     * @param authId 授权id
     * @return 账户名称
     */
    String findMessage(String authId);

    void updateProof(String proofId ,String name);
}

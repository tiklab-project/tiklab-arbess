package net.tiklab.matflow.setting.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.setting.dao.PipelineAuthCodeDao;
import net.tiklab.matflow.setting.entity.PipelineAuthCodeEntity;
import net.tiklab.matflow.setting.model.PipelineAuthCode;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Exporter
public class PipelineAuthCodeServerImpl implements PipelineAuthCodeServer {


    @Autowired
    PipelineAuthCodeDao authCodeDao;

    @Autowired
    JoinTemplate joinTemplate;


    /**
     * 创建流水线授权
     * @param pipelineAuthCode 流水线授权
     * @return 流水线授权id
     */
    public String createAuthCode(PipelineAuthCode pipelineAuthCode) {
        PipelineAuthCodeEntity pipelineAuthCodeEntity = BeanMapper.map(pipelineAuthCode, PipelineAuthCodeEntity.class);
        return authCodeDao.createAuthCode(pipelineAuthCodeEntity);
    }

    /**
     * 删除流水线授权
     * @param authCodeId 流水线授权id
     */
    @Override
    public void deleteAuthCode(String authCodeId) {
        authCodeDao.deleteAuthCode(authCodeId);
    }

    /**
     * 更新授权信息
     * @param pipelineAuthCode 信息
     */
    @Override
    public void updateAuthCode(PipelineAuthCode pipelineAuthCode) {
        PipelineAuthCodeEntity authCodeEntity = BeanMapper.map(pipelineAuthCode, PipelineAuthCodeEntity.class);
        authCodeDao.updateAuthCode(authCodeEntity);
    }

    /**
     * 查询授权信息
     * @param authCodeId id
     * @return 信息集合
     */
    @Override
    public PipelineAuthCode findOneAuthCode(String authCodeId) {
        PipelineAuthCodeEntity oneAuthCode = authCodeDao.findOneAuthCode(authCodeId);
        PipelineAuthCode pipelineAuthCode = BeanMapper.map(oneAuthCode, PipelineAuthCode.class);
        joinTemplate.joinQuery(pipelineAuthCode);
        return pipelineAuthCode;
    }

    /**
     * 查询说有需要更新的授权信息
     * @param type 类型
     * @return 授权信息
     */
    @Override
    public List<PipelineAuthCode> findAllAuthCode(int type) {
        List<PipelineAuthCode> allAuthCode = findAllAuthCode();
        if (allAuthCode == null){
            return null;
        }
        List<PipelineAuthCode> list = new ArrayList<>();
        for (PipelineAuthCode authCode : allAuthCode) {
            if (authCode.getAuthType() == type){
                list.add(authCode);
            }
        }
        return list;
    }

    /**
     * 获取不同授权类型的源码认证
     * @param type 类型 1.自定义 ，2. 授权获取
     * @return 认证信息
     */
    public List<PipelineAuthCode> findAllAuthCodeList(int type) {
        List<PipelineAuthCode> allAuthCode = findAllAuthCode();
        if (allAuthCode == null){
            return null;
        }
        List<PipelineAuthCode> authList = new ArrayList<>();
        List<PipelineAuthCode> list = new ArrayList<>();
        for (PipelineAuthCode authCode : allAuthCode) {
            if (authCode.getType() == 2 || authCode.getType() == 3){
                authList.add(authCode);
            }else {
                list.add(authCode);
            }
        }
        if (type == 2){
            return authList;
        }
        return list;
    }

    /**
     * 查询所有流水线授权
     * @return 流水线授权列表
     */
    @Override
    public List<PipelineAuthCode> findAllAuthCode() {
        List<PipelineAuthCodeEntity> allAuthCode = authCodeDao.findAllAuthCode();
        List<PipelineAuthCode> pipelineAuthCodes = BeanMapper.mapList(allAuthCode, PipelineAuthCode.class);
        joinTemplate.joinQuery(pipelineAuthCodes);
        return pipelineAuthCodes;
    }

    @Override
    public List<PipelineAuthCode> findAllAuthCodeList(List<String> idList) {
        List<PipelineAuthCodeEntity> allAuthCodeList = authCodeDao.findAllAuthCodeList(idList);
        return  BeanMapper.mapList(allAuthCodeList, PipelineAuthCode.class);
    }


}

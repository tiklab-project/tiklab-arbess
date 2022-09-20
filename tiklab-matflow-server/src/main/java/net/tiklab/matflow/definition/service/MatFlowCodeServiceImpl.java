package net.tiklab.matflow.definition.service;


import net.tiklab.beans.BeanMapper;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.definition.dao.MatFlowCodeDao;
import net.tiklab.matflow.definition.entity.MatFlowCodeEntity;
import net.tiklab.matflow.definition.model.MatFlowCode;
import net.tiklab.matflow.execute.service.CodeGitHubService;
import net.tiklab.matflow.execute.service.CodeGiteeApiService;
import net.tiklab.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class MatFlowCodeServiceImpl implements MatFlowCodeService {

    @Autowired
    MatFlowCodeDao matFlowCodeDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    CodeGiteeApiService codeGiteeApiService;

    @Autowired
    CodeGitHubService codeGitHubService;

    private static final Logger logger = LoggerFactory.getLogger(MatFlowCodeServiceImpl.class);

    //创建
    @Override
    public String createCode(MatFlowCode matFlowCode) {
        if (matFlowCode.getType() == 1){
            matFlowCode.setCodeAddress(matFlowCode.getCodeName());
        }
        return matFlowCodeDao.createCode(BeanMapper.map(matFlowCode, MatFlowCodeEntity.class));
    }

    //删除
    @Override
    public void deleteCode(String codeId) {
        matFlowCodeDao.deleteCode(codeId);
    }

    //修改
    @Override
    public void updateCode(MatFlowCode matFlowCode) {
        matFlowCodeDao.updateCode(BeanMapper.map(matFlowCode, MatFlowCodeEntity.class));
    }

    //通过授权信息获取仓库url
    public MatFlowCode getUrl(MatFlowCode matFlowCode){
        if (matFlowCode.getProof() == null){
            return null;
        }
        if (matFlowCode.getType() == 2 ){
            String cloneUrl = codeGiteeApiService.getCloneUrl(matFlowCode.getProof(), matFlowCode.getCodeName());
            matFlowCode.setCodeAddress(cloneUrl);
        }else if (matFlowCode.getType() == 3){
            String cloneUrl = codeGitHubService.getOneHouse(matFlowCode.getProof(), matFlowCode.getCodeName());
            matFlowCode.setCodeAddress(cloneUrl);
        }else {
            matFlowCode.setCodeAddress(matFlowCode.getCodeName());
        }
        return matFlowCode;
    }

    //查询单个
    @Override
    public MatFlowCode findOneCode(String codeId) {
        MatFlowCodeEntity oneCode = matFlowCodeDao.findOneCode(codeId);
        MatFlowCode matFlowCode = BeanMapper.map(oneCode, MatFlowCode.class);
        if (oneCode == null){
            return null;
        }
        joinTemplate.joinQuery(matFlowCode);
        return matFlowCode;
    }

    //查询所有
    @Override
    public List<MatFlowCode> findAllCode() {
        return BeanMapper.mapList(matFlowCodeDao.findAllCode(), MatFlowCode.class);
    }

    @Override
    public List<MatFlowCode> findAllCodeList(List<String> idList) {
        return BeanMapper.mapList(matFlowCodeDao.findAllCodeList(idList), MatFlowCode.class);
    }

}



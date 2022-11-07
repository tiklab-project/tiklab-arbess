package net.tiklab.matflow.definition.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.matflow.definition.dao.PipelineCodeDao;
import net.tiklab.matflow.definition.entity.PipelineCodeEntity;
import net.tiklab.matflow.definition.model.PipelineCode;
import net.tiklab.matflow.execute.service.CodeAuthorizeService;
import net.tiklab.matflow.orther.service.PipelineUntil;
import net.tiklab.matflow.setting.model.PipelineAuthThird;
import net.tiklab.matflow.setting.service.PipelineAuthServer;
import net.tiklab.matflow.setting.service.PipelineAuthThirdServer;
import net.tiklab.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class PipelineCodeServiceImpl implements PipelineCodeService {

    @Autowired
    PipelineCodeDao pipelineCodeDao;

    @Autowired
    PipelineAuthServer authServer;

    @Autowired
    PipelineAuthThirdServer authServerServer;

    @Autowired
    CodeAuthorizeService codeAuthorizeService;

    private static final Logger logger = LoggerFactory.getLogger(PipelineCodeServiceImpl.class);

    //创建
    @Override
    public String createCode(PipelineCode pipelineCode) {
        return pipelineCodeDao.createCode(BeanMapper.map(pipelineCode, PipelineCodeEntity.class));
    }


    //删除
    @Override
    public void deleteCode(String codeId) {
        pipelineCodeDao.deleteCode(codeId);
    }

    //修改
    @Override
    public void updateCode(PipelineCode pipelineCode) {
        String authId = pipelineCode.getAuthId();
        switch (pipelineCode.getType()) {
            case 2, 3 -> {
                if (!PipelineUntil.isNoNull(authId)){
                    break;
                }
                String houseUrl = codeAuthorizeService.getHouseUrl(authId, pipelineCode.getCodeName(), pipelineCode.getType());
                pipelineCode.setCodeAddress(houseUrl);
            }
            default -> pipelineCode.setCodeAddress(pipelineCode.getCodeName());
        }
        pipelineCodeDao.updateCode(BeanMapper.map(pipelineCode, PipelineCodeEntity.class));
    }

    //查询单个
    @Override
    public PipelineCode findOneCode(String codeId) {
        PipelineCodeEntity oneCodeEntity = pipelineCodeDao.findOneCode(codeId);
        PipelineCode pipelineCode = BeanMapper.map(oneCodeEntity, PipelineCode.class);
        pipelineCode.setAuth(findAuth(pipelineCode.getAuthId()));
        return pipelineCode;
    }

    //查询所有
    @Override
    public List<PipelineCode> findAllCode() {
        List<PipelineCode> pipelineCodes = BeanMapper.mapList(pipelineCodeDao.findAllCode(), PipelineCode.class);
        if (pipelineCodes == null){
            return null;
        }
        for (PipelineCode pipelineCode : pipelineCodes) {
            pipelineCode.setAuth(findAuth(pipelineCode.getAuthId()));
        }
        return pipelineCodes;
    }

    @Override
    public List<PipelineCode> findAllCodeList(List<String> idList) {
        return BeanMapper.mapList(pipelineCodeDao.findAllCodeList(idList), PipelineCode.class);
    }

    //获认证信息
    private Object findAuth(String id){
        PipelineAuthThird oneAuthServer = authServerServer.findOneAuthServer(id);
       if (oneAuthServer != null){
           return oneAuthServer;
       }
        return authServer.findOneAuth(id);
    }

}



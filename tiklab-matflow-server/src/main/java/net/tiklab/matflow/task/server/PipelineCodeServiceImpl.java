package net.tiklab.matflow.task.server;

import net.tiklab.beans.BeanMapper;
import net.tiklab.matflow.task.dao.PipelineCodeDao;
import net.tiklab.matflow.task.entity.PipelineCodeEntity;
import net.tiklab.matflow.task.model.PipelineCode;
import net.tiklab.matflow.achieve.server.CodeThirdService;
import net.tiklab.matflow.orther.until.PipelineUntil;
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
    CodeThirdService codeThirdService;

    private static final Logger logger = LoggerFactory.getLogger(PipelineCodeServiceImpl.class);

    //创建
    @Override
    public String createCode(PipelineCode pipelineCode) {
        return pipelineCodeDao.createCode(BeanMapper.map(pipelineCode, PipelineCodeEntity.class));
    }

    /**
     * 根据配置id删除任务
     * @param configId 配置id
     */
    @Override
    public void deleteCodeConfig(String configId){
        PipelineCode oneCodeConfig = findOneCodeConfig(configId);
        deleteCode(oneCodeConfig.getCodeId());
    }

    /**
     * 根据配置id查询任务
     * @param configId 配置id
     * @return 任务
     */
    @Override
    public PipelineCode findOneCodeConfig(String configId){
        List<PipelineCode> allCode = findAllCode();
        if (allCode == null){
            return null;
        }
        for (PipelineCode pipelineCode : allCode) {
            if (pipelineCode.getConfigId().equals(configId)){
                return pipelineCode;
            }
        }
        return null;
    }


    //删除
    @Override
    public void deleteCode(String codeId) {
        pipelineCodeDao.deleteCode(codeId);
    }

    //修改
    @Override
    public void updateCode(PipelineCode pipelineCode) {

        switch (pipelineCode.getType()) {
            case 2, 3 -> {
                if (!PipelineUntil.isNoNull(pipelineCode.getCodeName())){
                    break;
                }
                PipelineCode oneCode = findOneCode(pipelineCode.getCodeId());
                String authId = oneCode.getAuthId();
                String houseUrl = codeThirdService.getHouseUrl(authId, pipelineCode.getCodeName(), pipelineCode.getType());
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


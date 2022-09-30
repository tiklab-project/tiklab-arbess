package net.tiklab.pipeline.definition.service;


import net.tiklab.beans.BeanMapper;
import net.tiklab.join.JoinTemplate;
import net.tiklab.pipeline.definition.dao.PipelineCodeDao;
import net.tiklab.pipeline.definition.entity.PipelineCodeEntity;
import net.tiklab.pipeline.definition.model.PipelineCode;
import net.tiklab.pipeline.execute.service.CodeGitHubService;
import net.tiklab.pipeline.execute.service.CodeGiteeApiService;
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
    JoinTemplate joinTemplate;

    @Autowired
    CodeGiteeApiService codeGiteeApiService;

    @Autowired
    CodeGitHubService codeGitHubService;

    private static final Logger logger = LoggerFactory.getLogger(PipelineCodeServiceImpl.class);

    //创建
    @Override
    public String createCode(PipelineCode pipelineCode) {
        if (pipelineCode.getType() == 1){
            pipelineCode.setCodeAddress(pipelineCode.getCodeName());
        }
        return pipelineCodeDao.createCode(BeanMapper.map(pipelineCode, PipelineCodeEntity.class));
    }


    //删除
    @Override
    public void deleteCode(String codeId) {
        pipelineCodeDao.deleteCode(codeId);
    }

    //根据流水线id查询配置
    @Override
    public PipelineCode findCode(String pipelineId) {
        List<PipelineCode> allBuild = findAllCode();
        if (allBuild != null){
            for (PipelineCode pipelineCode : allBuild) {
                if (pipelineCode.getPipeline() == null){
                    continue;
                }
                if (pipelineCode.getPipeline().getPipelineId().equals(pipelineId)){
                    joinTemplate.joinQuery(pipelineCode);
                    return pipelineCode;
                }
            }
        }
        return null;
    }

    //修改
    @Override
    public void updateCode(PipelineCode pipelineCode) {
        pipelineCodeDao.updateCode(BeanMapper.map(pipelineCode, PipelineCodeEntity.class));
    }

    //修改
    @Override
    public void updateCode(PipelineCode pipelineCode,String pipelineId){
        PipelineCode code = findCode(pipelineId);
        if (code == null){
            if (pipelineCode.getSort() != 0){
                PipelineCode url = getUrl(pipelineCode);
                createCode(url);
            }
        }else {
            if (pipelineCode.getSort() == 0){
                deleteCode(code.getCodeId());
            }else {
                pipelineCode.setCodeId(code.getCodeId());
                PipelineCode url = getUrl(pipelineCode);
                updateCode(url);
            }
        }
    }

    //通过授权信息获取仓库url
    @Override
    public PipelineCode getUrl(PipelineCode pipelineCode){
        if (pipelineCode.getProof() == null){
            return null;
        }
        if (pipelineCode.getType() == 2 ){
            String cloneUrl = codeGiteeApiService.getCloneUrl(pipelineCode.getProof(), pipelineCode.getCodeName());
            pipelineCode.setCodeAddress(cloneUrl);
        }else if (pipelineCode.getType() == 3){
            String cloneUrl = codeGitHubService.getOneHouse(pipelineCode.getProof(), pipelineCode.getCodeName());
            pipelineCode.setCodeAddress(cloneUrl);
        }else {
            pipelineCode.setCodeAddress(pipelineCode.getCodeName());
        }
        return pipelineCode;
    }

    //查询单个
    @Override
    public PipelineCode findOneCode(String codeId) {
        PipelineCodeEntity oneCode = pipelineCodeDao.findOneCode(codeId);
        PipelineCode pipelineCode = BeanMapper.map(oneCode, PipelineCode.class);
        if (oneCode == null){
            return null;
        }
        joinTemplate.joinQuery(pipelineCode);
        return pipelineCode;
    }

    //查询所有
    @Override
    public List<PipelineCode> findAllCode() {
        return BeanMapper.mapList(pipelineCodeDao.findAllCode(), PipelineCode.class);
    }

    @Override
    public List<PipelineCode> findAllCodeList(List<String> idList) {
        return BeanMapper.mapList(pipelineCodeDao.findAllCodeList(idList), PipelineCode.class);
    }

}



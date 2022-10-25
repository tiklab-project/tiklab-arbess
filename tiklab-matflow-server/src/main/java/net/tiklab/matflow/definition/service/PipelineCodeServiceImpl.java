package net.tiklab.matflow.definition.service;


import net.tiklab.beans.BeanMapper;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.definition.dao.PipelineCodeDao;
import net.tiklab.matflow.definition.entity.PipelineCodeEntity;
import net.tiklab.matflow.definition.model.PipelineCode;
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
    PipelineCodeAuthorizeService codeAuthorizeService;

    private static final Logger logger = LoggerFactory.getLogger(PipelineCodeServiceImpl.class);

    //创建
    @Override
    public String createCode(PipelineCode pipelineCode) {
        PipelineCode authorizeUrl = codeAuthorizeService.getAuthorizeUrl(pipelineCode);
        return pipelineCodeDao.createCode(BeanMapper.map(authorizeUrl, PipelineCodeEntity.class));
    }


    //删除
    @Override
    public void deleteCode(String codeId) {
        pipelineCodeDao.deleteCode(codeId);
    }

    //修改
    @Override
    public void updateCode(PipelineCode pipelineCode) {
        PipelineCode oneCode = findOneCode(pipelineCode.getCodeId());
        pipelineCode.setProof(oneCode.getProof());
        PipelineCode authorizeUrl = codeAuthorizeService.getAuthorizeUrl(pipelineCode);
        pipelineCodeDao.updateCode(BeanMapper.map(authorizeUrl, PipelineCodeEntity.class));
    }

    //查询单个
    @Override
    public PipelineCode findOneCode(String codeId) {
        PipelineCodeEntity oneCode = pipelineCodeDao.findOneCode(codeId);
        PipelineCode pipelineCode = BeanMapper.map(oneCode, PipelineCode.class);
        joinTemplate.joinQuery(pipelineCode);
        return pipelineCode;
    }

    //查询所有
    @Override
    public List<PipelineCode> findAllCode() {
        List<PipelineCode> pipelineCodes = BeanMapper.mapList(pipelineCodeDao.findAllCode(), PipelineCode.class);
        joinTemplate.joinQuery(pipelineCodes);
        return pipelineCodes;
    }

    @Override
    public List<PipelineCode> findAllCodeList(List<String> idList) {
        return BeanMapper.mapList(pipelineCodeDao.findAllCodeList(idList), PipelineCode.class);
    }

}



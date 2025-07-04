package io.tiklab.arbess.setting.tool.service;


import io.tiklab.arbess.agent.util.PipelineFinal;
import io.tiklab.arbess.agent.util.PipelineUtil;
import io.tiklab.arbess.setting.k8s.config.KubectlConfig;
import io.tiklab.arbess.setting.k8s.model.KubectlVersion;
import io.tiklab.arbess.setting.tool.model.ScmQuery;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.arbess.setting.tool.dao.ScmDao;
import io.tiklab.arbess.setting.tool.entity.ScmEntity;
import io.tiklab.arbess.setting.tool.model.Scm;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static io.tiklab.arbess.agent.util.PipelineFinal.*;

@Service
public class ScmServiceImpl implements ScmService {


    @Autowired
    ScmDao scmDao;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public String createPipelineScm(Scm scm) {

        String scmType = scm.getScmType();
        String scmAddress = scm.getScmAddress();

        PipelineUtil.validFile(scmAddress, scmType);

        ScmEntity scmEntity = BeanMapper.map(scm, ScmEntity.class);
        return scmDao.createPipelineScm(scmEntity);
    }

    public String createPipelineScmNoValid(Scm scm) {

        // String scmType = scm.getScmType();
        // String scmAddress = scm.getScmAddress();

        // PipelineUtil.validFile(scmAddress, scmType);

        ScmEntity scmEntity = BeanMapper.map(scm, ScmEntity.class);
        return scmDao.createPipelineScm(scmEntity);
    }

    //删除
    @Override
    public void deletePipelineScm(String pathId) {

        Scm scm = findOnePipelineScm(pathId);
        String addType = scm.getAddType();
        if (!addType.equals("local")){
            File file = new File(scm.getScmAddress()).getParentFile();
            if (file.exists()){
                try {
                    FileUtils.deleteDirectory(file);
                } catch (IOException ex) {
                    throw new ApplicationException("文件删除失败："+ex.getMessage());
                }
            }
        }
        scmDao.deletePipelineScm(pathId);
    }

    //更新
    @Override
    public void updatePipelineScm(Scm scm) {

        String scmType = scm.getScmType();
        String scmAddress = scm.getScmAddress();
        PipelineUtil.validFile(scmAddress, scmType);
        if (scmType.equals(TASK_TOOL_TYPE_NODEJS)) {
            PipelineUtil.validFile(scmAddress, TASK_TOOL_TYPE_NPM);
        }
        if (scm.getScmId()==null || findOnePipelineScm(scm.getScmId())==null){
            createPipelineScm(scm);
            return;
        }
        scmDao.updatePipelineScm(BeanMapper.map(scm, ScmEntity.class));
    }

    //查询
    @Override
    public Scm findOnePipelineScm(String pathId) {
        ScmEntity scmEntity = scmDao.findOnePipelineScm(pathId);
        return BeanMapper.map(scmEntity, Scm.class);
    }

    //查询所有
    @Override
    public List<Scm> findAllPipelineScm() {
        List<ScmEntity> scmEntityList = scmDao.findAllPipelineScm();
        // scmEntityList.sort(Comparator.comparing(ScmEntity::getCreateTime));
        scmEntityList.sort(Comparator.comparing(ScmEntity::getScmType));
        return BeanMapper.mapList(scmEntityList, Scm.class);
    }

    @Override
    public List<Scm> findPipelineScmList(List<String> idList) {
        List<ScmEntity> scmEntityList = scmDao.findPipelineScmList(idList);
        return BeanMapper.mapList(scmEntityList, Scm.class);
    }

    @Override
    public List<Scm> findPipelineScmList(ScmQuery scmQuery) {
        List<ScmEntity> scmEntityList = scmDao.findPipelineScmList(scmQuery);
        if (Objects.isNull(scmEntityList) || scmEntityList.isEmpty()) {
            return new ArrayList<>();
        }
        return BeanMapper.mapList(scmEntityList, Scm.class);
    }


    @Override
    public Pagination<Scm> findPipelineScmPage(ScmQuery scmQuery) {
        Pagination<ScmEntity> scmEntityPage = scmDao.findPipelineScmPage(scmQuery);
        List<ScmEntity> scmEntityList = scmEntityPage.getDataList();
        if (Objects.isNull(scmEntityList) || scmEntityList.isEmpty()) {
            return PaginationBuilder.build(scmEntityPage, new ArrayList<>());
        }
        List<Scm> scmList = BeanMapper.mapList(scmEntityList, Scm.class);


        for (Scm scm : scmList) {
            if (scm.getScmType().equals(TASK_DEPLOY_K8S)){
                try {
                    String scmAddress = scm.getScmAddress();
                    KubectlConfig kubectlConfig = KubectlConfig.instance(scmAddress);
                    KubectlVersion k8sClientVersion = kubectlConfig.findK8sClientVersion();
                    scm.setVersion(k8sClientVersion.getClientVersion());
                }catch (Exception e){
                    logger.error("获取kuboctl 客户端版本失败：{}", e.getMessage());
                }
            }
        }
        return PaginationBuilder.build(scmEntityPage, scmList);
    }

    @Override
    public Integer findScmNumber() {
        return scmDao.findScmNumber();
    }
    
}

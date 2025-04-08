package io.tiklab.arbess.setting.service;


import io.tiklab.arbess.agent.util.PipelineUtil;
import io.tiklab.arbess.agent.util.runtime.ProcessFetcherFactory;
import io.tiklab.arbess.agent.util.runtime.model.RunVersion;
import io.tiklab.arbess.setting.model.ScmQuery;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.arbess.setting.dao.ScmDao;
import io.tiklab.arbess.setting.entity.ScmEntity;
import io.tiklab.arbess.setting.model.Scm;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static io.tiklab.arbess.agent.util.PipelineFinal.*;

@Service
public class ScmServiceImpl implements ScmService {


    @Autowired
    ScmDao scmDao;


    @Override
    public String createPipelineScm(Scm scm) {

        String scmType = scm.getScmType();
        String scmAddress = scm.getScmAddress();

        PipelineUtil.validFile(scmAddress, scmType);
        // try {
        //     PipelineUtil.validFile(scmAddress, scmType);
        // }catch (Exception e) {
        //     File file = new File(scm.getScmAddress()).getParentFile();
        //     if (file.exists()){
        //         try {
        //             FileUtils.deleteDirectory(file);
        //         } catch (IOException ex) {
        //             throw new ApplicationException("文件删除失败："+ex.getMessage());
        //         }
        //     }
        //     throw new ApplicationException(e.getMessage());
        // }

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
            return Collections.emptyList();
        }
        return BeanMapper.mapList(scmEntityList, Scm.class);
    }


    @Override
    public Pagination<Scm> findPipelineScmPage(ScmQuery scmQuery) {
        Pagination<ScmEntity> scmEntityPage = scmDao.findPipelineScmPage(scmQuery);
        List<ScmEntity> scmEntityList = scmEntityPage.getDataList();
        if (Objects.isNull(scmEntityList) || scmEntityList.isEmpty()) {
            return PaginationBuilder.build(scmEntityPage, Collections.emptyList());
        }
        List<Scm> scmList = BeanMapper.mapList(scmEntityList, Scm.class);
        return PaginationBuilder.build(scmEntityPage, scmList);
    }

    @Override
    public Integer findScmNumber() {
        return scmDao.findScmNumber();
    }
    
}

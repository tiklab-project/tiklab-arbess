package net.tiklab.matflow.definition.service;


import net.tiklab.beans.BeanMapper;
import net.tiklab.matflow.definition.service.MatFlowBuildService;
import net.tiklab.matflow.definition.dao.MatFlowBuildDao;
import net.tiklab.matflow.definition.entity.MatFlowBuildEntity;
import net.tiklab.matflow.definition.model.MatFlowBuild;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class MatFlowBuildServiceImpl implements MatFlowBuildService {

    @Autowired
    MatFlowBuildDao matFlowBuildDao;

    //创建
    @Override
    public String createBuild(MatFlowBuild matFlowBuild) {
        return matFlowBuildDao.createBuild(BeanMapper.map(matFlowBuild, MatFlowBuildEntity.class));
    }

    //删除
    @Override
    public void deleteBuild(String buildId) {
        matFlowBuildDao.deleteBuild(buildId);
    }

    //修改
    @Override
    public void updateBuild(MatFlowBuild matFlowBuild) {
        matFlowBuildDao.updateBuild(BeanMapper.map(matFlowBuild, MatFlowBuildEntity.class));
    }

    //查询单个
    @Override
    public MatFlowBuild findOneBuild(String buildId) {
        return BeanMapper.map(matFlowBuildDao.findOneBuild(buildId), MatFlowBuild.class);
    }

    //查询所有
    @Override
    public List<MatFlowBuild> findAllBuild() {
        return BeanMapper.mapList(matFlowBuildDao.findAllBuild(), MatFlowBuild.class);
    }

    @Override
    public List<MatFlowBuild> findAllBuildList(List<String> idList) {
        return BeanMapper.mapList(matFlowBuildDao.findAllCodeList(idList), MatFlowBuild.class);
    }
}

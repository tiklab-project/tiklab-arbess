package com.tiklab.matflow.setting.path.service;


import com.tiklab.beans.BeanMapper;
import com.tiklab.matflow.setting.path.dao.MatFlowPathDao;
import com.tiklab.matflow.setting.path.entity.MatFlowPathEntity;
import com.tiklab.matflow.setting.path.model.MatFlowPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MatFlowPathServiceImpl implements MatFlowPathService {


    @Autowired
    MatFlowPathDao matFlowPathDao;


    @Override
    public String createMatFlowPath(MatFlowPath matFlowPath) {
        MatFlowPathEntity matFlowPathEntity = BeanMapper.map(matFlowPath, MatFlowPathEntity.class);
        return matFlowPathDao.createMatFlowPath(matFlowPathEntity);
    }

    //删除
    @Override
    public void deleteMatFlowPath(String pathId) {
        matFlowPathDao.deleteMatFlowPath(pathId);
    }

    //更新
    @Override
    public void updateMatFlowPath(MatFlowPath matFlowPath) {
        if (matFlowPath.getPathId()==null || findOneMatFlowPath(matFlowPath.getPathId())==null){
            createMatFlowPath(matFlowPath);
            return;
        }
        matFlowPathDao.updateMatFlowPath(BeanMapper.map(matFlowPath, MatFlowPathEntity.class));
    }

    //查询
    @Override
    public MatFlowPath findOneMatFlowPath(String pathId) {
        MatFlowPathEntity matFlowPathEntity = matFlowPathDao.findOneMatFlowPath(pathId);
        return BeanMapper.map(matFlowPathEntity, MatFlowPath.class);
    }

    //查询所有
    @Override
    public List<MatFlowPath> findAllMatFlowPath() {
        List<MatFlowPathEntity> matFlowPathEntityList = matFlowPathDao.selectAllMatFlowPath();
        return BeanMapper.mapList(matFlowPathEntityList, MatFlowPath.class);
    }

    /**
     * 获取配置
     * @param type 类型
     * @return 配置信息
     */
    @Override
    public MatFlowPath findOneMatFlowPath(int type) {
        List<MatFlowPath> allMatFlowPath = findAllMatFlowPath();
        if (allMatFlowPath == null){
            return null;
        }

        for (MatFlowPath matFlowPath : allMatFlowPath) {
            if (type != matFlowPath.getPathType()){
                continue;
            }
            return matFlowPath;
        }
        return null;
    }
    
    @Override
    public List<MatFlowPath> selectMatFlowPathList(List<String> idList) {
        List<MatFlowPathEntity> matFlowPathEntityList = matFlowPathDao.selectAllMatFlowPathList(idList);
        return BeanMapper.mapList(matFlowPathEntityList, MatFlowPath.class);
    }

    
}

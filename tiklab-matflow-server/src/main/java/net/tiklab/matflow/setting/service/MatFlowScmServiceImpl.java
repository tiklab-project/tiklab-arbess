package net.tiklab.matflow.setting.service;


import net.tiklab.beans.BeanMapper;
import net.tiklab.matflow.setting.dao.MatFlowScmDao;
import net.tiklab.matflow.setting.entity.MatFlowScmEntity;
import net.tiklab.matflow.setting.model.MatFlowScm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MatFlowScmServiceImpl implements MatFlowScmService {


    @Autowired
    MatFlowScmDao matFlowScmDao;


    @Override
    public String createMatFlowScm(MatFlowScm matFlowScm) {
        MatFlowScmEntity matFlowScmEntity = BeanMapper.map(matFlowScm, MatFlowScmEntity.class);
        return matFlowScmDao.createMatFlowScm(matFlowScmEntity);
    }

    //删除
    @Override
    public void deleteMatFlowScm(String pathId) {
        matFlowScmDao.deleteMatFlowScm(pathId);
    }

    //更新
    @Override
    public void updateMatFlowScm(MatFlowScm matFlowScm) {
        if (matFlowScm.getScmId()==null || findOneMatFlowScm(matFlowScm.getScmId())==null){
            createMatFlowScm(matFlowScm);
            return;
        }
        matFlowScmDao.updateMatFlowScm(BeanMapper.map(matFlowScm, MatFlowScmEntity.class));
    }

    //查询
    @Override
    public MatFlowScm findOneMatFlowScm(String pathId) {
        MatFlowScmEntity matFlowScmEntity = matFlowScmDao.findOneMatFlowScm(pathId);
        return BeanMapper.map(matFlowScmEntity, MatFlowScm.class);
    }

    //查询所有
    @Override
    public List<MatFlowScm> findAllMatFlowScm() {
        List<MatFlowScmEntity> matFlowScmEntityList = matFlowScmDao.selectAllMatFlowScm();
        return BeanMapper.mapList(matFlowScmEntityList, MatFlowScm.class);
    }

    /**
     * 获取配置
     * @param type 类型
     * @return 配置信息
     */
    @Override
    public MatFlowScm findOneMatFlowScm(int type) {
        List<MatFlowScm> allMatFlowScm = findAllMatFlowScm();
        if (allMatFlowScm == null){
            return null;
        }

        for (MatFlowScm matFlowScm : allMatFlowScm) {
            if (type != matFlowScm.getScmType()){
                continue;
            }
            return matFlowScm;
        }
        return null;
    }
    
    @Override
    public List<MatFlowScm> selectMatFlowScmList(List<String> idList) {
        List<MatFlowScmEntity> matFlowScmEntityList = matFlowScmDao.selectAllMatFlowScmList(idList);
        return BeanMapper.mapList(matFlowScmEntityList, MatFlowScm.class);
    }

    
}

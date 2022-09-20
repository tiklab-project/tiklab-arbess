package net.tiklab.matflow.definition.service;


import net.tiklab.beans.BeanMapper;
import net.tiklab.matflow.definition.service.MatFlowTestService;
import net.tiklab.matflow.definition.dao.MatFlowTestDao;
import net.tiklab.matflow.definition.entity.MatFlowTestEntity;
import net.tiklab.matflow.definition.model.MatFlowTest;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class MatFlowTestServiceImpl implements MatFlowTestService {

    @Autowired
    MatFlowTestDao matFlowTestDao;

    //创建
    @Override
    public String createTest(MatFlowTest matFlowTest) {
        return matFlowTestDao.createTest(BeanMapper.map(matFlowTest, MatFlowTestEntity.class));
    }

    //删除
    @Override
    public void deleteTest(String testId) {
        matFlowTestDao.deleteTest(testId);
    }

    //修改
    @Override
    public void updateTest(MatFlowTest matFlowTest) {
        matFlowTestDao.updateTest(BeanMapper.map(matFlowTest, MatFlowTestEntity.class));
    }


    //查询单个
    @Override
    public MatFlowTest findOneTest(String testId) {
        return BeanMapper.map(matFlowTestDao.findOneTest(testId), MatFlowTest.class);
    }

    //查询所有
    @Override
    public List<MatFlowTest> findAllTest() {
        return  BeanMapper.mapList(matFlowTestDao.findAllTest(), MatFlowTest.class);
    }

    @Override
    public List<MatFlowTest> findAllTestList(List<String> idList) {
        return BeanMapper.mapList(matFlowTestDao.findAllCodeList(idList), MatFlowTest.class);
    }
}

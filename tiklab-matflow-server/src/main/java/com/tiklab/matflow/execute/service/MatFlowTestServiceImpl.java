package com.tiklab.matflow.execute.service;


import com.tiklab.beans.BeanMapper;
import com.tiklab.matflow.definition.model.MatFlow;
import com.tiklab.matflow.definition.model.MatFlowConfigure;
import com.tiklab.matflow.definition.model.MatFlowExecConfigure;
import com.tiklab.matflow.definition.service.MatFlowConfigureService;
import com.tiklab.matflow.execute.dao.MatFlowTestDao;
import com.tiklab.matflow.execute.entity.MatFlowTestEntity;
import com.tiklab.matflow.execute.model.MatFlowTest;
import com.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
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

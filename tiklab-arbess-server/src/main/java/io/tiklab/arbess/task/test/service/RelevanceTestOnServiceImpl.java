package io.tiklab.arbess.task.test.service;

import io.tiklab.arbess.setting.third.model.AuthThird;
import io.tiklab.arbess.setting.third.service.AuthThirdService;
import io.tiklab.arbess.support.util.util.PipelineUtil;
import io.tiklab.arbess.task.test.model.RelevanceTestOnQuery;
import io.tiklab.arbess.task.test.model.TestOnPlanInstance;
import io.tiklab.arbess.task.test.model.TestOnRelevance;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.arbess.pipeline.definition.model.Pipeline;
import io.tiklab.arbess.task.test.dao.RelevanceTestOnDao;
import io.tiklab.arbess.task.test.entity.RelevanceTestOnEntity;
import io.tiklab.arbess.task.test.model.RelevanceTestOn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * a
 * */
@Service
public class RelevanceTestOnServiceImpl implements RelevanceTestOnService{

    @Autowired
    private RelevanceTestOnDao relevanceTestOnDao;

    @Autowired
    private TaskTestOnService taskTestOnService;

    @Autowired
    private AuthThirdService authThirdService;


    @Override
    public String createRelevance(RelevanceTestOn relevanceTestOn) {
        relevanceTestOn.setCreateTime(PipelineUtil.date(1));
        RelevanceTestOnEntity testOnEntity = BeanMapper.map(relevanceTestOn, RelevanceTestOnEntity.class);
        return relevanceTestOnDao.createRelevanceTestOn(testOnEntity);
    }

    @Override
    public void deleteRelevance(String relevanceId) {
        relevanceTestOnDao.deleteRelevanceTestOn(relevanceId);
    }

    @Override
    public void deleteAllRelevance(String pipelineId) {
        List<RelevanceTestOn> allRelevance = findAllRelevance(pipelineId);
        if (allRelevance.isEmpty()){
            return;
        }
        for (RelevanceTestOn testOn : allRelevance) {
            deleteRelevance(testOn.getRelevanceId());
        }
    }

    @Override
    public List<RelevanceTestOn> findAllRelevance(String pipelineId) {
        List<RelevanceTestOnEntity> allRelevance = relevanceTestOnDao.findAllRelevance(pipelineId);

        if (Objects.isNull(allRelevance) || allRelevance.isEmpty()){
            return new ArrayList<>();
        }
        List<RelevanceTestOn> relevanceTestOns = BeanMapper.mapList(allRelevance, RelevanceTestOn.class);

        return findRelevanceList(relevanceTestOns);
    }

    @Override
    public Pagination<RelevanceTestOn> findRelevancePage(RelevanceTestOnQuery relevanceTestOnQuery){
        Pagination<RelevanceTestOnEntity> allRelevancePage = relevanceTestOnDao.findRelevancePage(relevanceTestOnQuery);
        List<RelevanceTestOnEntity> dataList = allRelevancePage.getDataList();

        List<RelevanceTestOn> list = new ArrayList<>();
        if (dataList == null || dataList.isEmpty()){
            return PaginationBuilder.build(allRelevancePage,list);
        }
        List<RelevanceTestOn> testOnList = BeanMapper.mapList(dataList, RelevanceTestOn.class);
        List<RelevanceTestOn> allRelevance = findRelevanceList(testOnList);
        return PaginationBuilder.build(allRelevancePage,allRelevance);
    }

    @Override
    public List<RelevanceTestOn> findRelevanceList(RelevanceTestOnQuery relevanceTestOnQuery){
        List<RelevanceTestOnEntity> relevanceList = relevanceTestOnDao.findRelevanceList(relevanceTestOnQuery);

        if (relevanceList == null || relevanceList.isEmpty()){
            return new ArrayList<>();
        }
        List<RelevanceTestOn> testOnList = BeanMapper.mapList(relevanceList, RelevanceTestOn.class);
        return findRelevanceList(testOnList);
    }


    private  List<RelevanceTestOn> findRelevanceList(List<RelevanceTestOn> relevanceTestOnList){

        List<RelevanceTestOn> list = new ArrayList<>();

        for (RelevanceTestOn relevanceTestOn : relevanceTestOnList) {
            String testonId = relevanceTestOn.getTestonId();
            String authId = relevanceTestOn.getAuthId();

            if (Objects.isNull(authId)){
                deleteRelevance(relevanceTestOn.getRelevanceId());
                continue;
            }
            AuthThird authThird = authThirdService.findOneAuthServer(authId);
            // TestOnPlanInstance testPlanInstance = taskTestOnService.findTestPlanInstance(authThird.getServerId(), testonId);
            relevanceTestOn.setStatus(1);

            String id = relevanceTestOn.getTestonId();
            String url = String.format("%s/#/plan/%s/instanceInfo/%s", authThird.getServerAddress(),relevanceTestOn.getTestPlanId(),id);
            relevanceTestOn.setUrl(url);

            Date date = PipelineUtil.StringChengeDate(relevanceTestOn.getCreateTime());
            String dateTime = PipelineUtil.findDateTime(date, 0);
            relevanceTestOn.setTime(dateTime);

            // relevanceTestOn.setObject(testPlanInstance);

            // if (Objects.isNull(testPlanInstance) || testPlanInstance.getTestPlanName().contains("删除")){
            //     relevanceTestOn.setStatus(2);
            // }else{
            //     // http://192.168.10.13:3000/#/plan/068f6da04eed/instanceInfo/76f147886cb8
            //     String id = testPlanInstance.getId();
            //     String url = String.format("%s/#/plan/%s/instanceInfo/%s", authThird.getServerAddress(),relevanceTestOn.getTestPlanId(),id);
            //     relevanceTestOn.setUrl(url);
            //
            //     Date date = PipelineUtil.StringChengeDate(relevanceTestOn.getCreateTime());
            //     String dateTime = PipelineUtil.findDateTime(date, 0);
            //     relevanceTestOn.setTime(dateTime);
            //
            //     relevanceTestOn.setObject(testPlanInstance);
            // }
            list.add(relevanceTestOn);
        }
        list.sort(Comparator.comparing(RelevanceTestOn::getCreateTime).reversed());
        return list;
    }

    @Override
    public RelevanceTestOn findOneRelevance(String relevanceId) {
        RelevanceTestOnEntity testOnEntity = relevanceTestOnDao.findOneRelevanceTestOn(relevanceId);
        return BeanMapper.map(testOnEntity, RelevanceTestOn.class);
    }

    @Override
    public List<RelevanceTestOn> findAllRelevance() {
        List<RelevanceTestOnEntity> allRelevanceTestOn = relevanceTestOnDao.findAllRelevanceTestOn();

        if (allRelevanceTestOn == null || allRelevanceTestOn.isEmpty()){
            return new ArrayList<>();
        }
        return  BeanMapper.mapList(allRelevanceTestOn,RelevanceTestOn.class);
    }

    @Override
    public List<RelevanceTestOn> findAllRelevanceList(List<String> idList) {
        List<RelevanceTestOnEntity> allCodeList = relevanceTestOnDao.findAllCodeList(idList);
        return  BeanMapper.mapList(allCodeList,RelevanceTestOn.class);
    }
}

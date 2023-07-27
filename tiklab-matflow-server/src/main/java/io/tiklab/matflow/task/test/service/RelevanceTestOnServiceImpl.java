package io.tiklab.matflow.task.test.service;

import io.tiklab.beans.BeanMapper;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.matflow.pipeline.definition.model.Pipeline;
import io.tiklab.matflow.setting.model.AuthThird;
import io.tiklab.matflow.setting.service.AuthThirdService;
import io.tiklab.matflow.support.util.PipelineUtil;
import io.tiklab.matflow.task.test.dao.RelevanceTestOnDao;
import io.tiklab.matflow.task.test.entity.RelevanceTestOnEntity;
import io.tiklab.matflow.task.test.model.RelevanceTestOn;
import io.tiklab.matflow.task.test.model.RelevanceTestOnQuery;
import io.tiklab.matflow.task.test.model.TestOnPlanInstance;
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
    public void createRelevance(String pipelineId, String instanceId) {
        RelevanceTestOn relevanceTestOn = new RelevanceTestOn();
        relevanceTestOn.setPipeline(new Pipeline(pipelineId));
        relevanceTestOn.setTestonId(instanceId);
        relevanceTestOn.setCreateTime(PipelineUtil.date(1));
        String relevance = createRelevance(relevanceTestOn);
        if (Objects.isNull(relevance)){
            throw new ApplicationException("创建TestOn关联关系失败！");
        }
    }

    @Override
    public String createRelevance(RelevanceTestOn relevanceTestOn) {
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

        if (allRelevance == null || allRelevance.isEmpty()){
            return Collections.emptyList();
        }
        List<RelevanceTestOn> relevanceTestOns = BeanMapper.mapList(allRelevance, RelevanceTestOn.class);

        List<AuthThird> authThirdList = authThirdService.findAllAuthServerList("teston");
        if (authThirdList.isEmpty()){
            return Collections.emptyList();
        }
        return findAllRelevance(relevanceTestOns,authThirdList);
    }

    public  Pagination<RelevanceTestOn> findAllRelevancePage(RelevanceTestOnQuery relevanceTestOnQuery){
        Pagination<RelevanceTestOnEntity> allRelevancePage = relevanceTestOnDao.findAllRelevancePage(relevanceTestOnQuery);
        List<RelevanceTestOnEntity> dataList = allRelevancePage.getDataList();

        List<RelevanceTestOn> list = new ArrayList<>();
        if (dataList == null || dataList.isEmpty()){
            return PaginationBuilder.build(allRelevancePage,list);
        }

        List<AuthThird> authThirdList = authThirdService.findAllAuthServerList("teston");
        if (authThirdList.isEmpty()){
            allRelevancePage.setDataList(new ArrayList<>());
            return PaginationBuilder.build(allRelevancePage,list);
        }
        List<RelevanceTestOn> testOnList = BeanMapper.mapList(dataList, RelevanceTestOn.class);
        List<RelevanceTestOn> allRelevance = findAllRelevance(testOnList, authThirdList);
        return PaginationBuilder.build(allRelevancePage,allRelevance);
    }


    private  List<RelevanceTestOn> findAllRelevance(List<RelevanceTestOn> relevanceTestOnList, List<AuthThird> authThirdList){

        List<RelevanceTestOn> list = new ArrayList<>();

        for (RelevanceTestOn relevanceTestOn : relevanceTestOnList) {
            String testonId = relevanceTestOn.getTestonId();
            if (relevanceTestOnList.size() == 1 ){
                AuthThird authThird = authThirdList.get(0);
                TestOnPlanInstance testPlanInstance = taskTestOnService.findAllTestPlanInstance(authThird.getServerId(), testonId);
                relevanceTestOn.setStatus(1);
                if (Objects.isNull(testPlanInstance)){
                    relevanceTestOn.setStatus(2);
                }
                Date date = PipelineUtil.StringChengeDate(relevanceTestOn.getCreateTime());
                String dateTime = PipelineUtil.findDateTime(date, 0);
                relevanceTestOn.setTime(dateTime);
                relevanceTestOn.setObject(testPlanInstance);
                relevanceTestOn.setUrl(authThird.getServerAddress());
                list.add(relevanceTestOn);
            }else {
                TestOnPlanInstance testPlanInstance = null;
                for (AuthThird authThird : authThirdList) {
                    TestOnPlanInstance testPlanInstance1 = taskTestOnService.findAllTestPlanInstance(authThird.getServerId(), testonId);
                    if (Objects.isNull(testPlanInstance1)){
                        continue;
                    }
                    relevanceTestOn.setUrl(authThird.getServerAddress());
                    testPlanInstance = testPlanInstance1;
                }
                Date date = PipelineUtil.StringChengeDate(relevanceTestOn.getCreateTime());
                String dateTime = PipelineUtil.findDateTime(date, 0);
                relevanceTestOn.setTime(dateTime);
                relevanceTestOn.setStatus(1);
                if (Objects.isNull(testPlanInstance)){
                    relevanceTestOn.setStatus(2);
                }
                relevanceTestOn.setObject(testPlanInstance);
                list.add(relevanceTestOn);
            }
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

        if (allRelevanceTestOn == null || allRelevanceTestOn.size() == 0){
            return Collections.emptyList();
        }
        return  BeanMapper.mapList(allRelevanceTestOn,RelevanceTestOn.class);
    }

    @Override
    public List<RelevanceTestOn> findAllRelevanceList(List<String> idList) {
        List<RelevanceTestOnEntity> allCodeList = relevanceTestOnDao.findAllCodeList(idList);
        return  BeanMapper.mapList(allCodeList,RelevanceTestOn.class);
    }
}

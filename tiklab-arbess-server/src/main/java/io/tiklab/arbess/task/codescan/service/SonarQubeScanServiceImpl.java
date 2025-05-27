package io.tiklab.arbess.task.codescan.service;

import io.tiklab.arbess.task.codescan.dao.SonarQubeScanDao;
import io.tiklab.arbess.task.codescan.entity.SonarQubeScanEntity;
import io.tiklab.arbess.task.codescan.model.SonarQubeScan;
import io.tiklab.arbess.task.codescan.model.SonarQubeScanQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.toolkit.beans.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class SonarQubeScanServiceImpl implements SonarQubeScanService {


    @Autowired
    SonarQubeScanDao sonarQubeScanDao;

    @Override
    public String creatSonarQubeScan(SonarQubeScan sonarQubeScan) {
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        sonarQubeScan.setCreateTime(format);
        SonarQubeScanEntity scanEntity = BeanMapper.map(sonarQubeScan, SonarQubeScanEntity.class);
        return sonarQubeScanDao.creatSonarQubeScan(scanEntity);
    }

    @Override
    public void updateSonarQubeScan(SonarQubeScan sonarQubeScan) {
        SonarQubeScanEntity scanEntity = BeanMapper.map(sonarQubeScan, SonarQubeScanEntity.class);
        sonarQubeScanDao.updateSonarQubeScan(scanEntity);
    }

    @Override
    public void deleteSonarQubeScan(String id) {
        sonarQubeScanDao.deleteSonarQubeScan(id);
    }


    @Override
    public SonarQubeScan findSonarQubeScan(String id) {
        SonarQubeScanEntity scanEntity = sonarQubeScanDao.findSonarQubeScan(id);

        return BeanMapper.map(scanEntity,SonarQubeScan.class);
    }


    @Override
    public List<SonarQubeScan> findAllSonarQubeScan() {
        List<SonarQubeScanEntity> scanEntityList = sonarQubeScanDao.findAllSonarQubeScan();
        if (scanEntityList == null || scanEntityList.isEmpty()){
            return Collections.emptyList();
        }
        return BeanMapper.mapList(scanEntityList,SonarQubeScan.class);
    }


    @Override
    public List<SonarQubeScan> findSonarQubeScanList(SonarQubeScanQuery scanQuery) {
        List<SonarQubeScanEntity> scanEntityList = sonarQubeScanDao.findSonarQubeScanList(scanQuery);

        if (scanEntityList == null || scanEntityList.isEmpty()){
            return Collections.emptyList();
        }
        return BeanMapper.mapList(scanEntityList,SonarQubeScan.class);
    }

    @Override
    public List<SonarQubeScan> findSonarQubeScanList(List<String> idList) {
        List<SonarQubeScanEntity> scanEntityList = sonarQubeScanDao.findSonarQubeScanList(idList);

        if (scanEntityList == null || scanEntityList.isEmpty()){
            return Collections.emptyList();
        }
        return BeanMapper.mapList(scanEntityList,SonarQubeScan.class);
    }

    @Override
    public Pagination<SonarQubeScan> findSonarQubeScanPage(SonarQubeScanQuery scanQuery) {
        Pagination<SonarQubeScanEntity> scanEntityPagination = sonarQubeScanDao.findSonarQubeScanPage(scanQuery);
        List<SonarQubeScanEntity> dataList = scanEntityPagination.getDataList();
        if (Objects.isNull(dataList) || dataList.isEmpty()){
            return PaginationBuilder.build(scanEntityPagination,Collections.emptyList());
        }

        List<SonarQubeScan> summaryList = BeanMapper.mapList(dataList, SonarQubeScan.class);
        return PaginationBuilder.build(scanEntityPagination,summaryList);
    }
}

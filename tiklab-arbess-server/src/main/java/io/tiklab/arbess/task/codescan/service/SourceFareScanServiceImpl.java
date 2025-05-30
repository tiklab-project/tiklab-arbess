package io.tiklab.arbess.task.codescan.service;

import io.tiklab.arbess.task.codescan.dao.SourceFareScanDao;
import io.tiklab.arbess.task.codescan.entity.SourceFareScanEntity;
import io.tiklab.arbess.task.codescan.model.SourceFareScan;
import io.tiklab.arbess.task.codescan.model.SourceFareScanQuery;
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
public class SourceFareScanServiceImpl implements SourceFareScanService {


    @Autowired
    SourceFareScanDao sourceFareScanDao;

    @Override
    public String creatSourceFareScan(SourceFareScan sourceFareScan) {
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        sourceFareScan.setCreateTime(format);
        SourceFareScanEntity scanEntity = BeanMapper.map(sourceFareScan, SourceFareScanEntity.class);
        return sourceFareScanDao.creatSourceFareScan(scanEntity);
    }

    @Override
    public void updateSourceFareScan(SourceFareScan sourceFareScan) {
        SourceFareScanEntity scanEntity = BeanMapper.map(sourceFareScan, SourceFareScanEntity.class);
        sourceFareScanDao.updateSourceFareScan(scanEntity);
    }

    @Override
    public void deleteSourceFareScan(String id) {
        sourceFareScanDao.deleteSourceFareScan(id);
    }


    @Override
    public SourceFareScan findSourceFareScan(String id) {
        SourceFareScanEntity scanEntity = sourceFareScanDao.findSourceFareScan(id);

        return BeanMapper.map(scanEntity,SourceFareScan.class);
    }


    @Override
    public List<SourceFareScan> findAllSourceFareScan() {
        List<SourceFareScanEntity> scanEntityList = sourceFareScanDao.findAllSourceFareScan();
        if (scanEntityList == null || scanEntityList.isEmpty()){
            return Collections.emptyList();
        }
        return BeanMapper.mapList(scanEntityList,SourceFareScan.class);
    }


    @Override
    public List<SourceFareScan> findSourceFareScanList(SourceFareScanQuery scanQuery) {
        List<SourceFareScanEntity> scanEntityList = sourceFareScanDao.findSourceFareScanList(scanQuery);

        if (scanEntityList == null || scanEntityList.isEmpty()){
            return Collections.emptyList();
        }
        return BeanMapper.mapList(scanEntityList,SourceFareScan.class);
    }

    @Override
    public List<SourceFareScan> findSourceFareScanList(List<String> idList) {
        List<SourceFareScanEntity> scanEntityList = sourceFareScanDao.findSourceFareScanList(idList);

        if (scanEntityList == null || scanEntityList.isEmpty()){
            return Collections.emptyList();
        }
        return BeanMapper.mapList(scanEntityList,SourceFareScan.class);
    }

    @Override
    public Pagination<SourceFareScan> findSourceFareScanPage(SourceFareScanQuery scanQuery) {
        Pagination<SourceFareScanEntity> scanEntityPagination = sourceFareScanDao.findSourceFareScanPage(scanQuery);
        List<SourceFareScanEntity> dataList = scanEntityPagination.getDataList();
        if (Objects.isNull(dataList) || dataList.isEmpty()){
            return PaginationBuilder.build(scanEntityPagination,Collections.emptyList());
        }

        List<SourceFareScan> summaryList = BeanMapper.mapList(dataList, SourceFareScan.class);
        return PaginationBuilder.build(scanEntityPagination,summaryList);
    }
}

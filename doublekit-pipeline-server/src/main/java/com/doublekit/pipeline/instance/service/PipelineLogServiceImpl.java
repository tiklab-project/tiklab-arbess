package com.doublekit.pipeline.instance.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.join.JoinTemplate;
import com.doublekit.pipeline.instance.dao.PipelineLogDao;
import com.doublekit.pipeline.instance.entity.PipelineLogEntity;
import com.doublekit.pipeline.instance.model.PipelineLog;
import com.doublekit.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Exporter
public class PipelineLogServiceImpl implements PipelineLogService {

    @Autowired
    GitCloneService gitCloneService;

    @Autowired
    PipelineLogDao pipelineLogDao;

    SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");

    @Override
    public String createPipelineLog(PipelineLog pipelineLog) {

        PipelineLogEntity pipelineLogEntity = BeanMapper.map(pipelineLog, PipelineLogEntity.class);

        return pipelineLogDao.createPipelineLog(pipelineLogEntity);
    }

    @Override
    public void deletePipelineLog(String id) {
        pipelineLogDao.deletePipelineLog(id);
    }

    @Override
    public void updatePipelineLog(PipelineLog pipelineLog) {

        PipelineLogEntity pipelineLogEntity = BeanMapper.map(pipelineLog, PipelineLogEntity.class);

        pipelineLogDao.updatePipelineLog(pipelineLogEntity);
    }

    @Override
    public PipelineLog selectPipelineLog(String id) {
        PipelineLogEntity pipelineLogEntity = pipelineLogDao.selectPipelineLog(id);

        return BeanMapper.map(pipelineLogEntity, PipelineLog.class);
    }

    @Override
    public List<PipelineLog> selectAllPipelineLog() {

        List<PipelineLogEntity> pipelineLogEntityList = pipelineLogDao.selectAllPipelineLog();

        return BeanMapper.mapList(pipelineLogEntityList, PipelineLog.class);
    }

    @Override
    public List<PipelineLog> selectAllPipelineLogList(List<String> idList) {

        List<PipelineLogEntity> pipelineLogList = pipelineLogDao.selectAllPipelineLogList(idList);

        return BeanMapper.mapList(pipelineLogList, PipelineLog.class);
    }


    public String structure(String pipelineId) throws Exception {

        PipelineLog pipelineLog = new PipelineLog();

        //开始运行时间
        String last = dateFormat.format(new Date());

        //拉取代码
        int  i = gitCloneService.gitClone(pipelineId);

        if (i == 1){
            pipelineLog.setLogCodeState(10);
        }
        //结束运行时间
        String now = dateFormat.format(new Date());

        //获取拉取代码所用时长
        pipelineLog.setLogCodeTime((int) time(now, last));

        //调用构建方法
        String write = gitCloneService.write(pipelineId);

        //获取构建日志
        if (write != null){
            pipelineLog.setLogRunLog(write);

        }
        String time =dateFormat.format(new Date());
        //获取构建所用时长
        pipelineLog.setLogPackTime((int)time(time, now));

        //设置构建状态
        pipelineLog.setLogPackState(10);

        //调用部署方法
        int j = gitCloneService.gitClone(pipelineId);

        if (j == 1){
            pipelineLog.setLogDeployState(10);
        }
        //获取部署时间
        pipelineLog.setLogDeployTime((int)time(dateFormat.format(new Date()), now));

        return createPipelineLog(pipelineLog);
    }

    /**
     * 获取时间差
     * @param now 现在
     * @param last 过去
     * @return 时间差
     * @throws ParseException 时间转换异常
     */
    private long time(String now ,String last) throws Exception {

        long l= 0;
        try {
            l = dateFormat.parse(now).getTime()-dateFormat.parse(last).getTime();

        } catch (Exception e) {
             throw new Exception("时间转换异常"+e);
        }
        long day=l/(24*60*60*1000);

        long hour=(l/(60*60*1000)-day*24);

        long min=((l/(60*1000))-day*24*60-hour*60);

        long s = (l/1000-day*24*60*60-hour*60*60-min*60);

        if (min>0){
            return min*60 + s;
        }
        return s;
    }

}

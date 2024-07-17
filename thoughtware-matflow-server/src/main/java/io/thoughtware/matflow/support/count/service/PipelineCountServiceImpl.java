package io.thoughtware.matflow.support.count.service;

import io.thoughtware.matflow.pipeline.instance.model.PipelineInstance;
import io.thoughtware.matflow.pipeline.instance.service.PipelineInstanceService;
import io.thoughtware.matflow.support.count.model.*;
import io.thoughtware.matflow.support.util.util.PipelineFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class PipelineCountServiceImpl implements PipelineCountService {


    @Autowired
    PipelineInstanceService pipelineInstanceService;


    @Override
    public List<PipelineRunDayCount> findPipelineRunTimeSpan(PipelineRunCountQuery countQuery){
        String pipelineId = countQuery.getPipelineId();
        List<String> dayTimeList ;
        if (countQuery.getCountDay() != 0){
            dayTimeList = findRecentDaysFormatted(countQuery.getCountDay());
        }else {
            dayTimeList = findZeroPointsBetween(countQuery.getQueryTime()[0], countQuery.getQueryTime()[1]);
        }
        int size = dayTimeList.size();
        Collections.sort(dayTimeList);
        List<PipelineRunDayCount> runDayCountList = new ArrayList<>();
        for (int i = 0; i < size ; i++) {
            String beginTime = dayTimeList.get(i);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(beginTime, formatter);

            PipelineRunDayCount pipelineRunDayCount = new PipelineRunDayCount();
            pipelineRunDayCount.setDay(dateTime.getMonthValue() + "-" + dateTime.getDayOfMonth());
            List<String> stageEndTimeList = findCalculateStageEndTimes(dateTime.toLocalDate());

            List<PipelineRunTimeCount> timeCountList = new ArrayList<>();
            for (int j = 0; j < stageEndTimeList.size() ; j++) {
                PipelineRunTimeCount timeCount = new PipelineRunTimeCount();
                String dayTime = stageEndTimeList.get(j);
                LocalDateTime time = LocalDateTime.parse(dayTime, formatter);
                int hour = time.getHour();
                if (hour < 10){
                    timeCount.setTime("0" + time.getHour() + ":00");
                }else {
                    timeCount.setTime(time.getHour() + ":00" );
                }
                String[] queryTime;
                if (j == 0){
                    queryTime = new String[]{beginTime, dayTime};
                }else if (j == stageEndTimeList.size()-1){
                    timeCount.setTime("23:59");
                    if (i == size -1){
                        queryTime =  new String[]{dayTime, findTomorrowTime()};
                    }else {
                        queryTime =  new String[]{dayTime, dayTimeList.get(i+1)};
                    }
                }else {
                    queryTime =  new String[]{stageEndTimeList.get(j-1),dayTime};
                }
                List<PipelineInstance> instanceList = pipelineInstanceService.findInstanceByTime(pipelineId, queryTime);
                timeCount.setNumber(instanceList.size());
                timeCountList.add(timeCount);
            }
            pipelineRunDayCount.setRunTimeCountList(timeCountList);
            runDayCountList.add(pipelineRunDayCount);
        }
        return runDayCountList;
    }


    @Override
    public List<PipelineRunCount> findPipelineRunCount(PipelineRunCountQuery countQuery) {
        List<String> dayTimeList ;
        if (countQuery.getCountDay() != 0){
            dayTimeList = findRecentDaysFormatted(countQuery.getCountDay());
        }else {
            dayTimeList = findZeroPointsBetween(countQuery.getQueryTime()[0], countQuery.getQueryTime()[1]);
        }
        int size = dayTimeList.size();
        Collections.sort(dayTimeList);
        String pipelineId = countQuery.getPipelineId();

        List<PipelineRunCount> countList = new ArrayList<>();
        for (int i = 0; i < size ; i++) {
            String beginTime = dayTimeList.get(i);

            PipelineRunCount pipelineRunCount = new PipelineRunCount();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(beginTime, formatter);
            pipelineRunCount.setDay( dateTime.getMonthValue() + "-" + dateTime.getDayOfMonth());
            String[] queryTime ;
            if (i == size - 1){
                queryTime =  new String[]{beginTime, findTomorrowTime()};
            }else {
                queryTime =  new String[]{beginTime, dayTimeList.get( i + 1 )};
            }

            List<PipelineInstance> instanceList = pipelineInstanceService.findInstanceByTime(pipelineId, queryTime);

            int runTime = 0;
            String type = countQuery.getType();
            if (type.equals("time")){
                List<PipelineInstance> list = instanceList.stream()
                        .filter(instance -> instance.getRunStatus().equals(PipelineFinal.RUN_SUCCESS))
                        .toList();
                for (PipelineInstance pipelineInstance : list) {
                    runTime =  runTime + pipelineInstance.getRunTime();
                }
                if (instanceList.isEmpty()){
                    pipelineRunCount.setNumber(0);
                }else {
                    pipelineRunCount.setNumber(parseDouble(runTime,instanceList.size()));
                }
            }else {
                List<PipelineInstance> list = instanceList.stream()
                        .filter(instance -> instance.getRunStatus().equals(type))
                        .toList();
                if (instanceList.isEmpty()){
                    pipelineRunCount.setNumber(0);
                }else {
                    pipelineRunCount.setNumber(parseDouble(list.size(),instanceList.size())*100);
                }
            }
            countList.add(pipelineRunCount);
        }
        return countList;
    }


    @Override
    public List<PipelineRunResultCount> findPipelineRunResultCount(PipelineRunCountQuery countQuery) {
        List<String> dayTimeList ;
        if (countQuery.getCountDay() != 0){
            dayTimeList = findRecentDaysFormatted(countQuery.getCountDay());
        }else {
            dayTimeList = findZeroPointsBetween(countQuery.getQueryTime()[0], countQuery.getQueryTime()[1]);
        }

        String pipelineId = countQuery.getPipelineId();
        Collections.sort(dayTimeList);
        int size = dayTimeList.size();

        List<PipelineRunResultCount> countList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            String beginTime = dayTimeList.get(i);

            PipelineRunResultCount runResultCount = new PipelineRunResultCount();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(beginTime, formatter);
            runResultCount.setDay(dateTime.getMonthValue() + "-" + dateTime.getDayOfMonth());

            String[] queryTime ;
            if (i == size - 1){
                queryTime =  new String[]{beginTime, findTomorrowTime()};
            }else {
                queryTime =  new String[]{beginTime, dayTimeList.get( i + 1 )};
            }

            List<PipelineInstance> instanceList = pipelineInstanceService.findInstanceByTime(pipelineId, queryTime);

            if (instanceList.isEmpty()){
                runResultCount.setSuccessNumber(0);
                runResultCount.setErrorNumber(0);
                runResultCount.setHaltNumber(0);
            }else {
                String type = countQuery.getType();
                List<PipelineInstance> successList = instanceList.stream()
                        .filter(instance -> instance.getRunStatus().equals(PipelineFinal.RUN_SUCCESS))
                        .toList();
                List<PipelineInstance> errorList = instanceList.stream()
                        .filter(instance -> instance.getRunStatus().equals(PipelineFinal.RUN_ERROR))
                        .toList();
                List<PipelineInstance> haltList = instanceList.stream()
                        .filter(instance -> instance.getRunStatus().equals(PipelineFinal.RUN_HALT))
                        .toList();
                if (type.equals("rate")){
                    runResultCount.setSuccessNumber(parseDouble(successList.size(),instanceList.size())*100);
                    runResultCount.setErrorNumber(parseDouble(errorList.size(),instanceList.size())*100);
                    runResultCount.setHaltNumber(parseDouble(haltList.size(),instanceList.size())*100);
                }else {
                    runResultCount.setSuccessNumber(successList.size());
                    runResultCount.setErrorNumber(errorList.size());
                    runResultCount.setHaltNumber( haltList.size());
                }
            }
            countList.add(runResultCount);
        }
        return countList;
    }

    /**
     * 获取最近的天数
     * @param days 最近几天
     * @return 天数
     */
    public List<String> findRecentDaysFormatted(int days) {
        List<String> recentDaysFormatted = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
        LocalDate currentDate = LocalDate.now();
        for (int i = 0; i < days; i++) {
            String formattedDate = currentDate.minusDays(i).format(formatter);
            recentDaysFormatted.add(formattedDate);
        }
        return recentDaysFormatted;
    }

    /**
     * 拆分一天时间为6份
     * @param localDate 天数
     * @return 时间
     */
    public List<String> findCalculateStageEndTimes(LocalDate localDate) {
        List<String> endTimeList = new ArrayList<>();

        LocalTime midnight = LocalTime.of(0, 0);
        LocalDateTime todayMidnight = LocalDateTime.of(localDate, midnight);

        for (int i = 0; i < 6; i++) {
            todayMidnight = todayMidnight.plusHours(4);

            String formattedEndTime = todayMidnight.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            endTimeList.add(formattedEndTime);
        }

        return endTimeList;
    }


    /**
     * 获取指定时间区间的天数
     * @param beginTime 最近几天
     * @param endTime 最近几天
     * @return 天数
     */
    public List<String> findZeroPointsBetween(String beginTime, String endTime) {

        LocalDateTime startDateTime = LocalDateTime.parse(beginTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endDateTime = LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        List<String> zeroPoints = new ArrayList<>();

        LocalDateTime currentDateTime = startDateTime.toLocalDate().atStartOfDay();

        while (!currentDateTime.isAfter(endDateTime)) {
            currentDateTime = currentDateTime.plusDays(1);
            String formattedEndTime = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            zeroPoints.add(formattedEndTime);
        }

        return zeroPoints;
    }


    /**
     * 获取明天零点
     * @return 明天零点
     */
    public String findTomorrowTime(){

        // 获取昨天的日期
        LocalDate yesterday = LocalDate.now().plusDays(1);

        // 设置时间为0点，即凌晨
        LocalTime midnight = LocalTime.of(0, 0);

        // 组合昨天的日期和0点时间
        LocalDateTime yesterdayMidnight = LocalDateTime.of(yesterday, midnight);

        // 定义日期时间格式化器
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 格式化输出
        return yesterdayMidnight.format(formatter);
    }

    /**
     * 保留两位小数点
     * @param value1 value1
     * @param value2 value2
     * @return 保留两位小数点
     */
    public double parseDouble(double value1, double value2) {
        double number = value1 / value2;
        DecimalFormat df = new DecimalFormat("#.00");
        String formattedNumber = df.format(number);
        return Double.parseDouble(formattedNumber);

    }

}










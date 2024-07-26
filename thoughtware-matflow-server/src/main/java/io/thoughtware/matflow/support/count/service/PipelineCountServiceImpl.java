package io.thoughtware.matflow.support.count.service;

import io.thoughtware.matflow.pipeline.definition.model.Pipeline;
import io.thoughtware.matflow.pipeline.definition.service.PipelineService;
import io.thoughtware.matflow.pipeline.instance.model.PipelineInstance;
import io.thoughtware.matflow.pipeline.instance.model.PipelineInstanceQuery;
import io.thoughtware.matflow.pipeline.instance.service.PipelineInstanceService;
import io.thoughtware.matflow.support.count.model.*;
import io.thoughtware.matflow.support.util.util.PipelineFinal;
import io.thoughtware.matflow.support.util.util.PipelineUtil;
import io.thoughtware.security.logging.logging.service.LoggingService;
import io.thoughtware.security.logging.logging.service.LoggingTypeService;
import io.thoughtware.user.user.model.User;
import io.thoughtware.user.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PipelineCountServiceImpl implements PipelineCountService {

    @Autowired
    PipelineInstanceService pipelineInstanceService;

    @Autowired
    LoggingTypeService loggingTypeService;

    @Autowired
    LoggingService loggingService;

    @Autowired
    PipelineService pipelineService;

    @Autowired
    UserService userService;

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
            List<String> stageEndTimeList = findCalculateStageEndTimes(dateTime.toLocalDate(),6);

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
    public List<PipelineDayCount> findRunTimeSpan(PipelineRunCountQuery countQuery){

        List<PipelineDayCount> dayCountList = new ArrayList<>();
        String dayFormatted = findRecentDayFormatted(countQuery.getCountDay());
        String dayFormat = findRecentDayFormatted(countQuery.getCountDay() - 1 );
        String[] queryTime =  new String[]{ dayFormatted, dayFormat };

        String pipelineId = countQuery.getPipelineId();
        List<PipelineInstance> instanceList;
        if (StringUtils.isEmpty(pipelineId)){
            instanceList = pipelineInstanceService.findInstanceByTime(queryTime);
        }else {
            instanceList = pipelineInstanceService.findInstanceByTime(pipelineId, queryTime);
        }

        instanceList.sort(Comparator.comparing(PipelineInstance::getCreateTime));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(dayFormatted, formatter);
        List<String> stageEndTimeList = findCalculateStageEndTimes(dateTime.toLocalDate(),24);

        Map<Integer,PipelineTimeCount> timeCountMap = new HashMap<>();
        for (PipelineInstance instance : instanceList) {
            LocalDateTime parse = LocalDateTime.parse(instance.getCreateTime(),formatter);
            int hour = parse.getHour();
            PipelineTimeCount timeCount = timeCountMap.get(hour);
            if (Objects.isNull(timeCount)){
                timeCount = new PipelineTimeCount();
            }
            if (instance.getRunStatus().equals(PipelineFinal.RUN_SUCCESS)){
                timeCount.setSuccessNumber(timeCount.getSuccessNumber() + 1);
            } else if (instance.getRunStatus().equals(PipelineFinal.RUN_ERROR)){
                timeCount.setErrNumber(timeCount.getErrNumber() + 1);
            }else {
                timeCount.setHaltNumber(timeCount.getHaltNumber() + 1);
            }
            timeCount.setAllNumber(timeCount.getAllNumber() + 1);
            timeCountMap.put(hour,timeCount);
        }

        for (String time : stageEndTimeList) {
            LocalDateTime parse = LocalDateTime.parse(time,formatter);
            int hour = parse.getHour();
            PipelineDayCount dayCount = new PipelineDayCount();
            if (hour < 10 && hour > 0){
                dayCount.setTime("0" + hour + ":00");
            }else if (hour == 0) {
                dayCount.setTime( "23:59");
            } else  {
                dayCount.setTime( hour + ":00");
            }
            PipelineTimeCount timeCount = timeCountMap.get(parse.getHour());
            if (Objects.isNull(timeCount)){
                timeCount = new PipelineTimeCount();
            }
            dayCount.setTimeCount(timeCount);
            dayCountList.add(dayCount);
        }
        return dayCountList;
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

        List<PipelineRunCount> countList = new ArrayList<>();
        for (int i = 0; i < size -1 ; i++) {
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

            String pipelineId = countQuery.getPipelineId();
            List<PipelineInstance> instanceList;
            if (StringUtils.isEmpty(pipelineId)){
                instanceList = pipelineInstanceService.findInstanceByTime(queryTime);
            }else {
                instanceList = pipelineInstanceService.findInstanceByTime(pipelineId, queryTime);
            }

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

        Collections.sort(dayTimeList);
        int size = dayTimeList.size();

        List<PipelineRunResultCount> countList = new ArrayList<>();
        for (int i = 0; i < size -1; i++) {
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

            String pipelineId = countQuery.getPipelineId();
            List<PipelineInstance> instanceList;
            if (StringUtils.isEmpty(pipelineId)){
                instanceList = pipelineInstanceService.findInstanceByTime(queryTime);
            }else {
                instanceList = pipelineInstanceService.findInstanceByTime(pipelineId, queryTime);
            }

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

    @Override
    public PipelineRunResultCount findRunResultCount(PipelineRunCountQuery countQuery) {

        String dayFormatted = findRecentDayFormatted(countQuery.getCountDay());
        String dayFormat = findRecentDayFormatted(countQuery.getCountDay() -1 );
        String[] queryTime =  new String[]{ dayFormatted, dayFormat };

        String pipelineId = countQuery.getPipelineId();
        List<PipelineInstance> instanceList;
        if (StringUtils.isEmpty(pipelineId)){
            instanceList = pipelineInstanceService.findInstanceByTime(queryTime);
        }else {
            instanceList = pipelineInstanceService.findInstanceByTime(pipelineId, queryTime);
        }

        PipelineRunResultCount runResultCount = new PipelineRunResultCount();

        if (instanceList.isEmpty()){
            runResultCount.setSuccessNumber(0);
            runResultCount.setErrorNumber(0);
            runResultCount.setHaltNumber(0);
            runResultCount.setAllNumber(0);
        }else {
            runResultCount.setAllNumber(instanceList.size());
            List<PipelineInstance> successList = instanceList.stream()
                    .filter(instance -> instance.getRunStatus().equals(PipelineFinal.RUN_SUCCESS))
                    .toList();
            List<PipelineInstance> errorList = instanceList.stream()
                    .filter(instance -> instance.getRunStatus().equals(PipelineFinal.RUN_ERROR))
                    .toList();
            List<PipelineInstance> haltList = instanceList.stream()
                    .filter(instance -> instance.getRunStatus().equals(PipelineFinal.RUN_HALT))
                    .toList();

            long count = instanceList.stream().mapToInt(PipelineInstance::getRunTime).sum();

            runResultCount.setSuccessNumber(successList.size());
            runResultCount.setErrorNumber(errorList.size());
            runResultCount.setHaltNumber( haltList.size());
            String time = PipelineUtil.formatDateTime(count / instanceList.size());
            runResultCount.setExecTime(time);
            double v = parseDouble(successList.size(), instanceList.size()) * 100;
            runResultCount.setSuccessRate(v+"%");
        }
        return runResultCount;
    }

    @Override
    public List<PipelineDayRateCount> findDayRateCount(PipelineRunCountQuery countQuery){

        String dayFormatted = findRecentDayFormatted(countQuery.getCountDay());
        String dayFormat = findRecentDayFormatted(countQuery.getCountDay() - 1 );
        String[] queryTime =  new String[]{ dayFormatted, dayFormat };

        String pipelineId = countQuery.getPipelineId();
        List<PipelineInstance> instanceList;
        if (StringUtils.isEmpty(pipelineId)){
            instanceList = pipelineInstanceService.findInstanceByTime(queryTime);
        }else {
            instanceList = pipelineInstanceService.findInstanceByTime(pipelineId, queryTime);
        }

        List<PipelineDayRateCount> rateList = new ArrayList<>();

        boolean type = countQuery.getType().equals("user");
        if (type){
            // 获取所有用户
            List<User> pipelineList = instanceList.stream().map(PipelineInstance::getUser)
                    .filter(user -> !Objects.isNull(user))
                    .toList();

            // 得到不同的流水线集合
            Map<String, List<User>> userGroupMap = pipelineList.stream()
                    .collect(Collectors.groupingBy(User::getId));

            // 统计不同的流水线数量
            List<PipelineDayRateCount> dayRateList = userGroupMap.values().stream()
                    .map(list -> new PipelineDayRateCount(list.get(0), list.size()))
                    .toList();

            List<PipelineDayRateCount> rateCounts = dayRateList.stream()
                    .sorted(Comparator.comparing(PipelineDayRateCount::getAllNumber).reversed())
                    .toList();
            if (rateCounts.size() > 10){
                rateCounts = rateCounts.subList(0, 10);
            }

            for (PipelineDayRateCount pipelineDayRateCount : rateCounts) {
                String id = pipelineDayRateCount.getUser().getId();
                User user = userService.findUser(id);
                pipelineDayRateCount.setUser(user);
                List<PipelineInstance> list = instanceList.stream().filter(a -> a.getUser().getId().equals(id)).toList();

                long successCount = list.stream().filter(a -> a.getUser().getId().equals(id) &&
                        a.getRunStatus().equals(PipelineFinal.RUN_SUCCESS)).count();

                pipelineDayRateCount.setSuccessNumber((int) successCount);
                double v = parseDouble(successCount, list.size()) * 100;

                pipelineDayRateCount.setSuccessRate(v + "%");
                long errorCount = list.stream().filter(a -> a.getUser().getId().equals(id) &&
                        a.getRunStatus().equals(PipelineFinal.RUN_ERROR)).count();

                pipelineDayRateCount.setErrorNumber((int) errorCount);
                rateList.add(pipelineDayRateCount);
            }

        } else {

            // 获取所有用户
            List<Pipeline> pipelineList = instanceList.stream().map(PipelineInstance::getPipeline)
                    .filter(pipeline -> !Objects.isNull(pipeline))
                    .toList();

            // 得到不同的流水线集合
            Map<String, List<Pipeline>> pipelineGroupMap = pipelineList.stream()
                    .collect(Collectors.groupingBy(Pipeline::getId));

            // 统计不同的流水线数量
            List<PipelineDayRateCount> dayRateList = pipelineGroupMap.values().stream()
                    .map(list -> new PipelineDayRateCount(list.get(0), list.size())).toList();

            List<PipelineDayRateCount> rateCounts = dayRateList.stream()
                    .sorted(Comparator.comparing(PipelineDayRateCount::getAllNumber).reversed()).toList();
            if (rateCounts.size() > 10){
                rateCounts = rateCounts.subList(0, 10);
            }

            for (PipelineDayRateCount pipelineDayRateCount : rateCounts) {
                String id = pipelineDayRateCount.getPipeline().getId();
                Pipeline pipeline = pipelineService.findPipelineById(id);
                pipelineDayRateCount.setPipeline(pipeline);
                List<PipelineInstance> list = instanceList.stream().filter(a -> a.getPipeline().getId().equals(id)).toList();
                pipelineDayRateCount.setAllNumber(list.size());
                long successCount = list.stream().filter(a -> a.getPipeline().getId().equals(id) && a.getRunStatus()
                        .equals(PipelineFinal.RUN_SUCCESS)).count();

                pipelineDayRateCount.setSuccessNumber((int) successCount);
                double v = parseDouble(successCount, list.size())*100;
                pipelineDayRateCount.setSuccessRate(v + "%");

                long errorCount = list.stream().filter(a -> a.getPipeline().getId().equals(id) && a.getRunStatus()
                        .equals(PipelineFinal.RUN_ERROR)).count();
                pipelineDayRateCount.setErrorNumber((int) errorCount);
                rateList.add(pipelineDayRateCount);
            }
        }
        rateList.sort(Comparator.comparing(PipelineDayRateCount::getAllNumber).reversed());
        return rateList;
    }

    @Override
    public PipelineSurveyCount findPipelineSurveyCount(String pipelineId) {

        PipelineSurveyCount pipelineSurveyCount = new PipelineSurveyCount();
        Pipeline pipeline = pipelineService.findOnePipeline(pipelineId);
        pipelineSurveyCount.setPipeline(pipeline);
        pipelineSurveyCount.setUser(pipeline.getUser());

        PipelineInstanceQuery pipelineInstanceQuery = new PipelineInstanceQuery();
        pipelineInstanceQuery.setPipelineId(pipelineId);
        List<PipelineInstance> pipelineInstanceList = pipelineInstanceService.findPipelineInstanceList(pipelineInstanceQuery);
        if (!pipelineInstanceList.isEmpty()){
            // 过滤出运行中的实例
            List<PipelineInstance> instanceList = pipelineInstanceList.stream().filter(a -> !a.getRunStatus().equals(PipelineFinal.RUN_RUN)).toList();
            if (!instanceList.isEmpty()){
                long haltNumber = instanceList.stream().filter(a -> a.getRunStatus().equals(PipelineFinal.RUN_HALT)).count();
                double v = parseDouble((double) haltNumber, instanceList.size()) * 100;
                pipelineSurveyCount.setHaltRate(v+"%");
                pipelineSurveyCount.setHaltNumber(String.valueOf(haltNumber));
                long successNumber = instanceList.stream().filter(a -> a.getRunStatus().equals(PipelineFinal.RUN_SUCCESS)).count();
                double v1 = parseDouble((double) successNumber, instanceList.size()) * 100;
                pipelineSurveyCount.setSuccessRate(v1+"%");
                pipelineSurveyCount.setSuccessNumber(String.valueOf(successNumber));
                long errorNumber = instanceList.stream().filter(a -> a.getRunStatus().equals(PipelineFinal.RUN_ERROR)).count();
                double v2 = parseDouble((double) errorNumber, instanceList.size()) * 100;
                pipelineSurveyCount.setErrorRate(v2+"%");
                pipelineSurveyCount.setErrorNumber(String.valueOf(errorNumber));
            }
            pipelineSurveyCount.setAllInstanceNumber(instanceList.size());
        }

        PipelineInstance latelyInstance = pipelineInstanceService.findLatelyInstance(pipelineId);
        if (Objects.isNull(latelyInstance)){
            pipelineSurveyCount.setRecentlyRunTime("无");
        }else {
            String createTime = latelyInstance.getCreateTime();
            int runTime = latelyInstance.getRunTime();
            Date date = PipelineUtil.StringChengeDate(createTime);
            String dateTime = PipelineUtil.findDateTime(date, 1000);
            pipelineSurveyCount.setRecentlyRunTime(dateTime);

            String formatted = PipelineUtil.formatDateTime(runTime);
            pipelineSurveyCount.setRunTime(formatted);
        }
        return pipelineSurveyCount;
    }

    @Override
    public PipelineSurveyResultCount findPipelineSurveyResultCount(String pipelineId){
        PipelineSurveyResultCount surveyResultCount = new PipelineSurveyResultCount();
        List<String> formatted = findRecentDaysFormatted(7);
        String[] timeString = { formatted.get(formatted.size()-1),formatted.get(0)};

        List<PipelineInstance> pipelineInstanceList = pipelineInstanceService.findInstanceByTime(pipelineId, timeString);

        findPipelineSurveyResultCount(pipelineInstanceList, surveyResultCount);

        return surveyResultCount;
    }

    @Override
    public PipelineSurveyCount findSurveyCount() {

        PipelineSurveyCount pipelineSurveyCount = new PipelineSurveyCount();
        PipelineInstanceQuery pipelineInstanceQuery = new PipelineInstanceQuery();
        List<PipelineInstance> pipelineInstanceList = pipelineInstanceService.findPipelineInstanceList(pipelineInstanceQuery);
        if (!pipelineInstanceList.isEmpty()){
            // 过滤出运行中的实例
            List<PipelineInstance> instanceList = pipelineInstanceList.stream().filter(a -> !a.getRunStatus().equals(PipelineFinal.RUN_RUN)).toList();
            if (!instanceList.isEmpty()){
                long haltNumber = instanceList.stream().filter(a -> a.getRunStatus().equals(PipelineFinal.RUN_HALT)).count();
                double v = parseDouble((double) haltNumber, instanceList.size()) * 100;
                pipelineSurveyCount.setHaltRate(v+"%");
                pipelineSurveyCount.setHaltNumber(String.valueOf(haltNumber));
                long successNumber = instanceList.stream().filter(a -> a.getRunStatus().equals(PipelineFinal.RUN_SUCCESS)).count();
                double v1 = parseDouble((double) successNumber, instanceList.size()) * 100;
                pipelineSurveyCount.setSuccessRate(v1+"%");
                pipelineSurveyCount.setSuccessNumber(String.valueOf(successNumber));
                long errorNumber = instanceList.stream().filter(a -> a.getRunStatus().equals(PipelineFinal.RUN_ERROR)).count();
                double v2 = parseDouble((double) errorNumber, instanceList.size()) * 100;
                pipelineSurveyCount.setErrorRate(v2+"%");
                pipelineSurveyCount.setErrorNumber(String.valueOf(errorNumber));
            }
            pipelineSurveyCount.setAllInstanceNumber(instanceList.size());
        }
        return pipelineSurveyCount;
    }

    @Override
    public PipelineSurveyResultCount findSurveyResultCount(){
        PipelineSurveyResultCount surveyResultCount = new PipelineSurveyResultCount();
        List<String> formatted = findRecentDaysFormatted(7);
        String[] timeString = { formatted.get(formatted.size()-1),formatted.get(0)};

        List<PipelineInstance> pipelineInstanceList = pipelineInstanceService.findInstanceByTime(timeString);

        findPipelineSurveyResultCount(pipelineInstanceList, surveyResultCount);

        return surveyResultCount;
    }

    public List<String> findRecentDaysFormatted(int days) {
        List<String> recentDaysFormatted = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
        LocalDate currentDate = LocalDate.now();
        for (int i = -1; i < days; i++) {
            String formattedDate = currentDate.minusDays(i).format(formatter);
            recentDaysFormatted.add(formattedDate);
        }
        return recentDaysFormatted;
    }

    @Override
    public List<String> findDaysFormatted(int days) {
        List<String> stringList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        LocalDate currentDate = LocalDate.now();
        for (int i = 0; i < days; i++) {
            if (i == 0){
                stringList.add("今天");
            } else if (i == 1) {
                stringList.add("昨天");
            } else if (i == 2) {
                stringList.add("前天");
            } else {
                String formattedDate = currentDate.minusDays(i).format(formatter);
                stringList.add(formattedDate);
            }
        }
        return stringList;
    }

    /**
     * 获取最近的天数
     * @param days 最近几天
     * @return 天数
     */
    public String findRecentDayFormatted(int days) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
        LocalDate currentDate = LocalDate.now();
        return currentDate.minusDays(days).format(formatter);
    }

    /**
     * 拆分一天时间为count份
     * @param localDate 天数
     * @return 时间
     */
    public List<String> findCalculateStageEndTimes(LocalDate localDate,int count) {
        List<String> endTimeList = new ArrayList<>();

        LocalTime midnight = LocalTime.of(0, 0);
        LocalDateTime todayMidnight = LocalDateTime.of(localDate, midnight);

        for (int i = 0; i < count; i++) {
            todayMidnight = todayMidnight.plusHours(24/count);

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

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(beginTime, dateTimeFormatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endTime, dateTimeFormatter);

        List<String> zeroPoints = new ArrayList<>();

        LocalDateTime currentDateTime = startDateTime.toLocalDate().atStartOfDay();

        while (!currentDateTime.isAfter(endDateTime)) {
            currentDateTime = currentDateTime.plusDays(1);
            String formattedEndTime = currentDateTime.format(dateTimeFormatter);
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

    /**
     * 获取日志用户数量
     * @param pipelineInstanceList 实例列表
     * @param surveyResultCount 实例列表
     */
    public void findPipelineSurveyResultCount(List<PipelineInstance> pipelineInstanceList,PipelineSurveyResultCount surveyResultCount) {
        if (pipelineInstanceList.isEmpty()){
            return;
        }

        // 过滤出运行中的实例
        List<PipelineInstance> instanceList = pipelineInstanceList.stream()
                .filter(a -> !a.getRunStatus().equals(PipelineFinal.RUN_RUN))
                .toList();
        if (!instanceList.isEmpty()) {

            long haltNumber = instanceList.stream()
                    .filter(a -> a.getRunStatus().equals(PipelineFinal.RUN_HALT)).count();

            surveyResultCount.setHaltNumber(haltNumber);

            long successNumber = instanceList.stream()
                    .filter(a -> a.getRunStatus().equals(PipelineFinal.RUN_SUCCESS)).count();
            surveyResultCount.setSuccessNumber(successNumber);

            long errorNumber = instanceList.stream()
                    .filter(a -> a.getRunStatus().equals(PipelineFinal.RUN_ERROR)).count();
            surveyResultCount.setErrorNumber(errorNumber);

            double totalRunTime = instanceList.stream()
                    .mapToInt(PipelineInstance::getRunTime)
                    .sum();
            String time = PipelineUtil.formatDateTime((long) (totalRunTime / instanceList.size()));
            surveyResultCount.setRunTime(time);
        }
    }


}


















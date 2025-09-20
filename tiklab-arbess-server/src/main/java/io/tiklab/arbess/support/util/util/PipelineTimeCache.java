package io.tiklab.arbess.support.util.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class PipelineTimeCache {

    private static final Logger logger = LoggerFactory.getLogger(PipelineTimeCache.class);

    /**
     * 任务运行时间记录
     */
    private final static Map<String, AtomicInteger> runTimeMap = new ConcurrentHashMap<>();

    public static void addRunTime(String taskInstanceId){
        runTimeMap.put(taskInstanceId, new AtomicInteger(1));
    }

    public static Integer findRuntime(String taskInstanceId){
        AtomicInteger atomicInteger = runTimeMap.get(taskInstanceId);
        if (Objects.isNull(atomicInteger)){
            return 1;
        }
        return atomicInteger.get();
    }

    public static Map<String, AtomicInteger> findRuntime(){
        return runTimeMap;
    }

    public static void updateRuntime(String taskInstanceId){
        runTimeMap.get(taskInstanceId).incrementAndGet();;
    }

    public static void removeRuntime(String taskInstanceId){
        if (StringUtils.isEmpty(taskInstanceId)){
            return;
        }
        runTimeMap.remove(taskInstanceId);
    }



}

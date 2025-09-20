package io.tiklab.arbess.support.util.configure;

import io.tiklab.arbess.support.util.util.PipelineTimeCache;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RunTimeConfigure {

    @Scheduled(fixedRate = 1000)
    public void countTaskRunTime(){
        Map<String, AtomicInteger> runtimeMap = PipelineTimeCache.findRuntime();
        runtimeMap.keySet().forEach(PipelineTimeCache::updateRuntime);
    }


}

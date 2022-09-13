package com.tiklab.matflow;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({MatFlowDistributionAutoConfiguration.class })
public @interface EnableMatFlowDistribution {
}

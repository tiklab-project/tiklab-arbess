package io.tiklab.matflow.starter.annotation;

import io.tiklab.matflow.starter.config.MatFlowAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({MatFlowAutoConfiguration.class })
public @interface MatFlowPipeline {
}

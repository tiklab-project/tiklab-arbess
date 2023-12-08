package io.thoughtware.matflow.starter.annotation;

import io.thoughtware.matflow.starter.config.MatFlowAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({MatFlowAutoConfiguration.class })
public @interface MatFlowPipeline {
}

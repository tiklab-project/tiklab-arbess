package com.doublekit.pipeline.annotation;

import com.doublekit.pipeline.config.PipelineAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({PipelineAutoConfiguration.class})
public @interface EnablePipeline {
}

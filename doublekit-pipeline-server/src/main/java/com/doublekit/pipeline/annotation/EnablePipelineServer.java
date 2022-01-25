package com.doublekit.pipeline.annotation;

import com.doublekit.pipeline.config.PipelineServerAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({PipelineServerAutoConfiguration.class})
public @interface EnablePipelineServer {
}

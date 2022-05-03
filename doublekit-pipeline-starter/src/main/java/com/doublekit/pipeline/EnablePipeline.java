package com.doublekit.pipeline;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({PipelineAutoConfiguration.class})
public @interface EnablePipeline {
}

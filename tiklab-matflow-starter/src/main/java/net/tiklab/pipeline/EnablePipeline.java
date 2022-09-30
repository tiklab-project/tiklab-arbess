package net.tiklab.pipeline;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({net.tiklab.pipeline.PipelineAutoConfiguration.class })
public @interface EnablePipeline {
}

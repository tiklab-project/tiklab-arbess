package io.tiklab.arbess.starter.annotation;

import io.tiklab.arbess.starter.config.ArbessAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({ArbessAutoConfiguration.class })
public @interface EnableArbess {
}

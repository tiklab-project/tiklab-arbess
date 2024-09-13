package io.thoughtware.arbess.starter.annotation;

import io.thoughtware.arbess.starter.config.ArbessAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({ArbessAutoConfiguration.class })
public @interface EnableArbess {
}

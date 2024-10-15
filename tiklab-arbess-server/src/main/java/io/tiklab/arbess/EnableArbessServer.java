package io.tiklab.arbess;

import org.springframework.context.annotation.Import;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({MatFlowServerAutoConfiguration.class})
public @interface EnableArbessServer {
}

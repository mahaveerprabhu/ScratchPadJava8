package mahaveer.auditlog;

/**
 * Created by qxw121 on 2/10/16.
 */

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented

public @interface Audit {

        String name() default "";

}

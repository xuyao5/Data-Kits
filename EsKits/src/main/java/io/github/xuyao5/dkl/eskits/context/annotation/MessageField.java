package io.github.xuyao5.dkl.eskits.context.annotation;

import java.lang.annotation.*;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 14/05/21 21:27
 * @apiNote MessageField
 * @implNote MessageField
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MessageField {

    String value();
}
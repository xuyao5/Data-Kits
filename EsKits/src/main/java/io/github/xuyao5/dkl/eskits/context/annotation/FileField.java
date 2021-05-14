package io.github.xuyao5.dkl.eskits.context.annotation;

import java.lang.annotation.*;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 14/05/21 19:51
 * @apiNote FileColumn
 * @implNote FileColumn
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FileField {

    String value();
}
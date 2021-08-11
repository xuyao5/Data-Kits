package io.github.xuyao5.dkl.eskits.context.annotation;

import java.lang.annotation.*;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 12/08/21 00:02
 * @apiNote FileName
 * @implNote FileName
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FileName {

    String value();
}

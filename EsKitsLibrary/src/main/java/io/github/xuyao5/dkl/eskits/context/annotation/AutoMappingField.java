package io.github.xuyao5.dkl.eskits.context.annotation;

import io.github.xuyao5.dkl.eskits.consts.SortTypeConst;

import java.lang.annotation.*;

import static io.github.xuyao5.dkl.eskits.consts.SortTypeConst.ASC;

/**
 * @author Thomas.XU(xuyao)
 * @version 14/05/21 19:51
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoMappingField {

    String column() default "";//列名模式

    int position() default -1;//列号模式

    int sortPriority() default -1;//优先级，-1代表不参加排序

    SortTypeConst order() default ASC;//排序顺序，默认为asc,可设置desc
}
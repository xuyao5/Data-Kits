package io.github.xuyao5.dkl.eskits.context.annotation;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.lang.annotation.*;
import java.util.Arrays;
import java.util.Optional;

import static io.github.xuyao5.dkl.eskits.context.annotation.FileField.SortType.ASC;

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

    String column() default "";

    int priority() default 0;//优先级，0代表不参加排序

    SortType order() default ASC;//排序顺序，默认为desc,可设置asc

    @RequiredArgsConstructor
    enum SortType {

        ASC("asc"), DESC("desc");

        @Getter
        private final String order;

        public static Optional<SortType> getSortType(@NonNull String order) {
            return Arrays.stream(SortType.values()).filter(sortType -> sortType.order.equalsIgnoreCase(order)).findFirst();
        }
    }
}
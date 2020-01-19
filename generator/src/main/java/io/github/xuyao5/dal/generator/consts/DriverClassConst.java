package io.github.xuyao5.dal.generator.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
public enum DriverClassConst {

    MYSQL_DRIVER("MYSQL", "com.mysql.cj.jdbc.Driver"),
    ;

    @Getter
    private final String type;

    @Getter
    private final String driverClass;

    public static Optional<DriverClassConst> getDriverClassByType(@NotNull String type) {
        return Arrays.stream(DriverClassConst.values()).parallel()
                .filter(driverClassConst -> driverClassConst.getType().equalsIgnoreCase(type))
                .findAny();
    }
}

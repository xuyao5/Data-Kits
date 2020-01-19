package io.github.xuyao5.dal.generator.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
public enum SupportDatabaseConst {

    ORACLE("ORACLE", "Oracle"),
    MYSQL("MYSQL", "MySql"),
    ;

    @Getter
    private final String type;

    @Getter
    private final String description;

    public static Optional<SupportDatabaseConst> getSupportDatabaseByType(@NotNull String type) {
        return Arrays.stream(SupportDatabaseConst.values()).parallel()
                .filter(supportDatabaseConst -> supportDatabaseConst.getType().equalsIgnoreCase(type))
                .findAny();
    }
}

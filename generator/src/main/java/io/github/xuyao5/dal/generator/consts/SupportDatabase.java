package io.github.xuyao5.dal.generator.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
public enum SupportDatabase {

    ORACLE("ORACLE", "Oracle"),
    MYSQL("MYSQL", "MySql"),
    ;

    @Getter
    private final String type;

    @Getter
    private final String description;

    public static Optional<SupportDatabase> getSupportDatabaseByType(@NotNull String type) {
        return Arrays.stream(SupportDatabase.values()).parallel()
                .filter(supportDatabase -> supportDatabase.getType().equalsIgnoreCase(type))
                .findAny();
    }
}

package io.github.xuyao5.dal.flatfile.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
public enum FlatFileType {

    SINGLE("SINGLE", "单行"),
    ;

    @Getter
    private final String type;

    @Getter
    private final String description;

    public static Optional<FlatFileType> getFlatFileByType(@NotNull String type) {
        return Arrays.stream(FlatFileType.values()).parallel()
                .filter(flatFileType -> flatFileType.getType().equalsIgnoreCase(type))
                .findAny();
    }
}

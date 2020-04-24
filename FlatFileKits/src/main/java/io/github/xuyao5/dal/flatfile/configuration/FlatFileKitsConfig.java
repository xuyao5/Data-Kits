package io.github.xuyao5.dal.flatfile.configuration;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Data(staticConstructor = "of")
public final class FlatFileKitsConfig {

    private Files files = Files.of();

    @Data(staticConstructor = "of")
    static class Files {

        private List<File> file = new ArrayList<>();

        @Data(staticConstructor = "of")
        static class File {

            private @NotNull String id;
            private @NotNull String name;
            private @NotNull String pattern;
            private @NotNull String localPath;
            private @NotNull String remotePath;
            private String comments = "#";
            private String encoding = StandardCharsets.UTF_8.name();
            private String lineMapper;
            private int headLinesToSkip = 0;
            private int tailLinesToSkip = 0;
            private String recordSeparatorPolicy;
            private String skippedLinesCallback;
            private boolean strict = false;
            private int batchSize = 1000;
        }
    }
}

package io.github.xuyao5.dal.flatfile.configuration;

import lombok.Data;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Data(staticConstructor = "of")
public class Configuration {

    private Files files = Files.of();

    @Data(staticConstructor = "of")
    public static class Files {

        private File file = File.of();

        @Data(staticConstructor = "of")
        public static class File {

            private String id = UUID.randomUUID().toString();
            private String name;
            private String pattern;
            private String localPath;
            private String remotePath;
            private String comments = "#";
            private String encoding = StandardCharsets.UTF_8.name();
            private String lineMapper;
            private int headLinesToSkip = 0;
            private int tailLinesToSkip = 0;
            private String recordSeparatorPolicy;
            private String skippedLinesCallback;
            private String strict;
            private int batchSize = 1000;
        }
    }
}

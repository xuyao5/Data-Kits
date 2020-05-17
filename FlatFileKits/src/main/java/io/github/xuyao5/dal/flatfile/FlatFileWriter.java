package io.github.xuyao5.dal.flatfile;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FlatFileWriter<T> {

    private final String ID;
//    private final Configuration CONFIG;

    public FlatFileWriter(String id) {
        ID = id;

//        CONFIG = Configuration.of();
    }

    public void process(@NotNull File file, List<T> list) throws IOException {
//        FileUtils.writeLines(file, CONFIG.getFiles().getFile().getEncoding(), list);
    }

    private List<File> getFiles() {
        List<File> files = new ArrayList<>();
        files.add(new File("/Users/xuyao/Downloads/test.txt"));
        return files;
    }
}

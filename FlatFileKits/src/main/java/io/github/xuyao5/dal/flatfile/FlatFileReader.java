package io.github.xuyao5.dal.flatfile;

import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collector;

@Slf4j
public class FlatFileReader<T, R> {

    private final String ID;
//    private final Configuration CONFIG;

    public FlatFileReader(String id) {
        ID = id;

//        CONFIG = Configuration.of();
    }

    public void process(Collector<? super String, T, R> collector) throws IOException {
        List<File> files = getFiles();

        List<R> rList = new CopyOnWriteArrayList<>();

        files.forEach(file -> {
            try (LineIterator iterator = FileUtils.lineIterator(file, "null")) {
                while (iterator.hasNext()) {
                    String line = iterator.nextLine();
                    R r = Splitter.on('|').trimResults().omitEmptyStrings().splitToList(line).parallelStream()
                            .collect(collector);
                }
            } catch (IOException ex) {
                log.error("", ex);
            }
        });
    }

    private List<File> getFiles() {
        List<File> files = new ArrayList<>();
        files.add(new File("/Users/xuyao/Downloads/test.txt"));
        return files;
    }
}

package io.github.xuyao5.dal.flatfile.reader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collector;

public class FlatFileReader<T, R> {

    private final String ID;
//    private final Configuration CONFIG;

    public FlatFileReader(String id) {
        ID = id;

//        CONFIG = Configuration.of();
    }

    public void process(Collector<? super String, T, R> collector) throws IOException {
        final List<File> files = getFiles();

        List<R> rList = new CopyOnWriteArrayList<>();

//        for (File file : files) {
//            try (LineIterator lineIterator = FileUtils.lineIterator(file, CONFIG.getFiles().getFile().getEncoding())) {
//                while (lineIterator.hasNext()) {
//                    final String line = lineIterator.nextLine();
//
//                    R r = Splitter.on('|').trimResults().omitEmptyStrings().splitToList(line).parallelStream()
//                            .collect(collector);
//
//                    rList.add(r);
//                }
//            }
//        }
    }

    private List<File> getFiles() {
        List<File> files = new ArrayList<>();
        files.add(new File("/Users/xuyao/Downloads/test.txt"));
        return files;
    }
}

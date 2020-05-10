package io.github.xuyao5.dal.flatfile.reader;

import io.github.xuyao5.dal.flatfile.AbstractTest;
import org.junit.jupiter.api.Test;

public class FlatFileReaderTest extends AbstractTest {

    @Test
    void process() {
        FlatFileReader myFlatFileReader = flatFileFactory.getFlatFileReader();

        System.out.println(flatFileFactory.getFlatFileKitsConfiguration());

        final long start = System.currentTimeMillis();
//        myFlatFileReader.process(null);
        final long end = System.currentTimeMillis();
        final long duration = (end - start) / 1000;
        System.out.println("耗时：" + duration + "秒");
    }
}
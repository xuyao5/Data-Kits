package io.github.xuyao5.dal.flatfile.writer;

import com.google.common.base.Joiner;
import io.github.xuyao5.dal.flatfile.FlatFileWriter;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FlatFileWriterTest {

    @SneakyThrows
    @Test
    void process() {
        final Random rand = new Random();
        FlatFileWriter<MyFileFormat> writer = new FlatFileWriter<>("");
        final MyFileFormat[] formatArray = new MyFileFormat[10000000];
        for (int i = 0; i < formatArray.length; i++) {
            MyFileFormat format = MyFileFormat.of();
            format.setName(RandomStringUtils.randomAlphanumeric(5, 10));
            format.setScore(rand.nextInt());
            formatArray[i] = format;
        }

        System.out.println("数据生成完成");

        List<String> valList = new ArrayList<>();
        for (int i = 0; i < formatArray.length; i++) {
            char split = 0x1E;
            valList.add(Joiner.on(split).join("学生", i, formatArray[i].getName(), formatArray[i].getScore()));

            if (i % 10000 == 0) {
                FileUtils.writeLines(new File("/Users/xuyao/Downloads/test.txt"), "UTF-8", valList, true);
                valList.clear();
            }
        }
    }
}
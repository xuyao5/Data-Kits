package io.github.xuyao5.dal.flatfile.writer;

import io.github.xuyao5.dal.flatfile.AbstractTest;
import io.github.xuyao5.dal.flatfile.FlatFileWriter;
import io.github.xuyao5.dal.flatfile.vo.MyFileFormat;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class FlatFileWriterTest extends AbstractTest {

    @SneakyThrows
    @Test
    void process() {
        Random rand = new Random();
        FlatFileWriter<MyFileFormat> writer = new FlatFileWriter<>("");
        final MyFileFormat[] formatArray = new MyFileFormat[10000000];
        for (int i = 0; i < formatArray.length; i++) {
            MyFileFormat format = MyFileFormat.of();
            format.setName(UUID.randomUUID().toString());
            format.setScore(rand.nextInt(100));
            formatArray[i] = format;
        }

        System.out.println("数据生成完成");

        List<String> valList = new ArrayList<>();
        for (int i = 0; i < formatArray.length; i++) {
            valList.add("学生|" + formatArray[i].getName() + "|成绩|" + formatArray[i].getScore() + "|");

            if (i % 10000 == 0) {
                FileUtils.writeLines(new File("/Users/xuyao/Downloads/test.txt"), "UTF-8", valList, true);
                valList.clear();
            }
        }
    }
}
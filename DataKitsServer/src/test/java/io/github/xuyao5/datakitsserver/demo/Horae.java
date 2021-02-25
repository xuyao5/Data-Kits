package io.github.xuyao5.datakitsserver.demo;

import io.github.xuyao5.datakitsserver.abstr.AbstractTest;
import io.github.xuyao5.datakitsserver.job.File2EsDemoJob;
import io.github.xuyao5.dkl.eskits.util.MyFileUtils;
import lombok.SneakyThrows;
import org.assertj.core.util.Files;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Strings;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.UUID;

final class Horae extends AbstractTest {

    @Resource(name = "file2EsDemoJob")
    private File2EsDemoJob file2EsDemoJob;

    @Test
    void test() {
        file2EsDemoJob.run();
    }

    @SneakyThrows
    @Test
    void genTestData() {
        String fileName = "/Users/xuyao/Downloads/DISRUPTOR_100W_T_00.txt";
        Random random = new Random();
        char split = 0x1E;
        File file = Files.newFile(fileName);

        for (int y = 0; y < 100; y++) {
            String[] fileMetadata = new String[10000];
            for (int i = 0; i < fileMetadata.length; i++) {
                fileMetadata[i] = Strings.concat(UUID.randomUUID(), split, i * y, split, "中文测试", split, random.nextInt());
            }
            MyFileUtils.writeLines(file, StandardCharsets.UTF_8.name(), Lists.list(fileMetadata), true);
        }
    }
}
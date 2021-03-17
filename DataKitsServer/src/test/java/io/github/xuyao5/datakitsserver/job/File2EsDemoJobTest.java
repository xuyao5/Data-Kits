package io.github.xuyao5.datakitsserver.job;

import io.github.xuyao5.datakitsserver.context.AbstractTest;
import io.github.xuyao5.dkl.eskits.util.MyFileUtils;
import io.github.xuyao5.dkl.eskits.util.MyRandomUtils;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.util.Files;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Strings;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.io.File;
import java.nio.charset.StandardCharsets;

final class File2EsDemoJobTest extends AbstractTest {

    @Resource(name = "file2EsDemoJob")
    private File2EsDemoJob file2EsDemoJob;

    @Test
    void test() {
        file2EsDemoJob.run();
    }

    @SneakyThrows
    @Test
    void genTestData() {
        String fileName = "/Users/xuyao/Downloads/DISRUPTOR_1000W_T_00.txt";

        char split = 0x1E;
        File file = Files.newFile(fileName);

        MyFileUtils.writeLines(file, StandardCharsets.UTF_8.name(), Lists.list(Strings.concat("UUID", split, "CASH_AMOUNT", split, "DESC", split, "DATE_TIME")), true);
        for (int y = 0; y < 1000; y++) {
            String[] content = new String[10000];
            for (int i = 0; i < content.length; i++) {
                content[i] = Strings.concat(snowflake.nextId(), split, MyRandomUtils.getLong(), split, RandomStringUtils.randomAlphabetic(46), split, System.currentTimeMillis());
            }
            MyFileUtils.writeLines(file, StandardCharsets.UTF_8.name(), Lists.list(content), true);
        }
    }
}
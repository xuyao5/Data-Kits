package io.github.xuyao5.datakitsserver.job;

import io.github.xuyao5.datakitsserver.context.AbstractTest;
import io.github.xuyao5.dkl.eskits.util.RandomUtilsNZ;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
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
        String fileName = "/Users/xuyao/Downloads/DISRUPTOR_1W_T_00.txt";

        char split = 0x1E;
        File file = Files.newFile(fileName);

        FileUtils.writeLines(file, StandardCharsets.UTF_8.name(), Lists.list(Strings.concat("UUID", split, "CASH_AMOUNT", split, "DESC", split, "DATE_TIME_1", split, "DATE_TIME_2")), true);
        for (int y = 0; y < 1; y++) {
            String[] content = new String[10000];
            for (int i = 0; i < content.length; i++) {
                content[i] = Strings.concat(snowflake.nextId(), split, RandomUtilsNZ.getFloat(), split, RandomStringUtils.randomAlphabetic(80000), split, System.currentTimeMillis(), split, "2021-03-21" + StringUtils.SPACE + "07:08:09");
            }
            FileUtils.writeLines(file, StandardCharsets.UTF_8.name(), Lists.list(content), true);
        }
    }
}
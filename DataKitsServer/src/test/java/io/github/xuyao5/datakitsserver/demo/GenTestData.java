package io.github.xuyao5.datakitsserver.demo;

import io.github.xuyao5.datakitsserver.abstr.AbstractTest;
import io.github.xuyao5.dkl.common.util.MyFileUtils;
import lombok.SneakyThrows;
import org.assertj.core.util.Files;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Random;

final class GenTestData extends AbstractTest {

    @SneakyThrows
    @Test
    void writeFile() {
        String fileName = "/Users/xuyao/Downloads/INT_PAB_CC_TEST-NAME-LIST_20201010_T_00.txt";
        Random random = new Random();
        char split = 0x1E;
        File file = Files.newFile(fileName);

        for (int y = 0; y < 1000; y++) {
            String[] fileMetadata = new String[10000];
            for (int i = 0; i < fileMetadata.length; i++) {
//                fileMetadata[i] = Joiner.on(split).skipNulls().join(UUID.randomUUID().toString(), i * y, "中文测试", random.nextInt());
            }
            MyFileUtils.writeLines(file, StandardCharsets.UTF_8.name(), Lists.list(fileMetadata), true);
        }
    }
}

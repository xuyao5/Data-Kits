package io.github.xuyao5.datakitsserver.service;

import io.github.xuyao5.datakitsserver.abstr.AbstractTest;
import io.github.xuyao5.dkl.eskits.support.IndexSupporter;
import io.github.xuyao5.dkl.eskits.util.MyFileUtils;
import lombok.SneakyThrows;
import org.assertj.core.util.Strings;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.nio.charset.StandardCharsets;

import static org.springframework.util.ResourceUtils.CLASSPATH_URL_PREFIX;

public class File2EsExecutorTest extends AbstractTest {

    @SneakyThrows
    @Test
    void recreateIndex() {
        String source = MyFileUtils.readFileToString(ResourceUtils.getFile(Strings.concat(CLASSPATH_URL_PREFIX, "elasticsearch/index/TEST_INDEX.json")), StandardCharsets.UTF_8);
        esClient.run(client -> {
            IndexSupporter indexSupporter = new IndexSupporter(client);
            if (indexSupporter.exists("file2es_disruptor_1")) {
                indexSupporter.delete("file2es_disruptor_1");
            }
            return indexSupporter.create("file2es_disruptor_1", source);
        });
    }
}

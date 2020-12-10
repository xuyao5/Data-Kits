package io.github.xuyao5.datakitsserver.demo;

import io.github.xuyao5.dal.common.util.MyFileUtils;
import io.github.xuyao5.dal.common.util.MyFilenameUtils;
import io.github.xuyao5.dal.eskits.configuration.xml.File2EsCollectorXml;
import io.github.xuyao5.dal.eskits.configuration.xml.File2EsCollectorXmlFile;
import io.github.xuyao5.dal.eskits.service.File2EsExecutor;
import io.github.xuyao5.datakitsserver.abstr.AbstractTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import javax.xml.bind.JAXB;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

final class DemoRunner extends AbstractTest {

    @SneakyThrows
    @Test
    void test() {
        File2EsCollectorXml file2EsCollectorXml = JAXB.unmarshal(ResourceUtils.getFile("classpath:File2EsCollector.xml"), File2EsCollectorXml.class);
        System.out.println("file2EsCollectorXml属性=" + file2EsCollectorXml.toString());
        File2EsExecutor.builder()
                .build()
                .execute("file1");
        Optional<File2EsCollectorXmlFile> file1 = file2EsCollectorXml.getFiles().seek("file1");
        file1.ifPresent(file2EsCollectorXmlFile -> {
            List<File> decisionFiles = MyFileUtils.getDecisionFiles(file2EsCollectorXmlFile.getPath(), file2EsCollectorXmlFile.getPattern());
            decisionFiles.stream()
                    .filter(file -> Files.exists(Paths.get(MyFilenameUtils.getConfirmFilename(file.getPath(), file2EsCollectorXmlFile.getFilenameSeparator(), file2EsCollectorXmlFile.getConfirmPrefix(), file2EsCollectorXmlFile.getConfirmSuffix()))))
                    .forEach(file -> {
                        System.out.println(file);
                    });
        });
    }
}

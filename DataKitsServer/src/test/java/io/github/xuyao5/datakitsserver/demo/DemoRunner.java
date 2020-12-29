package io.github.xuyao5.datakitsserver.demo;

import io.github.xuyao5.datakitsserver.abstr.AbstractTest;
import io.github.xuyao5.dkl.eskits.configuration.xml.File2EsTasks;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import javax.xml.bind.JAXB;

final class DemoRunner extends AbstractTest {

    @SneakyThrows
    @Test
    void test() {
        File2EsTasks file2EsTasks = JAXB.unmarshal(ResourceUtils.getFile("classpath:File2EsTasks.xml"), File2EsTasks.class);
        System.out.println("file2EsCollectorXml属性=" + file2EsTasks.toString());
//        File2EsExecutor.builder()
//                .build()
//                .execute("file1");
//        Optional<File2EsCollectorXmlFile> file1 = file2EsTasks.getTask().seek("file1");
//        file1.ifPresent(file2EsCollectorXmlFile -> {
//            List<File> decisionFiles = MyFileUtils.getDecisionFiles(file2EsCollectorXmlFile.getPath(), file2EsCollectorXmlFile.getPattern());
//            decisionFiles.stream()
//                    .filter(file -> Files.exists(Paths.get(MyFilenameUtils.getConfirmFilename(file.getPath(), file2EsCollectorXmlFile.getFilenameSeparator(), file2EsCollectorXmlFile.getConfirmPrefix(), file2EsCollectorXmlFile.getConfirmSuffix()))))
//                    .forEach(file -> {
//                        System.out.println(file);
//                    });
//        });
    }
}
package io.github.xuyao5.dal.eskits.demo;

import io.github.xuyao5.dal.common.util.MyFileUtils;
import io.github.xuyao5.dal.common.util.MyFilenameUtils;
import io.github.xuyao5.dal.eskits.File2EsExecutor;
import io.github.xuyao5.dal.eskits.abstr.AbstractTest;
import io.github.xuyao5.dal.eskits.configuration.xml.File2EsCollectorXmlFile;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

final class DemoRunner extends AbstractTest {

    @Resource(name = "file2EsExecutor")
    private File2EsExecutor file2EsExecutor;

    @Test
    void test() {
        System.out.println("EskitsServerUrl属性=" + file2EsPropertyBean.getEskitsServerUrl());
        System.out.println("file2EsCollectorXml属性=" + file2EsCollectorXml.toString());
//        file2EsExecutor.execute("file1");
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

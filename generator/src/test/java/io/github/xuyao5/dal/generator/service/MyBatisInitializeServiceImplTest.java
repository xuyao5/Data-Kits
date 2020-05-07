package io.github.xuyao5.dal.generator.service;

import io.github.xuyao5.dal.generator.AbstractTest;
import io.github.xuyao5.dal.generator.service.initial.MyBatisInitializeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

class MyBatisInitializeServiceImplTest extends AbstractTest {

    @Autowired
    private MyBatisInitializeService myBatisInitializeService;

    @Test
    void generator() {
        List<String> tableList = new ArrayList<>();
        tableList.add("BATCH_JOB_INSTANCE");
        tableList.add("BATCH_JOB_EXECUTION");

        final String filePath = myBatisInitializeService.generateFilePath(generatorConfigBean.getGeneratorDirRoot(), "PACES_MOS");
        final String templateFile = myBatisInitializeService.createTemplateFile(filePath, tableList);
        myBatisInitializeService.createSourceFile(templateFile);
        System.out.println("成功");
    }
}
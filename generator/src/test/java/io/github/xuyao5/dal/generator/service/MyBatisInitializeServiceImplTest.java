package io.github.xuyao5.dal.generator.service;

import io.github.xuyao5.AbstractTest;
import io.github.xuyao5.dal.generator.service.initial.MyBatisInitializeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

class MyBatisInitializeServiceImplTest extends AbstractTest {

    @Autowired
    private MyBatisInitializeService myBatisInitializeService;

    @Test
    void createGeneratorConfigXmlFile() {
        myBatisInitializeService.createGeneratorConfigXmlFile();
        System.out.println("成功");
    }

    @Test
    void createTemplateFile() {
        List<String> tableList = new ArrayList<>();
        tableList.add("BATCH_JOB_INSTANCE");
        tableList.add("BATCH_JOB_EXECUTION");
        myBatisInitializeService.createTemplateFile(tableList);
        System.out.println("成功");
    }
}
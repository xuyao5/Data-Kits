package io.github.xuyao5.dal.generator.service;

import io.github.xuyao5.AbstractTest;
import io.github.xuyao5.dal.generator.service.initial.MyBatisInitializeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
        myBatisInitializeService.createTemplateFile();
        System.out.println("成功");
    }
}
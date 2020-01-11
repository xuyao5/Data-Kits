package io.github.xuyao5.dal.generator.controller.generator;

import io.github.xuyao5.AbstractTest;
import io.github.xuyao5.dal.generator.request.generator.GenerateMyBatisFilesRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class GeneratorControllerTest extends AbstractTest {

    @Autowired
    private GeneratorController generatorController;

    @Test
    void generateMyBatisFiles() {
        generatorController.generateMyBatisFiles(GenerateMyBatisFilesRequest.of());
    }
}
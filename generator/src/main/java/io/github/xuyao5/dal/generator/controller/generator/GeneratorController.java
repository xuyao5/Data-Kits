package io.github.xuyao5.dal.generator.controller.generator;

import io.github.xuyao5.dal.generator.controller.AbstractController;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@Log4j2
@RestController
@RequestMapping(path = "/generator")
public class GeneratorController extends AbstractController {

    @PostMapping(path = "/generateMyBatisFiles")
    public void generateMyBatisFiles(@NotNull GenerateMyBatisFilesRequest request) {

    }

    @Data(staticConstructor = "of")
    class GenerateMyBatisFilesRequest {

    }
}

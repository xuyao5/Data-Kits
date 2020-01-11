package io.github.xuyao5.dal.generator.controller.generator;

import io.github.xuyao5.dal.generator.controller.AbstractController;
import io.github.xuyao5.dal.generator.request.generator.GenerateMyBatisFilesRequest;
import io.github.xuyao5.dal.generator.response.generator.GenerateMyBatisFilesResponse;
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
    public GenerateMyBatisFilesResponse generateMyBatisFiles(@NotNull GenerateMyBatisFilesRequest request) {
        log.info("测试");
        return GenerateMyBatisFilesResponse.of();
    }
}

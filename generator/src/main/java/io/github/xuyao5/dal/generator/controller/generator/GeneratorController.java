package io.github.xuyao5.dal.generator.controller.generator;

import io.github.xuyao5.dal.generator.controller.AbstractController;
import io.github.xuyao5.dal.generator.controller.request.generator.GenerateMyBatisFilesRequest;
import io.github.xuyao5.dal.generator.controller.response.generator.GenerateMyBatisFilesResponse;
import io.github.xuyao5.dal.generator.service.initial.MyBatisInitializeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Log4j2
@RestController
@RequestMapping(path = "/generator")
public class GeneratorController extends AbstractController {

    @Resource(name = "myBatisInitializeServiceImpl")
    private MyBatisInitializeService myBatisInitializeService;

    @PostMapping(path = "/generateMyBatisFiles")
    public GenerateMyBatisFilesResponse generateMyBatisFiles(@NotNull GenerateMyBatisFilesRequest request) {
        myBatisInitializeService.createTemplateFile(new ArrayList<>());
//        myBatisInitializeService.createGeneratorConfigXmlFile();
        return GenerateMyBatisFilesResponse.of();
    }
}

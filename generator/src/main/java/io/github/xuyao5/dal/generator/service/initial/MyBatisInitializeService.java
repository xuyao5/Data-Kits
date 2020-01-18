package io.github.xuyao5.dal.generator.service.initial;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface MyBatisInitializeService {

    void createGeneratorConfigXmlFile();

    void createTemplateFile(@NotNull List<String> tableList);
}

package io.github.xuyao5.dal.generator.service.initial;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface MyBatisInitializeService {

    String generateFilePath(@NotNull String rootPath, @NotNull String appName);

    String createTemplateFile(@NotNull String filePath, @NotNull List<String> tableList);

    void createSourceFile(@NotNull String templateFile);
}

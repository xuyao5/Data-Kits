package io.github.xuyao5.dal.generator.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@Getter
@RequiredArgsConstructor(staticName = "of")
public final class GeneratorConfigBean {

    @Value("${generator.dir.root}")
    private String generatorDirRoot;
}

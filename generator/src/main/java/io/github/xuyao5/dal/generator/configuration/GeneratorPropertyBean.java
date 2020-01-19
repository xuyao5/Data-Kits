package io.github.xuyao5.dal.generator.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Getter
@RequiredArgsConstructor(staticName = "of")
public final class GeneratorPropertyBean {

    @Value("${generator.dir.root}")
    private String generatorDirRoot;
}

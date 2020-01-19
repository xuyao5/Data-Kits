package io.github.xuyao5.dal.generator.controller;

import io.github.xuyao5.dal.generator.configuration.GeneratorPropertyBean;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractController {

    @Autowired
    protected GeneratorPropertyBean generatorPropertyBean;
}

package io.github.xuyao5.dkl.eskits.service.config;

import lombok.Data;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @author Thomas.XU(xuyao)
 * @version 25/02/21 16:36
 */
@Data(staticConstructor = "of")
public final class MySQL2EsConfig {

    private Map<String, List<String>> tables;//Key是主表，Value是从表
    private Charset charset = StandardCharsets.UTF_8;
}
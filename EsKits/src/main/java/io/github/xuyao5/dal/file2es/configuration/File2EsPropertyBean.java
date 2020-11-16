package io.github.xuyao5.dal.file2es.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Getter
@RequiredArgsConstructor(staticName = "of")
public final class File2EsPropertyBean {

    @Value("${eskits.server.url}")
    private String eskitsServerUrl;
}

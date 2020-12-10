package io.github.xuyao5.dal.eskitsserver.abstr;

import io.github.xuyao5.dal.eskitsserver.configuration.ElasticsearchKitsConfigBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 1/05/20 22:44
 * @apiNote AbstractSupporter
 * @implNote AbstractSupporter
 */
@Slf4j
public abstract class AbstractSupporter {

    @Autowired
    protected ElasticsearchKitsConfigBean configBean;
}

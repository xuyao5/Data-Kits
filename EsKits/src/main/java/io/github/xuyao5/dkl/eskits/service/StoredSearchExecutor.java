package io.github.xuyao5.dkl.eskits.service;

import io.github.xuyao5.dkl.eskits.context.AbstractExecutor;
import io.github.xuyao5.dkl.eskits.support.ClusterSupporter;
import io.github.xuyao5.dkl.eskits.support.IndexSupporter;
import io.github.xuyao5.dkl.eskits.support.mapping.XContentSupporter;
import io.github.xuyao5.dkl.eskits.util.MyFieldUtils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.Map;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 20/03/21 13:12
 * @apiNote StoredSearchExecutor
 * @implNote StoredSearchExecutor
 */
@Slf4j
public final class StoredSearchExecutor extends AbstractExecutor {

    private static final String SEARCH_STORED_INDEX = "";
    private static final String SEARCH_STORED_ALIAS = "";

    public StoredSearchExecutor(RestHighLevelClient client) {
        super(client);
    }

    //用存储在ES中的代码来搜索
    public void execute() {


    }

    public void initial() {
        IndexSupporter indexSupporter = IndexSupporter.getInstance();
        if (!indexSupporter.exists(client, SEARCH_STORED_INDEX)) {
            Map<String, Class<?>> declaredFieldsMap = MyFieldUtils.getDeclaredFieldsMap(null);
            int numberOfDataNodes = ClusterSupporter.getInstance().health(client).getNumberOfDataNodes();
            indexSupporter.create(client, SEARCH_STORED_INDEX, numberOfDataNodes, 1, XContentSupporter.buildMapping(declaredFieldsMap), new Alias(SEARCH_STORED_ALIAS));
            //设置别名
        }

        //插入Default Search

    }
}

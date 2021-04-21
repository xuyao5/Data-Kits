package io.github.xuyao5.dkl.eskits.service;

import io.github.xuyao5.dkl.eskits.configuration.ZeroDowntimeMigrationConfig;
import io.github.xuyao5.dkl.eskits.context.AbstractExecutor;
import io.github.xuyao5.dkl.eskits.support.batch.ReindexSupporter;
import io.github.xuyao5.dkl.eskits.support.general.IndexSupporter;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.reindex.BulkByScrollResponse;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 18/03/21 21:12
 * @apiNote ZeroDowntimeMigrationExecutor
 * @implNote ZeroDowntimeMigrationExecutor
 */
@Slf4j
public final class MergeIntoExecutor extends AbstractExecutor {

    public MergeIntoExecutor(RestHighLevelClient client) {
        super(client);
    }

    //用别名和reindex来0停机切换
    public void execute(@NotNull ZeroDowntimeMigrationConfig config) {
        ReindexSupporter instance = ReindexSupporter.getInstance();
        BulkByScrollResponse reindex = instance.reindex(client, config.getDestinationIndex(), config.getSourceIndices());

        List<IndicesAliasesRequest.AliasActions> aliasActionsList = new ArrayList<>();
        aliasActionsList.add(new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.ADD).index("index1").alias("alias1"));
        aliasActionsList.add(new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.ADD).indices("index1", "index2").alias("alias2"));
        aliasActionsList.add(new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.REMOVE).indices("index1").alias("alias1"));

        IndexSupporter.getInstance().updateAliases(client, aliasActionsList);
    }
}

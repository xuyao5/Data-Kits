package io.github.xuyao5.dkl.eskits.support.alias;

import io.github.xuyao5.dkl.eskits.support.general.IndexSupporter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.metadata.AliasMetadata;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 18/03/21 21:57
 * @apiNote AliasesSupporter
 * @implNote AliasesSupporter
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AliasesSupporter {

    public static final AliasesSupporter getInstance() {
        return AliasesSupporter.SingletonHolder.INSTANCE;
    }

    public void migrate(@NotNull RestHighLevelClient client, @NotNull String sourceIndex, @NotNull String targetIndex) {
        IndexSupporter indexSupporter = IndexSupporter.getInstance();
        List<AliasMetadata> indexAliases = indexSupporter.get(client, sourceIndex).getAliases().get(sourceIndex);
        List<IndicesAliasesRequest.AliasActions> collect = indexAliases.stream().collect(() -> new ArrayList<IndicesAliasesRequest.AliasActions>(), (aliasActionsList, aliasMetadata) -> {
            aliasActionsList.add(new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.ADD)
                    .index(targetIndex)
                    .alias(aliasMetadata.alias()));
            aliasActionsList.add(new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.REMOVE)
                    .index(sourceIndex)
                    .alias(aliasMetadata.alias()));
        }, ArrayList::addAll);
        if (!collect.isEmpty()) {
            indexSupporter.updateAliases(client, collect);
        }
    }

    private static class SingletonHolder {
        private static final AliasesSupporter INSTANCE = new AliasesSupporter();
    }
}

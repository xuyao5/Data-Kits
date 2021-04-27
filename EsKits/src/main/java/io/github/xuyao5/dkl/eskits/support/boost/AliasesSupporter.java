package io.github.xuyao5.dkl.eskits.support.boost;

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
import java.util.Map;
import java.util.Set;

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

    public boolean addWriteIndexToAlias(@NotNull RestHighLevelClient client, @NotNull String alias, @NotNull String targetIndex) {
        IndexSupporter indexSupporter = IndexSupporter.getInstance();
        if (!indexSupporter.exists(client, targetIndex)) {
            return false;
        }
        List<IndicesAliasesRequest.AliasActions> actionsList = indexSupporter.getAlias(client, alias).getAliases().keySet().stream().collect(ArrayList::new, (aliasActionsList, index) -> aliasActionsList.add(new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.ADD).index(index).alias(alias).writeIndex(false)), ArrayList::addAll);
        actionsList.add(new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.ADD).index(targetIndex).alias(alias).writeIndex(true));
        return indexSupporter.updateAliases(client, actionsList).isAcknowledged();
    }

    public boolean removeNonWriteIndexFromAlias(@NotNull RestHighLevelClient client, @NotNull String alias) {
        IndexSupporter indexSupporter = IndexSupporter.getInstance();
        Set<Map.Entry<String, Set<AliasMetadata>>> entries = indexSupporter.getAlias(client, alias).getAliases().entrySet();
        List<IndicesAliasesRequest.AliasActions> actionsList = entries.stream().filter(stringSetEntry -> {
            for (AliasMetadata aliasMetadata : stringSetEntry.getValue()) {
                return !aliasMetadata.writeIndex();
            }
            return false;
        }).collect(ArrayList::new, (aliasActionsList, index) -> aliasActionsList.add(new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.REMOVE).index(index.getKey()).alias(alias)), ArrayList::addAll);
        if (!actionsList.isEmpty()) {
            return indexSupporter.updateAliases(client, actionsList).isAcknowledged();
        }
        return false;
    }

    private static class SingletonHolder {
        private static final AliasesSupporter INSTANCE = new AliasesSupporter();
    }
}

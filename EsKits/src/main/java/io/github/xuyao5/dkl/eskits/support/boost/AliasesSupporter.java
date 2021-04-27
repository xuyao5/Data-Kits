package io.github.xuyao5.dkl.eskits.support.boost;

import io.github.xuyao5.dkl.eskits.support.general.IndexSupporter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.client.RestHighLevelClient;

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

    public boolean migrate(@NotNull RestHighLevelClient client, @NotNull String alias, @NotNull String targetIndex) {
        IndexSupporter indexSupporter = IndexSupporter.getInstance();
        if (!indexSupporter.exists(client, targetIndex)) {
            return false;
        }
        List<IndicesAliasesRequest.AliasActions> actionsList = indexSupporter.getAlias(client, alias).getAliases().keySet().stream().collect(ArrayList::new, (aliasActionsList, index) -> aliasActionsList.add(new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.REMOVE).index(index).alias(alias)), ArrayList::addAll);
        actionsList.add(new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.ADD).index(targetIndex).alias(alias).writeIndex(true));
        return indexSupporter.updateAliases(client, actionsList).isAcknowledged();
    }

    private static class SingletonHolder {
        private static final AliasesSupporter INSTANCE = new AliasesSupporter();
    }
}

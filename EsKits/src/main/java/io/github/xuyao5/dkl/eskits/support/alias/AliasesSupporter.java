package io.github.xuyao5.dkl.eskits.support.alias;

import io.github.xuyao5.dkl.eskits.support.general.IndexSupporter;
import io.github.xuyao5.dkl.eskits.util.MyStringUtils;
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

    public boolean migrate(@NotNull RestHighLevelClient client, @NotNull String sourceIndex, @NotNull String targetIndex) {
        if (MyStringUtils.equalsIgnoreCase(sourceIndex, targetIndex)) {
            return false;
        }
        IndexSupporter indexSupporter = IndexSupporter.getInstance();
        List<IndicesAliasesRequest.AliasActions> actionsList = indexSupporter.get(client, sourceIndex).getAliases().get(sourceIndex).stream().collect(ArrayList::new, (aliasActionsList, aliasMetadata) -> {
            aliasActionsList.add(new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.ADD).index(targetIndex).alias(aliasMetadata.alias()));
            aliasActionsList.add(new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.REMOVE).index(sourceIndex).alias(aliasMetadata.alias()));
        }, ArrayList::addAll);
        if (!actionsList.isEmpty()) {
            return indexSupporter.updateAliases(client, actionsList).isAcknowledged();
        }
        return false;
    }

    private static class SingletonHolder {
        private static final AliasesSupporter INSTANCE = new AliasesSupporter();
    }
}

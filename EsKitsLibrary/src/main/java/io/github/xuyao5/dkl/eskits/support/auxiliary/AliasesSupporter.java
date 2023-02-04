package io.github.xuyao5.dkl.eskits.support.auxiliary;

import io.github.xuyao5.dkl.eskits.support.general.IndexSupporter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Thomas.XU(xuyao)
 * @version 18/03/21 21:57
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AliasesSupporter {

    public static AliasesSupporter getInstance() {
        return AliasesSupporter.SingletonHolder.INSTANCE;
    }

    public String[] migrate(@NonNull RestHighLevelClient client, @NonNull String alias, @NonNull String targetIndex) {
        IndexSupporter indexSupporter = IndexSupporter.getInstance();
        if (indexSupporter.exists(client, targetIndex)) {
            Set<String> indexSet = indexSupporter.getAlias(client, alias).getAliases().keySet();
            List<IndicesAliasesRequest.AliasActions> actionsList = indexSet.stream().filter(sourceIndex -> !targetIndex.equals(sourceIndex)).collect(ArrayList::new, (aliasActionsList, index) -> aliasActionsList.add(new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.REMOVE).index(index).alias(alias)), ArrayList::addAll);
            actionsList.add(new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.ADD).index(targetIndex).alias(alias).writeIndex(true));
            return indexSupporter.updateAliases(client, actionsList).isAcknowledged() ? indexSet.toArray(new String[0]) : Strings.EMPTY_ARRAY;
        }
        return Strings.EMPTY_ARRAY;
    }

    public String[] increase(@NonNull RestHighLevelClient client, @NonNull String alias, @NonNull String targetIndex) {
        IndexSupporter indexSupporter = IndexSupporter.getInstance();
        if (indexSupporter.exists(client, targetIndex)) {
            List<IndicesAliasesRequest.AliasActions> actionsList = Collections.singletonList(new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.ADD).index(targetIndex).alias(alias));
            return indexSupporter.updateAliases(client, actionsList).isAcknowledged() ? indexSupporter.getAlias(client, alias).getAliases().keySet().toArray(new String[0]) : Strings.EMPTY_ARRAY;
        }
        return Strings.EMPTY_ARRAY;
    }

    private static class SingletonHolder {
        private static final AliasesSupporter INSTANCE = new AliasesSupporter();
    }
}

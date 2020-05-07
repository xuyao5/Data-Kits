package io.github.xuyao5.dal.elasticsearch.index;

import io.github.xuyao5.dal.elasticsearch.index.ilm.PutLifecyclePolicyParams;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.AcknowledgedResponse;
import org.elasticsearch.client.indexlifecycle.*;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 7/05/20 12:03
 * @apiNote EsIndexLifecycleSupporter
 * @implNote EsIndexLifecycleSupporter
 */
@RequiredArgsConstructor(staticName = "instance")
public final class EsIndexLifecycleSupporter {

    /**
     * Put Lifecycle Policy API
     */
    @SneakyThrows
    public AcknowledgedResponse putLifecyclePolicy(@NotNull RestHighLevelClient client, @NotNull PutLifecyclePolicyParams params) {
        Map<String, Phase> phases = new HashMap<>();
        Map<String, LifecycleAction> hotActions = new HashMap<>();
        hotActions.put(RolloverAction.NAME, new RolloverAction(new ByteSizeValue(50, ByteSizeUnit.GB), null, null));
        phases.put("hot", new Phase("hot", TimeValue.ZERO, hotActions));

        Map<String, LifecycleAction> deleteActions = Collections.singletonMap(DeleteAction.NAME, new DeleteAction());
        phases.put("delete", new Phase("delete", new TimeValue(90, TimeUnit.DAYS), deleteActions));

        LifecyclePolicy policy = new LifecyclePolicy("my_policy", phases);
        PutLifecyclePolicyRequest request = new PutLifecyclePolicyRequest(policy);

        return client.indexLifecycle().putLifecyclePolicy(request, RequestOptions.DEFAULT);
    }
}

package io.github.xuyao5.dkl.eskits.support.general;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.admin.indices.cache.clear.ClearIndicesCacheRequest;
import org.elasticsearch.action.admin.indices.cache.clear.ClearIndicesCacheResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.flush.FlushRequest;
import org.elasticsearch.action.admin.indices.flush.FlushResponse;
import org.elasticsearch.action.admin.indices.forcemerge.ForceMergeRequest;
import org.elasticsearch.action.admin.indices.forcemerge.ForceMergeResponse;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.action.admin.indices.refresh.RefreshRequest;
import org.elasticsearch.action.admin.indices.refresh.RefreshResponse;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsRequest;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsResponse;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsRequest;
import org.elasticsearch.action.admin.indices.template.delete.DeleteIndexTemplateRequest;
import org.elasticsearch.action.admin.indices.validate.query.ValidateQueryRequest;
import org.elasticsearch.action.admin.indices.validate.query.ValidateQueryResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.GetAliasesResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.ShardsAcknowledgedResponse;
import org.elasticsearch.client.indices.*;
import org.elasticsearch.client.indices.rollover.RolloverRequest;
import org.elasticsearch.client.indices.rollover.RolloverResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.github.xuyao5.dkl.eskits.consts.SettingConst.INDEX_NUMBER_OF_REPLICAS;
import static io.github.xuyao5.dkl.eskits.consts.SettingConst.INDEX_NUMBER_OF_SHARDS;
import static org.elasticsearch.client.RequestOptions.DEFAULT;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 1/05/20 22:48
 * @apiNote IndexSupporter
 * @implNote IndexSupporter
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IndexSupporter {

    public static IndexSupporter getInstance() {
        return IndexSupporter.SingletonHolder.INSTANCE;
    }

    /**
     * Create Index API
     */
    @SneakyThrows
    public CreateIndexResponse create(RestHighLevelClient client, String index, int shards, int replicas, XContentBuilder builder) {
        return client.indices().create(new CreateIndexRequest(index)
                .settings(Settings.builder()
                        .put(INDEX_NUMBER_OF_SHARDS.getName(), shards)
                        .put(INDEX_NUMBER_OF_REPLICAS.getName(), replicas))
                .mapping(builder), DEFAULT);
    }

    /**
     * Delete Index API
     */
    @SneakyThrows
    public AcknowledgedResponse delete(RestHighLevelClient client, String... index) {
        return client.indices().delete(new DeleteIndexRequest(index), DEFAULT);
    }

    /**
     * Index Exists API
     */
    @SneakyThrows
    public boolean exists(RestHighLevelClient client, String... indices) {
        return client.indices().exists(new GetIndexRequest(indices), DEFAULT);
    }

    /**
     * Open Index API
     */
    @SneakyThrows
    public OpenIndexResponse open(RestHighLevelClient client, String... indices) {
        return client.indices().open(new OpenIndexRequest(indices), DEFAULT);
    }

    /**
     * Close Index API
     */
    @SneakyThrows
    public AcknowledgedResponse close(RestHighLevelClient client, String... indices) {
        return client.indices().close(new CloseIndexRequest(indices), DEFAULT);
    }

    /**
     * Refresh API
     */
    @SneakyThrows
    public RefreshResponse refresh(RestHighLevelClient client, String... indices) {
        return client.indices().refresh(new RefreshRequest(indices), DEFAULT);
    }

    /**
     * Flush API
     */
    @SneakyThrows
    public FlushResponse flush(RestHighLevelClient client, String... indices) {
        return client.indices().flush(new FlushRequest(indices), DEFAULT);
    }

    /**
     * Clear Cache API
     */
    @SneakyThrows
    public ClearIndicesCacheResponse clearCache(RestHighLevelClient client, String... indices) {
        return client.indices().clearCache(new ClearIndicesCacheRequest(indices), DEFAULT);
    }

    /**
     * Force Merge API
     */
    @SneakyThrows
    public ForceMergeResponse forcemerge(RestHighLevelClient client, String... indices) {
        return client.indices().forcemerge(new ForceMergeRequest(indices), DEFAULT);
    }

    /**
     * Rollover Index API
     */
    @SneakyThrows
    public RolloverResponse rollover(RestHighLevelClient client, String alias, String newIndexName, int duration, long docsCondition, long sizeCondition) {
        return client.indices().rollover(new RolloverRequest(alias, newIndexName)
                .addMaxIndexAgeCondition(new TimeValue(duration, TimeUnit.DAYS))
                .addMaxIndexDocsCondition(docsCondition)
                .addMaxIndexSizeCondition(new ByteSizeValue(sizeCondition, ByteSizeUnit.GB)), DEFAULT);
    }

    /**
     * Put Mapping API
     */
    @SneakyThrows
    public boolean putMapping(RestHighLevelClient client, XContentBuilder builder, String... indices) {
        return client.indices().putMapping(new PutMappingRequest(indices).source(builder), DEFAULT).isAcknowledged();
    }

    /**
     * Get Mappings API
     */
    @SneakyThrows
    public GetMappingsResponse getMapping(RestHighLevelClient client, String... indices) {
        return client.indices().getMapping(new GetMappingsRequest().indices(indices), DEFAULT);
    }

    /**
     * Index Aliases API
     */
    @SneakyThrows
    public AcknowledgedResponse updateAliases(RestHighLevelClient client, List<IndicesAliasesRequest.AliasActions> aliasActionsList) {
        return client.indices().updateAliases(aliasActionsList.stream().collect(IndicesAliasesRequest::new, IndicesAliasesRequest::addAliasAction, (item1, item2) -> {
        }), DEFAULT);
    }

    /**
     * Delete Alias API
     */
    @SneakyThrows
    public org.elasticsearch.client.core.AcknowledgedResponse deleteAlias(RestHighLevelClient client, String index, String alias) {
        return client.indices().deleteAlias(new DeleteAliasRequest(index, alias), DEFAULT);
    }

    /**
     * Exists Alias API
     */
    @SneakyThrows
    public boolean existsAlias(RestHighLevelClient client, String... aliases) {
        return client.indices().existsAlias(new GetAliasesRequest(aliases), DEFAULT);
    }

    /**
     * Get Alias API
     */
    @SneakyThrows
    public GetAliasesResponse getAlias(RestHighLevelClient client, String... aliases) {
        return client.indices().getAlias(new GetAliasesRequest(aliases), DEFAULT);
    }

    /**
     * Update Indices Settings API
     */
    @SneakyThrows
    public AcknowledgedResponse putSettings(RestHighLevelClient client, Settings settings, String... indices) {
        return client.indices().putSettings(new UpdateSettingsRequest(settings, indices), DEFAULT);
    }

    /**
     * Get Settings API
     */
    @SneakyThrows
    public GetSettingsResponse getSettings(RestHighLevelClient client, String... indices) {
        return client.indices().getSettings(new GetSettingsRequest().indices(indices), DEFAULT);
    }

    /**
     * Put Template API
     */
    @SneakyThrows
    public AcknowledgedResponse putTemplate(RestHighLevelClient client, String templateName, List<String> indexPatterns) {
        return client.indices().putTemplate(new PutIndexTemplateRequest(templateName).patterns(indexPatterns), DEFAULT);
    }

    /**
     * Validate Query API
     */
    @SneakyThrows
    public ValidateQueryResponse validateQuery(RestHighLevelClient client, QueryBuilder builder, String... indices) {
        return client.indices().validateQuery(new ValidateQueryRequest(indices).query(builder), DEFAULT);
    }

    /**
     * Get Templates API
     */
    @SneakyThrows
    public GetIndexTemplatesResponse getIndexTemplate(RestHighLevelClient client, String... templateNames) {
        return client.indices().getIndexTemplate(new GetIndexTemplatesRequest(templateNames), DEFAULT);
    }

    /**
     * Templates Exist API
     */
    @SneakyThrows
    public boolean existsTemplate(RestHighLevelClient client, String... templateNames) {
        return client.indices().existsTemplate(new IndexTemplatesExistRequest(templateNames), DEFAULT);
    }

    /**
     * Get Index API
     */
    @SneakyThrows
    public GetIndexResponse get(RestHighLevelClient client, String... indices) {
        return client.indices().get(new GetIndexRequest(indices), DEFAULT);
    }

    /**
     * Freeze Index API
     */
    @SneakyThrows
    public ShardsAcknowledgedResponse freeze(RestHighLevelClient client, String... indices) {
        return client.indices().freeze(new FreezeIndexRequest(indices), DEFAULT);
    }

    /**
     * Unfreeze Index API
     */
    @SneakyThrows
    public ShardsAcknowledgedResponse unfreeze(RestHighLevelClient client, String... indices) {
        return client.indices().unfreeze(new UnfreezeIndexRequest(indices), DEFAULT);
    }

    /**
     * Delete Template API
     */
    @SneakyThrows
    public AcknowledgedResponse deleteTemplate(RestHighLevelClient client, String templateName) {
        return client.indices().deleteTemplate(new DeleteIndexTemplateRequest(templateName), DEFAULT);
    }

    /**
     * Reload Search Analyzers API
     */
    @SneakyThrows
    public ReloadAnalyzersResponse reloadAnalyzers(RestHighLevelClient client, String... indices) {
        return client.indices().reloadAnalyzers(new ReloadAnalyzersRequest(indices), DEFAULT);
    }

    private static class SingletonHolder {
        private static final IndexSupporter INSTANCE = new IndexSupporter();
    }
}
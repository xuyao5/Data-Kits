package io.github.xuyao5.dkl.eskits.support;

import io.github.xuyao5.dkl.eskits.abstr.AbstractSupporter;
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
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static org.elasticsearch.client.RequestOptions.DEFAULT;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 1/05/20 22:48
 * @apiNote EsIndexSupporter
 * @implNote EsIndexSupporter
 */
@Slf4j
public final class IndexSupporter extends AbstractSupporter {

    public IndexSupporter(@NotNull RestHighLevelClient client) {
        super(client);
    }

    /**
     * Create Index API
     */
    @SneakyThrows
    public CreateIndexResponse create(@NotNull String index, @NotNull String source) {
        return client.indices().create(new CreateIndexRequest(index.toLowerCase(Locale.ROOT)).source(source, XContentType.JSON), DEFAULT);
    }

    /**
     * Create Index API
     */
    @SneakyThrows
    public CreateIndexResponse create(@NotNull String index, int shards, @NotNull XContentBuilder builder) {
        return client.indices().create(new CreateIndexRequest(index.toLowerCase()).settings(Settings.builder()
                .put("index.number_of_shards", shards)
                .put("index.number_of_replicas", shards > 1 ? 1 : 0)
        ).mapping(builder), DEFAULT);
    }

    /**
     * Delete Index API
     */
    @SneakyThrows
    public AcknowledgedResponse delete(@NotNull String... index) {
        return client.indices().delete(new DeleteIndexRequest(index), DEFAULT);
    }

    /**
     * Index Exists API
     */
    @SneakyThrows
    public boolean exists(@NotNull String... indices) {
        return client.indices().exists(new GetIndexRequest(indices), DEFAULT);
    }

    /**
     * Open Index API
     */
    @SneakyThrows
    public OpenIndexResponse open(@NotNull String... indices) {
        return client.indices().open(new OpenIndexRequest(indices), DEFAULT);
    }

    /**
     * Close Index API
     */
    @SneakyThrows
    public AcknowledgedResponse close(@NotNull String... indices) {
        return client.indices().close(new CloseIndexRequest(indices), DEFAULT);
    }

    /**
     * Refresh API
     */
    @SneakyThrows
    public RefreshResponse refresh(@NotNull String... indices) {
        return client.indices().refresh(new RefreshRequest(indices), DEFAULT);
    }

    /**
     * Flush API
     */
    @SneakyThrows
    public FlushResponse flush(@NotNull String... indices) {
        return client.indices().flush(new FlushRequest(indices), DEFAULT);
    }

    /**
     * Clear Cache API
     */
    @SneakyThrows
    public ClearIndicesCacheResponse clearCache(@NotNull String... indices) {
        return client.indices().clearCache(new ClearIndicesCacheRequest(indices), DEFAULT);
    }

    /**
     * Force Merge API
     */
    @SneakyThrows
    public ForceMergeResponse forcemerge(@NotNull String... indices) {
        return client.indices().forcemerge(new ForceMergeRequest(indices), DEFAULT);
    }

    /**
     * Rollover Index API
     */
    @SneakyThrows
    public RolloverResponse rollover(@NotNull String alias, @NotNull String newIndexName, @NotNull int duration, @NotNull long docsCondition, @NotNull long sizeCondition) {
        return client.indices().rollover(new RolloverRequest(alias, newIndexName)
                .addMaxIndexAgeCondition(new TimeValue(duration, TimeUnit.DAYS))
                .addMaxIndexDocsCondition(docsCondition)
                .addMaxIndexSizeCondition(new ByteSizeValue(sizeCondition, ByteSizeUnit.GB)), DEFAULT);
    }

    /**
     * Index Aliases API
     */
    @SneakyThrows
    public AcknowledgedResponse updateAliases(@NotNull List<IndicesAliasesRequest.AliasActions> aliasActionsList) {
        return client.indices().updateAliases(aliasActionsList.stream().reduce(new IndicesAliasesRequest(), IndicesAliasesRequest::addAliasAction, (item1, item2) -> null), DEFAULT);
    }

    /**
     * Delete Alias API
     */
    @SneakyThrows
    public org.elasticsearch.client.core.AcknowledgedResponse deleteAlias(@NotNull String index, @NotNull String alias) {
        return client.indices().deleteAlias(new DeleteAliasRequest(index, alias), DEFAULT);
    }

    /**
     * Exists Alias API
     */
    @SneakyThrows
    public boolean existsAlias(@NotNull String... aliases) {
        return client.indices().existsAlias(new GetAliasesRequest(aliases), DEFAULT);
    }

    /**
     * Get Alias API
     */
    @SneakyThrows
    public GetAliasesResponse getAlias(@NotNull String... aliases) {
        return client.indices().getAlias(new GetAliasesRequest(aliases), DEFAULT);
    }

    /**
     * Update Indices Settings API
     */
    @SneakyThrows
    public AcknowledgedResponse putSettings(@NotNull Settings settings, @NotNull String... indices) {
        return client.indices().putSettings(new UpdateSettingsRequest(settings, indices), DEFAULT);
    }

    /**
     * Get Settings API
     */
    @SneakyThrows
    public GetSettingsResponse getSettings(@NotNull String... indices) {
        return client.indices().getSettings(new GetSettingsRequest().indices(indices), DEFAULT);
    }

    /**
     * Put Template API
     */
    @SneakyThrows
    public AcknowledgedResponse putTemplate(@NotNull String templateName, @NotNull List<String> indexPatterns) {
        return client.indices().putTemplate(new PutIndexTemplateRequest(templateName).patterns(indexPatterns), DEFAULT);
    }

    /**
     * Validate Query API
     */
    @SneakyThrows
    public ValidateQueryResponse validateQuery(@NotNull QueryBuilder builder, @NotNull String... indices) {
        return client.indices().validateQuery(new ValidateQueryRequest(indices).query(builder), DEFAULT);
    }

    /**
     * Get Templates API
     */
    @SneakyThrows
    public GetIndexTemplatesResponse getIndexTemplate(@NotNull String... templateNames) {
        return client.indices().getIndexTemplate(new GetIndexTemplatesRequest(templateNames), DEFAULT);
    }

    /**
     * Templates Exist API
     */
    @SneakyThrows
    public boolean existsTemplate(@NotNull String... templateNames) {
        return client.indices().existsTemplate(new IndexTemplatesExistRequest(templateNames), DEFAULT);
    }

    /**
     * Get Index API
     */
    @SneakyThrows
    public GetIndexResponse get(@NotNull String... indices) {
        return client.indices().get(new GetIndexRequest(indices), DEFAULT);
    }

    /**
     * Freeze Index API
     */
    @SneakyThrows
    public ShardsAcknowledgedResponse freeze(@NotNull String... indices) {
        return client.indices().freeze(new FreezeIndexRequest(indices), DEFAULT);
    }

    /**
     * Unfreeze Index API
     */
    @SneakyThrows
    public ShardsAcknowledgedResponse unfreeze(@NotNull String... indices) {
        return client.indices().unfreeze(new UnfreezeIndexRequest(indices), DEFAULT);
    }

    /**
     * Delete Template API
     */
    @SneakyThrows
    public AcknowledgedResponse deleteTemplate(@NotNull String templateName) {
        return client.indices().deleteTemplate(new DeleteIndexTemplateRequest(templateName), DEFAULT);
    }

    /**
     * Reload Search Analyzers API
     */
    @SneakyThrows
    public ReloadAnalyzersResponse reloadAnalyzers(@NotNull String... indices) {
        return client.indices().reloadAnalyzers(new ReloadAnalyzersRequest(indices), DEFAULT);
    }
}

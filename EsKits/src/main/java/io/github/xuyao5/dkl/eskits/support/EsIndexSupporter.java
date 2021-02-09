package io.github.xuyao5.dkl.eskits.support;

import io.github.xuyao5.dkl.eskits.abstr.AbstractSupporter;
import io.github.xuyao5.dkl.eskits.support.param.*;
import lombok.SneakyThrows;
import org.elasticsearch.action.admin.indices.alias.Alias;
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
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.ShardsAcknowledgedResponse;
import org.elasticsearch.client.indices.*;
import org.elasticsearch.client.indices.rollover.RolloverRequest;
import org.elasticsearch.client.indices.rollover.RolloverResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 1/05/20 22:48
 * @apiNote EsIndexSupporter
 * @implNote EsIndexSupporter
 */
public final class EsIndexSupporter extends AbstractSupporter {

    public EsIndexSupporter(@NotNull RestHighLevelClient client) {
        super(client);
    }

    /**
     * Create Index API
     */
    @SneakyThrows
    public CreateIndexResponse create(@NotNull String index, int shards, int replicas, String mapping, String alias, boolean isWriteIndex) {
        return client.indices().create(new CreateIndexRequest(index.toLowerCase())
                .settings(Settings.builder()
                        .put("index.number_of_shards", shards)
                        .put("index.number_of_replicas", replicas)
                )
                .mapping(mapping, XContentType.JSON)
                .alias(new Alias(alias.toUpperCase()).writeIndex(isWriteIndex)), RequestOptions.DEFAULT);
    }

    /**
     * Delete Index API
     */
    @SneakyThrows
    public AcknowledgedResponse delete(@NotNull DeleteIndexParams params) {
        DeleteIndexRequest request = new DeleteIndexRequest(params.getIndices());
        return client.indices().delete(request, RequestOptions.DEFAULT);
    }

    /**
     * Index Exists API
     */
    @SneakyThrows
    public boolean exists(@NotNull GetIndexParams params) {
        GetIndexRequest request = new GetIndexRequest("twitter");
        return client.indices().exists(request, RequestOptions.DEFAULT);
    }

    /**
     * Open Index API
     */
    @SneakyThrows
    public OpenIndexResponse open(@NotNull OpenIndexParams params) {
        OpenIndexRequest request = new OpenIndexRequest("index");
        return client.indices().open(request, RequestOptions.DEFAULT);
    }

    /**
     * Close Index API
     */
    @SneakyThrows
    public AcknowledgedResponse close(@NotNull CloseIndexParams params) {
        CloseIndexRequest request = new CloseIndexRequest("index");
        return client.indices().close(request, RequestOptions.DEFAULT);
    }

    /**
     * Shrink Index API
     */
    @SneakyThrows
    public ResizeResponse shrink(@NotNull ResizeParams params) {
        ResizeRequest request = new ResizeRequest("target_index", "source_index");
        return client.indices().shrink(request, RequestOptions.DEFAULT);
    }

    /**
     * Split Index API
     */
    @SneakyThrows
    public ResizeResponse split(@NotNull ResizeRequest params) {
        ResizeRequest request = new ResizeRequest("target_index", "source_index");
        return client.indices().split(request, RequestOptions.DEFAULT);
    }

    /**
     * Clone Index API
     */
    @SneakyThrows
    public ResizeResponse clone(@NotNull ResizeRequest params) {
        ResizeRequest request = new ResizeRequest("target_index", "source_index");
        return client.indices().clone(request, RequestOptions.DEFAULT);
    }

    /**
     * Refresh API
     */
    @SneakyThrows
    public RefreshResponse refresh(@NotNull RefreshParams params) {
        RefreshRequest request = new RefreshRequest("index1", "index2");
        return client.indices().refresh(request, RequestOptions.DEFAULT);
    }

    /**
     * Flush API
     */
    @SneakyThrows
    public FlushResponse flush(@NotNull FlushParams params) {
        FlushRequest request = new FlushRequest("index1", "index2");
        return client.indices().flush(request, RequestOptions.DEFAULT);
    }

    /**
     * Clear Cache API
     */
    @SneakyThrows
    public ClearIndicesCacheResponse clearCache(@NotNull ClearIndicesCacheParams params) {
        ClearIndicesCacheRequest request = new ClearIndicesCacheRequest("index1", "index2");
        return client.indices().clearCache(request, RequestOptions.DEFAULT);
    }

    /**
     * Force Merge API
     */
    @SneakyThrows
    public ForceMergeResponse forcemerge(@NotNull ForceMergeParams params) {
        ForceMergeRequest request = new ForceMergeRequest("index1", "index2");
        return client.indices().forcemerge(request, RequestOptions.DEFAULT);
    }

    /**
     * Rollover Index API
     */
    @SneakyThrows
    public RolloverResponse rollover(@NotNull RolloverParams params) {
        RolloverRequest request = new RolloverRequest("alias", "index-2");
        request.addMaxIndexAgeCondition(new TimeValue(7, TimeUnit.DAYS));
        request.addMaxIndexDocsCondition(1000);
        request.addMaxIndexSizeCondition(new ByteSizeValue(5, ByteSizeUnit.GB));
        return client.indices().rollover(request, RequestOptions.DEFAULT);
    }

    /**
     * Put Mapping API
     */
    @SneakyThrows
    public AcknowledgedResponse putMapping(@NotNull PutMappingParams params) {
        PutMappingRequest request = new PutMappingRequest("twitter");
        return client.indices().putMapping(request, RequestOptions.DEFAULT);
    }

    /**
     * Get Mappings API
     */
    @SneakyThrows
    public GetMappingsResponse getMapping(@NotNull GetMappingsParams params) {
        GetMappingsRequest request = new GetMappingsRequest();
        request.indices("twitter");
        return client.indices().getMapping(request, RequestOptions.DEFAULT);
    }

    /**
     * Get Field Mappings API
     */
    @SneakyThrows
    public GetFieldMappingsResponse getFieldMapping(@NotNull GetFieldMappingsParams params) {
        GetFieldMappingsRequest request = new GetFieldMappingsRequest();
        request.indices("twitter");
        request.fields("message", "timestamp");
        return client.indices().getFieldMapping(request, RequestOptions.DEFAULT);
    }

    /**
     * Index Aliases API
     */
    @SneakyThrows
    public AcknowledgedResponse updateAliases(@NotNull IndicesAliasesParams params) {
        IndicesAliasesRequest request = new IndicesAliasesRequest();
        IndicesAliasesRequest.AliasActions aliasAction =
                new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.ADD)
                        .index("index1")
                        .alias("alias1");
        request.addAliasAction(aliasAction);
        return client.indices().updateAliases(request, RequestOptions.DEFAULT);
    }

    /**
     * Delete Alias API
     */
    @SneakyThrows
    public org.elasticsearch.client.core.AcknowledgedResponse deleteAlias(@NotNull DeleteAliasParams params) {
        DeleteAliasRequest request = new DeleteAliasRequest("index1", "alias1");
        return client.indices().deleteAlias(request, RequestOptions.DEFAULT);
    }

    /**
     * Exists Alias API
     */
    @SneakyThrows
    public boolean existsAlias(@NotNull GetAliasesParams params) {
        GetAliasesRequest request = new GetAliasesRequest("alias1", "alias2");
        return client.indices().existsAlias(request, RequestOptions.DEFAULT);
    }

    /**
     * Get Alias API
     */
    @SneakyThrows
    public GetAliasesResponse getAlias(@NotNull GetAliasesParams params) {
        GetAliasesRequest request = new GetAliasesRequest("alias1", "alias2");
        return client.indices().getAlias(request, RequestOptions.DEFAULT);
    }

    /**
     * Update Indices Settings API
     */
    @SneakyThrows
    public AcknowledgedResponse putSettings(@NotNull UpdateSettingsParams params) {
        UpdateSettingsRequest request = new UpdateSettingsRequest("index1", "index2");
        return client.indices().putSettings(request, RequestOptions.DEFAULT);
    }

    /**
     * Get Settings API
     */
    @SneakyThrows
    public GetSettingsResponse getSettings(@NotNull GetSettingsParams params) {
        GetSettingsRequest request = new GetSettingsRequest().indices("index");
        return client.indices().getSettings(request, RequestOptions.DEFAULT);
    }

    /**
     * Put Template API
     */
    @SneakyThrows
    public AcknowledgedResponse putTemplate(@NotNull PutIndexTemplateParams params) {
        PutIndexTemplateRequest request = new PutIndexTemplateRequest("my-template");
        request.patterns(Arrays.asList("pattern-1", "log-*"));
        return client.indices().putTemplate(request, RequestOptions.DEFAULT);
    }

    /**
     * Validate Query API
     */
    @SneakyThrows
    public ValidateQueryResponse validateQuery(@NotNull ValidateQueryParams params) {
        ValidateQueryRequest request = new ValidateQueryRequest("index");
        return client.indices().validateQuery(request, RequestOptions.DEFAULT);
    }

    /**
     * Get Templates API
     */
    @SneakyThrows
    public GetIndexTemplatesResponse getIndexTemplate(@NotNull GetIndexTemplatesParams params) {
        GetIndexTemplatesRequest request = new GetIndexTemplatesRequest("my-template");
        return client.indices().getIndexTemplate(request, RequestOptions.DEFAULT);
    }

    /**
     * Templates Exist API
     */
    @SneakyThrows
    public boolean existsTemplate(@NotNull IndexTemplatesExistParams params) {
        IndexTemplatesExistRequest request = new IndexTemplatesExistRequest("template-1", "template-2");
        return client.indices().existsTemplate(request, RequestOptions.DEFAULT);
    }

    /**
     * Get Index API
     */
    @SneakyThrows
    public GetIndexResponse get(@NotNull GetIndexParams params) {
        GetIndexRequest request = new GetIndexRequest("index");
        return client.indices().get(request, RequestOptions.DEFAULT);
    }

    /**
     * Freeze Index API
     */
    @SneakyThrows
    public ShardsAcknowledgedResponse freeze(@NotNull FreezeIndexParams params) {
        FreezeIndexRequest request = new FreezeIndexRequest("index");
        return client.indices().freeze(request, RequestOptions.DEFAULT);
    }

    /**
     * Unfreeze Index API
     */
    @SneakyThrows
    public ShardsAcknowledgedResponse unfreeze(@NotNull UnfreezeIndexParams params) {
        UnfreezeIndexRequest request = new UnfreezeIndexRequest("index");
        return client.indices().unfreeze(request, RequestOptions.DEFAULT);
    }

    /**
     * Delete Template API
     */
    @SneakyThrows
    public AcknowledgedResponse deleteTemplate(@NotNull DeleteIndexTemplateParams params) {
        DeleteIndexTemplateRequest request = new DeleteIndexTemplateRequest();
        request.name("my-template");
        return client.indices().deleteTemplate(request, RequestOptions.DEFAULT);
    }

    /**
     * Reload Search Analyzers API
     */
    @SneakyThrows
    public ReloadAnalyzersResponse reloadAnalyzers(@NotNull ReloadAnalyzersParams params) {
        ReloadAnalyzersRequest request = new ReloadAnalyzersRequest("index");
        return client.indices().reloadAnalyzers(request, RequestOptions.DEFAULT);
    }
}

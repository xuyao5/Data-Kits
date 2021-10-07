package io.github.xuyao5.dkl.eskits.support.general;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
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
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.github.xuyao5.dkl.eskits.consts.SettingKeyword.*;
import static org.elasticsearch.client.RequestOptions.DEFAULT;

/**
 * @author Thomas.XU(xuyao)
 * @version 1/05/20 22:48
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IndexSupporter {

    public static IndexSupporter getInstance() {
        return IndexSupporter.SingletonHolder.INSTANCE;
    }

    /**
     * Create Index API
     *
     * @param client   客户端
     * @param index    索引
     * @param shards   分片数
     * @param replicas 副本数
     * @param builder  构建器
     * @return 返回对象
     */
    @SneakyThrows
    public CreateIndexResponse create(@NonNull RestHighLevelClient client, @NonNull String index, int shards, int replicas, @NonNull XContentBuilder builder) {
        return client.indices().create(new CreateIndexRequest(index)
                .settings(Settings.builder()
                        .put(INDEX_NUMBER_OF_SHARDS.getName(), shards)
                        .put(INDEX_NUMBER_OF_REPLICAS.getName(), replicas))
                .mapping(builder), DEFAULT);
    }

    /**
     * Create Index API
     *
     * @param client       客户端
     * @param index        索引
     * @param shards       分片数
     * @param replicas     副本数
     * @param builder      构建器
     * @param indexSorting 索引排序
     * @return 返回对象
     */
    @SneakyThrows
    public CreateIndexResponse create(@NonNull RestHighLevelClient client, @NonNull String index, int shards, int replicas, @NonNull XContentBuilder builder, @NonNull Map<String, String> indexSorting) {
        return client.indices().create(new CreateIndexRequest(index)
                .settings(Settings.builder()
                        .put(INDEX_NUMBER_OF_SHARDS.getName(), shards)
                        .put(INDEX_NUMBER_OF_REPLICAS.getName(), replicas)
                        .putList(INDEX_SORT_FIELD.getName(), new ArrayList<>(indexSorting.keySet()))
                        .putList(INDEX_SORT_ORDER.getName(), new ArrayList<>(indexSorting.values())))
                .mapping(builder), DEFAULT);
    }

    /**
     * Delete Index API
     *
     * @param client 客户端
     * @param index 索引
     * @return 返回对象
     */
    @SneakyThrows
    public AcknowledgedResponse delete(@NonNull RestHighLevelClient client, @NonNull String... index) {
        return client.indices().delete(new DeleteIndexRequest(index), DEFAULT);
    }

    /**
     * Index Exists API
     *
     * @param client 客户端
     * @param indices 索引(1或多个)
     * @return 返回对象
     */
    @SneakyThrows
    public boolean exists(@NonNull RestHighLevelClient client, @NonNull String... indices) {
        return client.indices().exists(new GetIndexRequest(indices), DEFAULT);
    }

    /**
     * Open Index API
     *
     * @param client 客户端
     * @param indices 索引(1或多个)
     * @return 返回对象
     */
    @SneakyThrows
    public OpenIndexResponse open(@NonNull RestHighLevelClient client, @NonNull String... indices) {
        return client.indices().open(new OpenIndexRequest(indices), DEFAULT);
    }

    /**
     * Close Index API
     *
     * @param client 客户端
     * @param indices 索引(1或多个)
     * @return 返回对象
     */
    @SneakyThrows
    public AcknowledgedResponse close(@NonNull RestHighLevelClient client, @NonNull String... indices) {
        return client.indices().close(new CloseIndexRequest(indices), DEFAULT);
    }

    /**
     * Refresh API
     *
     * @param client 客户端
     * @param indices 索引(1或多个)
     * @return 返回对象
     */
    @SneakyThrows
    public RefreshResponse refresh(@NonNull RestHighLevelClient client, @NonNull String... indices) {
        return client.indices().refresh(new RefreshRequest(indices), DEFAULT);
    }

    /**
     * Flush API
     *
     * @param client 客户端
     * @param indices 索引(1或多个)
     * @return 返回对象
     */
    @SneakyThrows
    public FlushResponse flush(@NonNull RestHighLevelClient client, @NonNull String... indices) {
        return client.indices().flush(new FlushRequest(indices), DEFAULT);
    }

    /**
     * Clear Cache API
     *
     * @param client 客户端
     * @param indices 索引(1或多个)
     * @return 返回对象
     */
    @SneakyThrows
    public ClearIndicesCacheResponse clearCache(@NonNull RestHighLevelClient client, @NonNull String... indices) {
        return client.indices().clearCache(new ClearIndicesCacheRequest(indices), DEFAULT);
    }

    /**
     * Force Merge API
     *
     * @param client 客户端
     * @param indices 索引(1或多个)
     * @return 返回对象
     */
    @SneakyThrows
    public ForceMergeResponse forcemerge(@NonNull RestHighLevelClient client, @NonNull String... indices) {
        return client.indices().forcemerge(new ForceMergeRequest(indices), DEFAULT);
    }

    /**
     * Put Mapping API
     *
     * @param client 客户端
     * @param builder 构建器
     * @param indices 索引(1或多个)
     * @return 返回对象
     */
    @SneakyThrows
    public boolean putMapping(@NonNull RestHighLevelClient client, @NonNull XContentBuilder builder, @NonNull String... indices) {
        return client.indices().putMapping(new PutMappingRequest(indices).source(builder), DEFAULT).isAcknowledged();
    }

    /**
     * Get Mappings API
     *
     * @param client 客户端
     * @param indices 索引(1或多个)
     * @return 返回对象
     */
    @SneakyThrows
    public GetMappingsResponse getMapping(@NonNull RestHighLevelClient client, @NonNull String... indices) {
        return client.indices().getMapping(new GetMappingsRequest().indices(indices), DEFAULT);
    }

    /**
     * Index Aliases API
     *
     * @param client 客户端
     * @param aliasActionsList 别名动作列表
     * @return 返回对象
     */
    @SneakyThrows
    public AcknowledgedResponse updateAliases(@NonNull RestHighLevelClient client, @NonNull List<IndicesAliasesRequest.AliasActions> aliasActionsList) {
        return client.indices().updateAliases(aliasActionsList.stream().collect(IndicesAliasesRequest::new, IndicesAliasesRequest::addAliasAction, (item1, item2) -> {
        }), DEFAULT);
    }

    /**
     * Delete Alias API
     *
     * @param client 客户端
     * @param index 索引
     * @param alias 别名
     * @return 返回对象
     */
    @SneakyThrows
    public org.elasticsearch.client.core.AcknowledgedResponse deleteAlias(@NonNull RestHighLevelClient client, @NonNull String index, @NonNull String alias) {
        return client.indices().deleteAlias(new DeleteAliasRequest(index, alias), DEFAULT);
    }

    /**
     * Exists Alias API
     *
     * @param client 客户端
     * @param aliases (1或多个)
     * @return 返回对象
     */
    @SneakyThrows
    public boolean existsAlias(@NonNull RestHighLevelClient client, @NonNull String... aliases) {
        return client.indices().existsAlias(new GetAliasesRequest(aliases), DEFAULT);
    }

    /**
     * Get Alias API
     *
     * @param client 客户端
     * @param aliases (1或多个)
     * @return 返回对象
     */
    @SneakyThrows
    public GetAliasesResponse getAlias(@NonNull RestHighLevelClient client, @NonNull String... aliases) {
        return client.indices().getAlias(new GetAliasesRequest(aliases), DEFAULT);
    }

    /**
     * Update Indices Settings API
     *
     * @param client 客户端
     * @param settings 设置集
     * @param indices 索引(1或多个)
     * @return 返回对象
     */
    @SneakyThrows
    public AcknowledgedResponse putSettings(@NonNull RestHighLevelClient client, @NonNull Settings settings, @NonNull String... indices) {
        return client.indices().putSettings(new UpdateSettingsRequest(settings, indices), DEFAULT);
    }

    /**
     * Get Settings API
     *
     * @param client 客户端
     * @param indices 索引(1或多个)
     * @return 返回对象
     */
    @SneakyThrows
    public GetSettingsResponse getSettings(@NonNull RestHighLevelClient client, @NonNull String... indices) {
        return client.indices().getSettings(new GetSettingsRequest().indices(indices), DEFAULT);
    }

    /**
     * Put Template API
     *
     * @param client 客户端
     * @param templateName 模版名
     * @param indexPatterns 索引模式
     * @return 返回对象
     */
    @SneakyThrows
    public AcknowledgedResponse putTemplate(@NonNull RestHighLevelClient client, @NonNull String templateName, @NonNull List<String> indexPatterns) {
        return client.indices().putTemplate(new PutIndexTemplateRequest(templateName).patterns(indexPatterns), DEFAULT);
    }

    /**
     * Validate Query API
     *
     * @param client 客户端
     * @param builder 构建器
     * @param indices 索引(1或多个)
     * @return 返回对象
     */
    @SneakyThrows
    public ValidateQueryResponse validateQuery(@NonNull RestHighLevelClient client, @NonNull QueryBuilder builder, @NonNull String... indices) {
        return client.indices().validateQuery(new ValidateQueryRequest(indices).query(builder), DEFAULT);
    }

    /**
     * Get Templates API
     *
     * @param client 客户端
     * @param templateNames 模版名(1或多个)
     * @return 返回对象
     */
    @SneakyThrows
    public GetIndexTemplatesResponse getIndexTemplate(@NonNull RestHighLevelClient client, @NonNull String... templateNames) {
        return client.indices().getIndexTemplate(new GetIndexTemplatesRequest(templateNames), DEFAULT);
    }

    /**
     * Templates Exist API
     *
     * @param client 客户端
     * @param templateNames 模版名(1或多个)
     * @return 返回对象
     */
    @SneakyThrows
    public boolean existsTemplate(@NonNull RestHighLevelClient client, @NonNull String... templateNames) {
        return client.indices().existsTemplate(new IndexTemplatesExistRequest(templateNames), DEFAULT);
    }

    /**
     * Get Index API
     *
     * @param client 客户端
     * @param indices 索引(1或多个)
     * @return 返回对象
     */
    @SneakyThrows
    public GetIndexResponse get(@NonNull RestHighLevelClient client, @NonNull String... indices) {
        return client.indices().get(new GetIndexRequest(indices), DEFAULT);
    }

    /**
     * Freeze Index API
     *
     * @param client 客户端
     * @param indices 索引(1或多个)
     * @return 返回对象
     */
    @SneakyThrows
    public ShardsAcknowledgedResponse freeze(@NonNull RestHighLevelClient client, @NonNull String... indices) {
        return client.indices().freeze(new FreezeIndexRequest(indices), DEFAULT);
    }

    /**
     * Unfreeze Index API
     *
     * @param client 客户端
     * @param indices 索引(1或多个)
     * @return 返回对象
     */
    @SneakyThrows
    public ShardsAcknowledgedResponse unfreeze(@NonNull RestHighLevelClient client, @NonNull String... indices) {
        return client.indices().unfreeze(new UnfreezeIndexRequest(indices), DEFAULT);
    }

    /**
     * Delete Template API
     *
     * @param client 客户端
     * @param templateName 模版名
     * @return 返回对象
     */
    @SneakyThrows
    public AcknowledgedResponse deleteTemplate(@NonNull RestHighLevelClient client, @NonNull String templateName) {
        return client.indices().deleteTemplate(new DeleteIndexTemplateRequest(templateName), DEFAULT);
    }

    /**
     * Reload Search Analyzers API
     *
     * @param client 客户端
     * @param indices 索引(1或多个)
     * @return 返回对象
     */
    @SneakyThrows
    public ReloadAnalyzersResponse reloadAnalyzers(@NonNull RestHighLevelClient client, @NonNull String... indices) {
        return client.indices().reloadAnalyzers(new ReloadAnalyzersRequest(indices), DEFAULT);
    }

    private static class SingletonHolder {
        private static final IndexSupporter INSTANCE = new IndexSupporter();
    }
}
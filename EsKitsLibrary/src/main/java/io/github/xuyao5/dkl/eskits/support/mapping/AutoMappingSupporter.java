package io.github.xuyao5.dkl.eskits.support.mapping;

import io.github.xuyao5.dkl.eskits.context.annotation.AutoMappingField;
import io.github.xuyao5.dkl.eskits.support.general.ClusterSupporter;
import io.github.xuyao5.dkl.eskits.support.general.IndexSupporter;
import io.github.xuyao5.dkl.eskits.util.GsonUtilsPlus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.xcontent.XContentBuilder;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Thomas.XU(xuyao)
 * @version 3/02/23 09:45
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AutoMappingSupporter {

    public static AutoMappingSupporter getInstance() {
        return AutoMappingSupporter.SingletonHolder.INSTANCE;
    }

    public boolean isSameMapping(@NonNull RestHighLevelClient client, String index, @NonNull Class<?> clz) {
        try (XContentBuilder contentBuilder = XContentSupporter.getInstance().buildMapping(clz)) {
            return GsonUtilsPlus.json2Obj(Strings.toString(contentBuilder), clz).equals(GsonUtilsPlus.json2Obj(IndexSupporter.getInstance().getMapping(client, index).mappings().get(index).source().toString(), clz));
        }
    }

    public void autoMappingField(@NonNull RestHighLevelClient client, String index, int shards, int replicas, @NonNull Class<?> clz) {
        try (XContentBuilder contentBuilder = XContentSupporter.getInstance().buildMapping(clz)) {
            log.info("索引Mapping:[{}],[{}]", index, Strings.toString(contentBuilder));

            IndexSupporter indexSupporter = IndexSupporter.getInstance();

            if (!indexSupporter.exists(client, index)) {
                Map<String, String> indexSorting = FieldUtils.getFieldsListWithAnnotation(clz, AutoMappingField.class)
                        //获取被@AutoMappingField注解的成员
                        .stream()
                        //过滤
                        .filter(field -> field.getDeclaredAnnotation(AutoMappingField.class).sortPriority() >= 0)
                        //排序
                        .sorted(Comparator.comparing(field -> field.getDeclaredAnnotation(AutoMappingField.class).sortPriority()))
                        //收集
                        .collect(Collectors.toMap(Field::getName, field -> field.getDeclaredAnnotation(AutoMappingField.class).order().getOrder(), (o1, o2) -> null, LinkedHashMap::new));

                int numberOfDataNodes = shards > 0 ? shards : ClusterSupporter.getInstance().health(client).getNumberOfDataNodes();//自动计算主分片
                log.info("索引Shards:[{}],[{}]", index, numberOfDataNodes);

                if (!indexSorting.isEmpty()) {
                    indexSupporter.create(client, index, numberOfDataNodes, replicas, contentBuilder, indexSorting);
                    log.info("索引Sorting:[{}],[{}]", index, indexSorting);
                } else {
                    indexSupporter.create(client, index, numberOfDataNodes, replicas, contentBuilder);
                }
            } else {
                indexSupporter.putMapping(client, contentBuilder, index);
            }
        }
    }

    private static class SingletonHolder {
        private static final AutoMappingSupporter INSTANCE = new AutoMappingSupporter();
    }
}
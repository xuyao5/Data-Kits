package io.github.xuyao5.dkl.eskits.support.mapping;

import io.github.xuyao5.dkl.eskits.context.annotation.AutoMappingField;
import io.github.xuyao5.dkl.eskits.schema.base.BaseDocument;
import io.github.xuyao5.dkl.eskits.support.general.ClusterSupporter;
import io.github.xuyao5.dkl.eskits.support.general.IndexSupporter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentBuilder;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
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

    public void mapping(RestHighLevelClient client, String index, int priShards, Class<? extends BaseDocument> clz) {
        List<Field> fieldsList = FieldUtils.getFieldsListWithAnnotation(clz, AutoMappingField.class);//获取被@AutoMappingField注解的成员
        XContentBuilder contentBuilder = XContentSupporter.getInstance().buildMapping(clz);//根据Document Class生成ES的Mapping
        //检查索引是否存在
        IndexSupporter indexSupporter = IndexSupporter.getInstance();
        boolean isIndexExist = indexSupporter.exists(client, index);

        if (!isIndexExist) {
            Map<String, String> indexSorting = fieldsList.stream()
                    //过滤
                    .filter(field -> field.getDeclaredAnnotation(AutoMappingField.class).sortPriority() >= 0)
                    //排序
                    .sorted(Comparator.comparing(field -> field.getDeclaredAnnotation(AutoMappingField.class).sortPriority()))
                    //收集
                    .collect(Collectors.toMap(Field::getName, field -> field.getDeclaredAnnotation(AutoMappingField.class).order().getOrder(), (o1, o2) -> null, LinkedHashMap::new));
            int numberOfDataNodes = priShards > 0 ? priShards : ClusterSupporter.getInstance().health(client).getNumberOfDataNodes();//自动计算主分片
            log.info("ES服务器数据节点数为:[{}]", numberOfDataNodes);
            if (!indexSorting.isEmpty()) {
                indexSupporter.create(client, index, numberOfDataNodes, 0, contentBuilder, indexSorting);
            } else {
                indexSupporter.create(client, index, numberOfDataNodes, 0, contentBuilder);
            }
        } else {
            indexSupporter.putMapping(client, contentBuilder, index);
            indexSupporter.open(client, index);
        }

    }

    private static class SingletonHolder {
        private static final AutoMappingSupporter INSTANCE = new AutoMappingSupporter();
    }
}
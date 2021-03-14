package io.github.xuyao5.dkl.eskits.support.mapping;

import io.github.xuyao5.dkl.eskits.abstr.AbstractSupporter;
import io.github.xuyao5.dkl.eskits.schema.range.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 13/03/21 17:33
 * @apiNote XContentSupporter
 * @implNote XContentSupporter
 */
@Slf4j
public final class XContentSupporter extends AbstractSupporter {

    public XContentSupporter(@NotNull RestHighLevelClient client) {
        super(client);
    }

    @SneakyThrows
    public static XContentBuilder buildMapping(@NotNull Map<String, Class<?>> declaredFieldsMap) {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.field("dynamic", false);

            builder.startObject("properties");
            {
                builder.startObject("recordId");
                {
                    builder.field("type", "keyword");
                    builder.field("ignore_above", 100);
                }
                builder.endObject();

                builder.startObject("dateTag");
                {
                    builder.field("type", "keyword");
                    builder.field("ignore_above", 8);
                }
                builder.endObject();

                builder.startObject("serialNo");
                {
                    builder.field("type", "long");
                }
                builder.endObject();

                builder.startObject("allFieldMd5");
                {
                    builder.field("type", "keyword");
                    builder.field("ignore_above", 32);
                }
                builder.endObject();

                builder.startObject("randomNum");
                {
                    builder.field("type", "long");
                }
                builder.endObject();

                builder.startObject("createDate");
                {
                    builder.field("type", "date");
                }
                builder.endObject();

                builder.startObject("modifyDate");
                {
                    builder.field("type", "date");
                }
                builder.endObject();

                for (Map.Entry<String, Class<?>> entry : declaredFieldsMap.entrySet()) {
                    builder.startObject(entry.getKey());
                    {
                        Class<?> clz = entry.getValue();
                        if (String.class.equals(clz)) {
                            builder.field("type", "keyword");
                            builder.field("ignore_above", 256);
                        } else if (char.class.equals(clz) || Character.class.equals(clz)) {
                            builder.field("type", "keyword");
                            builder.field("ignore_above", 1);
                        } else if (long.class.equals(clz) || Long.class.equals(clz)) {
                            builder.field("type", "long");
                        } else if (int.class.equals(clz) || Integer.class.equals(clz)) {
                            builder.field("type", "integer");
                        } else if (short.class.equals(clz) || Short.class.equals(clz)) {
                            builder.field("type", "short");
                        } else if (byte.class.equals(clz) || Byte.class.equals(clz)) {
                            builder.field("type", "byte");
                        } else if (double.class.equals(clz) || Double.class.equals(clz)) {
                            builder.field("type", "double");
                        } else if (float.class.equals(clz) || Float.class.equals(clz)) {
                            builder.field("type", "float");
                        } else if (boolean.class.equals(clz) || Boolean.class.equals(clz)) {
                            builder.field("type", "boolean");
                        } else if (Date.class.equals(clz)) {
                            builder.field("type", "date");
                        } else if (BigDecimal.class.equals(clz)) {
                            builder.field("type", "scaled_float");
                            builder.field("scaling_factor", 100);
                        } else if (BigInteger.class.equals(clz)) {
                            builder.field("type", "scaled_float");
                            builder.field("scaling_factor", 100);
                        } else if (LongAdder.class.equals(clz)) {
                            builder.field("type", "scaled_float");
                            builder.field("scaling_factor", 100);
                        } else if (InetAddress.class.equals(clz)) {
                            builder.field("type", "ip");
                        } else if (IntegerRange.class.equals(clz)) {
                            builder.field("type", "integer_range");
                        } else if (LongRange.class.equals(clz)) {
                            builder.field("type", "long_range");
                        } else if (FloatRange.class.equals(clz)) {
                            builder.field("type", "float_range");
                        } else if (DoubleRange.class.equals(clz)) {
                            builder.field("type", "double_range");
                        } else if (DateRange.class.equals(clz)) {
                            builder.field("type", "date_range");
                        } else if (IpRange.class.equals(clz)) {
                            builder.field("type", "ip_range");
                        } else {
                            builder.field("type", "text");
                            builder.startObject("fields");
                            builder.startObject("keyword");
                            builder.field("type", "keyword");
                            builder.field("ignore_above", 256);
                            builder.endObject();
                            builder.endObject();
                        }
                    }
                    builder.endObject();
                }
            }
            builder.endObject();
        }
        builder.endObject();
        return builder;
    }
}
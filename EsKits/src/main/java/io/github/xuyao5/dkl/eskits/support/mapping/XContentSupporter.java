package io.github.xuyao5.dkl.eskits.support.mapping;

import com.google.gson.reflect.TypeToken;
import io.github.xuyao5.dkl.eskits.schema.range.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 13/03/21 17:33
 * @apiNote XContentSupporter
 * @implNote XContentSupporter
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class XContentSupporter {

    public static final XContentSupporter getInstance() {
        return XContentSupporter.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final XContentSupporter INSTANCE = new XContentSupporter();
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
                        if (String.class.equals(clz) || String[].class.equals(clz) || new TypeToken<List<String>>() {
                        }.getClass().equals(clz)) {
                            builder.field("type", "keyword");
                            builder.field("ignore_above", 256);
                        } else if (char.class.equals(clz) || Character.class.equals(clz) || char[].class.equals(clz) || new TypeToken<List<Character>>() {
                        }.getClass().equals(clz)) {
                            builder.field("type", "keyword");
                            builder.field("ignore_above", 1);
                        } else if (long.class.equals(clz) || Long.class.equals(clz) || long[].class.equals(clz) || new TypeToken<List<Long>>() {
                        }.getClass().equals(clz)) {
                            builder.field("type", "long");
                        } else if (int.class.equals(clz) || Integer.class.equals(clz) || int[].class.equals(clz) || new TypeToken<List<Integer>>() {
                        }.getClass().equals(clz)) {
                            builder.field("type", "integer");
                        } else if (short.class.equals(clz) || Short.class.equals(clz) || short[].class.equals(clz) || new TypeToken<List<Short>>() {
                        }.getClass().equals(clz)) {
                            builder.field("type", "short");
                        } else if (byte.class.equals(clz) || Byte.class.equals(clz) || byte[].class.equals(clz) || new TypeToken<List<Byte>>() {
                        }.getClass().equals(clz)) {
                            builder.field("type", "byte");
                        } else if (double.class.equals(clz) || Double.class.equals(clz) || double[].class.equals(clz) || new TypeToken<List<Double>>() {
                        }.getClass().equals(clz)) {
                            builder.field("type", "double");
                        } else if (float.class.equals(clz) || Float.class.equals(clz) || float[].class.equals(clz) || new TypeToken<List<Float>>() {
                        }.getClass().equals(clz)) {
                            builder.field("type", "float");
                        } else if (boolean.class.equals(clz) || Boolean.class.equals(clz) || boolean[].class.equals(clz) || new TypeToken<List<Boolean>>() {
                        }.getClass().equals(clz)) {
                            builder.field("type", "boolean");
                        } else if (Date.class.equals(clz) || Date[].class.equals(clz) || new TypeToken<List<Date>>() {
                        }.getClass().equals(clz)) {
                            builder.field("type", "date");
                        } else if (BigDecimal.class.equals(clz) || BigDecimal[].class.equals(clz) || new TypeToken<List<BigDecimal>>() {
                        }.getClass().equals(clz)) {
                            builder.field("type", "scaled_float");
                            builder.field("scaling_factor", 100);
                        } else if (BigInteger.class.equals(clz) || BigInteger[].class.equals(clz) || new TypeToken<List<BigInteger>>() {
                        }.getClass().equals(clz)) {
                            builder.field("type", "scaled_float");
                            builder.field("scaling_factor", 100);
                        } else if (LongAdder.class.equals(clz) || LongAdder[].class.equals(clz) || new TypeToken<List<LongAdder>>() {
                        }.getClass().equals(clz)) {
                            builder.field("type", "scaled_float");
                            builder.field("scaling_factor", 100);
                        } else if (InetAddress.class.equals(clz) || InetAddress[].class.equals(clz) || new TypeToken<List<InetAddress>>() {
                        }.getClass().equals(clz)) {
                            builder.field("type", "ip");
                        } else if (IntegerRange.class.equals(clz) || IntegerRange[].class.equals(clz) || new TypeToken<List<IntegerRange>>() {
                        }.getClass().equals(clz)) {
                            builder.field("type", "integer_range");
                        } else if (LongRange.class.equals(clz) || LongRange[].class.equals(clz) || new TypeToken<List<LongRange>>() {
                        }.getClass().equals(clz)) {
                            builder.field("type", "long_range");
                        } else if (FloatRange.class.equals(clz) || FloatRange[].class.equals(clz) || new TypeToken<List<FloatRange>>() {
                        }.getClass().equals(clz)) {
                            builder.field("type", "float_range");
                        } else if (DoubleRange.class.equals(clz) || DoubleRange[].class.equals(clz) || new TypeToken<List<DoubleRange>>() {
                        }.getClass().equals(clz)) {
                            builder.field("type", "double_range");
                        } else if (DateRange.class.equals(clz) || DateRange[].class.equals(clz) || new TypeToken<List<DateRange>>() {
                        }.getClass().equals(clz)) {
                            builder.field("type", "date_range");
                        } else if (IpRange.class.equals(clz) || IpRange[].class.equals(clz) || new TypeToken<List<IpRange>>() {
                        }.getClass().equals(clz)) {
                            builder.field("type", "ip_range");
                        } else {
                            builder.field("type", "text");//nested??
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

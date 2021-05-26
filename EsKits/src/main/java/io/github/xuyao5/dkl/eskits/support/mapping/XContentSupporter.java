package io.github.xuyao5.dkl.eskits.support.mapping;

import com.google.gson.reflect.TypeToken;
import io.github.xuyao5.dkl.eskits.schema.range.*;
import io.github.xuyao5.dkl.eskits.util.MyFieldUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static io.github.xuyao5.dkl.eskits.consts.SettingConst.MAPPING_DATE_FORMAT;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 13/03/21 17:33
 * @apiNote XContentSupporter
 * @implNote XContentSupporter
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class XContentSupporter {

    public static XContentSupporter getInstance() {
        return XContentSupporter.SingletonHolder.INSTANCE;
    }

    @SneakyThrows
    public XContentBuilder buildMapping(@NotNull Serializable obj) {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.field("dynamic", false);

            builder.startObject("properties");
            {
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

                builder.startObject("recordMd5");
                {
                    builder.field("type", "keyword");
                    builder.field("ignore_above", 32);
                }
                builder.endObject();

                builder.startObject("createDate");
                {
                    builder.field("type", "date");
                    builder.field("format", MAPPING_DATE_FORMAT.getName());
                }
                builder.endObject();

                builder.startObject("modifyDate");
                {
                    builder.field("type", "date");
                    builder.field("format", MAPPING_DATE_FORMAT.getName());
                }
                builder.endObject();

                //定义继承自BaseDocument的自定义文档类型
                customized(builder, MyFieldUtils.getDeclaredFieldsMap(obj));
            }
            builder.endObject();
        }
        builder.endObject();
        return builder;
    }

    @SneakyThrows
    private void customized(@NotNull XContentBuilder builder, @NotNull Map<String, Type> declaredFieldsMap) {
        for (Map.Entry<String, Type> entry : declaredFieldsMap.entrySet()) {
            builder.startObject(entry.getKey());
            {
                Type type = entry.getValue();
                if (String.class.equals(type) || String[].class.equals(type) || MyFieldUtils.isSameType(new TypeToken<List<String>>() {
                }, type)) {
                    builder.field("type", "keyword");
                    builder.field("ignore_above", 512);
                } else if (char.class.equals(type) || Character.class.equals(type) || char[].class.equals(type) || Character[].class.equals(type) || MyFieldUtils.isSameType(new TypeToken<List<Character>>() {
                }, type)) {
                    builder.field("type", "keyword");
                    builder.field("ignore_above", 1);
                } else if (long.class.equals(type) || Long.class.equals(type) || long[].class.equals(type) || Long[].class.equals(type) || MyFieldUtils.isSameType(new TypeToken<List<Long>>() {
                }, type)) {
                    builder.field("type", "long");
                } else if (int.class.equals(type) || Integer.class.equals(type) || int[].class.equals(type) || Integer[].class.equals(type) || MyFieldUtils.isSameType(new TypeToken<List<Integer>>() {
                }, type)) {
                    builder.field("type", "integer");
                } else if (short.class.equals(type) || Short.class.equals(type) || short[].class.equals(type) || Short[].class.equals(type) || MyFieldUtils.isSameType(new TypeToken<List<Short>>() {
                }, type)) {
                    builder.field("type", "short");
                } else if (byte.class.equals(type) || Byte.class.equals(type) || byte[].class.equals(type) || Byte[].class.equals(type) || MyFieldUtils.isSameType(new TypeToken<List<Byte>>() {
                }, type)) {
                    builder.field("type", "byte");
                } else if (double.class.equals(type) || Double.class.equals(type) || double[].class.equals(type) || Double[].class.equals(type) || MyFieldUtils.isSameType(new TypeToken<List<Double>>() {
                }, type)) {
                    builder.field("type", "double");
                } else if (float.class.equals(type) || Float.class.equals(type) || float[].class.equals(type) || Float[].class.equals(type) || MyFieldUtils.isSameType(new TypeToken<List<Float>>() {
                }, type)) {
                    builder.field("type", "float");
                } else if (boolean.class.equals(type) || Boolean.class.equals(type) || boolean[].class.equals(type) || Boolean[].class.equals(type) || MyFieldUtils.isSameType(new TypeToken<List<Boolean>>() {
                }, type)) {
                    builder.field("type", "boolean");
                } else if (Date.class.equals(type) || Date[].class.equals(type) || MyFieldUtils.isSameType(new TypeToken<List<Date>>() {
                }, type)) {
                    builder.field("type", "date");
                    builder.field("format", MAPPING_DATE_FORMAT.getName());
                } else if (BigDecimal.class.equals(type) || BigDecimal[].class.equals(type) || MyFieldUtils.isSameType(new TypeToken<List<BigDecimal>>() {
                }, type)) {
                    builder.field("type", "scaled_float");
                    builder.field("scaling_factor", 100);
                } else if (BigInteger.class.equals(type) || BigInteger[].class.equals(type) || MyFieldUtils.isSameType(new TypeToken<List<BigInteger>>() {
                }, type)) {
                    builder.field("type", "unsigned_long");
                } else if (InetAddress.class.equals(type) || InetAddress[].class.equals(type) || MyFieldUtils.isSameType(new TypeToken<List<InetAddress>>() {
                }, type)) {
                    builder.field("type", "ip");
                } else if (IntegerRange.class.equals(type) || IntegerRange[].class.equals(type) || MyFieldUtils.isSameType(new TypeToken<List<IntegerRange>>() {
                }, type)) {
                    builder.field("type", "integer_range");
                } else if (LongRange.class.equals(type) || LongRange[].class.equals(type) || MyFieldUtils.isSameType(new TypeToken<List<LongRange>>() {
                }, type)) {
                    builder.field("type", "long_range");
                } else if (FloatRange.class.equals(type) || FloatRange[].class.equals(type) || MyFieldUtils.isSameType(new TypeToken<List<FloatRange>>() {
                }, type)) {
                    builder.field("type", "float_range");
                } else if (DoubleRange.class.equals(type) || DoubleRange[].class.equals(type) || MyFieldUtils.isSameType(new TypeToken<List<DoubleRange>>() {
                }, type)) {
                    builder.field("type", "double_range");
                } else if (DateRange.class.equals(type) || DateRange[].class.equals(type) || MyFieldUtils.isSameType(new TypeToken<List<DateRange>>() {
                }, type)) {
                    builder.field("type", "date_range");
                } else if (IpRange.class.equals(type) || IpRange[].class.equals(type) || MyFieldUtils.isSameType(new TypeToken<List<IpRange>>() {
                }, type)) {
                    builder.field("type", "ip_range");
                } else if (StringBuilder.class.equals(type) || StringBuilder[].class.equals(type) || MyFieldUtils.isSameType(new TypeToken<List<StringBuilder>>() {
                }, type) || StringBuffer.class.equals(type) || StringBuffer[].class.equals(type) || MyFieldUtils.isSameType(new TypeToken<List<StringBuffer>>() {
                }, type)) {
                    builder.field("type", "text");
                    builder.startObject("fields");
                    {
                        builder.startObject("keyword");
                        {
                            builder.field("type", "keyword");
                            builder.field("ignore_above", 1024);
                        }
                        builder.endObject();
                    }
                    builder.endObject();
                } else {
                    builder.field("type", "nested");
                    builder.startObject("properties");
                    {
                        //递归调用
                        customized(builder, MyFieldUtils.getDeclaredFieldsMap(type));
                    }
                    builder.endObject();
                }
            }
            builder.endObject();
        }
    }

    private static class SingletonHolder {
        private static final XContentSupporter INSTANCE = new XContentSupporter();
    }
}

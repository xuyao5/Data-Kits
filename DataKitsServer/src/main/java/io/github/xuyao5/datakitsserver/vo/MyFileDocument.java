package io.github.xuyao5.datakitsserver.vo;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import io.github.xuyao5.dkl.eskits.adapter.GeoPointTypeAdapter;
import io.github.xuyao5.dkl.eskits.context.annotation.FileField;
import io.github.xuyao5.dkl.eskits.schema.base.BaseDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.elasticsearch.common.geo.GeoPoint;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import static io.github.xuyao5.dkl.eskits.consts.SortTypeConst.ASC;
import static io.github.xuyao5.dkl.eskits.consts.SortTypeConst.DESC;

@Data(staticConstructor = "of")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonAdapter(GeoPointTypeAdapter.class)
public final class MyFileDocument extends BaseDocument {

    //UUID          CASH_AMOUNT          DESC          DATE_TIME_1          DATE_TIME_2
    @SerializedName(value = "uuid", alternate = {"Uuid"})
    @FileField(column = "UUID")
    private String uuid;

    @SerializedName(value = "cashAmount", alternate = {"CashAmount"})
    @FileField(column = "CASH_AMOUNT")
    private BigDecimal cashAmount;

    @SerializedName(value = "desc", alternate = {"Desc"})
    @FileField(column = "DESC", priority = 1, order = DESC)
    private String desc;

    @SerializedName(value = "dateTime1", alternate = {"DateTime1"})
    @FileField(column = "DATE_TIME_1", priority = 2, order = ASC)
    private long dateTime1;

    @SerializedName(value = "dateTime2", alternate = {"DateTime2"})
    @FileField(column = "DATE_TIME_2")
    private Date dateTime2;

    @SerializedName(value = "discount1", alternate = {"Discount1"})
    @FileField(priority = 3, order = DESC)
    private int discount1;

    @SerializedName(value = "discount2", alternate = {"Discount2"})
    private int discount2;

    @SerializedName(value = "position", alternate = {"Position"})
    @FileField(column = "GEO")
    private GeoPoint position;

//    private NestedTags tags;//会导致Nested，则无法使用Index Sorting

    @Data(staticConstructor = "of")
    public static class NestedTags implements Serializable {

        private final String name;
        private final boolean isSuccess;
    }
}

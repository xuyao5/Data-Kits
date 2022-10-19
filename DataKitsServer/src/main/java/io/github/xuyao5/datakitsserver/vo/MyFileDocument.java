package io.github.xuyao5.datakitsserver.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.SerializedName;
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
public final class MyFileDocument extends BaseDocument {

    //UUID          CASH_AMOUNT          DESC          DATE_TIME_1          DATE_TIME_2
    @SerializedName(value = "uuid", alternate = {"Uuid"})
    @FileField(position = 0)//column = "UUID",
    private String uuid;

    @SerializedName(value = "cashAmount", alternate = {"CashAmount"})
    @FileField(position = 1)//column = "CASH_AMOUNT",
    private BigDecimal cashAmount;

    @SerializedName(value = "desc1", alternate = {"Desc1"})
    @FileField(position = 2, sortPriority = -1, order = DESC)//column = "DESC1"
    private String desc1;

    @SerializedName(value = "desc2", alternate = {"Desc2"})
    @FileField(position = 3, sortPriority = -1, order = DESC)//column = "DESC2"
    private StringBuilder desc2;

    @SerializedName(value = "dateTime1", alternate = {"DateTime1"})
    @FileField(position = 4, sortPriority = 2, order = ASC)//column = "DATE_TIME_1"
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private long dateTime1;

    @SerializedName(value = "dateTime2", alternate = {"DateTime2"})
    @FileField(position = 5)//column = "DATE_TIME_2"
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dateTime2;

    @SerializedName(value = "discount1", alternate = {"Discount1"})
    @FileField(sortPriority = 3, order = DESC)//
    private int discount1;

    @SerializedName(value = "discount2", alternate = {"Discount2"})
    private int discount2;

    @SerializedName(value = "position", alternate = {"Position"})
    @FileField(position = 6)//column = "GEO"
    private GeoPoint position;

    @SerializedName(value = "location", alternate = {"Location"})
    private GeoPoint location;//大多数场景可以不需要@FileField转换，通过内建的计算引擎直接new一个GeoPoint

//    private NestedTags tags;//会导致Nested，则无法使用Index Sorting

    @Data(staticConstructor = "of")
    public static class NestedTags implements Serializable {

        private final String name;
        private final boolean isSuccess;
    }
}

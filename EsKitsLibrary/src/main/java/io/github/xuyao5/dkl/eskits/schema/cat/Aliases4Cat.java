package io.github.xuyao5.dkl.eskits.schema.cat;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 18/09/21 21:58
 * @apiNote TODO 这里输入文件说明
 * @implNote TODO 这里输入实现说明
 */
@Data(staticConstructor = "of")
public final class Aliases4Cat implements Serializable {

    @SerializedName(value = "alias", alternate = {"ALIAS"})
    private String alias;

    @SerializedName(value = "index", alternate = {"INDEX"})
    private String index;

    @SerializedName(value = "filter", alternate = {"FILTER"})
    private String filter;

    @SerializedName(value = "routing.index", alternate = {"ROUTING.INDEX"})
    private String routingIndex;

    @SerializedName(value = "routing.search", alternate = {"ROUTING.SEARCH"})
    private String routingSearch;

    @SerializedName(value = "is_write_index", alternate = {"IS_WRITE_INDEX"})
    private String isWriteIndex;
}

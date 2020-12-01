package io.github.xuyao5.dal.common.standard;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 13/10/20 01:51
 * @apiNote 标准文件行Bean
 * @implNote 用于存储标准的文本文件的一行数据
 */
@Data(staticConstructor = "of")
public final class StandardFileLine implements Serializable {

    private int lineNo;//行号
    private String lineRecord;//行记录
}

package io.github.xuyao5.dal.core.standard;

import lombok.Data;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 13/10/20 01:51
 * @apiNote TODO 这里输入文件说明
 * @implNote TODO 这里输入实现说明
 */
@Data(staticConstructor = "of")
public class StandardFileLine {

    private int lineNo;//行号
    private String lineRecord;//行记录
}

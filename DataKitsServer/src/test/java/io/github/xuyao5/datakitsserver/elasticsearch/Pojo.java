package io.github.xuyao5.datakitsserver.elasticsearch;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 16/02/21 23:34
 * @apiNote TODO 这里输入文件说明
 * @implNote TODO 这里输入实现说明
 */
@Data(staticConstructor = "of")
public final class Pojo implements Serializable {

    private final String message;
}

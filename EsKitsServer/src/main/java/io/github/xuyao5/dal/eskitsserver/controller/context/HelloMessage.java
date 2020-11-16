package io.github.xuyao5.dal.eskitsserver.controller.context;

import lombok.Data;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 14/10/20 21:51
 * @apiNote TODO 这里输入文件说明
 * @implNote TODO 这里输入实现说明
 */
@Data(staticConstructor = "of")
public class HelloMessage {

    private final String name;
}

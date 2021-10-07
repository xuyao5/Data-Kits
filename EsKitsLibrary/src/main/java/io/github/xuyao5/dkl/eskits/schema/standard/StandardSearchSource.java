package io.github.xuyao5.dkl.eskits.schema.standard;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 20/03/21 20:47
 * @apiNote StandardSearchSource
 * @implNote StandardSearchSource
 */
@Data(staticConstructor = "of")
public final class StandardSearchSource implements Serializable {

    private final StringBuilder searchCode;
    private String searchType = "DSL";//DSL or SQL
    private String searchDescription;//描述
}

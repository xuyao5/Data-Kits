package io.github.xuyao5.datakitsserver.dao.secondary.mymapper;

import io.github.xuyao5.datakitsserver.dao.secondary.model.OmsOrder2;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Thomas.XU(xuyao)
 * @version 24/06/22 22:26
 */
public interface MyOmsOrder2Mapper {

    int mergeSelective(@Param("orderList") List<OmsOrder2> list);
}
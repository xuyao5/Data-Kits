package io.github.xuyao5.datakitsserver.dao.primary.mymapper;

import io.github.xuyao5.datakitsserver.dao.primary.model.OmsOrder1;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.session.ResultHandler;

import java.util.Date;

/**
 * @author Thomas.XU(xuyao)
 * @version 24/06/22 22:26
 */
public interface MyOmsOrder1Mapper {

    @Options(resultSetType = ResultSetType.FORWARD_ONLY, fetchSize = Integer.MIN_VALUE)
    void streamQuery(@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo, ResultHandler<OmsOrder1> handler);
}
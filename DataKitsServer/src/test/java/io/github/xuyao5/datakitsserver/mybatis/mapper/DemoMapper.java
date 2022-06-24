package io.github.xuyao5.datakitsserver.mybatis.mapper;

import io.github.xuyao5.dkl.eskits.schema.standard.StandardFileLine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.session.ResultHandler;

import java.util.List;

/**
 * @author Thomas.XU(xuyao)
 * @version 24/06/22 12:49
 */
@Mapper
public interface DemoMapper {

    @Options(resultSetType = ResultSetType.FORWARD_ONLY, fetchSize = Integer.MIN_VALUE)
    void streamQueryDemo(ResultHandler<StandardFileLine> handler);

    long merge(List<StandardFileLine> list);
}

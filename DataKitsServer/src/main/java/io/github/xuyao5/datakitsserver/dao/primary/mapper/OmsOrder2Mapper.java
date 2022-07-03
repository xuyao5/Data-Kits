package io.github.xuyao5.datakitsserver.dao.primary.mapper;

import io.github.xuyao5.datakitsserver.dao.primary.model.OmsOrder2;
import io.github.xuyao5.datakitsserver.dao.primary.mymapper.MyOmsOrder2Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface OmsOrder2Mapper extends MyOmsOrder2Mapper {
    int insert(OmsOrder2 row);

    int insertSelective(OmsOrder2 row);

    OmsOrder2 selectByPrimaryKey(@Param("id") Long id, @Param("applyDate") Date applyDate);

    int updateByPrimaryKeySelective(OmsOrder2 row);

    int updateByPrimaryKey(OmsOrder2 row);
}
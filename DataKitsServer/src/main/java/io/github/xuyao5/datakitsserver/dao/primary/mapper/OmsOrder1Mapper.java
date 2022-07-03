package io.github.xuyao5.datakitsserver.dao.primary.mapper;

import io.github.xuyao5.datakitsserver.dao.primary.model.OmsOrder1;
import io.github.xuyao5.datakitsserver.dao.primary.mymapper.MyOmsOrder1Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface OmsOrder1Mapper extends MyOmsOrder1Mapper {
    int insert(OmsOrder1 row);

    int insertSelective(OmsOrder1 row);

    OmsOrder1 selectByPrimaryKey(@Param("id") Long id, @Param("applyDate") Date applyDate);

    int updateByPrimaryKeySelective(OmsOrder1 row);

    int updateByPrimaryKey(OmsOrder1 row);
}
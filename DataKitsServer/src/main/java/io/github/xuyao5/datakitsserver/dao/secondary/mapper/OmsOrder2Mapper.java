package io.github.xuyao5.datakitsserver.dao.secondary.mapper;

import io.github.xuyao5.datakitsserver.dao.secondary.model.OmsOrder2;
import io.github.xuyao5.datakitsserver.dao.secondary.mymapper.MyOmsOrder2Mapper;

public interface OmsOrder2Mapper extends MyOmsOrder2Mapper {
    int insert(OmsOrder2 row);

    int insertSelective(OmsOrder2 row);

    OmsOrder2 selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OmsOrder2 row);

    int updateByPrimaryKey(OmsOrder2 row);
}
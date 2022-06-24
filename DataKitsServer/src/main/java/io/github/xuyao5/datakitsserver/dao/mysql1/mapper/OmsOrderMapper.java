package io.github.xuyao5.datakitsserver.dao.mysql1.mapper;

import io.github.xuyao5.datakitsserver.dao.mysql1.model.OmsOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface OmsOrderMapper {
    int deleteByPrimaryKey(@Param("id") Long id, @Param("applyDate") Date applyDate);

    int insert(OmsOrder row);

    int insertSelective(OmsOrder row);

    OmsOrder selectByPrimaryKey(@Param("id") Long id, @Param("applyDate") Date applyDate);

    int updateByPrimaryKeySelective(OmsOrder row);

    int updateByPrimaryKey(OmsOrder row);
}
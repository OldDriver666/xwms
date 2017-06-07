package com.fise.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fise.model.entity.IMEvent;
import com.fise.model.entity.IMEventExample;

public interface IMEventMapper {
    long countByExample(IMEventExample example);

    int deleteByExample(IMEventExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IMEvent record);

    int insertSelective(IMEvent record);

    List<IMEvent> selectByExample(@Param("tableName") String tableName, @Param("example") IMEventExample example);

    IMEvent selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IMEvent record, @Param("example") IMEventExample example);

    int updateByExample(@Param("record") IMEvent record, @Param("example") IMEventExample example);

    int updateByPrimaryKeySelective(IMEvent record);

    int updateByPrimaryKey(IMEvent record);
}
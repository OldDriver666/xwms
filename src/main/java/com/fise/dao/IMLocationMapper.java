package com.fise.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fise.model.entity.IMLocation;
import com.fise.model.entity.IMLocationExample;

public interface IMLocationMapper {
    long countByExample(IMLocationExample example);

    int deleteByExample(IMLocationExample example);

    int insert(IMLocation record);

    int insertSelective(IMLocation record);

    List<IMLocation> selectByExample(@Param("tableName") String tableName, @Param("example") IMLocationExample example);

    int updateByExampleSelective(@Param("record") IMLocation record, @Param("example") IMLocationExample example);

    int updateByExample(@Param("record") IMLocation record, @Param("example") IMLocationExample example);
}
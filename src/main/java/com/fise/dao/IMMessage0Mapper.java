package com.fise.dao;

import com.fise.model.entity.IMMessage0;
import com.fise.model.entity.IMMessage0Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IMMessage0Mapper {
    long countByExample(IMMessage0Example example);

    int deleteByExample(IMMessage0Example example);

    int deleteByPrimaryKey(Integer id);

    int insert(IMMessage0 record);

    int insertSelective(IMMessage0 record);

    List<IMMessage0> selectByExample(@Param("tableName") String tableName,@Param("example") IMMessage0Example example);

    IMMessage0 selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IMMessage0 record, @Param("example") IMMessage0Example example);

    int updateByExample(@Param("record") IMMessage0 record, @Param("example") IMMessage0Example example);

    int updateByPrimaryKeySelective(IMMessage0 record);

    int updateByPrimaryKey(IMMessage0 record);
}
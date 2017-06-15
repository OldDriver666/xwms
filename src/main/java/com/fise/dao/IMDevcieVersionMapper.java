package com.fise.dao;

import com.fise.model.entity.IMDevcieVersion;
import com.fise.model.entity.IMDevcieVersionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IMDevcieVersionMapper {
    long countByExample(IMDevcieVersionExample example);

    int deleteByExample(IMDevcieVersionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IMDevcieVersion record);

    int insertSelective(IMDevcieVersion record);

    List<IMDevcieVersion> selectByExample(IMDevcieVersionExample example);

    IMDevcieVersion selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IMDevcieVersion record, @Param("example") IMDevcieVersionExample example);

    int updateByExample(@Param("record") IMDevcieVersion record, @Param("example") IMDevcieVersionExample example);

    int updateByPrimaryKeySelective(IMDevcieVersion record);

    int updateByPrimaryKey(IMDevcieVersion record);
}
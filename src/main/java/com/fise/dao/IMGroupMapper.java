package com.fise.dao;

import com.fise.model.entity.IMGroup;
import com.fise.model.entity.IMGroupExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IMGroupMapper {
    long countByExample(IMGroupExample example);

    int deleteByExample(IMGroupExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IMGroup record);

    int insertSelective(IMGroup record);

    List<IMGroup> selectByExample(IMGroupExample example);

    IMGroup selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IMGroup record, @Param("example") IMGroupExample example);

    int updateByExample(@Param("record") IMGroup record, @Param("example") IMGroupExample example);

    int updateByPrimaryKeySelective(IMGroup record);

    int updateByPrimaryKey(IMGroup record);
}
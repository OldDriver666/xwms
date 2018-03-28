package com.fise.dao;

import com.fise.model.entity.Wiki;
import com.fise.model.entity.WikiExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WikiMapper {
    long countByExample(WikiExample example);

    int deleteByExample(WikiExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Wiki record);

    int insertSelective(Wiki record);

    List<Wiki> selectByExample(WikiExample example);

    Wiki selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Wiki record, @Param("example") WikiExample example);

    int updateByExample(@Param("record") Wiki record, @Param("example") WikiExample example);

    int updateByPrimaryKeySelective(Wiki record);

    int updateByPrimaryKey(Wiki record);
}
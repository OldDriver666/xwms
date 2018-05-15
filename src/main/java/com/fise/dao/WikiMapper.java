package com.fise.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fise.base.Page;
import com.fise.model.entity.Wiki;
import com.fise.model.entity.WikiExample;

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
    
    List<Wiki> selectByExampleByPage(@Param("example") WikiExample example,@Param("page") Page<Wiki> page);
}
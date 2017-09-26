package com.fise.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fise.base.Page;
import com.fise.model.entity.IMUser;
import com.fise.model.entity.IMUserExample;
import com.fise.model.param.QueryUserParam;

public interface IMUserMapper {
    long countByExample(IMUserExample example);

    int deleteByExample(IMUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IMUser record);

    int insertSelective(IMUser record);

    List<IMUser> selectByExample(IMUserExample example);

    IMUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IMUser record, @Param("example") IMUserExample example);

    int updateByExample(@Param("record") IMUser record, @Param("example") IMUserExample example);

    int updateByPrimaryKeySelective(IMUser record);

    int updateByPrimaryKey(IMUser record);
    
    List<IMUser> selectByExamplebypage(@Param("example") IMUserExample example,@Param("page") Page<QueryUserParam> page);
}
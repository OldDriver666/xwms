package com.fise.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fise.base.Page;
import com.fise.model.entity.IMSmsPlatfrom;
import com.fise.model.entity.IMSmsPlatfromExample;

public interface IMSmsPlatfromMapper {
    long countByExample(IMSmsPlatfromExample example);

    int deleteByExample(IMSmsPlatfromExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IMSmsPlatfrom record);

    int insertSelective(IMSmsPlatfrom record);

    List<IMSmsPlatfrom> selectByExample(IMSmsPlatfromExample example);

    IMSmsPlatfrom selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IMSmsPlatfrom record, @Param("example") IMSmsPlatfromExample example);

    int updateByExample(@Param("record") IMSmsPlatfrom record, @Param("example") IMSmsPlatfromExample example);

    int updateByPrimaryKeySelective(IMSmsPlatfrom record);

    int updateByPrimaryKey(IMSmsPlatfrom record);
    
    List<IMSmsPlatfrom> selectByExampleByPage(@Param("example") IMSmsPlatfromExample example,@Param("page") Page<IMSmsPlatfrom> page);
}
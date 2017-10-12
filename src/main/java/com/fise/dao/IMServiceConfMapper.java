package com.fise.dao;

import com.fise.model.entity.IMServiceConf;
import com.fise.model.entity.IMServiceConfExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IMServiceConfMapper {
    long countByExample(IMServiceConfExample example);

    int deleteByExample(IMServiceConfExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IMServiceConf record);

    int insertSelective(IMServiceConf record);

    List<IMServiceConf> selectByExample(IMServiceConfExample example);

    IMServiceConf selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IMServiceConf record, @Param("example") IMServiceConfExample example);

    int updateByExample(@Param("record") IMServiceConf record, @Param("example") IMServiceConfExample example);

    int updateByPrimaryKeySelective(IMServiceConf record);

    int updateByPrimaryKey(IMServiceConf record);
}
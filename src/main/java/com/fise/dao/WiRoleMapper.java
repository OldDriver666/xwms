package com.fise.dao;

import com.fise.model.entity.WiRole;
import com.fise.model.entity.WiRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WiRoleMapper {
    long countByExample(WiRoleExample example);

    int deleteByExample(WiRoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WiRole record);

    int insertSelective(WiRole record);

    List<WiRole> selectByExample(WiRoleExample example);

    WiRole selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WiRole record, @Param("example") WiRoleExample example);

    int updateByExample(@Param("record") WiRole record, @Param("example") WiRoleExample example);

    int updateByPrimaryKeySelective(WiRole record);

    int updateByPrimaryKey(WiRole record);
}
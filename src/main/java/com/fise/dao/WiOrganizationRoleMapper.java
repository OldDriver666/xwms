package com.fise.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fise.model.entity.WiOrganizationRole;
import com.fise.model.entity.WiOrganizationRoleExample;
import com.fise.model.param.InsertAuthParam;

public interface WiOrganizationRoleMapper {
    long countByExample(WiOrganizationRoleExample example);

    int deleteByExample(WiOrganizationRoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WiOrganizationRole record);

    int insertSelective(WiOrganizationRole record);

    List<WiOrganizationRole> selectByExample(WiOrganizationRoleExample example);

    WiOrganizationRole selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WiOrganizationRole record, @Param("example") WiOrganizationRoleExample example);

    int updateByExample(@Param("record") WiOrganizationRole record, @Param("example") WiOrganizationRoleExample example);

    int updateByPrimaryKeySelective(WiOrganizationRole record);
    int updateById(WiOrganizationRole record);

    int updateByPrimaryKey(WiOrganizationRole record);
    
    int delRoleByRoleId(InsertAuthParam role);
    int delAuthByRoleId(InsertAuthParam role);
}
package com.fise.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fise.model.entity.WiPermission;
import com.fise.model.entity.WiPermissionExample;
import com.fise.model.param.ModuleQueryParam;
import com.fise.model.param.ModuleQueryResult;

public interface WiPermissionMapper {
    long countByExample(WiPermissionExample example);

    int deleteByExample(WiPermissionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WiPermission record);

    int insertSelective(WiPermission record);

    List<WiPermission> selectByExample(WiPermissionExample example);

    WiPermission selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WiPermission record, @Param("example") WiPermissionExample example);

    int updateByExample(@Param("record") WiPermission record, @Param("example") WiPermissionExample example);

    int updateByPrimaryKeySelective(WiPermission record);

    int updateByPrimaryKey(WiPermission record);
    
    List<ModuleQueryResult> selectPermissionByParam(ModuleQueryParam param);
}
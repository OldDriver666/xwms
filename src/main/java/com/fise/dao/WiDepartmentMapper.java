package com.fise.dao;

import com.fise.base.Page;
import com.fise.model.entity.WiAdmin;
import com.fise.model.entity.WiAdminExample;
import com.fise.model.entity.WiDepartment;
import com.fise.model.entity.WiDepartmentExample;
import com.fise.model.param.DepartmentParam;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WiDepartmentMapper {
    long countByExample(WiDepartmentExample example);

    int deleteByExample(WiDepartmentExample example);

    int deleteByPrimaryKey(Integer id);
    
    int deleteById(DepartmentParam param);

    int insert(WiDepartment record);

    int insertSelective(WiDepartment record);

    List<WiDepartment> selectByExample(WiDepartmentExample example);

    WiDepartment selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WiDepartment record, @Param("example") WiDepartmentExample example);

    int updateByExample(@Param("record") WiDepartment record, @Param("example") WiDepartmentExample example);

    int updateByPrimaryKeySelective(WiDepartment record);

    int updateByPrimaryKey(WiDepartment record);
    
    List<WiDepartment> selectByExampleByPage(@Param("example") WiDepartmentExample example,@Param("page") Page<WiDepartment> page);
}
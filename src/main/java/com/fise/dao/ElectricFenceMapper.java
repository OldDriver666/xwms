package com.fise.dao;

import com.fise.model.entity.ElectricFence;
import com.fise.model.entity.ElectricFenceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ElectricFenceMapper {
    long countByExample(ElectricFenceExample example);

    int deleteByExample(ElectricFenceExample example);

    int deleteByPrimaryKey(Integer fenceId);

    int insert(ElectricFence record);

    int insertSelective(ElectricFence record);

    List<ElectricFence> selectByExample(ElectricFenceExample example);

    ElectricFence selectByPrimaryKey(Integer fenceId);

    int updateByExampleSelective(@Param("record") ElectricFence record, @Param("example") ElectricFenceExample example);

    int updateByExample(@Param("record") ElectricFence record, @Param("example") ElectricFenceExample example);

    int updateByPrimaryKeySelective(ElectricFence record);

    int updateByPrimaryKey(ElectricFence record);
}
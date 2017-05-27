package com.fise.dao;

import com.fise.model.entity.TBAdminstrator;
import com.fise.model.entity.TBAdminstratorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TBAdminstratorMapper {
    long countByExample(TBAdminstratorExample example);

    int deleteByExample(TBAdminstratorExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TBAdminstrator record);

    int insertSelective(TBAdminstrator record);

    List<TBAdminstrator> selectByExample(TBAdminstratorExample example);

    TBAdminstrator selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TBAdminstrator record, @Param("example") TBAdminstratorExample example);

    int updateByExample(@Param("record") TBAdminstrator record, @Param("example") TBAdminstratorExample example);

    int updateByPrimaryKeySelective(TBAdminstrator record);

    int updateByPrimaryKey(TBAdminstrator record);
}
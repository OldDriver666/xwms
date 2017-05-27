package com.fise.dao;

import com.fise.model.entity.TBCompany;
import com.fise.model.entity.TBCompanyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TBCompanyMapper {
    long countByExample(TBCompanyExample example);

    int deleteByExample(TBCompanyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TBCompany record);

    int insertSelective(TBCompany record);

    List<TBCompany> selectByExample(TBCompanyExample example);

    TBCompany selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TBCompany record, @Param("example") TBCompanyExample example);

    int updateByExample(@Param("record") TBCompany record, @Param("example") TBCompanyExample example);

    int updateByPrimaryKeySelective(TBCompany record);

    int updateByPrimaryKey(TBCompany record);
}
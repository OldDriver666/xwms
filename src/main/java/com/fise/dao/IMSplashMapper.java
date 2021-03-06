package com.fise.dao;

import com.fise.model.entity.IMSplash;
import com.fise.model.entity.IMSplashExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IMSplashMapper {
    long countByExample(IMSplashExample example);

    int deleteByExample(IMSplashExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IMSplash record);

    int insertSelective(IMSplash record);

    List<IMSplash> selectByExample(IMSplashExample example);

    IMSplash selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IMSplash record, @Param("example") IMSplashExample example);

    int updateByExample(@Param("record") IMSplash record, @Param("example") IMSplashExample example);

    int updateByPrimaryKeySelective(IMSplash record);

    int updateByPrimaryKey(IMSplash record);
}
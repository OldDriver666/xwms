package com.fise.dao;

import com.fise.model.entity.AppStore;
import com.fise.model.entity.AppStoreExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppStoreMapper {
    long countByExample(AppStoreExample example);

    int deleteByExample(AppStoreExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AppStore record);

    int insertSelective(AppStore record);

    List<AppStore> selectByExample(AppStoreExample example);

    AppStore selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AppStore record, @Param("example") AppStoreExample example);

    int updateByExample(@Param("record") AppStore record, @Param("example") AppStoreExample example);

    int updateByPrimaryKeySelective(AppStore record);

    int updateByPrimaryKey(AppStore record);
}
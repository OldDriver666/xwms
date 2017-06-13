package com.fise.dao;

import com.fise.model.entity.WatchConf;
import com.fise.model.entity.WatchConfExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WatchConfMapper {
    long countByExample(WatchConfExample example);

    int deleteByExample(WatchConfExample example);

    int deleteByPrimaryKey(Integer deviceId);

    int insert(WatchConf record);

    int insertSelective(WatchConf record);

    List<WatchConf> selectByExample(WatchConfExample example);

    WatchConf selectByPrimaryKey(Integer deviceId);

    int updateByExampleSelective(@Param("record") WatchConf record, @Param("example") WatchConfExample example);

    int updateByExample(@Param("record") WatchConf record, @Param("example") WatchConfExample example);

    int updateByPrimaryKeySelective(WatchConf record);

    int updateByPrimaryKey(WatchConf record);
}
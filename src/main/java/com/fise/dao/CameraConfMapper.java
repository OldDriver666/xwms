package com.fise.dao;

import com.fise.model.entity.CameraConf;
import com.fise.model.entity.CameraConfExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CameraConfMapper {
    long countByExample(CameraConfExample example);

    int deleteByExample(CameraConfExample example);

    int deleteByPrimaryKey(Integer deviceId);

    int insert(CameraConf record);

    int insertSelective(CameraConf record);

    List<CameraConf> selectByExample(CameraConfExample example);

    CameraConf selectByPrimaryKey(Integer deviceId);

    int updateByExampleSelective(@Param("record") CameraConf record, @Param("example") CameraConfExample example);

    int updateByExample(@Param("record") CameraConf record, @Param("example") CameraConfExample example);

    int updateByPrimaryKeySelective(CameraConf record);

    int updateByPrimaryKey(CameraConf record);
}
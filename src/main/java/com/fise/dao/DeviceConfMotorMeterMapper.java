package com.fise.dao;

import com.fise.model.entity.DeviceConfMotorMeter;
import com.fise.model.entity.DeviceConfMotorMeterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DeviceConfMotorMeterMapper {
    long countByExample(DeviceConfMotorMeterExample example);

    int deleteByExample(DeviceConfMotorMeterExample example);

    int deleteByPrimaryKey(Integer deviceId);

    int insert(DeviceConfMotorMeter record);

    int insertSelective(DeviceConfMotorMeter record);

    List<DeviceConfMotorMeter> selectByExample(DeviceConfMotorMeterExample example);

    DeviceConfMotorMeter selectByPrimaryKey(Integer deviceId);

    int updateByExampleSelective(@Param("record") DeviceConfMotorMeter record, @Param("example") DeviceConfMotorMeterExample example);

    int updateByExample(@Param("record") DeviceConfMotorMeter record, @Param("example") DeviceConfMotorMeterExample example);

    int updateByPrimaryKeySelective(DeviceConfMotorMeter record);

    int updateByPrimaryKey(DeviceConfMotorMeter record);
}
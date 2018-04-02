package com.fise.dao;

import com.fise.model.entity.WiPatient;
import com.fise.model.entity.WiPatientExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WiPatientMapper {
    long countByExample(WiPatientExample example);

    int deleteByExample(WiPatientExample example);

    int insert(WiPatient record);

    int insertSelective(WiPatient record);

    List<WiPatient> selectByExample(WiPatientExample example);

    int updateByExampleSelective(@Param("record") WiPatient record, @Param("example") WiPatientExample example);

    int updateByExample(@Param("record") WiPatient record, @Param("example") WiPatientExample example);
}
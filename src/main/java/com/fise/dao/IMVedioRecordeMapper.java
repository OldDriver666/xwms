package com.fise.dao;

import com.fise.model.entity.IMVedioRecorde;
import com.fise.model.entity.IMVedioRecordeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IMVedioRecordeMapper {
    long countByExample(IMVedioRecordeExample example);

    int deleteByExample(IMVedioRecordeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IMVedioRecorde record);

    int insertSelective(IMVedioRecorde record);

    List<IMVedioRecorde> selectByExample(IMVedioRecordeExample example);

    IMVedioRecorde selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IMVedioRecorde record, @Param("example") IMVedioRecordeExample example);

    int updateByExample(@Param("record") IMVedioRecorde record, @Param("example") IMVedioRecordeExample example);

    int updateByPrimaryKeySelective(IMVedioRecorde record);

    int updateByPrimaryKey(IMVedioRecorde record);
}
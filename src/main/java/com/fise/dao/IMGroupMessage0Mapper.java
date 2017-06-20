package com.fise.dao;

import com.fise.base.Page;
import com.fise.model.entity.IMGroupMessage0;
import com.fise.model.entity.IMGroupMessage0Example;
import com.fise.model.param.GroupMessageQueryParam;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IMGroupMessage0Mapper {
    long countByExample(IMGroupMessage0Example example);

    int deleteByExample(IMGroupMessage0Example example);

    int deleteByPrimaryKey(Integer id);

    int insert(IMGroupMessage0 record);

    int insertSelective(IMGroupMessage0 record);

    List<IMGroupMessage0> selectByExample(@Param("tableName") String tableName,@Param("example") IMGroupMessage0Example example,@Param("page") Page<GroupMessageQueryParam> param);

    IMGroupMessage0 selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IMGroupMessage0 record, @Param("example") IMGroupMessage0Example example);

    int updateByExample(@Param("record") IMGroupMessage0 record, @Param("example") IMGroupMessage0Example example);

    int updateByPrimaryKeySelective(IMGroupMessage0 record);

    int updateByPrimaryKey(IMGroupMessage0 record);
}
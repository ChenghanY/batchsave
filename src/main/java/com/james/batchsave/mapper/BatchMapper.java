package com.james.batchsave.mapper;

import com.james.batchsave.dataobject.Batch;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BatchMapper {

    Batch selectById(@Param("id") String id);

    void batchUpdate(@Param("pos") List<Batch> pos);

    void batchInsert(@Param("id") Long id);
}

package com.mapper;

import com.entity.UserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    UserEntity select(@Param("id") Long id);

    List<UserEntity> selectAll();
}

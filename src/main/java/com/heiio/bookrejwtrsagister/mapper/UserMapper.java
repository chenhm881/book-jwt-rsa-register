package com.heiio.bookrejwtrsagister.mapper;


import com.heiio.bookrejwtrsagister.domain.SysUser;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    @Select({"select * from sys_user where username = #{username}"})
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "userRole", column = "id", javaType = List.class,
                    many = @Many(select = "com.heiio.bookrejwtrsagister.mapper.RoleMapper.findUserRole"))
    })
    SysUser findByName(String username);


}

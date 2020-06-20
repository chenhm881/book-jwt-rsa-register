package com.heiio.bookrejwtrsagister.mapper;

import com.heiio.bookrejwtrsagister.domain.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoleMapper {
    @Select("select * from sys_role where id in (select rid from sys_user_role where uid = #{id})")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "roleName", column = "role_name")
    })
    List<SysRole> findUserRole(Integer id);
}

package com.heiio.bookrejwtrsagister.service;

import com.heiio.bookrejwtrsagister.domain.SysUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;


public interface UserService extends UserDetailsService {
    void save(SysUser sysUser);
    List<SysUser> findAll();
    Map<String, Object> toAddRolePage(Integer id);
    void addRoleToUser(Integer userId, Integer ids);
}

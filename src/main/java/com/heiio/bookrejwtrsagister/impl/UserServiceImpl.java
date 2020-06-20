package com.heiio.bookrejwtrsagister.impl;

import com.heiio.bookrejwtrsagister.config.RsaKeyProperties;
import com.heiio.bookrejwtrsagister.domain.SysRole;
import com.heiio.bookrejwtrsagister.domain.SysUser;
import com.heiio.bookrejwtrsagister.mapper.RoleMapper;
import com.heiio.bookrejwtrsagister.mapper.UserMapper;
import com.heiio.bookrejwtrsagister.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RsaKeyProperties prop;

    @Override
    public void save(SysUser sysUser) {

    }

    @Override
    public List<SysUser> findAll() {
        return null;
    }

    @Override
    public Map<String, Object> toAddRolePage(Integer id) {
        return null;
    }

    @Override
    public void addRoleToUser(Integer userId, Integer ids) {

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SysUser sysUser = userMapper.findByName(username);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        List<SysRole> userRoles = sysUser.getUserRole();
        for (SysRole sysRole : userRoles) {
             authorities.add(new SimpleGrantedAuthority(sysRole.getRoleName()));
        }
        UserDetails userDetails = new User(sysUser.getUsername(), sysUser.getPassword(), authorities);
        if (userDetails != null) {
            return sysUser;
        } else {
            return null;
        }

    }
}

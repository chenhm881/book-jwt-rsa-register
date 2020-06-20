package com.heiio.bookrejwtrsagister.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heiio.bookrejwtrsagister.config.RsaKeyProperties;
import com.heiio.bookrejwtrsagister.domain.SysRole;
import com.heiio.bookrejwtrsagister.domain.SysUser;
import com.heiio.bookrejwtrsagister.utils.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private RsaKeyProperties prop;

    public JwtLoginFilter(AuthenticationManager authenticationManager, RsaKeyProperties prop) {
        this.authenticationManager = authenticationManager;
        this.prop = prop;
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {

            //UserDetails userDetails = new ObjectMapper().readValue(request.getInputStream(), UserDetails.class);
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
            return authenticationManager.authenticate(authRequest);
        } catch (Exception e) {
            try {
                response.setContentType("application/json;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                PrintWriter out = response.getWriter();
                Map map = new HashMap<>();
                map.put("code", HttpServletResponse.SC_UNAUTHORIZED);
                map.put("msg", "用户名或者密码错误");
                out.write(new ObjectMapper().writeValueAsString(map));
                out.flush();
                out.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }

            throw new RuntimeException(e);
        }
    }

    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SysUser resultUser = new SysUser();
        SysUser authUser = (SysUser) authResult.getPrincipal();

        resultUser.setUsername(authUser.getUsername());
        resultUser.setId(authUser.getId());
        resultUser.setStatus(authUser.getStatus());
        resultUser.setUserRole((List<SysRole>) authResult.getAuthorities());
        String token = JwtUtils.generateTokenExpireInMinutes(resultUser, prop.getPrivateKey(), 3600*24);
        response.addHeader("Authorization", "Bearer " + token);
        try {
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = response.getWriter();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("code", HttpServletResponse.SC_OK);
            map.put("message", "登陆成功！");
            out.write(new ObjectMapper().writeValueAsString(map));
            out.flush();
            out.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}


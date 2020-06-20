package com.heiio.bookrejwtrsagister.config;

import com.heiio.bookrejwtrsagister.filters.JwtLoginFilter;
import com.heiio.bookrejwtrsagister.filters.JwtVerifyFilter;
import com.heiio.bookrejwtrsagister.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService UserService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private RsaKeyProperties prop;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .formLogin().loginPage("/login").loginProcessingUrl("/login").permitAll()
                .and()
                //允许不登陆就可以访问的方法，多个用逗号分隔
                .authorizeRequests()
                //其他的需要授权后访问
                .anyRequest().authenticated()
                .and()
                //增加自定义认证过滤器
                .addFilter(new JwtLoginFilter(authenticationManager(), prop))
                //增加自定义验证认证过滤器
                .addFilter(new JwtVerifyFilter(authenticationManager(), prop))
                // 前后端分离是无状态的，不用session了，直接禁用。
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    public  void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(UserService).passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}

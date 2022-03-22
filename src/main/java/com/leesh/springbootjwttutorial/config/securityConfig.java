package com.leesh.springbootjwttutorial.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class securityConfig extends WebSecurityConfigurerAdapter {

    //h2-console, favicon은 시큐리티에 걸리지않도록 설정
    @Override
    public void configure(WebSecurity web) throws Exception {
       web.ignoring()
               .antMatchers(
                       "/h2-console/**"
                       ,"/favicon.ico"
               );
    }

    //http 요청에대한 권한 제한 설정
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/hello").permitAll() //모든 접근허용
                .anyRequest().authenticated(); // 인증받아야함.
    }
}

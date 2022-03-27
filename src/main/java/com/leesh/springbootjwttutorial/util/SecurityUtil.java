package com.leesh.springbootjwttutorial.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class SecurityUtil {

    //현재 security context에 인증된 사용자 username 조회
    public static Optional<String> getCurrentUsername(){
        final Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();

        if(authentication == null){
            log.debug("Security Context에 인증정보가 없습니다.");
            return Optional.empty();
        }

        String username = null;
        if(authentication.getPrincipal() instanceof UserDetails){
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            username = springSecurityUser.getUsername();
        }else if(authentication.getPrincipal() instanceof String){
            username = (String)authentication.getPrincipal();
        }
        return Optional.ofNullable(username);
    }
}

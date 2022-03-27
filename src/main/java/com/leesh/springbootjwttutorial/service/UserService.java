package com.leesh.springbootjwttutorial.service;

import com.leesh.springbootjwttutorial.dto.UserDto;
import com.leesh.springbootjwttutorial.entity.Authority;
import com.leesh.springbootjwttutorial.entity.User;
import com.leesh.springbootjwttutorial.repository.UserRepository;
import com.leesh.springbootjwttutorial.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    @Transactional
    public User signup(UserDto userDto){
        if(userRepository.findOneWithAuthoritiesByEmail(userDto.getEmail()).orElse(null) != null){
            throw new RuntimeException(("이미 가입되어 있는 유저입니다."));
        }

        //권한entity, 유저entity 생성
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword())) //패스워드 암호화
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return userRepository.save(user);
    }

    //email로 회원정보 조회
    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(String email){
        return userRepository.findOneWithAuthoritiesByEmail(email);
    }

    //현재 인증된 사용자의 회원정보 조회
    @Transactional(readOnly = true)
    public Optional<User> getMyUserWithAuthorities(){
        log.info(SecurityUtil.getCurrentUsername().toString());
        return SecurityUtil.getCurrentUsername()
                .flatMap(email -> userRepository.findOneWithAuthoritiesByEmail(email));
    }
}

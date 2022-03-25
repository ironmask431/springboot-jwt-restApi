package com.leesh.springbootjwttutorial.service;

import com.leesh.springbootjwttutorial.dto.UserDto;
import com.leesh.springbootjwttutorial.entity.Authority;
import com.leesh.springbootjwttutorial.entity.User;
import com.leesh.springbootjwttutorial.repository.UserRepository;
import com.leesh.springbootjwttutorial.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User signup(UserDto userDto){
        if(userRepository.findOneWithAuthoritiesByEmail(userDto.getEmail()).orElse(null) != null){
            throw new RuntimeException(("이미 가입되어 있는 유저입니다."));
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(String email){
        return userRepository.findOneWithAuthoritiesByEmail(email);
    }

    @Transactional(readOnly = true)
    public Optional<User> getMyUserWithAuthorities(){
        return SecurityUtil.getCurrentUsername()
                .flatMap(userRepository::findOneWithAuthoritiesByEmail);
    }
}

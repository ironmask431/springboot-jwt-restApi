package com.leesh.springbootjwttutorial.controller;

import com.leesh.springbootjwttutorial.entity.User;
import com.leesh.springbootjwttutorial.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 유저 API 컨트롤러
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    //현재인증된 사용자의 정보 조회 (나의 정보조회)
    @GetMapping("/myInfo")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<User> getMyUserInfo(){
        return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
    }

    //EMAIL로 회원정보 조회
    @GetMapping("/email/{email}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<User> getUserInfo(@PathVariable String email){
        return ResponseEntity.ok(userService.getUserWithAuthorities(email).get());
    }
}

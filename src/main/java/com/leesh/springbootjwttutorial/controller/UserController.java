package com.leesh.springbootjwttutorial.controller;

import com.leesh.springbootjwttutorial.dto.UserDto;
import com.leesh.springbootjwttutorial.entity.User;
import com.leesh.springbootjwttutorial.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    //나의정보조회
    @GetMapping("/myInfo")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<User> getMyUserInfo(){
        return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
    }

    //특정회원정보 조회
    @GetMapping("/email/{email}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<User> getUserInfo(@PathVariable String email){
        return ResponseEntity.ok(userService.getUserWithAuthorities(email).get());
    }
}

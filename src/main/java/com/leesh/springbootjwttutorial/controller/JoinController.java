package com.leesh.springbootjwttutorial.controller;

import com.leesh.springbootjwttutorial.dto.UserDto;
import com.leesh.springbootjwttutorial.entity.User;
import com.leesh.springbootjwttutorial.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/join")
public class JoinController {
    private final UserService userService;

    //회원가입 - email, password
    @PostMapping("/signup")
    public ResponseEntity<User> signup(@Valid @RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.signup(userDto));
    }
}
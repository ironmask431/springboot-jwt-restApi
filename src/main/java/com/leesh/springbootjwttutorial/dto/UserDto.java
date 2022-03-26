package com.leesh.springbootjwttutorial.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.leesh.springbootjwttutorial.entity.User;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long userId;

    @NotNull //requestBody로 입력받을 때 null 비허용
    @Size(min=3, max=50)
    private String email;

    //회원가입 테스트코드에서 오류발생 (password를 get하지 못함)하여 주석처리함.
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min=3, max=100)
    private String password;

    public UserDto(User user){
        this.userId = user.getUserId();
        this.email = user.getEmail();
    }
}

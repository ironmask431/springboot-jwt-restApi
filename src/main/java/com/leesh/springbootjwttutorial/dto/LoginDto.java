package com.leesh.springbootjwttutorial.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    //@NotNull = requestBody로 입력받을때 not null 체크
    @NotNull
    @Size(min=3, max=50)
    private String email;

    @NotNull
    @Size(min=3, max=100)
    private String password;
}

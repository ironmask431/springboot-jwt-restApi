package com.leesh.springbootjwttutorial.restApiTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leesh.springbootjwttutorial.dto.UserDto;
import com.leesh.springbootjwttutorial.entity.Authority;
import com.leesh.springbootjwttutorial.entity.User;
import com.leesh.springbootjwttutorial.jwt.JwtFilter;
import com.leesh.springbootjwttutorial.jwt.TokenProvider;
import com.leesh.springbootjwttutorial.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTest {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ProductConterllerTest.class);

    @LocalServerPort
    private int port;

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private MockMvc mvc;

    //테스트용 유저 데이터
    private Long userId;
    private String email = "test@gmail.com";
    private String password = "test";

    @Before //테스트 시작전 실행
    public void setUp(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity()).build();

        //data.sql로 미리들어간 데이터가 있으므로 테스트 실행전 전체 삭제해줌.
        userRepository.deleteAll();

        //테스트용 유저정보 입력
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        userRepository.save(user);

        //Security Context에 유저정보 입력
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER,"Bearer "+ jwt);//
    }

    @After // 테스트 후 실행
    public void tearDown() throws Exception {
        userRepository.deleteAll();
    }

    @Test
    public void 로그인() throws Exception {
        //given
        String url = "http://localhost:"+port+"/api/auth/login";

        UserDto userDto = new UserDto();
        userDto.setEmail(email);
        userDto.setPassword(password);

        log.info("userDto.getEmail()="+userDto.getEmail());
        log.info("userDto.getPassword()="+userDto.getPassword());

        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDto)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.token").exists())
        ;
    }
}

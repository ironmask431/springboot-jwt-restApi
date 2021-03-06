package com.leesh.springbootjwttutorial.restApiTest;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

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

    //???????????? ?????? ?????????
    private Long userId;
    private String email = "test@gmail.com";
    private String password = "test";

    @Before //????????? ????????? ??????
    public void setUp(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity()).build();

        //data.sql??? ??????????????? ???????????? ???????????? ????????? ????????? ?????? ????????????.
        userRepository.deleteAll();

        //???????????? ???????????? ??????
        Authority authority = Authority.builder()
                .authorityName("ROLE_ADMIN")
                .build();

        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        userRepository.save(user);

        //Security Context??? ???????????? ??????, ????????????
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER,"Bearer "+ jwt);//
    }

    @After // ????????? ??? ??????
    public void tearDown() throws Exception {
        userRepository.deleteAll();
    }

    @Test
    public void ???????????????_????????????() throws Exception {
        //given
        String url = "http://localhost:"+port+"/api/user/myInfo";

        //when //then
        mvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email",is(email)))
                .andExpect(jsonPath("$.authorities[0].authorityName",is("ROLE_ADMIN")))
        ;
    }

    @Test
    public void email???_????????????() throws Exception {
        //given
        String url = "http://localhost:"+port+"/api/user/email/"+email;

        //when //then
        mvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email",is(email)))
                .andExpect(jsonPath("$.authorities[0].authorityName",is("ROLE_ADMIN")))
        ;
    }
}

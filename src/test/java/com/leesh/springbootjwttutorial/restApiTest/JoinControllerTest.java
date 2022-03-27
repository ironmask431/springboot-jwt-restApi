package com.leesh.springbootjwttutorial.restApiTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leesh.springbootjwttutorial.dto.UserDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JoinControllerTest {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ProductConterllerTest.class);

    @LocalServerPort
    private int port;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    //테스트용 유저 데이터
    private String email = "test@gmail.com";
    private String password = "test";

    @Before //테스트 시작전 실행
    public void setUp(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity()).build();
    }

    @After // 테스트 후 실행
    public void tearDown() throws Exception {

    }

    @Test
    public void 회원가입() throws Exception {
        //given
        String url = "http://localhost:"+port+"/api/join/signup";

        UserDto userDto = new UserDto();
        userDto.setEmail(email);
        userDto.setPassword(password);

        log.info("userDto.getEmail()="+userDto.getEmail());
        log.info("userDto.getPassword()="+userDto.getPassword());

        //정상응답, 가입한 email과 "USER" 권한이 리턴되는지 확인
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDto)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.email",is(email)))
                        .andExpect(jsonPath("$.authorities[0].authorityName",is("ROLE_USER")))
        ;
    }
}

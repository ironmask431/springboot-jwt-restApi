package com.leesh.springbootjwttutorial.restApiTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leesh.springbootjwttutorial.dto.OrderDto;
import com.leesh.springbootjwttutorial.dto.UserDto;
import com.leesh.springbootjwttutorial.entity.Order;
import com.leesh.springbootjwttutorial.entity.Product;
import com.leesh.springbootjwttutorial.jwt.JwtFilter;
import com.leesh.springbootjwttutorial.jwt.TokenProvider;
import com.leesh.springbootjwttutorial.repository.OrderRepository;
import com.leesh.springbootjwttutorial.repository.ProductRepository;
import com.leesh.springbootjwttutorial.repository.UserRepository;
import com.leesh.springbootjwttutorial.util.SecurityUtil;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerTest {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ProductConterllerTest.class);

    @LocalServerPort
    private int port;

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    private MockMvc mvc;

    //테스트용 데이터
    private Long prdId;
    private String prdNm = "테스트상품";;
    private int price = 3000;
    private Long ordId;
    private Long userId;

    private String email = "admin@gmail.com";
    private String password = "admin";

    @Before //테스트 시작전 실행
    public void setUp(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity()).build();

        //Security Context에 유저정보 입력
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER,"Bearer "+ jwt);//

        //Security Context 에 입력된 유저의 userId 조회
        userId = SecurityUtil.getCurrentUsername()
                .flatMap(email -> userRepository.findOneWithAuthoritiesByEmail(email)).get().getUserId();

        //data.sql로 미리들어간 데이터가 있으므로 테스트 실행전 전체 삭제해줌.
        productRepository.deleteAll();
        orderRepository.deleteAll();

        //테스트용 상품data 입력
        productRepository.save(Product.builder().prdNm(prdNm).price(price).build());
        //입력된 prdId 조회
        prdId = productRepository.findAll().get(0).getPrdId();

        //테스트용 주문data 입력
        orderRepository.save(Order.builder().prdId(prdId).userId(userId).build());
        //입력된 ordId 조회
        ordId = orderRepository.findAll().get(0).getOrdId();
    }

    @After // 테스트 후 실행
    public void tearDown() throws Exception {
        productRepository.deleteAll();
        orderRepository.deleteAll();
    }

    @Test
    public void 전체주문조회() throws Exception {
        //given
        String url = "http://localhost:"+port+"/api/order/all";

        //when //then
        mvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ordId",is(ordId.intValue())))
                .andExpect(jsonPath("$[0].prdId",is(prdId.intValue())))
                .andExpect(jsonPath("$[0].userId",is(userId.intValue())))
        ;
    }

    @Test
    public void 주문id로_주문조회() throws Exception {
        //given
        String url = "http://localhost:"+port+"/api/order/ordId/"+ordId.intValue();

        //when //then
        mvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ordId",is(ordId.intValue())))
                .andExpect(jsonPath("$.prdId",is(prdId.intValue())))
                .andExpect(jsonPath("$.userId",is(userId.intValue())))
        ;
    }

    @Test
    public void 유저id로_주문조회() throws Exception {
        //given
        String url = "http://localhost:"+port+"/api/order/userId/"+userId.intValue();

        //when //then
        mvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ordId",is(ordId.intValue())))
                .andExpect(jsonPath("$[0].prdId",is(prdId.intValue())))
                .andExpect(jsonPath("$[0].userId",is(userId.intValue())))
        ;
    }

    @Test
    public void 나의_주문조회() throws Exception {
        //given
        String url = "http://localhost:"+port+"/api/order/myOrder";

        //when //then
        mvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ordId",is(ordId.intValue())))
                .andExpect(jsonPath("$[0].prdId",is(prdId.intValue())))
                .andExpect(jsonPath("$[0].userId",is(userId.intValue())))
        ;
    }

    @Test
    public void 주문입력() throws Exception {
        String url = "http://localhost:"+port+"/api/order/save";

        OrderDto orderDto = new OrderDto();
        orderDto.setPrdId(prdId);

        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(orderDto)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.prdId",is(prdId.intValue())))
                        .andExpect(jsonPath("$.userId",is(userId.intValue())))
        ;
    }

}

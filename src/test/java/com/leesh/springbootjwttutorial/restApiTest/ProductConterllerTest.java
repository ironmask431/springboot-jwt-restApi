package com.leesh.springbootjwttutorial.restApiTest;

import com.leesh.springbootjwttutorial.entity.Product;
import com.leesh.springbootjwttutorial.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductConterllerTest {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ProductConterllerTest.class);

    @LocalServerPort
    private int port;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ProductRepository productRepository;

    private MockMvc mvc;

    private Long prdId;
    private String prdNm = "테스트상품";;
    private int price = 3000;

    @Before //테스트 시작전 실행
    public void setUp(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity()).build();

        //data.sql로 미리들어간 데이터가 있으므로 테스트 실행전 전체 삭제해줌.
        productRepository.deleteAll();

        //테스트할 상품data 입력
        productRepository.save(Product.builder()
                .prdNm(prdNm).price(price).build());

        //입력된 prdId 조회
        prdId = productRepository.findAll().get(0).getPrdId();
    }

    @After // 테스트 후 실행
    public void tearDown() throws Exception {
        productRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER") //security 권한 USER로 설정
    public void 전체상품조회() throws Exception {
        //given
        String url = "http://localhost:"+port+"/api/product/all";

        //when //then
        mvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].prdId").value(prdId.intValue()))
                .andExpect(jsonPath("$[0].prdNm").value(prdNm))
                .andExpect(jsonPath("$[0].price").value(price))
        ;
    }

    @Test
    @WithMockUser(roles = "USER") //security 권한 USER로 설정
    public void 상품id로_상품조회() throws Exception {
        //given
        String url = "http://localhost:"+port+"/api/product/prdId/"+prdId.intValue();

        //when //then
        mvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prdId",is(prdId.intValue())))
                .andExpect(jsonPath("$.prdNm",is(prdNm)))
                .andExpect(jsonPath("$.price",is(price)))
        ;
    }
}

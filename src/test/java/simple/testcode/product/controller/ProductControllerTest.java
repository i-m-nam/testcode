package simple.testcode.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import simple.testcode.product.domain.ProductSellingStatus;
import simple.testcode.product.dto.ProductRequest;
import simple.testcode.product.service.ProductService;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {

    // service layer 하위는 mocking 처리
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper; // post body 의 직렬화 <> 역직렬화 위함


    @MockBean // mockito 로 만든 mock 객체를 productService 에 넣어줌
    private ProductService productService;

    @DisplayName("신규 상품을 등록 요청을 받아 OK로 응답한다.")
    @Test
    void createProduct() throws Exception {
        // given
        ProductRequest request = ProductRequest.builder()
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("아메리카노a")
                .price(4100)
                .build();


        // when && // then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products/new")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print()) // log 기록
                .andExpect(status().isOk());
    }

    // 파라미터 유효성 검사 테스트
    @DisplayName("신규 상품을 등록할 때, 상품 타입은 필수값이다.")
    @Test
    void createProductWithoutType() throws Exception {
        // given
        ProductRequest request = ProductRequest.builder()
                // .sellingStatus(ProductSellingStatus.SELLING) // 상품 판매상태 를 요청 파라미터에 넣지 않음
                .name("아메리카노")
                .price(4000)
                .build();


        // when && // then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products/new")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                // 간단 버전
                // .andExpect(status().isBadRequest());
                // 상세 버전
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("상품 판매상태는 필수입니다."))
                .andExpect(jsonPath("$.data").isEmpty());

    }
}
package simple.testcode.product.e2e;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import simple.testcode.common.controller.ApiResponse;
import simple.testcode.product.controller.ProductController;
import simple.testcode.product.domain.ProductSellingStatus;
import simple.testcode.product.dto.ProductRequest;
import simple.testcode.product.dto.ProductResponse;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class ProductEndToEndTest {

    @Autowired
    ProductController productController;

    @DisplayName("[상품 생성] 에 대한 E2E 테스트__응답 데이터 까지 확인")
    @Test
    void fromProductController() {
        // given
        ProductRequest request = ProductRequest.builder()
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("콜드브루라떼")
                .price(4000)
                .build();


        // when
        ApiResponse<ProductResponse> response = productController.createProduct(request);


        // then
        // response code
        assertThat(response.getCode()).isEqualTo(200);

        // response data
        assertThat(response.getData()).extracting("sellingStatus", "name", "price")
                .contains(ProductSellingStatus.SELLING, "콜드브루라떼", 4000);
    }
}

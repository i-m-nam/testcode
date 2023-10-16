package simple.testcode.order.e2e;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import simple.testcode.common.controller.ApiResponse;
import simple.testcode.order.controller.OrderController;
import simple.testcode.order.dto.OrderRequest;
import simple.testcode.order.dto.OrderResponse;
import simple.testcode.product.controller.ProductController;
import simple.testcode.product.domain.ProductEntity;
import simple.testcode.product.domain.ProductSellingStatus;
import simple.testcode.product.dto.ProductRequest;
import simple.testcode.product.dto.ProductResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class OrderEndToEndTest {

    @Autowired OrderController orderController;
    @Autowired
    ProductController productController;

    ProductResponse productResponse;

    ProductRequest productRequest;

    @BeforeEach
    void setProductData() {
        productRequest = ProductRequest.builder()
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("카페라떼")
                .price(3200)
                .build();

        ApiResponse<ProductResponse> product = productController.createProduct(productRequest);
        productResponse = product.getData();
    }

    @DisplayName("[주문 생성] 에 대한 E2E 테스트__응답 데이터 까지 확인")
    @Test
    void fromOrderController() {
        // given
        OrderRequest request = OrderRequest.builder()
                .productNumbers(List.of(productResponse.getProductNumber()))
                .build();
        ProductEntity entity = productRequest.toServiceVo().toEntity(productResponse.getProductNumber());


        // when
        ApiResponse<OrderResponse> response = orderController.createOrder(request);
        ProductResponse lastProductResponse = response.getData().getProducts().get(0);


        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);

        assertThat(response.getData().getTotalPrice()).isEqualTo(productRequest.getPrice());

        assertThat(lastProductResponse).extracting("productNumber", "price", "name", "sellingStatus")
                .contains(entity.getProductNumber(), entity.getPrice(), entity.getName(), entity.getSellingStatus());
    }
}

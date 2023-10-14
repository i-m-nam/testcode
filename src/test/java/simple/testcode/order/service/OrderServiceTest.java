package simple.testcode.order.service;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import simple.testcode.order.dao.OrderRepository;
import simple.testcode.order.dto.OrderServiceRequestVo;
import simple.testcode.order.dto.OrderResponse;
import simple.testcode.orderproduct.dao.OrderProductRepository;
import simple.testcode.product.dao.ProductRepository;
import simple.testcode.product.domain.ProductEntity;
import simple.testcode.product.domain.ProductSellingStatus;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("주문번호 리스트를 받아 주문을 생성한다.")
    @Test
    void createOrder() {

        // given
        LocalDateTime registeredDateTime = LocalDateTime.now();

        ProductEntity product1 = this.createProduct("001", 1000);
        ProductEntity product2 = this.createProduct("002", 2000);
        ProductEntity product3 = this.createProduct("003", 7000);

        productRepository.saveAll(List.of(product1, product2, product3));

        OrderServiceRequestVo request = OrderServiceRequestVo.builder()
                .productNumbers(List.of("001", "002"))
                .build();


        // when
        OrderResponse orderResponse = orderService.createOrder(request, registeredDateTime);


        // then
        assertThat(orderResponse.getId()).isNotNull();

        assertThat(orderResponse)
                .extracting("registeredDateTime", "totalPrice")
                .contains(registeredDateTime, 3000);

        assertThat(orderResponse.getProducts()).hasSize(2)
                .extracting("productNumber", "price")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("001", 1000),
                        Tuple.tuple("002", 2000)
                );
    }

    private ProductEntity createProduct(String productNumber, int price) {
        return ProductEntity.builder()
                .productNumber(productNumber)
                .price(price)
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("샘플 메뉴 이름")
                .build();
    }
}
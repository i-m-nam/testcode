package simple.testcode.product.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import simple.testcode.product.dao.ProductRepository;
import simple.testcode.product.domain.ProductEntity;
import simple.testcode.product.domain.ProductSellingStatus;
import simple.testcode.product.dto.ProductServiceRequestVo;
import simple.testcode.product.dto.ProductResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest
class ProductServiceTest {

    @Autowired private ProductService productService;

    @Autowired private ProductRepository productRepository;

    @DisplayName("신규 상품을 추가한다. 상품번호는 가장 최근 상품의 상품번호에서 1 증가한 값이다.")
    @Test
    void createProduct() {
        // given
        ProductEntity product1 = createProduct("001", ProductSellingStatus.SELLING, "아메리카노", 4000);
        productRepository.save(product1);

        ProductServiceRequestVo request = ProductServiceRequestVo.builder()
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("카푸치노")
                .price(5700)
                .build();


        // when
        ProductResponse productResponse = productService.createProduct(request);


        // then
        assertThat(productResponse)
                .extracting("productNumber", "sellingStatus", "name", "price")
                .contains("002", ProductSellingStatus.SELLING, "카푸치노", 5700);

        // 상품 저장 확인
        List<ProductEntity> products = productRepository.findAll();
        assertThat(products).hasSize(2)
                .extracting("productNumber", "sellingStatus", "name", "price")
                .containsExactlyInAnyOrder(
                        tuple("001", ProductSellingStatus.SELLING, "아메리카노", 4000),
                        tuple("002", ProductSellingStatus.SELLING, "카푸치노", 5700)
                );
    }

    @DisplayName("상품이 하나도 없는 경우, 신규 상품을 추가하면, 상품 번호는 001 이다.")
    @Test
    void createProductWhenProductsIsEmpty() {
        clearData();
        // given
        // 기존의 product 데이터 있다면 지우기 (AfterEach 에서 구현함)
        ProductServiceRequestVo request = ProductServiceRequestVo.builder()
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("카푸치노")
                .price(5700)
                .build();


        // when
        ProductResponse productResponse = productService.createProduct(request);


        // then
        assertThat(productResponse)
                .extracting("productNumber", "sellingStatus", "name", "price")
                .contains("001", ProductSellingStatus.SELLING, "카푸치노", 5700);

        // 상품 저장 확인
        List<ProductEntity> products = productRepository.findAll();
        assertThat(products).hasSize(1)
                .extracting("productNumber", "sellingStatus", "name", "price")
                .contains(
                        tuple("001", ProductSellingStatus.SELLING, "카푸치노", 5700)
                );
    }

    private void clearData() {
        productRepository.deleteAllInBatch();
    }

    private ProductEntity createProduct(String productNumber, ProductSellingStatus sellingStatus, String name, int price) {
        return ProductEntity.builder()
                .productNumber(productNumber)
                .sellingStatus(sellingStatus)
                .name(name)
                .price(price)
                .build();
    }
}
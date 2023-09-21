package simple.testcode.product.dao;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import simple.testcode.product.domain.ProductEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static simple.testcode.product.domain.ProductSellingStatus.*;


@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @DisplayName("3_1_1_원하는 판매상태를 가진 상품들을 조회한다.")
    @Test
    void selectQueryCheck() {
        // given
        ProductEntity productEntityA = new ProductEntity("001", SELLING, "상품-A", 1000);
        ProductEntity productEntityB = new ProductEntity("002", HOLD, "상품-B", 2000);
        ProductEntity productEntityC = new ProductEntity("003", STOP, "상품-C", 5500);

        productRepository.saveAll(List.of(productEntityA, productEntityB, productEntityC));


        // when
        List<ProductEntity> productEntityList = productRepository.findAllBySellingStatusIn(List.of(SELLING, HOLD));


        // then (AssertJ import)
        assertThat(productEntityList).hasSize(2)
                .extracting("productNumber", "sellingStatus", "name", "price")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("001", SELLING, "상품-A", 1000),
                        Tuple.tuple("002", HOLD, "상품-B", 2000)
                );
    }

    @DisplayName("3_1_2_상품번호 리스트를 가진 상품들을 조회한다.")
    @Test
    void findAllByProductNumberIn() {
        // given
        ProductEntity product1 = new ProductEntity("021", SELLING, "아메리카노", 4000);
        ProductEntity product2 = new ProductEntity("302", SELLING, "라뗴", 1000);
        ProductEntity product3 = new ProductEntity("003", SELLING, "BB", 3500);
        productRepository.saveAll(List.of(product1, product2, product3));


        // when
        List<ProductEntity> products = productRepository.findAllByProductNumberIn(List.of("302", "003"));


        // then
        assertThat(products).hasSize(2)
                .extracting("productNumber", "name", "sellingStatus")
                .containsExactlyInAnyOrder(
                        tuple("003", "BB", SELLING),
                        tuple("302", "라뗴", SELLING)
                );

    }

    @DisplayName("3_1_3_가장 마지막으로 저장한 상품의 상품번호를 조회한다.")
    @Test
    void findLatestProductNumber() {
        // given
        String targetProductNumber = "003";
        ProductEntity product1 = new ProductEntity("000", SELLING, "아메리카노", 4000);
        ProductEntity product2 = new ProductEntity("001", SELLING, "라뗴", 1000);
        ProductEntity product3 = new ProductEntity(targetProductNumber, SELLING, "BB", 3500);
        // 순차적으로 저장 될 것
        productRepository.saveAll(List.of(product1, product2, product3));


        // when
        String latestProductNumber = productRepository.findLatestProductNumber();


        // then
        assertThat(latestProductNumber).isEqualTo(targetProductNumber);
    }

}
package simple.testcode.product.dao;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import simple.testcode.product.domain.ProductEntity;
import simple.testcode.product.domain.ProductSellingStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static simple.testcode.product.domain.ProductSellingStatus.*;


//@SpringBootTest
// 테스트에서 사용될 떈, 각 테스트를 실행 할 때마다 트랜잭션을 시작하고 끝나면 트랜잭션을 강제로 롤백시킴
// 사용 주의
// @Transactional
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

//    @AfterEach
//    void tearDown() {
//        // repo 삭제 순서 유의
//        productRepository.deleteAllInBatch();
//    }

    @DisplayName("3_1_1_원하는 판매상태를 가진 상품들을 조회한다.")
    @Test
    void selectQueryCheck() {
        // given
        ProductEntity productEntityA = createProduct("001", SELLING, "상품-A", 1000);
        ProductEntity productEntityB = createProduct("002", HOLD, "상품-B", 2000);
        ProductEntity productEntityC = createProduct("003", STOP, "상품-C", 5500);

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
        ProductEntity product1 = createProduct("021", SELLING, "아메리카노", 4000);
        ProductEntity product2 = createProduct("302", SELLING, "라뗴", 1000);
        ProductEntity product3 = createProduct("003", SELLING, "BB", 3500);
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
        ProductEntity product1 = createProduct("000", SELLING, "아메리카노", 4000);
        ProductEntity product2 = createProduct("001", SELLING, "라뗴", 1000);
        ProductEntity product3 = createProduct(targetProductNumber, SELLING, "BB", 3500);
        // 순차적으로 저장 될 것
        productRepository.saveAll(List.of(product1, product2, product3));


        // when
        String latestProductNumber = productRepository.findLatestProductNumber();


        // then
        assertThat(latestProductNumber).isEqualTo(targetProductNumber);
    }

    private static ProductEntity createProduct(String productNumber, ProductSellingStatus sellingStatus, String name, int price) {
        return ProductEntity.builder()
                .productNumber(productNumber)
                .sellingStatus(sellingStatus)
                .name(name)
                .price(price)
                .build();
    }
}
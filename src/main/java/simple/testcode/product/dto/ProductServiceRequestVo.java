package simple.testcode.product.dto;

import lombok.Builder;
import simple.testcode.product.domain.ProductEntity;
import simple.testcode.product.domain.ProductSellingStatus;

/**
 * 상품 서비스 요청 전용 vo
 * service layer 전용, 책임 분리, 의존성 분리, 확장 용이성
 */
public class ProductServiceRequestVo {

    private ProductSellingStatus sellingStatus;
    private String name;
    private int price;

    @Builder
    private ProductServiceRequestVo(ProductSellingStatus sellingStatus, String name, int price) {
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }

    public ProductEntity toEntity(String nextProductNumber) {
        return ProductEntity.builder()
                .productNumber(nextProductNumber)
                .sellingStatus(sellingStatus)
                .name(name)
                .price(price)
                .build();
    }
}

package simple.testcode.product.dto;

import lombok.Builder;
import lombok.Getter;
import simple.testcode.product.domain.ProductEntity;
import simple.testcode.product.domain.ProductSellingStatus;

/**
 * 상품 응답 전용 vo
 */
@Getter
public class ProductResponse {
    private Long id;

    private String productNumber;

    private ProductSellingStatus sellingStatus;

    private String name;

    private int price;

    @Builder
    private ProductResponse(Long id, String productNumber, ProductSellingStatus sellingStatus, String name, int price) {
        this.id = id;
        this.productNumber = productNumber;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }

    // entity -> vo
    public static ProductResponse of(ProductEntity product) {
        return ProductResponse.builder()
                .id(product.getId())
                .productNumber(product.getProductNumber())
                .sellingStatus(product.getSellingStatus())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }
}

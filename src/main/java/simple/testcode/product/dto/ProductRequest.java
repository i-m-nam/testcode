package simple.testcode.product.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import simple.testcode.product.domain.ProductSellingStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductRequest {

    @NotNull(message = "상품 판매상태는 필수입니다.")
    private ProductSellingStatus sellingStatus;

    @NotBlank(message = "상품 이름은 필수입니다.")
    private String name;

    @Positive(message = "상품 가격은 양수여야 합니다.")
    private int price;

    @Builder
    private ProductRequest(ProductSellingStatus sellingStatus, String name, int price) {
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }


    /**
     * controller >> service dto convert
     */
    public ProductServiceVo toServiceVo() {
        return ProductServiceVo.builder()
                .sellingStatus(sellingStatus)
                .name(name)
                .price(price)
                .build();
    }
}

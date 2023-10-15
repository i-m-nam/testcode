package simple.testcode.order.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
// com.fasterxml.jackson.databind.exc.InvalidDefinitionException 방지 위함
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderRequest {
    @NotEmpty(message = "상품 번호 리스틑 필수입니다.")
    private List<String> productNumbers;

    @Builder
    private OrderRequest(List<String> productNumbers) {
        this.productNumbers = productNumbers;
    }

    public OrderServiceVo toServiceVo() {
        return OrderServiceVo.builder()
                .productNumbers(productNumbers)
                .build();
    }
}

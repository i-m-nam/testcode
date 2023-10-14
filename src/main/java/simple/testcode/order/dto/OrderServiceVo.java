package simple.testcode.order.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Getter
// 서비스 전용 vo
// service layer 전용, 책임 분리, 의존성 분리, 확장 용이성
public class OrderServiceVo {

    private List<String> productNumbers;

    @Builder
    private OrderServiceVo(List<String> productNumbers) {
        this.productNumbers = productNumbers;
    }
}

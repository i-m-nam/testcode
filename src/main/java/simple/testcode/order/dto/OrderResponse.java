package simple.testcode.order.dto;

import lombok.Builder;
import lombok.Getter;
import simple.testcode.order.domain.OrderEntity;
import simple.testcode.product.dto.ProductResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OrderResponse {

    private Long id;

    private int totalPrice;
    private LocalDateTime registeredDateTime;

    // Order 클래스의 맴버 orderProducts 를 그대로 사용하는 것 보단 응답 로직 전용 객체 ProductResponse 로 바꿔서 선언
    private List<ProductResponse> products; // List<OrderProduct> orderProducts

    @Builder
    private OrderResponse(Long id, int totalPrice, LocalDateTime registeredDateTime, List<ProductResponse> products) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.registeredDateTime = registeredDateTime;
        this.products = products;
    }

    public static OrderResponse of(OrderEntity order) {
        return OrderResponse.builder()
                .id(order.getId())
                .totalPrice(order.getTotalPrice())
                .registeredDateTime(order.getRegisteredDateTime())
                // OrderProduct <<>> ProductResponse mapping 부분
                .products(order.getOrderProducts().stream()
                        .map(orderProduct -> ProductResponse.of(orderProduct.getProduct()))
                        .collect(Collectors.toList())
                )
                .build();
    }
}

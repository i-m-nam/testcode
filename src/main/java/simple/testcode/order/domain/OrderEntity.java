package simple.testcode.order.domain;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import simple.testcode.orderproduct.domain.OrderProductEntity;
import simple.testcode.product.domain.ProductEntity;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders") // db 예약어이여서 달리 명시함
@Entity
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private int totalPrice;

    private LocalDateTime registeredDateTime;

    // 연관관계 주인 (필드명으로 설정), Order 가 cud 될 때, 같이 반영 되도록 생명주기도 all 로 설정
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProductEntity> orderProducts = new ArrayList<>();

    private int calculateTotalPrice(List<ProductEntity> products) {
        return products.stream()
                .mapToInt(ProductEntity::getPrice)
                .sum();
    }


    @Builder
    public OrderEntity(List<ProductEntity> products, OrderStatus orderStatus, LocalDateTime registeredDateTime) {
        this.orderStatus = orderStatus;
        this.totalPrice = calculateTotalPrice(products);
        this.registeredDateTime = registeredDateTime;
        this.orderProducts = products.stream()
                .map(product -> new OrderProductEntity(this, product))
                .collect(Collectors.toList());
    }

    public static OrderEntity create(List<ProductEntity> products, LocalDateTime registeredDateTime) {
        return OrderEntity.builder()
                .orderStatus(OrderStatus.INIT)
                .products(products)
                .registeredDateTime(registeredDateTime)
                .build();
    }
}

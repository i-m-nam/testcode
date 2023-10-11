package simple.testcode.orderproduct.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import simple.testcode.order.domain.OrderEntity;
import simple.testcode.product.domain.ProductEntity;

import javax.persistence.*;

/**
 * 중간 테이블
 * Order - Product
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "orderProduct")
public class OrderProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ManyToOne 경우에는 필요한 시점에 데이터를 조회하기 위해, 보통 lazy fetch
    @ManyToOne(fetch = FetchType.LAZY)
    private OrderEntity order;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductEntity product;

    public OrderProductEntity(OrderEntity order, ProductEntity product) {
        this.order = order;
        this.product = product;
    }
}

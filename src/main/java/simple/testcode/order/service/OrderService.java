package simple.testcode.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simple.testcode.order.dao.OrderRepository;
import simple.testcode.order.domain.OrderEntity;
import simple.testcode.order.dto.OrderServiceVo;
import simple.testcode.order.dto.OrderResponse;
import simple.testcode.product.dao.ProductRepository;
import simple.testcode.product.domain.ProductEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public OrderResponse createOrder(OrderServiceVo orderCreateServiceVo, LocalDateTime registeredDateTime) {

        // Product
        List<String> productNumbers = orderCreateServiceVo.getProductNumbers();
        List<ProductEntity> products = findProductsBy(productNumbers);

        // Order
        OrderEntity order = OrderEntity.create(products, registeredDateTime);
        OrderEntity savedOrder = orderRepository.save(order);

        return OrderResponse.of(savedOrder);
    }

    /**
     * 상품번호로 상품 조회, 중복되는 상품번호 목록으로 주문 가능
     */
    private List<ProductEntity> findProductsBy(List<String> productNumbers) {
        List<ProductEntity> products = productRepository.findAllByProductNumberIn(productNumbers);
        Map<String, ProductEntity> productMap = products.stream()
                .collect(Collectors.toMap(ProductEntity::getProductNumber, p -> p));

        return productNumbers.stream()
                .map(productMap::get)
                .collect(Collectors.toList());
    }
}

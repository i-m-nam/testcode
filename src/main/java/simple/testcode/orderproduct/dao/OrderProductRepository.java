package simple.testcode.orderproduct.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import simple.testcode.orderproduct.domain.OrderProductEntity;
import simple.testcode.product.domain.ProductEntity;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProductEntity, Long> {
}

package simple.testcode.product.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import simple.testcode.product.domain.ProductEntity;
import simple.testcode.product.domain.ProductSellingStatus;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    /**
     * select *
     * from product
     * where selling_status in ('SELLING', 'HOLD');
     */
    List<ProductEntity> findAllBySellingStatusIn(List<ProductSellingStatus> sellingStatus);

    List<ProductEntity> findAllByProductNumberIn(List<String> productNumbers);

    @Query(nativeQuery = true, value = "select p.product_number from product p order by p.id desc limit 1")
    String findLatestProductNumber();
}

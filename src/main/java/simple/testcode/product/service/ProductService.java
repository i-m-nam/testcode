package simple.testcode.product.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simple.testcode.product.dao.ProductRepository;
import simple.testcode.product.domain.ProductEntity;
import simple.testcode.product.dto.ProductServiceVo;
import simple.testcode.product.dto.ProductResponse;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductResponse createProduct(ProductServiceVo productCreateServiceVo) {
        String nextProductNumber = this.createNextProductNumber();

        ProductEntity product = productCreateServiceVo.toEntity(nextProductNumber);
        ProductEntity savedProduct = productRepository.save(product);

        return ProductResponse.of(savedProduct);
    }

    /**
     * 다음 상품 번호 generator
     */
    private String createNextProductNumber() {
        String latestProductNumber = productRepository. findLatestProductNumber();
        if (latestProductNumber == null) {
            return "001";
        }

        int latestProductNumberInt = Integer.valueOf(latestProductNumber);
        int nextProductNumberInt = latestProductNumberInt + 1;

        // 9 -> 009, 10 -> 010
        return String.format("%03d", nextProductNumberInt);
    }
}


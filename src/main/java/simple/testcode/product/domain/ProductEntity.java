package simple.testcode.product.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productNumber;


    @Enumerated(EnumType.STRING)
    private ProductSellingStatus sellingStatus;

    private String name;

    private int price;

    public ProductEntity(String productNumber, ProductSellingStatus sellingStatus, String name, int price) {
        this.productNumber = productNumber;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }
}
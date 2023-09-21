package simple.testcode.product.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum ProductSellingStatus {

    SELLING("판매중"),
    HOLD("판매보류"),
    STOP("판매중지");

    private final String text;
}

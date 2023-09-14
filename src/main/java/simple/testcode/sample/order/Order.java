package simple.testcode.sample.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import simple.testcode.sample.beverage.Beverage;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class Order {

    private final LocalDateTime orderDateTime;
    private final List<Beverage> beverages;
}

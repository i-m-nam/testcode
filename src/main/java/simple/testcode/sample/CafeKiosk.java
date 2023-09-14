package simple.testcode.sample;

import lombok.Getter;
import simple.testcode.sample.beverage.Beverage;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CafeKiosk {

    private final List<Beverage> beverages = new ArrayList<>();


    public void add(Beverage beverage) {
        beverages.add(beverage);
    }

    /**
     * 음료 추가 (한 종류의 음료 여러 잔을 한 번에 담는 기능)
     */
    public void add(Beverage beverage, int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("음료는 1잔 이상 주문 할 수 있음.");
        }

        for (int i = 0; i < count; i++) {
            beverages.add(beverage);
        }
    }

    // 음료 한 잔 제거
    public void remove(Beverage beverage) {
        beverages.remove(beverage);
    }

    // 음료 전체 제거
    public void clear() {
        beverages.clear();
    }
}
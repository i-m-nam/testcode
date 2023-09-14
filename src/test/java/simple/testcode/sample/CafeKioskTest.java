package simple.testcode.sample;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import simple.testcode.sample.beverage.Americano;
import simple.testcode.sample.beverage.Latte;

import static org.assertj.core.api.Assertions.assertThat;

class CafeKioskTest {

    private CafeKiosk cafeKiosk;


    // [명확한 문장으로 표현] "음료 1개 추가 테스트" 라는 테스트명 보단
    // 도메인 정책, 용어를 사용한 명확한 문장을 추천함
    @DisplayName("음료 1잔 추가하면 주문 목록에 담긴다.")
    @Test
    void add() {
        // given
        cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();


        // when
        cafeKiosk.add(americano);


        // then
        assertThat(cafeKiosk.getBeverages().size()).isEqualTo(1);
        assertThat(cafeKiosk.getBeverages()).hasSize(1);

        assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }


    @DisplayName("추가한 음료 1 잔을 취소한다.")
    @Test
    void remove() {
        // given
        cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano, 1);

        assertThat(cafeKiosk.getBeverages().size()).isEqualTo(1);


        // when
        cafeKiosk.remove(americano);


        // then
        assertThat(cafeKiosk.getBeverages().size()).isEqualTo(0);
    }

    @DisplayName("추가한 음료 전체를 제거한다.")
    @Test
    void clear() {
        // given
        cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();

        cafeKiosk.add(americano);
        cafeKiosk.add(latte);

        assertThat(cafeKiosk.getBeverages().size()).isEqualTo(2);


        // when
        cafeKiosk.clear();


        // then
        assertThat(cafeKiosk.getBeverages().size()).isEqualTo(0);
        assertThat(cafeKiosk.getBeverages()).isEmpty();
    }
}
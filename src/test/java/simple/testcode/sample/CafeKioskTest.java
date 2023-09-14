package simple.testcode.sample;

import org.assertj.core.api.Assertions;
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

    // [해피케이스]
    @DisplayName("음료를 여러 잔 추가할 수 있다.")
    @Test
    void addSeveralBeverages() {
        // given
        cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();


        // when
        cafeKiosk.add(americano, 2);
        cafeKiosk.add(latte, 1);


        // then
        assertThat(cafeKiosk.getBeverages().get(0)).isEqualTo(americano);
        assertThat(cafeKiosk.getBeverages().get(1)).isEqualTo(americano);
        assertThat(cafeKiosk.getBeverages().get(2)).isEqualTo(latte);
    }

    // [예외케이스][경게값]
    @DisplayName("[예외 case] 음료를 1잔 미만으로 추가하면 예외가 발생한다.")
    @Test
    void addZeroBeverages() {
        // given
        cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();


        // when && // then (경계값 테스트)
        Assertions.assertThatThrownBy(() -> cafeKiosk.add(americano, 0))
                .isInstanceOf(IllegalArgumentException.class);

        Assertions.assertThatThrownBy(() -> cafeKiosk.add(americano, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("음료는 1잔 이상 주문 할 수 있음.");
    }
}
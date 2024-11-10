package store.domain.store.promotion;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import store.domain.Purchase;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PromotionImplTest {

    private static PromotionImpl promotionTwoPlusOne;
    private static PromotionImpl promotionOnePlusOne;

    @BeforeAll
    static void setUp() {
        promotionTwoPlusOne = new PromotionImpl("탄산2+1", 2, 1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31"));
        promotionOnePlusOne = new PromotionImpl("MD추천상품", 1, 1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31"));
    }

    @ParameterizedTest
    @DisplayName("프로모션은 기간에 해당하지 않으면 적용이 불가하다")
    @MethodSource("unPromotableDateProvider")
    void unPromotableDate(LocalDate date) {
        assertThat(promotionTwoPlusOne.available(date)).isFalse();
    }

    static List<LocalDate> unPromotableDateProvider() {
        return List.of(
                LocalDate.of(2021, 1, 1),
                LocalDate.of(2025, 5, 15),
                LocalDate.of(2026, 12, 25)
        );
    }

    @ParameterizedTest
    @DisplayName("프로모션은 기간에 해당하면 적용이 가능하다")
    @MethodSource("promotableDateProvider")
    void promotableDate(LocalDate date) {
        assertThat(promotionTwoPlusOne.available(date)).isTrue();
    }

    static List<LocalDate> promotableDateProvider() {
        return List.of(
                LocalDate.of(2024, 1, 2),
                LocalDate.of(2024, 5, 15),
                LocalDate.of(2024, 11, 30)
        );
    }

    @ParameterizedTest
    @DisplayName("프로모션은 증정해야할 보너스를 계산할 수 있다.")
    @CsvSource({
            "6, 2",
            "4, 1",
            "5, 1",
            "9, 3",
            "10, 3"
    })
    void test(int input, int expect) {
        assertThat(promotionTwoPlusOne.calculateBonusToGive(input)).isEqualTo(expect);
    }

    /**
     * 2+1 로직 프로모션 로직 테스트
     */

    @Test
    @DisplayName("7개의 프로모션 재고 보유중 10개 주문이 들어오면 6개만 프로모션 가능합니다. 이는 승인되지 않은 주문입니다")
    void promotionLogicTest1() {
        Purchase answer = new Purchase("콜라", false, 6, 4, 0);
        Purchase purchase = promotionTwoPlusOne.makePendingPromotionPurchase("콜라", 10, 7);
        assertThat(purchase).isEqualTo(answer);
    }

    @Test
    @DisplayName("6개의 프로모션 재고 보유중 6개 주문이 들어오면 전부 프로모션 대상입니다. 이는 승인된 주문입니다.")
    void promotionLogicTest2() {
        Purchase answer = new Purchase("콜라", true, 6, 0, 0);
        Purchase purchase = promotionTwoPlusOne.makePendingPromotionPurchase("콜라", 6, 6);
        assertThat(purchase).isEqualTo(answer);
    }

    @Test
    @DisplayName("6개의 프로모션 재고 보유중 5개 주문이 들어오면 보너스 지급이 가능할수도 있다. 이는 승인되지 않은 주문입니다.")
    void promotionLogicTest3() {
        Purchase answer = new Purchase("콜라", false, 5, 0, 1);
        Purchase purchase = promotionTwoPlusOne.makePendingPromotionPurchase("콜라", 5, 6);
        assertThat(purchase).isEqualTo(answer);
    }

    @Test
    @DisplayName("6개의 프로모션 재고 보유중 4개 주문이 들어오면 1개는 프로모션을 받지 못한다. 이는 승인되지 않은 주문입니다.")
    void promotionLogicTest4() {
        Purchase answer = new Purchase("콜라", false, 3, 1, 0);
        Purchase purchase = promotionTwoPlusOne.makePendingPromotionPurchase("콜라", 4, 6);
        assertThat(purchase).isEqualTo(answer);
    }

    /**
     * 1+1 로직 테스트
     */

    @Test
    @DisplayName("6개의 프로모션 재고 보유중 5개 주문이 들어오면 보너스 지급이 가능할수도 있다. 이는 승인되지 않은 주문입니다.")
    void promotionLogicTest5() {
        Purchase answer = new Purchase("콜라", false, 5, 0, 1);
        Purchase purchase = promotionOnePlusOne.makePendingPromotionPurchase("콜라", 5, 6);
        assertThat(purchase).isEqualTo(answer);
    }

    @Test
    @DisplayName("6개의 프로모션 재고 보유중 6개 주문이 들어오면 전부 프로모션 대상입니다. 이는 승인된 주문입니다.")
    void promotionLogicTest6() {
        Purchase answer = new Purchase("콜라", true, 6, 0, 0);
        Purchase purchase = promotionOnePlusOne.makePendingPromotionPurchase("콜라", 6, 6);
        assertThat(purchase).isEqualTo(answer);
    }


}

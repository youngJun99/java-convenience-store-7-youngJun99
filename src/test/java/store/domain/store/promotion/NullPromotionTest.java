package store.domain.store.promotion;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import store.domain.Purchase;


import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class NullPromotionTest {

    private static NullPromotion nullPromotion;

    @BeforeAll
    static void setUp() {
        nullPromotion = NullPromotion.getInstance();
    }

    @ParameterizedTest
    @DisplayName("NullPromotion은 어떤 날짜가 들어와도 available하지  않다")
    @MethodSource("localDateProvider")
    void alwaysUnAvailable(LocalDate date) {
        assertThat(nullPromotion.available(date)).isFalse();
    }

    static List<LocalDate> localDateProvider() {
        return List.of(
                LocalDate.of(2021, 1, 1),
                LocalDate.of(2022, 5, 15),
                LocalDate.of(2023, 12, 25)
        );
    }

    @Test
    @DisplayName("NullPromotion은 Promotion 주문이 들어와도 unPromotable 주문으로 인식하고 purchase를 만든다")
    void returnNormalPurchase() {
        Purchase dummyPurchase = new Purchase("콜라", true, 0, 10, 0);
        Purchase normalPurchase = nullPromotion.makePendingPromotionPurchase("콜라", 10, 10);
        assertThat(dummyPurchase).isEqualTo(normalPurchase);
    }

    @ParameterizedTest
    @DisplayName("Nullpromotion은 보너스 물량을 항상 0으로 반환한다")
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8})
    void bonusUngivable(int input) {
        assertThat(nullPromotion.calculateBonusToGive(input)).isEqualTo(0);
    }

    @Test
    @DisplayName("NullPromotion은 프로모션 명이 없다")
    void blankPromotionName() {
        assertThat(nullPromotion.getName()).isBlank();
    }

}

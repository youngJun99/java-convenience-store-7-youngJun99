package store.domain.store.factory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.store.promotion.PromotionImpl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class PromotionFactoryTest {

    private static List<PromotionImpl> answer;

    @BeforeAll
    static void setUp() {
        PromotionImpl promotion1 = new PromotionImpl("탄산2+1", 2, 1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31"));
        PromotionImpl promotion2 = new PromotionImpl("MD추천상품", 1, 1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31"));
        PromotionImpl promotion3 = new PromotionImpl("반짝할인", 1, 1, LocalDate.parse("2024-11-01"), LocalDate.parse("2024-11-30"));
        answer = List.of(promotion1, promotion2, promotion3);
    }

    @Test
    @DisplayName("PromotionFactory 기능 테스트")
    void promotionFactoryFunctionTest() throws IOException {
        PromotionFactory promotionFactory = PromotionFactory.getInstance();
        List<PromotionImpl> promotions = promotionFactory.loadPromotions();
        assertThat(promotions).isEqualTo(answer);
    }

}

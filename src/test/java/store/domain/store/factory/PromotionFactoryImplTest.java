package store.domain.store.factory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.store.promotion.Promotion;
import store.domain.store.promotion.PromotionImpl;
import store.factory.PromotionFactoryImpl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class PromotionFactoryImplTest {

    private static List<PromotionImpl> answer;

    @BeforeAll
    static void setUp() {
        PromotionImpl promotion1 = new PromotionImpl("탄산2+1", 2, 1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31"));
        PromotionImpl promotion2 = new PromotionImpl("MD추천상품", 1, 1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31"));
        PromotionImpl promotion3 = new PromotionImpl("반짝할인", 1, 1, LocalDate.parse("2024-11-01"), LocalDate.parse("2024-11-30"));
        answer = List.of(promotion1, promotion2, promotion3);
    }

    @Test
    @DisplayName("PromotionFactoryImpl 기능 테스트")
    void promotionFactoryFunctionTest() throws IOException {
        PromotionFactoryImpl promotionFactoryImpl = new PromotionFactoryImpl();
        List<Promotion> promotions = promotionFactoryImpl.loadPromotions("src/main/resources/promotions.md");
        assertThat(promotions).isEqualTo(answer);
    }

}

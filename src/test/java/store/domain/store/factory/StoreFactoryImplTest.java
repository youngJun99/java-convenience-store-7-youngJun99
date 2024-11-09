package store.domain.store.factory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.store.Store;
import store.domain.store.Product;
import store.domain.store.promotion.NullPromotion;
import store.domain.store.promotion.PromotionImpl;
import store.factory.PromotionFactoryImpl;
import store.factory.StoreFactoryImpl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class StoreFactoryImplTest {


    private static Store answer;

    @BeforeAll
    static void setUp() {
        PromotionImpl promotion1 = new PromotionImpl("탄산2+1", 2, 1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31"));
        PromotionImpl promotion2 = new PromotionImpl("MD추천상품", 1, 1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31"));
        PromotionImpl promotion3 = new PromotionImpl("반짝할인", 1, 1, LocalDate.parse("2024-11-01"), LocalDate.parse("2024-11-30"));

        List<Product> products = new ArrayList<>();
        products.add(new Product("콜라", promotion1, 1000, 10, 10));
        products.add(new Product("사이다", promotion1, 1000, 8, 7));
        products.add(new Product("오렌지주스", promotion2, 1800, 9, 0));
        products.add(new Product("탄산수", promotion1, 1200, 5, 0));
        products.add(new Product("물", NullPromotion.getInstance(), 500, 0, 10));
        products.add(new Product("비타민워터", NullPromotion.getInstance(), 1500, 0, 6));
        products.add(new Product("감자칩", promotion3, 1500, 5, 5));
        products.add(new Product("초코바", promotion2, 1200, 5, 5));
        products.add(new Product("에너지바", NullPromotion.getInstance(), 2000, 0, 5));
        products.add(new Product("정식도시락", NullPromotion.getInstance(), 6400, 0, 8));
        products.add(new Product("컵라면", promotion2, 1700, 1, 10));

        answer = new Store(products);
    }


    @Test
    @DisplayName("StoreFactoryImpl 기능 테스트")
    void ConvenienceStoreFactoryFunctionTest() throws IOException {
        PromotionFactoryImpl promotionFactoryImpl = PromotionFactoryImpl.getInstance();
        StoreFactoryImpl storeFactoryImpl = new StoreFactoryImpl(promotionFactoryImpl.loadPromotions());
        Store store = storeFactoryImpl.createStore();
        assertThat(store).isEqualTo(answer);
    }

}

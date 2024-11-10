package store.factory;

import store.domain.promotion.Promotion;

import java.io.IOException;
import java.util.List;

public interface PromotionFactory {

    List<Promotion> loadPromotions(String promotionPath) throws IOException;
}

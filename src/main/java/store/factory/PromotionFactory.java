package store.factory;

import store.domain.promotion.Promotion;

import java.io.IOException;
import java.util.List;

public interface PromotionFactory {

    List<Promotion> createPromotions(String promotionPath) throws IOException;
}

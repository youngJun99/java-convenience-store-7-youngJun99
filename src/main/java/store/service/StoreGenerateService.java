package store.service;

import store.domain.store.Store;
import store.domain.store.promotion.PromotionImpl;
import store.factory.PromotionFactoryImpl;
import store.factory.StoreFactoryImpl;

import java.io.IOException;
import java.util.List;

public class StoreGenerateService {

    private final StoreFactoryImpl storeFactoryImpl;
    private final PromotionFactoryImpl promotionFactoryImpl;

    public StoreGenerateService(StoreFactoryImpl storeFactoryImpl, PromotionFactoryImpl promotionFactoryImpl) {
        this.storeFactoryImpl = storeFactoryImpl;
        this.promotionFactoryImpl = promotionFactoryImpl;
    }

    public Store generateStore() throws IOException {
        List<PromotionImpl> promotions = promotionFactoryImpl.loadPromotions();
        return storeFactoryImpl.createStore(promotions);
    }
}

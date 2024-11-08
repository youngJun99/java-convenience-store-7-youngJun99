package store.service;

import store.domain.store.Store;
import store.domain.store.factory.PromotionFactory;
import store.domain.store.factory.StoreFactory;

import java.io.IOException;

public class StoreGenerateService {

    public Store generateStore() throws IOException {
        PromotionFactory promotionFactory = PromotionFactory.getInstance();
        StoreFactory storeFactory = new StoreFactory(promotionFactory.loadPromotions());
        return storeFactory.createStore();
    }
}

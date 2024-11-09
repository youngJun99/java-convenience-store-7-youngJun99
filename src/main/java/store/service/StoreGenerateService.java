package store.service;

import store.domain.store.Store;
import store.factory.StoreFactory;

import java.io.IOException;

public class StoreGenerateService {

    private static final String promotionPath = "src/main/resources/promotions.md";
    private static final String productsPath = "src/main/resources/products.md";

    private final StoreFactory storeFactory;

    public StoreGenerateService(StoreFactory storeFactory) {
        this.storeFactory = storeFactory;
    }

    public Store generateStore() throws IOException {
        return storeFactory.createStore(productsPath,promotionPath);
    }
}

package store.service;

import store.constants.InputErrors;
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

    public Store generateStore() {
        try {
            return storeFactory.createStore(productsPath, promotionPath);
        } catch (IOException e) {
            System.err.println(InputErrors.STORE_BUILDING_ERROR);
            System.exit(1);
            return null;
        }
    }
}

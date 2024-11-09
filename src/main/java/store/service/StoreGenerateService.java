package store.service;

import store.domain.store.Store;
import store.domain.store.factory.StoreFactory;

import java.io.IOException;

public class StoreGenerateService {

    private final StoreFactory storeFactory;

    public StoreGenerateService(StoreFactory storeFactory) {
        this.storeFactory = storeFactory;
    }

    public Store generateStore() throws IOException {
        storeFactory.createStore();
    }
}

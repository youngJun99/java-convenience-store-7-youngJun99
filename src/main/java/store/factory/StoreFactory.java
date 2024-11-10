package store.factory;

import store.domain.store.Store;

import java.io.IOException;

public interface StoreFactory {

    public Store createStore(String productPath, String promotionPath) throws IOException;
}

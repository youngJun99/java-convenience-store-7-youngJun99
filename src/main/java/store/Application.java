package store;


import store.config.AppConfiguration;
import store.controller.StoreController;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        AppConfiguration appConfig = new AppConfiguration();

        StoreController controller = appConfig.storeController();

        controller.run();
    }
}

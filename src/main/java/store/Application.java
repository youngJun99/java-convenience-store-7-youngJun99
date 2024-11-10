package store;

import store.config.AppConfiguration;
import store.controller.StoreController;

public class Application {
    public static void main(String[] args) {
        AppConfiguration appConfig = new AppConfiguration();

        StoreController controller = appConfig.storeController();

        controller.run();
    }
}

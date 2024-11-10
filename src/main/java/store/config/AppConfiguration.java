package store.config;

import store.controller.StoreController;
import store.factory.PromotionFactoryImpl;
import store.factory.StoreFactoryImpl;
import store.handler.InputHandler;
import store.handler.InputValidator;
import store.handler.OutputHandler;
import store.service.StoreGenerateService;
import store.service.StoreOrderService;
import store.service.StoreRecieptService;
import store.view.InputView;
import store.view.OutputView;

public class AppConfiguration {

    public StoreController storeController() {
        InputHandler inputHandler = new InputHandler(new InputValidator(), new InputView());
        OutputHandler outputHandler = new OutputHandler(new OutputView());
        return storeController(inputHandler, outputHandler);
    }

    private StoreController storeController(InputHandler inputHandler, OutputHandler outputHandler) {
        StoreGenerateService storeGenerateService = storeGenerateService();
        StoreOrderService storeOrderService = storeOrderService(inputHandler, outputHandler);
        StoreRecieptService storeRecieptService = storeRecieptService(outputHandler);
        return new StoreController(storeGenerateService, storeOrderService, storeRecieptService);
    }

    private StoreGenerateService storeGenerateService() {
        return new StoreGenerateService(new StoreFactoryImpl(new PromotionFactoryImpl()));
    }

    private StoreOrderService storeOrderService(InputHandler inputHandler, OutputHandler outputHandler) {
        return new StoreOrderService(inputHandler, outputHandler);
    }

    private StoreRecieptService storeRecieptService(OutputHandler outputHandler) {
        return new StoreRecieptService(outputHandler);
    }


}

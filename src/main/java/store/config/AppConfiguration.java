package store.config;

import store.controller.StoreController;
import store.domain.membership.DefaultMembership;
import store.factory.PromotionFactoryImpl;
import store.factory.StoreFactoryImpl;
import store.handler.InputHandler;
import store.handler.InputValidator;
import store.handler.OutputHandler;
import store.service.StoreGenerateService;
import store.service.StoreOrderService;
import store.service.StoreReceiptService;
import store.view.InputView;
import store.view.OutputView;

public class AppConfiguration {

    public StoreController storeController() {
        InputHandler inputHandler = new InputHandler(new InputValidator(), new InputView());
        OutputHandler outputHandler = new OutputHandler(new OutputView());
        return storeController(inputHandler, outputHandler);
    }

    private StoreController storeController(InputHandler inputHandler, OutputHandler outputHandler) {
        StoreGenerateService storeGenerateService = new StoreGenerateService(new StoreFactoryImpl(new PromotionFactoryImpl()));
        StoreOrderService storeOrderService = new StoreOrderService(inputHandler, outputHandler);
        StoreReceiptService storeReceiptService = new StoreReceiptService(inputHandler, outputHandler, new DefaultMembership());
        return new StoreController(storeGenerateService, storeOrderService, storeReceiptService);
    }
}

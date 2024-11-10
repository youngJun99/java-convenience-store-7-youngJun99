package store.config;

import store.controller.StoreController;
import store.domain.store.membership.DefaultMembership;
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

    /**
     * 이번에는 Config를 사용해서 필요하지 않다면 별도의 싱글톤 설계를 하지 않았습니다.
     */
    public StoreController storeController() {
        InputHandler inputHandler = new InputHandler(new InputValidator(), new InputView());
        OutputHandler outputHandler = new OutputHandler(new OutputView());
        return storeController(inputHandler, outputHandler);
    }

    private StoreController storeController(InputHandler inputHandler, OutputHandler outputHandler) {
        StoreGenerateService storeGenerateService = new StoreGenerateService(new StoreFactoryImpl(new PromotionFactoryImpl()));
        StoreOrderService storeOrderService = new StoreOrderService(inputHandler, outputHandler);
        StoreRecieptService storeRecieptService = new StoreRecieptService(inputHandler, outputHandler, new DefaultMembership());
        return new StoreController(storeGenerateService, storeOrderService, storeRecieptService);
    }
}

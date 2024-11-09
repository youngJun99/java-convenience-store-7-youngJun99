package store.config;

import store.dto.ReceiptDto;
import store.factory.PromotionFactoryImpl;
import store.factory.StoreFactoryImpl;
import store.handler.InputHandler;
import store.handler.InputValidator;
import store.service.StoreGenerateService;
import store.service.StoreOrderService;

//서비스와 필요한 Validator를 박아서 주자
public class AppConfiguration {





    private StoreGenerateService storeGenerateService() {
        return new StoreGenerateService(new StoreFactoryImpl(new PromotionFactoryImpl()));
    }

    private StoreOrderService storeOrderService() {
        return  new StoreOrderService(new InputHandler(new InputValidator()));
    }




}

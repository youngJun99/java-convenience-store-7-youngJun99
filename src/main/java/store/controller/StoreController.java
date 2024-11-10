package store.controller;

import store.domain.store.Store;
import store.dto.ProductReceiptDto;
import store.service.StoreGenerateService;
import store.service.StoreOrderService;
import store.service.StoreRecieptService;

import java.util.List;

public class StoreController {

    private final StoreGenerateService storeGenerateService;
    private final StoreOrderService storeOrderService;
    private final StoreRecieptService storeRecieptService;

    public StoreController(StoreGenerateService storeGenerateService, StoreOrderService storeOrderService, StoreRecieptService storeRecieptService) {
        this.storeGenerateService = storeGenerateService;
        this.storeOrderService = storeOrderService;
        this.storeRecieptService = storeRecieptService;
    }

    public void run() {
        Store store = storeGenerateService.generateStore();
        boolean keepBuying = true;
        while (keepBuying) {
            List<ProductReceiptDto> productReceiptDtos = storeOrderService.takeOrderFrom(store);
            storeRecieptService.printReceipt(productReceiptDtos);
            keepBuying = storeOrderService.keepShopping();
        }
    }

}

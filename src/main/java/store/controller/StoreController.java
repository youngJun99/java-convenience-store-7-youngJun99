package store.controller;

import store.domain.store.Store;
import store.dto.ProductReceiptDto;
import store.service.StoreGenerateService;
import store.service.StoreOrderService;
import store.service.StoreReceiptService;

import java.util.List;

public class StoreController {

    private final StoreGenerateService storeGenerateService;
    private final StoreOrderService storeOrderService;
    private final StoreReceiptService storeReceiptService;

    public StoreController(StoreGenerateService storeGenerateService, StoreOrderService storeOrderService, StoreReceiptService storeReceiptService) {
        this.storeGenerateService = storeGenerateService;
        this.storeOrderService = storeOrderService;
        this.storeReceiptService = storeReceiptService;
    }

    public void run() {
        Store store = storeGenerateService.generateStore();
        boolean keepBuying = true;
        while (keepBuying) {
            List<ProductReceiptDto> productReceiptDtos = storeOrderService.takeOrderFrom(store);
            storeReceiptService.printReceipt(productReceiptDtos);
            keepBuying = storeOrderService.keepShopping();
        }
    }

}

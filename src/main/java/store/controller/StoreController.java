package store.controller;

import camp.nextstep.edu.missionutils.DateTimes;
import store.domain.Purchase;
import store.domain.store.Store;
import store.dto.ReceiptDto;
import store.service.StoreGenerateService;
import store.service.StoreOrderService;
import store.service.StoreRecieptService;

import java.io.IOException;
import java.time.LocalDate;
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
        while (keepBuying){
            List<ReceiptDto> receiptDtos = storeOrderService.takeOrderFrom(store);
            storeRecieptService.printReceipt(receiptDtos);
            keepBuying = storeOrderService.keepShopping();
        }
    }

}

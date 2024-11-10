package store.service;

import camp.nextstep.edu.missionutils.DateTimes;
import store.domain.ShoppingCart;
import store.domain.store.Store;
import store.dto.*;
import store.handler.InputHandler;
import store.handler.OutputHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static store.handler.InputHandler.MAX_RETRIES;


public class StoreOrderService {

    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;

    public StoreOrderService(InputHandler inputHandler, OutputHandler outputHandler) {
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
    }


    public List<ReceiptDto> takeOrderFrom(Store store) {
        List<ProductInventoryDto> currentInventory = store.showStoreInventory();
        outputHandler.printInventoryStatus(currentInventory);

        ShoppingCart purchases = validateOverInventoryOrder(store);
        LocalDateTime orderedDateTime = DateTimes.now();
        LocalDate orderedDate = orderedDateTime.toLocalDate();

        if (purchases.isNotApproved()) {
            List<OrderApproveRequestDto> unapproved = purchases.getUnApprovedPurchases();
            List<OrderApproveResponseDto> response = unapproved.stream()
                    .map(inputHandler::requestApproval)
                    .toList();
            purchases.processResponses(response);
        }
        return store.executeOrder(purchases, orderedDate);
    }

    public boolean keepShopping() {
        return inputHandler.requestContinueShopping();
    }

    private ShoppingCart validateOverInventoryOrder(Store store) {
        int retryCount = 0;

        while (retryCount < MAX_RETRIES) {
            try {
                List<OrderDto> customerOrder = inputHandler.requestInputOrder();
                LocalDateTime orderedDateTime = DateTimes.now();
                LocalDate orderedTime = orderedDateTime.toLocalDate();
                return store.putInCart(customerOrder, orderedTime);
            } catch (IllegalArgumentException e) {  // 예외 타입을 구체적으로 설정
                retryCount++;
                System.out.println(e.getMessage());
            }
        }
        System.out.println("최대 재시도 횟수에 도달했습니다. 애플리케이션을 종료합니다.");
        System.exit(1);
        return null;
    }
}

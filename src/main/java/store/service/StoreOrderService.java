package store.service;

import camp.nextstep.edu.missionutils.DateTimes;
import store.constants.InputErrors;
import store.domain.shoppingcart.ShoppingCart;
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


    public List<ProductReceiptDto> takeOrderFrom(Store store) {
        List<ProductInventoryDto> currentInventory = store.showStoreInventory();
        outputHandler.printInventoryStatus(currentInventory);

        ShoppingCart purchases = validateOverInventoryOrder(store);
        LocalDateTime orderedDateTime = DateTimes.now();
        LocalDate orderedDate = orderedDateTime.toLocalDate();

        if (purchases.isNotApproved()) {
            requestAndProcessResponse(purchases);
        }
        return store.executeOrder(purchases, orderedDate);
    }

    private void requestAndProcessResponse(ShoppingCart purchases) {
        List<OrderApproveRequestDto> unapproved = purchases.getUnApprovedPurchases();
        List<OrderApproveResponseDto> response = unapproved.stream()
                .map(inputHandler::requestApproval)
                .toList();
        purchases.processResponses(response);
    }

    public boolean keepShopping() {
        return inputHandler.requestContinueShopping();
    }

    private ShoppingCart validateOverInventoryOrder(Store store) {
        int retryCount = 0;

        while (retryCount < MAX_RETRIES) {
            try {
                return getShoppingCart(store);
            } catch (IllegalArgumentException e) {
                retryCount++;
                System.out.println(e.getMessage());
            }
        }
        System.err.println(InputErrors.EXCEED_MAX_RETRY_LIMIT);
        System.exit(1);
        return null;
    }

    private ShoppingCart getShoppingCart(Store store) {
        List<OrderDto> customerOrder = inputHandler.requestInputOrder();
        LocalDateTime orderedDateTime = DateTimes.now();
        LocalDate orderedTime = orderedDateTime.toLocalDate();
        return store.putInCart(customerOrder, orderedTime);
    }
}

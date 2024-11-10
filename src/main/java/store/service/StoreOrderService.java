package store.service;

import store.domain.ShoppingCart;
import store.domain.store.Store;
import store.dto.*;
import store.handler.InputHandler;
import store.handler.OutputHandler;

import java.time.LocalDate;
import java.util.List;


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
        List<OrderDto> customerOrder = inputHandler.requestInputOrder();
        LocalDate orderedTime = LocalDate.now();
        ShoppingCart purchases = store.putInCart(customerOrder,orderedTime);
        if(purchases.isNotApproved()) {
            List<OrderApproveRequestDto> unapproved = purchases.getUnApprovedPurchases();
            List<OrderApproveResponseDto> response = unapproved.stream()
                    .map(inputHandler::requestApproval)
                    .toList();
            purchases.processResponses(response);
        }
        return store.executeOrder(purchases,orderedTime);
   }

}

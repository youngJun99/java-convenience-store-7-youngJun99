package store.domain.store;

import camp.nextstep.edu.missionutils.DateTimes;
import store.constants.InputErrors;
import store.dto.OrderDto;
import store.dto.OrderSheetDto;
import store.domain.Purchase;
import store.domain.ShoppingCart;
import store.dto.ReceiptDto;
import store.dto.ReceiptSheetDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ConvenienceStore {

    private final List<Product> products;

    public ConvenienceStore(List<Product> products) {

        this.products = products;
    }

    public ShoppingCart putInCart(OrderSheetDto orderSheetDto) {
        validateOrderProducts(orderSheetDto);
        LocalDate orderedTime = orderSheetDto.orderedTime();
        return new ShoppingCart(orderSheetDto.orderDtos().stream()
                .map(orderDto -> {
                    return getPurchase(orderDto, orderedTime);
                })
                .toList());

    }

    public List<ReceiptDto> executeOrder(ShoppingCart shoppingCart, LocalDate orderTime) {
        List<Purchase> purchases = shoppingCart.getPurchases();
        return purchases.stream()
                .flatMap(purchase -> products.stream()
                        .filter(product -> product.getProductName().equals(purchase.getProductName()))
                        .map(product -> product.executePurchase(purchase,orderTime))
                )
                .toList();
    }

    private Purchase getPurchase(OrderDto orderDto, LocalDate orderedTime) {
        Product matchingProduct = products.stream()
                .filter(product -> product.getProductName().equalsIgnoreCase(orderDto.itemName().getName()))
                .findFirst()
                .get();

        return matchingProduct.makePendingPurchase(orderDto.purchaseQuantity().getQuantity(), orderedTime);
    }


    private void validateOrderProducts(OrderSheetDto orderSheetDto) {
        boolean allItemsAvailable = orderSheetDto.orderDtos().stream()
                .allMatch(orderDto -> products.stream()
                        .anyMatch(product -> product.getProductName().equals(orderDto.itemName().getName()))
                );
        if (!allItemsAvailable) {
            throw new IllegalArgumentException(InputErrors.NO_SUCH_ITEM.getMessage());
        }
    }


}

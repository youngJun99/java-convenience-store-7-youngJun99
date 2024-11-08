package store.domain.store;

import store.constants.InputErrors;
import store.domain.Order;
import store.domain.OrderSheet;
import store.domain.Purchase;
import store.domain.ShoppingCart;

import java.time.LocalDateTime;
import java.util.List;

public class ConvenienceStore {

    private final List<Product> products;

    public ConvenienceStore(List<Product> products) {

        this.products = products;
    }

    public ShoppingCart putInCart(OrderSheet orderSheet) {
        validateOrderProducts(orderSheet);
        LocalDateTime orderedTime = orderSheet.orderedTime();
        return new ShoppingCart(orderSheet.orders().stream()
                .map(order -> {
                    return getPurchase(order, orderedTime);
                })
                .toList());

    }

    private Purchase getPurchase(Order order, LocalDateTime orderedTime) {
        Product matchingProduct = products.stream()
                .filter(product -> product.getProductName().equalsIgnoreCase(order.itemName().getName()))
                .findFirst()
                .get();

        return matchingProduct.makePendingPurchase(order.purchaseQuantity().getQuantity(), orderedTime);
    }


    private void validateOrderProducts(OrderSheet orderSheet) {
        boolean allItemsAvailable = orderSheet.orders().stream()
                .allMatch(order -> products.stream()
                        .anyMatch(product -> product.getProductName().equals(order.itemName().getName()))
                );
        if (!allItemsAvailable) {
            throw new IllegalArgumentException(InputErrors.NO_SUCH_ITEM.getMessage());
        }
    }


}

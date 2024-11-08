package store.domain.store;

import store.constants.InputErrors;
import store.dto.OrderDto;
import store.dto.OrderSheetDto;
import store.domain.Purchase;
import store.domain.ShoppingCart;
import store.dto.ReceiptDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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
                        .map(product -> product.executePurchase(purchase, orderTime))
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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ConvenienceStore that = (ConvenienceStore) object;
        return Objects.equals(products, that.products);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(products);
    }
}

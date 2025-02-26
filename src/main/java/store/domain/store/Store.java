package store.domain.store;

import store.constants.InputErrors;
import store.dto.OrderDto;
import store.domain.shoppingcart.Purchase;
import store.domain.shoppingcart.ShoppingCart;
import store.dto.ProductInventoryDto;
import store.dto.ProductReceiptDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Store {

    private final List<Product> products;

    public Store(List<Product> products) {

        this.products = products;
    }

    public List<ProductInventoryDto> showStoreInventory() {
        return products.stream()
                .map(Product::showInventory)
                .toList();
    }

    public ShoppingCart putInCart(List<OrderDto> orders, LocalDate orderedTime) {
        validateOrderProducts(orders);
        return new ShoppingCart(orders.stream()
                .map(orderDto -> {
                    return getPurchase(orderDto, orderedTime);
                })
                .toList());

    }

    private void validateOrderProducts(List<OrderDto> orders) {
        boolean notAllItemIsAvailable = !orders.stream()
                .allMatch(orderDto -> products.stream()
                        .anyMatch(product -> product.getProductName().equals(orderDto.productName()))
                );
        if (notAllItemIsAvailable) {
            throw new IllegalArgumentException(InputErrors.NO_SUCH_ITEM.getMessage());
        }
    }

    private Purchase getPurchase(OrderDto orderDto, LocalDate orderedTime) {
        Product matchingProduct = products.stream()
                .filter(product -> product.getProductName().equalsIgnoreCase(orderDto.productName()))
                .findFirst()
                .get();

        return matchingProduct.makePendingPurchase(orderDto.purchaseQuantity(), orderedTime);
    }

    public List<ProductReceiptDto> executeOrder(ShoppingCart shoppingCart, LocalDate orderTime) {
        List<Purchase> purchases = shoppingCart.getPurchases();
        return purchases.stream()
                .flatMap(purchase -> products.stream()
                        .filter(product -> product.getProductName().equals(purchase.getProductName()))
                        .map(product -> product.executePurchase(purchase, orderTime))
                )
                .toList();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Store that = (Store) object;
        return Objects.equals(products, that.products);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(products);
    }
}

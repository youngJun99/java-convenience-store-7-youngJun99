package store.domain.shoppingcart;

import store.dto.OrderApproveRequestDto;
import store.dto.OrderApproveResponseDto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ShoppingCart {

    private final List<Purchase> purchases;

    public ShoppingCart(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    public boolean isNotApproved() {
        return !purchases.stream()
                .allMatch(Purchase::getApproved);
    }

    public List<OrderApproveRequestDto> getUnApprovedPurchases() {
        return purchases.stream()
                .filter(purchase -> !purchase.getApproved())
                .map(Purchase::makeApproveRequest)
                .collect(Collectors.toList());
    }

    public void processResponses(List<OrderApproveResponseDto> responses) {
        responses.forEach(response ->
                purchases.stream()
                        .filter(purchase -> purchase.getProductName().equals(response.productName()))
                        .findFirst()
                        .ifPresent(purchase -> purchase.processResponse(response))
        );
    }

    public List<Purchase> getPurchases() {
        return Collections.unmodifiableList(purchases);
    }
}

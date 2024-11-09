package store.domain;

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

    public boolean isAllApproved() {
        return purchases.stream()
                .allMatch(Purchase::getApproved);
    }

    public List<OrderApproveRequestDto> printUnApprovedPurchases() {
        return purchases.stream()
                .filter(purchase -> !purchase.getApproved()) // 승인되지 않은 Purchase만 필터링
                .map(purchase -> purchase.makeConfirmRequest()) // 각 Purchase에서 makeRequest() 호출하여 OrderApproveRequestDto 생성
                .collect(Collectors.toList()); // 결과를 리스트로 반환
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

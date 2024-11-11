package store.domain.store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.constants.InputErrors;
import store.domain.shoppingcart.Purchase;
import store.domain.promotion.Promotion;
import store.domain.promotion.PromotionImpl;
import store.dto.ProductInventoryDto;
import store.dto.ProductReceiptDto;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class ProductTest {

    private Product coke;
    private LocalDate promotableDate = LocalDate.of(2024, 1, 14);
    private LocalDate unPromotableDate = LocalDate.of(2025, 1, 14);

    @BeforeEach
    void setUp() {
        Promotion promotion = new PromotionImpl("탄산2+1", 2, 1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31"));
        coke = new Product("콜라", promotion, 1000, 6, 3);
    }

    @Test
    @DisplayName("Product는 재고 상황을 보여주는 Dto를 제공한다.")
    void showInventory() {
        ProductInventoryDto answer = new ProductInventoryDto("콜라", 1000, 6, 3, "탄산2+1");
        ProductInventoryDto productInventoryDto = coke.showInventory();
        assertThat(productInventoryDto).isEqualTo(answer);
    }

    @ParameterizedTest
    @DisplayName("Product는 보유 재고 이상을 장바구니에 담으려고 하면 에러가 난다")
    @ValueSource(ints = {10, 11, 12, 14})
    void overProductInventory(int orderAmount) {
        assertThatThrownBy(() -> {
            coke.makePendingPurchase(orderAmount, promotableDate);
        }).hasMessageContaining(InputErrors.INVENTORY_SHORTAGE.getMessage());
    }

    /**
     * 쟤고 소진 테스트
     * <p>
     * 서비스단을 통해서 승인되고 정제된 주문만 받게됩니다.
     * <p>
     * 현재 테스트 대상은,
     * {상품명: 콜라} ,{프로모션: 탄산2+1}, {가격: 1000}, {프로모션 재고:6}, {일반 재고:3} 입니다.
     */
    @Test
    @DisplayName("5개의 프로모션 주문 요청이 들어온 경우")
    void promotionOrderLogicTest1() {
        //given
        Purchase purchase = new Purchase("콜라", true, 5, 0, 0);

        //when
        coke.executePurchase(purchase, promotableDate);

        //then
        ProductInventoryDto answer = new ProductInventoryDto("콜라", 1000, 1, 3, "탄산2+1");
        assertThat(coke.showInventory()).isEqualTo(answer);
    }

    @Test
    @DisplayName("7개의 프로모션 주문 요청이 들어온 경우")
    void promotionOrderLogicTest2() {
        //given
        Purchase purchase = new Purchase("콜라", true, 7, 0, 0);

        //when
        coke.executePurchase(purchase, promotableDate);

        //then
        ProductInventoryDto answer = new ProductInventoryDto("콜라", 1000, 0, 2, "탄산2+1");
        assertThat(coke.showInventory()).isEqualTo(answer);
    }

    @Test
    @DisplayName("6개의 일반 주문 요청이 들어온 경우")
    void unPromotedOrderLogicTest3() {
        //given
        Purchase purchase = new Purchase("콜라", true, 0, 6, 0);

        //when
        coke.executePurchase(purchase, unPromotableDate);

        //then
        ProductInventoryDto answer = new ProductInventoryDto("콜라", 1000, 3, 0, "탄산2+1");
        assertThat(coke.showInventory()).isEqualTo(answer);
    }

    @Test
    @DisplayName("2개의 일반 주문 요청이 들어온 경우")
    void unPromotedOrderLogicTest4() {
        //given
        Purchase purchase = new Purchase("콜라", true, 0, 2, 0);

        //when
        coke.executePurchase(purchase, unPromotableDate);

        //then
        ProductInventoryDto answer = new ProductInventoryDto("콜라", 1000, 6, 1, "탄산2+1");
        assertThat(coke.showInventory()).isEqualTo(answer);
    }

    /**
     * 영수증 발급 테스트
     */
    @Test
    void orderReceiptTest1() {
        //given
        Purchase purchase = new Purchase("콜라", true, 6, 2, 0);

        //when
        ProductReceiptDto productReceiptDto = coke.executePurchase(purchase, promotableDate);
        //then
        ProductReceiptDto answer = new ProductReceiptDto("콜라", 1000, 8, 2);
        assertThat(productReceiptDto).isEqualTo(answer);
    }

    @Test
    void orderReceiptTest2() {
        //given
        Purchase purchase = new Purchase("콜라", true, 0, 3, 0);

        //when
        ProductReceiptDto productReceiptDto = coke.executePurchase(purchase, promotableDate);
        //then
        ProductReceiptDto answer = new ProductReceiptDto("콜라", 1000, 3, 0);
        assertThat(productReceiptDto).isEqualTo(answer);
    }

}

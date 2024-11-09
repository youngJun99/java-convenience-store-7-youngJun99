package store.domain.store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.constants.InputErrors;
import store.domain.Purchase;
import store.domain.ShoppingCart;
import store.dto.OrderDto;
import store.dto.ProductInventoryDto;
import store.dto.ReceiptDto;
import store.factory.PromotionFactoryImpl;
import store.factory.StoreFactory;
import store.factory.StoreFactoryImpl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class StoreTest {

    private Store testStore;
    private ShoppingCart testShoppingCart;

    /**
     * 테스트 스토어 구성요소
     * 콜라,1000,10,10,탄산2+1
     * 오렌지주스,1800,9,0,MD추천상품
     */
    @BeforeEach
    void setStore() throws IOException {
        StoreFactory storeFactory = new StoreFactoryImpl(new PromotionFactoryImpl());
        testStore = storeFactory.createStore("src/test/java/store/domain/store/dummy/products.md",
                "src/test/java/store/domain/store/dummy/promotions.md");
    }


    @BeforeEach
    void setShoppingCart() {
        Purchase cokePurchase = new Purchase("콜라",true,3,0,0);
        Purchase orangeJuicePurchase = new Purchase("오렌지주스",true,2,0,0);
        testShoppingCart = new ShoppingCart(List.of(cokePurchase,orangeJuicePurchase));
    }

    @Test
    @DisplayName("Store는 요청에 따라 재고의 정보를 담고 있는 Dto를 반환한다")
    void showStoreTest() {
        //given
        ProductInventoryDto coke = new ProductInventoryDto("콜라",1000,10,10,"탄산2+1");
        ProductInventoryDto orangeJuice = new ProductInventoryDto("오렌지주스",1800,9,0,"MD추천상품");
        List<ProductInventoryDto> answer = List.of(coke,orangeJuice);

        //when
        List<ProductInventoryDto> shownInventory = testStore.showStoreInventory();

        //then
        assertThat(shownInventory).isEqualTo(answer);
    }

    @Test
    @DisplayName("Store는 보유하지 않는 재고에 대해서 주문이 들어오면 에러가 난다.")
    void noSuchInventoryTest() {
        //given
        List<OrderDto> orderDtos = List.of( new OrderDto("감자칩",2));

        assertThatThrownBy(()->{
            testStore.putInCart(orderDtos, LocalDate.now());
        }).hasMessageContaining(InputErrors.NO_SUCH_ITEM.getMessage());
    }

    @Test
    @DisplayName("Store는 주문 요청에 대해서 장바구니를 만들어서 반환한다")
    void putInCartTest() {
        //given
        OrderDto cokeOrder = new OrderDto("콜라",3);
        OrderDto orangeJuiceOrder = new OrderDto("오렌지주스",2);
        List<OrderDto> order = List.of(cokeOrder,orangeJuiceOrder);
        LocalDate promotable = LocalDate.of(2024,3,3);

        //when
        ShoppingCart shoppingCart = testStore.putInCart(order,promotable);

        //then
        assertThat(shoppingCart.getPurchases()).isEqualTo(testShoppingCart.getPurchases());
    }

    @Test
    @DisplayName("Store는 확정된 장바구니를 넣으면 영수증을 반환한다")
    void executeOrder() {
        //given
        ReceiptDto cokeReceipt = new ReceiptDto("콜라",1000,3,1);
        ReceiptDto orangeJuiceReceipt = new ReceiptDto("오렌지주스",1800,2,1);
        List<ReceiptDto> answer = List.of(cokeReceipt,orangeJuiceReceipt);
        LocalDate promotable = LocalDate.of(2024,3,3);

        //when
        List<ReceiptDto> receipt = testStore.executeOrder(testShoppingCart,promotable);

        //then
        assertThat(receipt).isEqualTo(answer);
    }

}

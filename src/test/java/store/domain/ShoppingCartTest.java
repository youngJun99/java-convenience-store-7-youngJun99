package store.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.dto.OrderApproveRequestDto;
import store.dto.OrderApproveResponseDto;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ShoppingCartTest {

    private ShoppingCart shoppingCart;

    @BeforeEach
    void setUp() {
        Purchase bonusReceivablePurchase = new Purchase("콜라", false, 5, 0, 1);
        Purchase unPromotablePurchase = new Purchase("오렌지주스", false, 6, 2, 0);
        Purchase approvedPurchase = new Purchase("물",true,5,0,0);
        shoppingCart = new ShoppingCart(List.of(bonusReceivablePurchase,unPromotablePurchase,approvedPurchase));
    }

    @Test
    @DisplayName("장바구니는 모든 주문이 승인되었는지 확인할 수 있다.")
    void isNotApprovedTest() {
        assertThat(shoppingCart.isNotApproved()).isFalse();
    }

    @Test
    @DisplayName("장바구니는 승인되지 않은 주문만 승인 요청 Dto로 요청한다.")
    void requestConfirmTest() {
        //given
        OrderApproveRequestDto cokeRequest = new OrderApproveRequestDto("콜라",0,1);
        OrderApproveRequestDto orangeJuiceRequest = new OrderApproveRequestDto("오렌지주스",2,0);
        List<OrderApproveRequestDto> answer = List.of(cokeRequest,orangeJuiceRequest);

        //when
        List<OrderApproveRequestDto> requestDtos = shoppingCart.getUnApprovedPurchases();

        //then
        assertThat(requestDtos).isEqualTo(answer);
    }

    @Test
    @DisplayName("장바구니는 승인 요청에 대한 답을 바탕으로 장바구니를 확정한다.")
    void processConfirmResponse() {
        //given
        OrderApproveResponseDto cokeResponse = new OrderApproveResponseDto("콜라",true);
        OrderApproveResponseDto orangeJuiceResponse = new OrderApproveResponseDto("오렌지주스",true);
        List<OrderApproveResponseDto> responseDtos = List.of(cokeResponse,orangeJuiceResponse);

        //when
        shoppingCart.processResponses(responseDtos);

        //then
        Purchase cokeConfirmed = new Purchase("콜라", true, 6, 0, 0);
        Purchase orangeConfirmed = new Purchase("오렌지주스", true, 6, 2, 0);
        Purchase waterConfirmed = new Purchase("물",true,5,0,0);
        List<Purchase> confirmed = List.of(cokeConfirmed,orangeConfirmed,waterConfirmed);

        assertThat(confirmed).isEqualTo(shoppingCart.getPurchases());
    }


}

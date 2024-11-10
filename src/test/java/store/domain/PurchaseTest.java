package store.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.shoppingcart.Purchase;
import store.dto.OrderApproveRequestDto;
import store.dto.OrderApproveResponseDto;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PurchaseTest {

    private Purchase bonusReceivablePurchase;
    private Purchase unPromotablePurchase;

    @BeforeEach
    void setUp() {
        bonusReceivablePurchase = new Purchase("콜라", false, 5, 0, 1);
        unPromotablePurchase = new Purchase("콜라", false, 6, 2, 0);
    }

    @Test
    @DisplayName("Purchase는 승인을 요청하는 dto를 반환한다.")
    void testConfirmRequest() {
        //given
        OrderApproveRequestDto answer = new OrderApproveRequestDto("콜라", 0, 1);

        //when
        OrderApproveRequestDto orderApproveRequestDto = bonusReceivablePurchase.makeConfirmRequest();

        //then
        assertThat(orderApproveRequestDto).isEqualTo(answer);
    }

    @Test
    @DisplayName("Purchase는 보너스를 거절한 요청을 반영한다.")
    void testRefuseBonus() {
        //given
        OrderApproveResponseDto responseDto = new OrderApproveResponseDto("콜라", false);

        //when
        bonusReceivablePurchase.processResponse(responseDto);

        //then
        assertThat(bonusReceivablePurchase.getApproved()).isTrue();
        assertThat(bonusReceivablePurchase.getPromotableAmount()).isEqualTo(5);
        assertThat(bonusReceivablePurchase.getExtraReceivableBonus()).isEqualTo(0);
    }

    @Test
    @DisplayName("Purchase는 보너스를 승인 요청을 반영한다.")
    void testConfirmBonus() {
        //given
        OrderApproveResponseDto responseDto = new OrderApproveResponseDto("콜라", true);

        //when
        bonusReceivablePurchase.processResponse(responseDto);

        //then
        assertThat(bonusReceivablePurchase.getApproved()).isTrue();
        assertThat(bonusReceivablePurchase.getPromotableAmount()).isEqualTo(6);
        assertThat(bonusReceivablePurchase.getExtraReceivableBonus()).isEqualTo(0);
    }

    @Test
    @DisplayName("Purchase는 프로모션이 안되는 구매 거절을 반영한다.")
    void testRefuseUnPromotable() {
        //given
        OrderApproveResponseDto responseDto = new OrderApproveResponseDto("콜라", false);

        //when
        unPromotablePurchase.processResponse(responseDto);

        //then
        assertThat(unPromotablePurchase.getApproved()).isTrue();
        assertThat(unPromotablePurchase.getPromotableAmount()).isEqualTo(6);
        assertThat(unPromotablePurchase.getUnPromotableAmount()).isEqualTo(0);
    }

    @Test
    @DisplayName("Purchase는 프로모션이 안되는 구매 승인을 반영한다.")
    void testConfirmUnPromotable() {
        //given
        OrderApproveResponseDto responseDto = new OrderApproveResponseDto("콜라", true);

        //when
        unPromotablePurchase.processResponse(responseDto);

        //then
        assertThat(unPromotablePurchase.getApproved()).isTrue();
        assertThat(unPromotablePurchase.getPromotableAmount()).isEqualTo(6);
        assertThat(unPromotablePurchase.getUnPromotableAmount()).isEqualTo(2);
    }
}

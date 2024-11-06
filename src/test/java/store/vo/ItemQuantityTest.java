package store.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ItemQuantityTest {

    @ParameterizedTest
    @DisplayName("상품의 재고는 음수일 수 없다")
    @ValueSource(ints = {-1, -2, -10})
    void negativeQuantityTest(int inputQuantity) {
        assertThatThrownBy(() -> new ItemQuantity(inputQuantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상품의 재고는 음수일 수 없습니다");
    }

    @Test
    @DisplayName("상품 재고는 리필할 수 있다")
    void refillQuantityTest() {
        //given
        ItemQuantity itemQuantity = new ItemQuantity(0);

        //when
        itemQuantity.refill(2);

        //then
        assertThat(itemQuantity.getItemQuantity()).isEqualTo(2);
    }

    @Test
    @DisplayName("상품 재고는 보유 이상을 팔려고 하면 에러가 난다")
    void sellOverPossession() {
        ItemQuantity itemQuantity = new ItemQuantity(0);

        assertThatThrownBy(() -> itemQuantity.sell(1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상품의 재고가 충분하지 않습니다");
    }
}

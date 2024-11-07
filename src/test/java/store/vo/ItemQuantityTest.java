package store.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class ItemQuantityTest {

    @ParameterizedTest
    @DisplayName("상품의 수량는 음수일 수 없다")
    @ValueSource(ints = {-1, -2, -10})
    void negativeQuantityTest(int inputQuantity) {
        assertThatThrownBy(() -> new ItemQuantity(inputQuantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상품의 수량은 음수일 수 없습니다");
    }

    @Test
    @DisplayName("상품 수량은 더할 수 있다")
    void addQuantityTest() {
        //given
        ItemQuantity itemQuantity = new ItemQuantity(0);

        //when
        itemQuantity.add(2);

        //then
        assertThat(itemQuantity.getItemQuantity()).isEqualTo(2);
    }

    @Test
    @DisplayName("상품 수량 이상을 빼려고 하면 에러가 난다")
    void subtractOverPossession() {
        ItemQuantity itemQuantity = new ItemQuantity(0);

        assertThatThrownBy(() -> itemQuantity.subtract(1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상품의 수량을 음수로 만들 수 없습니다, 현재 수량은 0개 입니다.");
    }
}

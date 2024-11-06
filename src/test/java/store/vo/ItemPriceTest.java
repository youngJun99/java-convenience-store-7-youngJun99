package store.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ItemPriceTest {

    @ParameterizedTest
    @DisplayName("가격은 0보다 커야한다")
    @ValueSource(ints = {0, -1, -2})
    void priceUnderZeroTest(int inputPrice) {
        assertThatThrownBy(() -> new ItemPrice(inputPrice))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("품목의 가격은 0보다 커야 합니다");
    }

}

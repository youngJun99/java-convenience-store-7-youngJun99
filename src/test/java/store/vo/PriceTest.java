package store.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class PriceTest {

    @ParameterizedTest
    @DisplayName("가격은 0보다 커야한다")
    @ValueSource(ints = {0, -1, -2})
    void priceUnderZeroTest(int inputPrice) {
        assertThatThrownBy(() -> new Price(inputPrice))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("가격은 0보다 커야합니다");
    }

}

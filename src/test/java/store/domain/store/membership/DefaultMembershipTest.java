package store.domain.store.membership;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import store.domain.membership.DefaultMembership;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DefaultMembershipTest {

    private static DefaultMembership defaultMemberShip;

    @BeforeAll
    static void setUp() {
        defaultMemberShip = new DefaultMembership();
    }

    @ParameterizedTest
    @DisplayName("26666원의 30%는 8000원 입니다. 이 이상의 값이 입력되면 8000원을 반환합니다.")
    @ValueSource(ints = {27000, 30000, 50000})
    void overDiscountMaximumTest(int input) {
        assertThat(defaultMemberShip.calculateDiscount(input)).isEqualTo(8000);
    }

    @ParameterizedTest
    @DisplayName("맴버십은 26666원 이하의 값들에 대해서는 정확한 할인가격을 반환합니다.")
    @CsvSource({
            "25000, 7500",
            "20000, 6000",
            "15000, 4500"
    })
    void test(int input, int expect) {
        assertThat(defaultMemberShip.calculateDiscount(input)).isEqualTo(expect);
    }
}

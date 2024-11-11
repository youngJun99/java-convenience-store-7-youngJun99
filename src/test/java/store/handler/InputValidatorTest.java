package store.handler;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.constants.InputErrors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class InputValidatorTest {

    private static InputValidator inputvalidator;

    @BeforeAll
    static void setUp() {
        inputvalidator = new InputValidator();
    }

    @Test
    @DisplayName("주문으로 null값을 입력할 수 없다")
    void nullOrderNotAllowed() {
        String nullString = null;
        assertThatThrownBy(() -> {
            inputvalidator.validateProductOrder(nullString);
        }).hasMessageContaining(InputErrors.INVALID_AMOUNT_OR_NAME.getMessage());
    }

    @ParameterizedTest
    @DisplayName("주문으로 공백을 받을 수 없다")
    @ValueSource(strings = {"", " ", "  ", "\n", "\t"})
    void blankOrderNotAllowed(String input) {
        assertThatThrownBy(() -> {
            inputvalidator.validateProductOrder(input);
        }).hasMessageContaining(InputErrors.INVALID_AMOUNT_OR_NAME.getMessage());
    }

    @ParameterizedTest
    @DisplayName("올바르지 않은 형식으로 입력하면 에러가 난다")
    @ValueSource(strings = {"[감자칩]-[2]", "[감자칩2]", "[2-감자칩]", "[감자칩-]"})
    void notValidOrderInput(String input) {
        assertThatThrownBy(() -> {
            inputvalidator.validateProductOrder(input);
        }).hasMessageContaining(InputErrors.INVALID_AMOUNT_OR_NAME.getMessage());
    }

    @ParameterizedTest
    @DisplayName("형식에 맞게 입력하면 오류가 나지 않는다")
    @ValueSource(strings = {"[감자칩-2]", "[콜라-2]", "[감자칩-2],[콜라-2]"})
    void validOrderInput(String input) {
        assertDoesNotThrow(() -> {
            inputvalidator.validateProductOrder(input);
        });
    }

    @Test
    @DisplayName("응답으로 null값을 입력할 수 없다")
    void nullResponseNotAllowed() {
        String nullString = null;
        assertThatThrownBy(() -> {
            inputvalidator.validateCustomerResponse(nullString);
        }).hasMessageContaining(InputErrors.WRONG_INPUT.getMessage());
    }

    @ParameterizedTest
    @DisplayName("응답으로 공백을 입력할 수 없다.")
    @ValueSource(strings = {"", " ", "  ", "\n", "\t"})
    void blankResponseNotAllowed(String input) {
        assertThatThrownBy(() -> {
            inputvalidator.validateCustomerResponse(input);
        }).hasMessageContaining(InputErrors.WRONG_INPUT.getMessage());
    }

    @ParameterizedTest
    @DisplayName("올바르지 않은 형식으로 입력하면 에러가 난다")
    @ValueSource(strings = {"y", "YES", "ㅇ", "No", "NO"})
    void notValidResponseInput(String input) {
        assertThatThrownBy(() -> {
            inputvalidator.validateProductOrder(input);
        }).hasMessageContaining(InputErrors.INVALID_AMOUNT_OR_NAME.getMessage());
    }

    @ParameterizedTest
    @DisplayName("형식에 맞게 입력하면 오류가 나지 않는다")
    @ValueSource(strings = {"Y", "N"})
    void validResponseInput(String input) {
        assertDoesNotThrow(() -> {
            inputvalidator.validateCustomerResponse(input);
        });
    }


}

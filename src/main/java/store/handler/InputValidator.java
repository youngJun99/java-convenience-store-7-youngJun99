package store.handler;

import store.constants.InputErrors;

public class InputValidator {

    private static final String REGEX_OF_PRODUCT_ORDER = "^\\[(.+)-\\d+\\](,\\[(.+)-\\d+\\])*$";
    private static final String REGEX_OF_CUSTOMER_RESPONSE = "^[YN]$";

    public void validateProductOrder(String input) {
        if (input == null || DoesNotMatchOrderRegex(input)) {
            throw new IllegalArgumentException(InputErrors.INVALID_AMOUNT_OR_NAME.getMessage());
        }
    }

    public void validateCustomerResponse(String input) {
        if (input == null || DoesNotMatchResponseRegex(input)) {
            throw new IllegalArgumentException(InputErrors.WRONG_INPUT.getMessage());
        }
    }

    private static boolean DoesNotMatchOrderRegex(String input) {
        return !input.matches(REGEX_OF_PRODUCT_ORDER);
    }

    private static boolean DoesNotMatchResponseRegex(String input) {
        return !input.matches(REGEX_OF_CUSTOMER_RESPONSE);
    }
}

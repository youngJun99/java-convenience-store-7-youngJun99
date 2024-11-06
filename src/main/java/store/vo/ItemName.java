package store.vo;

import org.junit.platform.commons.util.StringUtils;
import store.constants.Errors;

import java.util.Objects;

public class ItemName {

    private static final int NAME_LIMIT = 10;

    private final String itemName;

    public ItemName(String itemName) {
        validateNull(itemName);
        validateBlank(itemName);
        validateLimit(itemName);
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    private void validateNull(String itemName) {
        if (Objects.isNull(itemName)) {
            throw new IllegalArgumentException(Errors.NULL_NAME.getMessage());
        }
    }

    private void validateBlank(String itemName) {
        if (StringUtils.isBlank(itemName)) {
            throw new IllegalArgumentException(Errors.BLANK_NAME.getMessage());
        }
    }

    private void validateLimit(String itemName) {
        if (itemName.length() > NAME_LIMIT) {
            throw new IllegalArgumentException(String.format(Errors.LONG_NAME.getMessage(), NAME_LIMIT));
        }
    }
}

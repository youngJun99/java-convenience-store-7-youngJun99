package store.vo;

import org.junit.platform.commons.util.StringUtils;
import store.constants.Errors;

import java.util.Objects;

public class ItemName {

    private final String name;

    public ItemName(String name) {
        validateNull(name);
        validateBlank(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private void validateNull(String name) {
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException(Errors.NULL_NAME.getMessage());
        }
    }

    private void validateBlank(String itemName) {
        if (StringUtils.isBlank(itemName)) {
            throw new IllegalArgumentException(Errors.BLANK_NAME.getMessage());
        }
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ItemName itemName1 = (ItemName) object;
        return Objects.equals(name, itemName1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}

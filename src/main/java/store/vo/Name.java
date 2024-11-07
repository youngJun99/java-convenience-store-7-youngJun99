package store.vo;

import org.junit.platform.commons.util.StringUtils;
import store.constants.Errors;

import java.util.Objects;

public class Name {

    private static final int NAME_LIMIT = 10;

    private final String name;

    public Name(String name) {
        validateNull(name);
        validateBlank(name);
        validateLimit(name);
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

    private void validateLimit(String itemName) {
        if (itemName.length() > NAME_LIMIT) {
            throw new IllegalArgumentException(String.format(Errors.LONG_NAME.getMessage(), NAME_LIMIT));
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Name name1 = (Name) object;
        return Objects.equals(name, name1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}

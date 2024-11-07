package store.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class NameTest {

    @Test
    @DisplayName("이름은 null 값이 입력될 수 없다")
    void nullNameTest() {
        assertThatThrownBy(() -> new Name(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이름으로 null값이 입력되었습니다.");
    }

    @ParameterizedTest
    @DisplayName("이름은 빈칸이 들어갈 수 없다")
    @ValueSource(strings = {"", "  ", "   ", "\n", "\t"})
    void blankNameTest(String input) {
        assertThatThrownBy(() -> new Name(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이름으로 빈칸이 입력되었습니다.");
    }

    /**
     * Magic Number를 상수화 하고 싶었지만, NAME_LIMIT을 private으로 유지하는 것이 더 중요하다고 생각했습니다.
     */
    @Test
    @DisplayName("이름은 지정된 최대 길이를 넘을 수 없다")
    void nameLimitTest() {
        assertThatThrownBy(() -> new Name("X".repeat(11)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이름이 10글자를 초과했습니다");
    }

}

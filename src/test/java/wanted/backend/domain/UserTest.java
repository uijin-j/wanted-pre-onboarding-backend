package wanted.backend.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Locale;
import net.datafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class UserTest {

    static final Faker faker = new Faker(Locale.KOREA);

    @Nested
    class 생성_테스트 {

        @DisplayName("유저를 생성할 수 있다.")
        @Test
        void createUser() {
            // given
            String name = faker.name().fullName();

            // when
            User user = new User(name);

            // then
            assertThat(user)
                .hasFieldOrPropertyWithValue("name", name);
        }

        @DisplayName("이름 없이 유저를 생성할 수 없다.")
        @Test
        void createUserWithoutName() {
            // when & then
            assertThatThrownBy(() -> new User(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 필수값입니다.");
        }

        @DisplayName("이름이 50자를 초과할 수 없다")
        @Test
        void createUserWithNameOver50() {
            // given
            String name = faker.lorem().fixedString(51);

            // when & then
            assertThatThrownBy(() -> new User(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 50자 이하여야 합니다.");
        }
    }
}

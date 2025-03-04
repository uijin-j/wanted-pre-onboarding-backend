package wanted.backend.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Locale;
import net.datafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MoneyTest {

    static final Faker faker = new Faker(Locale.KOREA);

    @DisplayName("돈을 생성할 수 있다")
    @Test
    void createMoney() {
        // given
        long amount = faker.number().positive();

        // when
        Money money = Money.from(amount);

        // then
        assertThat(money)
            .hasFieldOrPropertyWithValue("amount", amount);
    }

    @DisplayName("금액 없으면 0원으로 생성한다")
    @Test
    void createMoneyWithoutAmount() {
        // when
        Money money = Money.from(null);

        // then
        assertThat(money)
            .hasFieldOrPropertyWithValue("amount", 0L);
    }


    @DisplayName("돈은 0보다 작을 수 없다")
    @Test
    void createMoneyWithNegativeAmount() {
        // given
        long amount = faker.number().negative();

        // when & then
        assertThatThrownBy(() -> Money.from(amount))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("금액은 양수입니다.");
    }
}

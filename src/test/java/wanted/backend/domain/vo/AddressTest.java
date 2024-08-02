package wanted.backend.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Locale;
import net.datafaker.Faker;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AddressTest {

    static final Faker faker = new Faker(Locale.KOREA);


    @DisplayName("나라명과 도시명으로 주소를 생성할 수 있다")
    @Test
    void create() {
        // given
        String country = faker.address().country();
        String city = faker.address().city();

        // when
        Address address = Address.of(country, city);

        // then
        assertThat(address)
            .hasFieldOrPropertyWithValue("country", country)
            .hasFieldOrPropertyWithValue("city", city);
    }

    @DisplayName("나라명과 도시명이 모두 없으면 빈 주소가 생성된다")
    @Test
    void createEmptyAddress() {
        // when
        Address address = Address.of(null, null);

        // then
        assertThat(address)
            .hasFieldOrPropertyWithValue("country", "")
            .hasFieldOrPropertyWithValue("city", "");
    }

    @DisplayName("나라명만 있으면 주소를 생성할 수 없다")
    @Test
    void createAddressWithOnlyContry() {
        // given
        String country = faker.address().country();

        // when
        ThrowingCallable create = () -> Address.of(null, country);

        // then
        assertThatThrownBy(create)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("국가는 필수값입니다.");
    }

    @DisplayName("도시명만 있으면 주소를 생성할 수 없다")
    @Test
    void createAddressWithOnlyCity() {
        // given
        String city = faker.address().city();

        // when & then
        assertThatThrownBy(() -> Address.of(city, null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("도시는 필수값입니다.");
    }
}

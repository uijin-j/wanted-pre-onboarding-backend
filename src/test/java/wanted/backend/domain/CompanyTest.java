package wanted.backend.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Locale;
import net.datafaker.Faker;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CompanyTest {

    static final Faker faker = new Faker(Locale.KOREA);

    String name;
    String description;
    String country;
    String city;

    @BeforeEach
    void setUp() {
        name = faker.company().name();
        description = faker.lorem().sentence();
        country = faker.address().country();
        city = faker.address().city();
    }

    @Nested
    class 생성_테스트 {

        @DisplayName("회사를 생성할 수 있다")
        @Test
        void createCompany() {
            // when
            Company company = Company.builder()
                .name(name)
                .description(description)
                .country(country)
                .city(city)
                .build();

            // then
            assertThat(company)
                .hasFieldOrPropertyWithValue("name", name)
                .hasFieldOrPropertyWithValue("description", description)
                .hasFieldOrPropertyWithValue("country", country)
                .hasFieldOrPropertyWithValue("city", city);
        }

        @DisplayName("회사명이 없으면 회사를 생성할 수 없다")
        @Test
        void createCompanyWithoutName() {
            // when
            ThrowingCallable careate = () -> Company.builder()
                .name(null)
                .description(description)
                .country(country)
                .city(city)
                .build();

            // then
            assertThatThrownBy(careate)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("회사명은 필수 입력값입니다.");
        }

        @DisplayName("회사명이 50자를 초과할 수 없다")
        @Test
        void createCompanyWithNameOver50() {
            // given
            String longName = faker.lorem().fixedString(51);

            // when
            ThrowingCallable create = () -> Company.builder()
                .name(longName)
                .description(description)
                .country(country)
                .city(city)
                .build();

            // then
            assertThatThrownBy(create)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("회사명은 50자 이하여야 합니다.");
        }
    }
}

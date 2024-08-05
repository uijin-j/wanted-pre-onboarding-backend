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

class ApplicationTest {

    static final Faker faker = new Faker(Locale.KOREA);

    User user;
    Company company;
    JobOpening jobOpening;

    @BeforeEach
    void setUp() {
        user = User.builder()
            .name(faker.name().fullName())
            .build();

        company = Company.builder()
            .name(faker.company().name())
            .build();

        jobOpening = JobOpening.builder()
            .title(faker.lorem().sentence())
            .position(faker.job().position())
            .company(company)
            .build();
    }

    @Nested
    class 지원_테스트 {

        @DisplayName("사용자는 채용공고에 지원할 수 있다")
        @Test
        void createApplication() {
            // when
            Application application = Application.builder()
                .applicant(user)
                .jobOpening(jobOpening)
                .build();

            // then
            assertThat(application)
                .hasFieldOrPropertyWithValue("applicant", user)
                .hasFieldOrPropertyWithValue("jobOpening", jobOpening);
        }

        @DisplayName("지원자가 없는 경우 지원할 수 없다")
        @Test
        void createApplicationWithoutApplicant() {
            // when
            ThrowingCallable apply = () -> Application.builder()
                .applicant(null)
                .jobOpening(jobOpening)
                .build();

            // then
            assertThatThrownBy(apply)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("지원자는 필수값입니다.");
        }

        @DisplayName("지원할 채용공고가 없는 경우 지원할 수 없다")
        @Test
        void createApplicationWithoutJobOpening() {
            // when
            ThrowingCallable apply = () -> Application.builder()
                .applicant(user)
                .jobOpening(null)
                .build();

            // then
            assertThatThrownBy(apply)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("지원할 채용공고는 필수값입니다.");
        }

    }

}

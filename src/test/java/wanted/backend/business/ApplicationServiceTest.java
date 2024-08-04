package wanted.backend.business;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jakarta.persistence.EntityNotFoundException;
import java.util.Locale;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import wanted.backend.domain.Application;
import wanted.backend.domain.Company;
import wanted.backend.domain.JobOpening;
import wanted.backend.domain.User;
import wanted.backend.dto.request.ApplyRequest;
import wanted.backend.dto.response.ApplyResponse;
import wanted.backend.persistence.ApplicationRepository;
import wanted.backend.persistence.CompanyRepository;
import wanted.backend.persistence.JobOpeningRepository;
import wanted.backend.persistence.UserRepository;

@SpringBootTest
@Transactional
class ApplicationServiceTest {

    static final Faker faker = new Faker(Locale.KOREA);

    @Autowired
    ApplicationService applicationService;

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JobOpeningRepository jobOpeningRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Nested
    class 지원_테스트 {

        Company company;
        User user;
        JobOpening jobOpening;

        @BeforeEach
        void setUp() {
            user = User.builder()
                .name(faker.name().fullName())
                .build();

            userRepository.save(user);

            company = Company.builder()
                .name(faker.company().name())
                .build();

            companyRepository.save(company);

            jobOpening = JobOpening.builder()
                .title(faker.job().position())
                .company(company)
                .position(faker.job().position())
                .build();

            jobOpeningRepository.save(jobOpening);
        }

        @DisplayName("채용공고에 지원할 수 있다")
        @Test
        void apply() {
            // given
            ApplyRequest request = new ApplyRequest(jobOpening.getId(), user.getId());

            // when
            ApplyResponse response = applicationService.apply(request);

            // then
            Application actual = applicationRepository.findById(response.id())
                .get();
            assertThat(actual)
                .hasFieldOrPropertyWithValue("applicant.id", user.getId())
                .hasFieldOrPropertyWithValue("jobOpening.id", jobOpening.getId());
        }

        @DisplayName("이미 지원한 채용공고에 다시 지원할 수 없다")
        @Test
        void applyTwice() {
            // given
            Application application = Application.builder()
                .applicant(user)
                .jobOpening(jobOpening)
                .build();

            applicationRepository.save(application);

            ApplyRequest request = new ApplyRequest(jobOpening.getId(), user.getId());

            // when & then
            assertThatThrownBy(() -> applicationService.apply(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 지원한 채용공고입니다.");
        }

        @DisplayName("존재하지 않는 채용공고에 지원할 수 없다")
        @Test
        void applyToNonExistJobOpening() {
            // given
            Long nonExistJobOpeningId = faker.number().randomNumber();
            ApplyRequest request = new ApplyRequest(nonExistJobOpeningId, user.getId());

            // when & then
            assertThatThrownBy(() -> applicationService.apply(request))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("존재하지 않는 채용공고입니다.");
        }

        @DisplayName("존재하지 않는 사용자가 지원할 수 없다")
        @Test
        void applyNonExistUser() {
            // given
            Long nonExistUserId = faker.number().randomNumber();
            ApplyRequest request = new ApplyRequest(jobOpening.getId(), nonExistUserId);

            // when & then
            assertThatThrownBy(() -> applicationService.apply(request))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("존재하지 않는 사용자입니다.");
        }
    }

}

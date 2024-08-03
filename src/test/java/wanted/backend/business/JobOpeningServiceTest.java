package wanted.backend.business;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import net.datafaker.Faker;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import wanted.backend.domain.Company;
import wanted.backend.domain.JobOpening;
import wanted.backend.dto.request.JobOpeningCreateRequest;
import wanted.backend.dto.request.JobOpeningUpdateRequest;
import wanted.backend.dto.response.JobOpeningDetail;
import wanted.backend.dto.response.JobOpeningResponse;
import wanted.backend.dto.response.JobOpeningSummary;
import wanted.backend.persistence.CompanyRepository;
import wanted.backend.persistence.JobOpeningRepository;

@SpringBootTest
class JobOpeningServiceTest {

    static final Faker faker = new Faker(Locale.KOREA);

    @Autowired
    JobOpeningService jobOpeningService;

    @Autowired
    JobOpeningRepository jobOpeningRepository;

    @Autowired
    CompanyRepository companyRepository;

    Company company;
    String title;
    String position;
    long reward;
    String description;
    String techStack;

    @BeforeEach
    void setUp() {
        company = Company.builder()
            .name(faker.company().name())
            .build();

        companyRepository.save(company);

        title = faker.lorem().sentence();
        position = faker.job().position();
        reward = faker.number().positive();
        description = faker.lorem().sentence();
        techStack = faker.lorem().characters();
    }

    @Nested
    class 채용공고_생성_테스트 {

        @DisplayName("채용공고를 생성할 수 있다")
        @Test
        void createJobOpening() {
            // given
            JobOpeningCreateRequest request = new JobOpeningCreateRequest(
                company.getId(),
                title,
                position,
                reward,
                description,
                techStack
            );

            // when
            JobOpeningResponse response = jobOpeningService.post(request);

            // then
            JobOpening actual = jobOpeningRepository.findById(response.id()).get();
            assertThat(actual)
                .hasFieldOrPropertyWithValue("title", title)
                .hasFieldOrPropertyWithValue("position", position)
                .hasFieldOrPropertyWithValue("reward", reward)
                .hasFieldOrPropertyWithValue("description", description)
                .hasFieldOrPropertyWithValue("techStack", techStack);
            assertThat(actual)
                .extracting("company.id")
                .isEqualTo(company.getId());
        }

        @DisplayName("회사가 존재하지 않으면 채용공고를 생성할 수 없다")
        @Test
        void createJobOpeningWithWrongCompany() {
            // given
            long wrongCompanyId = faker.number().positive();
            JobOpeningCreateRequest request = new JobOpeningCreateRequest(
                wrongCompanyId,
                title,
                position,
                reward,
                description,
                techStack
            );

            // when
            ThrowingCallable create = () -> jobOpeningService.post(request);

            // then
            assertThatThrownBy(create)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("존재하지 않는 회사입니다.");
        }
    }

    @Nested
    class 채용공고_수정_테스트 {

        JobOpening jobOpening;
        String newTitle;
        String newPosition;
        long newReward;
        String newDescription;
        String newTechStack;

        @BeforeEach
        void setUp() {
            jobOpening = JobOpening.builder()
                .company(company)
                .title(title)
                .position(position)
                .reward(reward)
                .description(description)
                .techStack(techStack)
                .build();

            jobOpeningRepository.save(jobOpening);

            newTitle = faker.lorem().sentence();
            newPosition = faker.job().position();
            newReward = faker.number().positive();
            newDescription = faker.lorem().sentence();
            newTechStack = faker.lorem().characters();
        }

        @DisplayName("채용공고를 수정할 수 있다")
        @Test
        void updateJobOpening() {
            // given
            JobOpeningUpdateRequest request = new JobOpeningUpdateRequest(
                newTitle,
                newPosition,
                newReward,
                newDescription,
                newTechStack
            );

            // when
            JobOpeningResponse response = jobOpeningService.update(jobOpening.getId(), request);

            // then
            JobOpening actual = jobOpeningRepository.findById(response.id()).get();
            assertThat(actual)
                .hasFieldOrPropertyWithValue("title", newTitle)
                .hasFieldOrPropertyWithValue("position", newPosition)
                .hasFieldOrPropertyWithValue("reward", newReward)
                .hasFieldOrPropertyWithValue("description", newDescription)
                .hasFieldOrPropertyWithValue("techStack", newTechStack);
        }

        @DisplayName("채용공고가 존재하지 않으면 예외가 발생한다")
        @Test
        void updateNonExistJobOpening() {
            // given
            long wrongJobOpeningId = faker.number().positive();
            JobOpeningUpdateRequest request = new JobOpeningUpdateRequest(
                newTitle,
                newPosition,
                newReward,
                newDescription,
                newTechStack
            );

            // when
            ThrowingCallable update = () -> jobOpeningService.update(wrongJobOpeningId, request);

            // then
            assertThatThrownBy(update)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("존재하지 않는 채용공고입니다.");
        }
    }

    @Nested
    class 채용공고_삭제_테스트 {

        JobOpening jobOpening;

        @BeforeEach
        void setUp() {
            jobOpening = JobOpening.builder()
                .company(company)
                .title(title)
                .position(position)
                .reward(reward)
                .description(description)
                .techStack(techStack)
                .build();

            jobOpeningRepository.save(jobOpening);
        }

        @DisplayName("채용공고를 삭제할 수 있다")
        @Test
        void deleteJobOpening() {
            // given
            Optional<JobOpening> beforeDelete = jobOpeningRepository.findById(jobOpening.getId());
            assertThat(beforeDelete).isPresent();

            // when
            jobOpeningService.delete(jobOpening.getId());

            // then
            Optional<JobOpening> afterDelete = jobOpeningRepository.findById(jobOpening.getId());
            assertThat(afterDelete).isEmpty();
        }
    }

    @Nested
    class 채용공고_전체_조회_테스트 {

        JobOpening jobOpening1;
        JobOpening jobOpening2;

        @BeforeEach
        void setUp() {
            jobOpening1 = JobOpening.builder()
                .company(company)
                .title(title)
                .position(position)
                .reward(reward)
                .description(description)
                .techStack(techStack)
                .build();

            jobOpening2 = JobOpening.builder()
                .company(company)
                .title(title)
                .position(position)
                .reward(reward)
                .description(description)
                .techStack(techStack)
                .build();

            jobOpeningRepository.save(jobOpening1);
            jobOpeningRepository.save(jobOpening2);
        }

        @DisplayName("전체 채용공고를 페이지로 조회할 수 있다")
        @Test
        void getJobOpenings() {
            // given
            PageRequest pageable = PageRequest.of(0, 10);

            // when
            Page<JobOpeningSummary> page = jobOpeningService.getJobOpenings(pageable);

            // then
            List<JobOpeningSummary> content = page.getContent();
            assertThat(content).hasSize(2);
            assertThat(content)
                .extracting("id")
                .containsExactlyInAnyOrder(jobOpening1.getId(), jobOpening2.getId());
        }
    }

    @Nested
    class 채용공고_상세_조회_테스트 {

        JobOpening jobOpening;

        @BeforeEach
        void setUp() {
            jobOpening = JobOpening.builder()
                .company(company)
                .title(title)
                .position(position)
                .reward(reward)
                .description(description)
                .techStack(techStack)
                .build();

            jobOpeningRepository.save(jobOpening);
        }

        @DisplayName("채용공고의 상세 정보를 조회할 수 있다")
        @Test
        void getJobOpeningDetail() {
            // when
            JobOpeningDetail actual = jobOpeningService.getInfo(jobOpening.getId());

            // then
            assertThat(actual)
                .hasFieldOrPropertyWithValue("id", jobOpening.getId())
                .hasFieldOrPropertyWithValue("title", title)
                .hasFieldOrPropertyWithValue("position", position)
                .hasFieldOrPropertyWithValue("companyName", company.getName())
                .hasFieldOrPropertyWithValue("country", company.getCountry())
                .hasFieldOrPropertyWithValue("city", company.getCity())
                .hasFieldOrPropertyWithValue("position", position)
                .hasFieldOrPropertyWithValue("reward", reward)
                .hasFieldOrPropertyWithValue("techStack", techStack)
                .hasFieldOrPropertyWithValue("description", description);
        }

        @DisplayName("채용공고 상세 조회 시 회사의 다른 채용공고도 함께 조회할 수 있다")
        @Test
        void getJobOpeningWithOtherJobOpenings() {
            // given
            JobOpening otherJobOpening = JobOpening.builder()
                .company(company)
                .title(faker.lorem().sentence())
                .position(faker.job().position())
                .build();

            jobOpeningRepository.save(otherJobOpening);

            // when
            JobOpeningDetail actual = jobOpeningService.getInfo(jobOpening.getId());

            // then
            assertThat(actual).extracting("otherJobOpenings")
                .isEqualTo(List.of(otherJobOpening.getId()));
        }
    }
}

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
import wanted.backend.dto.request.JobOpeningUpdateRequest;

class JobOpeningTest {

    static final Faker faker = new Faker(Locale.KOREA);

    String title;
    String position;
    long reward;
    String description;
    String teckStack;
    Company company;

    @BeforeEach
    void setUp() {
        title = faker.lorem().sentence();
        position = faker.job().position();
        reward = faker.number().positive();
        description = faker.lorem().sentence();
        teckStack = faker.lorem().characters();
        company = createRandomCompany();
    }

    @Nested
    class 생성_테스트 {

        @DisplayName("채용공고를 생성할 수 있다")
        @Test
        void createJobOpening() {
            // when
            JobOpening jobOpening = JobOpening.builder()
                .title(title)
                .position(position)
                .reward(reward)
                .description(description)
                .techStack(teckStack)
                .company(company)
                .build();

            // then
            assertThat(jobOpening)
                .hasFieldOrPropertyWithValue("title", title)
                .hasFieldOrPropertyWithValue("position", position)
                .hasFieldOrPropertyWithValue("reward", reward)
                .hasFieldOrPropertyWithValue("description", description)
                .hasFieldOrPropertyWithValue("techStack", teckStack)
                .hasFieldOrPropertyWithValue("company", company);
        }

        @DisplayName("제목 없이 채용공고를 생성할 수 없다.")
        @Test
        void createJobOpeningWithoutTitle() {
            // when
            ThrowingCallable create = () -> JobOpening.builder()
                .title(null)
                .position(position)
                .company(company)
                .build();

            // then
            assertThatThrownBy(create)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("제목은 필수값입니다.");
        }

        @DisplayName("제목은 255자를 초과할 수 없다")
        @Test
        void createJobOpeningWithTitleOver255() {
            // given
            String longTitle = faker.lorem().fixedString(256);

            // when
            ThrowingCallable create = () -> JobOpening.builder()
                .title(longTitle)
                .position(position)
                .company(company)
                .build();

            // then
            assertThatThrownBy(create)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("제목은 255자 이하여야 합니다.");
        }

        @DisplayName("직책 없이 채용공고를 생성할 수 없다.")
        @Test
        void createJobOpeningWithoutPosition() {
            // when
            ThrowingCallable create = () -> JobOpening.builder()
                .title(title)
                .position(null)
                .company(company)
                .build();

            // then
            assertThatThrownBy(create)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("직책은 필수값입니다.");
        }

        @DisplayName("직책은 255자를 초과할 수 없다")
        @Test
        void createJobOpeningWithPositionOver255() {
            // given
            String longPosition = faker.lorem().fixedString(256);

            // when
            ThrowingCallable create = () -> JobOpening.builder()
                .title(title)
                .position(longPosition)
                .company(company)
                .build();

            // then
            assertThatThrownBy(create)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("직책은 255자 이하여야 합니다.");
        }

        @DisplayName("기술스택은 255자를 초과할 수 없다")
        @Test
        void createJobOpeningWithTechStackOver255() {
            // given
            String longTechStack = faker.lorem().fixedString(256);

            // when
            ThrowingCallable create = () -> JobOpening.builder()
                .title(title)
                .position(position)
                .techStack(longTechStack)
                .company(company)
                .build();

            // then
            assertThatThrownBy(create)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("기술 스택은 255자 이하여야 합니다.");
        }

        @DisplayName("회사 없이 채용공고를 생성할 수 없다.")
        @Test
        void createJobOpeningWithoutCompany() {
            // when
            ThrowingCallable create = () -> JobOpening.builder()
                .title(title)
                .position(position)
                .company(null)
                .build();

            // then
            assertThatThrownBy(create)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("회사는 필수값입니다.");
        }
    }

    @Nested
    class 수정_테스트 {

        JobOpening jobOpening;
        String newTitle;
        String newPosition;
        long newReward;
        String newDescription;
        String newTeckStack;

        @BeforeEach
        void setUp() {
            jobOpening = JobOpening.builder()
                .title(title)
                .position(position)
                .reward(reward)
                .description(description)
                .techStack(teckStack)
                .company(company)
                .build();

            newTitle = faker.lorem().sentence();
            newPosition = faker.job().position();
            newReward = faker.number().positive();
            newDescription = faker.lorem().sentence();
            newTeckStack = faker.lorem().characters();
        }

        @DisplayName("채용공고를 수정할 수 있다")
        @Test
        void updateJobOpening() {
            // given
            JobOpeningUpdateRequest request = new JobOpeningUpdateRequest(newTitle, newPosition,
                newReward, newDescription, newTeckStack);

            // when
            jobOpening.update(request);

            // then
            assertThat(jobOpening)
                .hasFieldOrPropertyWithValue("title", newTitle)
                .hasFieldOrPropertyWithValue("position", newPosition)
                .hasFieldOrPropertyWithValue("reward", newReward)
                .hasFieldOrPropertyWithValue("description", newDescription);
        }

        @DisplayName("제목 없이 채용공고를 수정할 수 없다.")
        @Test
        void updateJobOpeningWithoutTitle() {
            // given
            JobOpeningUpdateRequest request = new JobOpeningUpdateRequest(null, newPosition,
                newReward, newDescription, newTeckStack);

            // then
            assertThatThrownBy(() -> jobOpening.update(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("제목은 필수값입니다.");
        }

        @DisplayName("직책 없이 채용공고를 수정할 수 없다.")
        @Test
        void updateJobOpeningWithoutPosition() {
            // given
            JobOpeningUpdateRequest request = new JobOpeningUpdateRequest(newTitle, null,
                newReward, newDescription, newTeckStack);

            // then
            assertThatThrownBy(() -> jobOpening.update(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("직책은 필수값입니다.");
        }

        @DisplayName("직책은 255자를 초과할 수 없다")
        @Test
        void updateJobOpeningWithPositionOver255() {
            // given
            String longPosition = faker.lorem().fixedString(256);
            JobOpeningUpdateRequest request = new JobOpeningUpdateRequest(newTitle, longPosition,
                newReward, newDescription, newTeckStack);

            // then
            assertThatThrownBy(() -> jobOpening.update(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("직책은 255자 이하여야 합니다.");
        }

        @DisplayName("기술스택은 255자를 초과할 수 없다")
        @Test
        void updateJobOpeningWithTechStackOver255() {
            // given
            String longTechStack = faker.lorem().fixedString(256);
            JobOpeningUpdateRequest request = new JobOpeningUpdateRequest(newTitle, newPosition,
                newReward, newDescription, longTechStack);

            // then
            assertThatThrownBy(() -> jobOpening.update(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("기술 스택은 255자 이하여야 합니다.");
        }

    }

    private Company createRandomCompany() {
        return Company.builder()
            .name(faker.company().name())
            .build();
    }

}

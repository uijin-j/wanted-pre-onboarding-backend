package wanted.backend.domain;

import static com.google.common.base.Preconditions.checkArgument;
import static io.micrometer.common.util.StringUtils.isBlank;
import static java.util.Objects.nonNull;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wanted.backend.domain.common.BaseTimeEntity;
import wanted.backend.domain.vo.Money;
import wanted.backend.dto.request.JobOpeningUpdateRequest;

@Entity
@Table(name = "job_opening")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobOpening extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "position", nullable = false)
    private String position;
    @Embedded
    @Getter(AccessLevel.NONE)
    private Money reward;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "tech_stack")
    private String techStack;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Builder
    public JobOpening(String title, String position, Long reward, String description,
        String techStack, Company company) {
        validateTitle(title);
        validatePosition(position);
        validateTechStack(techStack);
        validateCompany(company);

        this.title = title;
        this.position = position;
        this.reward = Money.from(reward);
        this.description = description;
        this.techStack = techStack;
        this.company = company;
    }

    public void update(JobOpeningUpdateRequest request) {
        validateTitle(request.title());
        validatePosition(request.position());
        validateTechStack(request.techStack());

        this.title = request.title();
        this.position = request.position();
        this.reward = Money.from(request.reward());
        this.description = request.description();
        this.techStack = request.techStack();
    }

    public Long getReward() {
        return reward.getAmount();
    }

    private void validateCompany(Company company) {
        checkArgument(nonNull(company), "회사는 필수값입니다.");
    }

    private void validateTechStack(String techStack) {
        if (isBlank(techStack)) {
            return;
        }
        checkArgument(techStack.length() <= 255, "기술 스택은 255자 이하여야 합니다.");
    }

    private void validatePosition(String position) {
        checkArgument(nonNull(position), "직책은 필수값입니다.");
        checkArgument(position.length() <= 255, "직책은 255자 이하여야 합니다.");
    }

    private void validateTitle(String title) {
        checkArgument(nonNull(title), "제목은 필수값입니다.");
        checkArgument(title.length() <= 255, "제목은 255자 이하여야 합니다.");
    }
}

package wanted.backend.domain;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.nonNull;

import jakarta.persistence.Column;
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

@Entity
@Table(name = "application")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Application extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User applicant;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_opening_id", nullable = false)
    private JobOpening jobOpening;

    @Builder
    public Application(User applicant, JobOpening jobOpening) {
        validateApplicant(applicant);
        validateJobOpening(jobOpening);

        this.applicant = applicant;
        this.jobOpening = jobOpening;
    }

    private void validateApplicant(User applicant) {
        checkArgument(nonNull(applicant), "지원자는 필수값입니다.");
    }

    private void validateJobOpening(JobOpening jobOpening) {
        checkArgument(nonNull(jobOpening), "지원할 채용공고는 필수값입니다.");
    }
}

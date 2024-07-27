package wanted.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import wanted.backend.domain.common.BaseTimeEntity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Application extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User applicant;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_opening_id", nullable = false)
    private JobOpening jobOpening;
}

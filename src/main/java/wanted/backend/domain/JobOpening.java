package wanted.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import wanted.backend.domain.vo.Money;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobOpening extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "position", nullable = false)
    private String position;
    @Embedded
    private Money reward;
    @Column(name = "description")
    private String description;
    @Column(name = "tech_stack")
    private String techStack;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
}

package wanted.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import wanted.backend.domain.common.BaseTimeEntity;
import wanted.backend.domain.vo.Address;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "name", length = 50, nullable = false)
    private String name;
    @Column(name = "description")
    private String description;
    @Embedded
    private Address address;
}

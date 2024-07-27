package wanted.backend.domain;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.nonNull;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wanted.backend.domain.common.BaseTimeEntity;
import wanted.backend.domain.vo.Address;

@Entity
@Table(name = "company")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "name", length = 50, nullable = false)
    private String name;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Embedded
    @Getter(AccessLevel.NONE)
    private Address address;

    @Builder
    public Company(String name, String description, String country, String city) {
        validateName(name);

        this.name = name;
        this.description = description;
        this.address = Address.of(country, city);
    }

    public String getCountry() {
        return address.getCountry();
    }

    public String getCity() {
        return address.getCity();
    }

    private void validateName(String name) {
        checkArgument(nonNull(name), "회사명은 필수 입력값입니다.");
        checkArgument(name.length() <= 50, "회사명은 50자 이하여야 합니다.");
    }
}

package wanted.backend.domain.vo;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    public static final Address EMPTY = new Address("", "");

    @Column(name = "country")
    private String country;
    @Column(name = "city")
    private String city;

    private Address(String country, String city) {
        validate(country, city);

        this.country = country;
        this.city = city;
    }

    public static Address of(String country, String city) {
        if (isNull(country) && isNull(city)) {
            return EMPTY;
        }

        return new Address(country, city);
    }

    private void validate(String country, String city) {
        validateCountry(country);
        validateCity(city);
    }

    private void validateCountry(String country) {
        checkArgument(nonNull(country), "국가는 필수값입니다.");
        checkArgument(country.length() <= 255, "국가는 255자 이하여야 합니다.");
    }

    private void validateCity(String city) {
        checkArgument(nonNull(city), "도시는 필수값입니다.");
        checkArgument(city.length() <= 255, "도시는 255자 이하여야 합니다.");
    }
}

package wanted.backend.domain.vo;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.nonNull;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Money {

    public static final Money ZERO = new Money(0L);

    @Column(name = "reward")
    private Long amount;

    private Money(Long amount) {
        validateAmount(amount);
        this.amount = amount;
    }

    public static Money from(Long amount) {
        return new Money(amount);
    }

    private void validateAmount(Long reward) {
        checkArgument(nonNull(reward), "금액은 필수값입니다.");
        checkArgument(reward >= 0, "금액은 양수입니다.");
    }
}

package wanted.backend.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ApplyRequest(
    @NotNull @Positive
    Long jobOpeningId,
    @NotNull @Positive
    Long userId
) {

}

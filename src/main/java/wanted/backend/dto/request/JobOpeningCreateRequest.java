package wanted.backend.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Max.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

public record JobOpeningCreateRequest(
    @NotNull @Positive
    Long companyId,
    @NotBlank @Length(max = 255)
    String title,
    @NotBlank @Length(max = 255)
    String position,
    @Positive
    Long reward,
    String description,
    String techStack
) {

}

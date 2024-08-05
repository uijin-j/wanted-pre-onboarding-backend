package wanted.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

public record JobOpeningUpdateRequest(
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

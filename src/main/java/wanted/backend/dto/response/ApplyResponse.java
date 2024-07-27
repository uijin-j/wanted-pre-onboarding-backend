package wanted.backend.dto.response;

import wanted.backend.domain.Application;

public record ApplyResponse(
    Long id,
    Long jobOpeningId,
    Long userId
) {

    public static ApplyResponse from(Application application) {
        return new ApplyResponse(
            application.getId(),
            application.getJobOpening().getId(),
            application.getApplicant().getId()
        );
    }

}

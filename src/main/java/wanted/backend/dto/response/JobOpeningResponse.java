package wanted.backend.dto.response;

import wanted.backend.domain.JobOpening;

public record JobOpeningResponse(
    Long id,
    Long companyId,
    String title,
    String position,
    Long reward,
    String description,
    String techStack
) {

    public static JobOpeningResponse from(JobOpening jobOpening) {
        return new JobOpeningResponse(
            jobOpening.getId(),
            jobOpening.getCompany().getId(),
            jobOpening.getTitle(),
            jobOpening.getPosition(),
            jobOpening.getReward(),
            jobOpening.getDescription(),
            jobOpening.getTechStack()
        );

    }

}

package wanted.backend.dto.response;

import wanted.backend.domain.JobOpening;

public record JobOpeningSummary(
    Long id,
    String title,
    String companyName,
    String country,
    String city,
    String position,
    Long reward,
    String techStack
) {

    public static JobOpeningSummary from(JobOpening jobOpening) {
        return new JobOpeningSummary(
            jobOpening.getId(),
            jobOpening.getTitle(),
            jobOpening.getCompany().getName(),
            jobOpening.getCompany().getCountry(),
            jobOpening.getCompany().getCity(),
            jobOpening.getPosition(),
            jobOpening.getReward(),
            jobOpening.getTechStack()
        );
    }

}

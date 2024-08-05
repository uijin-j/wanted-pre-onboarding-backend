package wanted.backend.dto.response;

import java.util.List;
import wanted.backend.domain.JobOpening;

public record JobOpeningDetail(
    Long id,
    String title,
    String companyName,
    String country,
    String city,
    String position,
    Long reward,
    String techStack,
    String description,
    List<Long> otherJobOpenings
) {

    public static JobOpeningDetail of(JobOpening jobOpening, List<Long> otherJobOpenings) {
        return new JobOpeningDetail(
            jobOpening.getId(),
            jobOpening.getTitle(),
            jobOpening.getCompany().getName(),
            jobOpening.getCompany().getCountry(),
            jobOpening.getCompany().getCity(),
            jobOpening.getPosition(),
            jobOpening.getReward(),
            jobOpening.getTechStack(),
            jobOpening.getDescription(),
            otherJobOpenings
        );
    }

}

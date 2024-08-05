package wanted.backend.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.backend.domain.Application;
import wanted.backend.domain.JobOpening;
import wanted.backend.domain.User;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    boolean existsByApplicantAndJobOpening(User applicant, JobOpening jobOpening);
}

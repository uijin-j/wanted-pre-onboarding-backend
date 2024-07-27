package wanted.backend.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.backend.domain.JobOpening;

public interface JobOpeningRepository extends JpaRepository<JobOpening, Long> {

}

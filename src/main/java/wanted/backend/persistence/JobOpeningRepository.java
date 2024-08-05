package wanted.backend.persistence;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wanted.backend.domain.Company;
import wanted.backend.domain.JobOpening;

public interface JobOpeningRepository extends JpaRepository<JobOpening, Long>,
    JobOpeningSearchRepository {

    @Query("SELECT j FROM JobOpening j JOIN FETCH j.company WHERE j.id = :id")
    Optional<JobOpening> findByIdWithCompany(Long id);

    @Query("SELECT j.id FROM JobOpening j WHERE j.company = :company")
    List<Long> findIdsByCompany(Company company);
}

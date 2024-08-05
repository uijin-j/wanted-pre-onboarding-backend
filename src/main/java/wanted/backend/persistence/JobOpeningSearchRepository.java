package wanted.backend.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import wanted.backend.domain.JobOpening;

public interface JobOpeningSearchRepository {

    Page<JobOpening> search(String keyword, Pageable pageable);
}

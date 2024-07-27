package wanted.backend.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.backend.domain.Company;

public interface CompanyRepository  extends JpaRepository<Company, Long> {

}

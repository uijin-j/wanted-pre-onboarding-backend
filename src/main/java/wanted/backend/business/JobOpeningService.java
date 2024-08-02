package wanted.backend.business;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.backend.domain.Company;
import wanted.backend.domain.JobOpening;
import wanted.backend.dto.request.JobOpeningCreateRequest;
import wanted.backend.dto.request.JobOpeningUpdateRequest;
import wanted.backend.dto.response.JobOpeningDetail;
import wanted.backend.dto.response.JobOpeningResponse;
import wanted.backend.dto.response.JobOpeningSummary;
import wanted.backend.persistence.CompanyRepository;
import wanted.backend.persistence.JobOpeningRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JobOpeningService {

    private final CompanyRepository companyRepository;
    private final JobOpeningRepository jobOpeningRepository;

    @Transactional
    public JobOpeningResponse post(JobOpeningCreateRequest request) {
        Company company = getCompany(request.companyId());
        JobOpening jobOpening = JobOpening.builder()
            .company(company)
            .title(request.title())
            .position(request.position())
            .reward(request.reward())
            .description(request.description())
            .techStack(request.techStack())
            .build();

        return JobOpeningResponse.from(jobOpeningRepository.save(jobOpening));
    }

    @Transactional
    public JobOpeningResponse update(Long id, JobOpeningUpdateRequest request) {
        JobOpening jobOpening = getJobOpening(id);

        jobOpening.update(request);

        return JobOpeningResponse.from(jobOpening);
    }

    @Transactional
    public void delete(Long id) {
        jobOpeningRepository.findById(id)
            .ifPresent(jobOpeningRepository::delete);
    }

    public Page<JobOpeningSummary> getJobOpenings(Pageable pageable) {
        return jobOpeningRepository.findAll(pageable)
            .map(JobOpeningSummary::from);
    }

    public JobOpeningDetail getInfo(Long id) {
        JobOpening jobOpening = getJobOpening(id);
        List<Long> otherJobOpeningIds = getOthersForCompany(jobOpening);

        return JobOpeningDetail.of(jobOpening, otherJobOpeningIds);
    }

    private Company getCompany(Long companyId) {
        return companyRepository.findById(companyId)
            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회사입니다."));
    }

    private JobOpening getJobOpening(Long id) {
        return jobOpeningRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 채용공고입니다."));
    }

    private List<Long> getOthersForCompany(JobOpening jobOpening) {
        List<Long> jobOpenings = getAllByCompany(jobOpening.getCompany());
        jobOpenings.remove(jobOpening.getId());

        return jobOpenings;
    }

    private List<Long> getAllByCompany(Company company) {
        return jobOpeningRepository.findIdsByCompany(company);
    }
}

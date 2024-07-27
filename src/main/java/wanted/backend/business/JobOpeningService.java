package wanted.backend.business;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.backend.domain.Company;
import wanted.backend.domain.JobOpening;
import wanted.backend.dto.request.JobOpeningCreateRequest;
import wanted.backend.dto.request.JobOpeningUpdateRequest;
import wanted.backend.dto.response.JobOpeningResponse;
import wanted.backend.persistence.CompanyRepository;
import wanted.backend.persistence.JobOpeningRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JobOpeningService {

    private final CompanyRepository comanyRepository;
    private final JobOpeningRepository jobOpeningRepository;

    @Transactional
    public JobOpeningResponse post(JobOpeningCreateRequest request) {
        Company company = comanyRepository.findById(request.companyId())
            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회사입니다."));

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
        JobOpening jobOpening = jobOpeningRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 채용공고입니다."));

        jobOpening.updateJobOpening(request);

        return JobOpeningResponse.from(jobOpening);
    }

    @Transactional
    public void delete(Long id) {
        jobOpeningRepository.findById(id).ifPresent(jobOpeningRepository::delete);
    }
}

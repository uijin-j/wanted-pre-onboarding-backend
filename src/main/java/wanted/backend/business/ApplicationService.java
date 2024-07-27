package wanted.backend.business;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.backend.domain.Application;
import wanted.backend.domain.JobOpening;
import wanted.backend.domain.User;
import wanted.backend.dto.request.ApplyRequest;
import wanted.backend.dto.response.ApplyResponse;
import wanted.backend.persistence.ApplicationRepository;
import wanted.backend.persistence.JobOpeningRepository;
import wanted.backend.persistence.UserRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobOpeningRepository jobOpeningRepository;
    private final UserRepository userRepository;

    @Transactional
    public ApplyResponse apply(ApplyRequest request) {
        JobOpening jobOpening = jobOpeningRepository.findById(request.jobOpeningId())
            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 채용공고입니다."));
        User user = userRepository.findById(request.userId())
            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자입니다."));

        checkDuplicateApply(user, jobOpening);

        Application application = Application.builder()
            .jobOpening(jobOpening)
            .applicant(user)
            .build();

        return ApplyResponse.from(applicationRepository.save(application));
    }

    public void checkDuplicateApply(User user, JobOpening jobOpening) {
        if (applicationRepository.existsByApplicantAndJobOpening(user, jobOpening)) {
            throw new IllegalArgumentException("이미 지원한 채용공고입니다.");
        }
    }
}

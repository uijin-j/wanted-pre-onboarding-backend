package wanted.backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import wanted.backend.domain.Company;
import wanted.backend.domain.User;
import wanted.backend.persistence.CompanyRepository;
import wanted.backend.persistence.UserRepository;

@Component
@RequiredArgsConstructor
public class DummyDataLoader implements ApplicationRunner {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Company company = Company
            .builder()
            .name("원티드랩")
            .country("한국")
            .city("서울")
            .build();

        User user = User
            .builder()
            .name("정의진")
            .build();

        companyRepository.save(company);
        userRepository.save(user);
    }
}

package wanted.backend.persistence;

import static wanted.backend.domain.QJobOpening.jobOpening;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import wanted.backend.domain.JobOpening;

@Repository
@RequiredArgsConstructor
public class JobOpeningSearchRepositoryImpl implements JobOpeningSearchRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<JobOpening> search(String keyword, Pageable pageable) {
        List<JobOpening> content = jpaQueryFactory.selectFrom(jobOpening)
            .where(
                containsKeyword(keyword)
            ).join(jobOpening.company)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPQLQuery<JobOpening> count = jpaQueryFactory
            .selectFrom(jobOpening)
            .where(containsKeyword(keyword))
            .join(jobOpening.company);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchCount);
    }

    private BooleanExpression containsKeyword(String keyword) {
        return jobOpening.title.contains(keyword)
            .or(jobOpening.description.contains(keyword))
            .or(jobOpening.company.name.contains(keyword))
            .or(jobOpening.position.contains(keyword))
            .or(jobOpening.techStack.contains(keyword));
    }
}

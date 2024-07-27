package wanted.backend.domain.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class BaseTimeEntity {

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "modified_at", nullable = false, columnDefinition = "TIMESTAMP")
    @LastModifiedDate
    private LocalDateTime modifiedAt;
}

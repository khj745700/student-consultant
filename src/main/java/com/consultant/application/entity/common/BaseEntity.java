package com.consultant.application.entity.common;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@NoArgsConstructor
@SuperBuilder
@Getter
@MappedSuperclass // 부모 클래스는 테이블과 매핑하지 않고 부모 클래스를 상속 받는 자식 클래스들에게 매핑 정보만 제공합니다.
@EntityListeners(AuditingEntityListener.class) // JPA Entity에 이벤트가 발생할 때 콜백을 처리하고 코드를 실행하는 방법입니다.
public abstract class BaseEntity {
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_at", updatable = false)
    private LocalDateTime modifiedAt;
}

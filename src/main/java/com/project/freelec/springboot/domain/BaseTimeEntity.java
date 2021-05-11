package com.project.freelec.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // Jpa entity 클래스들이 이 엔티티 클래스를 상속할 경우 필드도 칼럼으로 인식하도록 함
@EntityListeners(AuditingEntityListener.class) // basetimeentity 클래스에 auditing(감사) 기능 포함
public abstract class BaseTimeEntity {

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

}

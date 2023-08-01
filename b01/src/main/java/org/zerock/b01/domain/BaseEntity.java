package org.zerock.b01.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
// Board가 상속하려면 이 어노테이션 두개가 있어야하고, @EntityListeners 쓰려면 b01어플리케이션에  @EnableJpaAuditing 들어가있어야 함 // 규칙임

@Getter
abstract class BaseEntity {   // 인터페이스 비슷한거 , 테이블에서 공통적으로 가질 속성 있는거

    @CreatedDate    // 생성 시간
    @Column(name="regdate", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate   // 최종 변경 시간
    @Column(name="moddate")
    private LocalDateTime modDate;


}

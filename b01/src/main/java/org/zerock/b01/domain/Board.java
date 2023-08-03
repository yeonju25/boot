package org.zerock.b01.domain;

import lombok.*;

import javax.persistence.*;

@Entity     // 이 자바클래스를 DB랑 매칭시킬 것, 테이블 만들거란 뜻
@Builder    // @Setter 역할 / 근데 DTO에 넣을 때 문법이 다름
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Board extends BaseEntity{
    // DTO에 쓴거랑 똑같아야 함, SQL문으로 DB로 보내기~
    @Id     // 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // IDENTITY는 마리아디비랑 mysql의 시퀀스같은 역할 // auto는 디비에 맞춰서 자동으로 해주는거
    private Long bno;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(length = 2000, nullable = false)
    private String content;

    @Column(length = 50, nullable = false, updatable = false)
    private String writer;

    public void change(String title, String content){
        this.title = title;
        this.content = content;
    }


}

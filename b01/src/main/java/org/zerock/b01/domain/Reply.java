package org.zerock.b01.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Reply", indexes = {@Index(name="idx_reply_board_bno", columnList = "board_bno")})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "board")
public class Reply extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    // fetch = FetchType.EAGER = 두 테이블 동시에 읽어옴
    // fetch = FetchType.LAZY = board 테이블의 내용이 필요할 때만 읽어옴
    @ManyToOne(fetch = FetchType.LAZY)    // 다대일 : 현재 테이블인 Reply가 다, 참조하고 있는 Board가 1이라는 뜻
    private Board board;    // 일종의 외래키

    private String replyText;
    private String replyer;
}

package org.zerock.b01.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {

    private Long rno;

    @NonNull
    private Long bno;   // board table 기본키

    @NotEmpty
    private String replyText;

    @NotEmpty
    private String replyer;
    private LocalDateTime regDate, modDate;

}

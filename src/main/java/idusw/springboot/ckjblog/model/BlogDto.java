package idusw.springboot.ckjblog.model;

import lombok.*;
import java.time.LocalDateTime;

@Data   // @Getter / @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogDto {
    private Long Idx; // no, id, idx, bidx ..
    private String title;
    private String content;

    private Long views; // blog 뷰수
    private String block; // id 차단

    private Long writerIdx; // Blogger <- Member
    private String writerEmail;
    private String writerName;

    private LocalDateTime regDate; // 블로그 생성일
    private LocalDateTime modDate; // 블로그 수정일
}
package idusw.springboot.ckjblog.model;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class PageRequestDto {
    private int currPage; // 요청하는 페이지
    private int perPage; // 페이지당 게시물 수
    private int perPagination;  // 화면당 페이지 번호 수

    //private SpringDataWebProperties.Sort sort; // 정렬

    // Search
    private String type; // 검색 조건, setType(), getType()
    private String keyword; // 검색어, setKeyword(), getKeyword()

    public Pageable getPageable(Sort sort) {
        return PageRequest.of(currPage - 1, perPage, sort);
    }

}

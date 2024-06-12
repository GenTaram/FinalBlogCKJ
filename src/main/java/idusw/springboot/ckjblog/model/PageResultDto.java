package idusw.springboot.ckjblog.model;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDto<DTO, EN> {
    private List<DTO> dtoList;      // DTO : MemberDto, BlogDto에 대한 Generics
    private int totalPage;          // 총 페이지 번호
    private int curPage;            // 현재 페이지 번호
    private int perPage;            // 한 페이지 게시물(레코드 갯수) : pageable 객체에서 이미 적용
    private int perPagination;      // 한 페이지에 나타나는 페이지 번호 갯수
    private long totalRows;         // 총 행의 수 (총 갯수)
    private int startRow, endRow;   // 시작 레코드 번호, 끝 레코드 번호
    private int startPage, endPage; // 시작 페이지 번호, 끝 페이지 번호 : 게시물
    private boolean prev, next;     // 버튼 표시
    private List<Integer> pageList; // 페이지 번호 목록

    public PageResultDto(Page<EN> result, Function<EN, DTO> fn, int perPagination) {
        totalRows = result.getTotalElements();
        dtoList = result.stream().map(fn).collect(Collectors.toList()); // get records
        totalPage = result.getTotalPages();
        this.perPagination = perPagination;
        makePageList(result.getPageable());
    }
    private void makePageList(Pageable pageable){
        //perPage = 10, pageDouble = 10.0, currentPage = 2
        //2 / 10.0 = 0.2 -> Math.ceil(0.2) -> 올림 : 1 * 10 = 10(일시적인 마지막 페이지)
        //curPage = 12 12/10.0 = 1.2 -> Math.ceil(1.2) -> 올림 : 2 * 10 = 20
        //start 11, end 20
        this.curPage = pageable.getPageNumber() + 1; // 현재 페이지를 가져옴, 0부터 시작함
        this.startRow = 1 + (curPage - 1) * perPage;
        this.endRow = startRow + perPage - 1;
        this.perPage = pageable.getPageSize();
        int tempEnd = (int)(Math.ceil(curPage / ((double) perPagination))) * perPagination;

        startPage = tempEnd - (perPagination - 1);
        endPage = (totalPage > tempEnd)? tempEnd: totalPage;
        prev = startPage > 1; // 1보다 크면 true, 작으면 false
        next = totalPage > tempEnd;
        pageList = IntStream.rangeClosed(startPage, endPage).boxed().collect(Collectors.toList()); // get pageNumber list
    }
}

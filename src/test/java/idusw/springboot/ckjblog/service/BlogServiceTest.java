package idusw.springboot.ckjblog.service;

import idusw.springboot.ckjblog.entity.BlogEntity;
import idusw.springboot.ckjblog.entity.MemberEntity;
import idusw.springboot.ckjblog.model.BlogDto;
import idusw.springboot.ckjblog.model.MemberDto;
import idusw.springboot.ckjblog.repository.BlogRepository;
import idusw.springboot.ckjblog.serivce.BlogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class BlogServiceTest {
    // field DI : Test단계 사용, constructor DI : development, deployment code
    @Autowired
    BlogService blogService;

    @Test
    public void registerBlog() {
        BlogDto dto = BlogDto.builder()
                .title("우아앙")
                .content("으아앙")
                .writerIdx(1L) //idx가 3번인 멤버(blogger)가 미리 생성되어 있어야 함.
                .block("non")
                .build();
        blogService.create((dto));
    }
    @Test
    public void getBlogs() { //DB로부터 등록된 블로그를 가져옴
        List<BlogDto> blogDtoList = blogService.readList();
        // getBlogs()는 service에게 요청하는 코드와 반환/출력하는 코드
        // service -> readList()라는 repository에게 요청하는 코드가 존재
        for(BlogDto dto : blogDtoList)
            System.out.println(dto.toString());
    }
    @Test
    public void readByIdBlog() {
        BlogDto dto = BlogDto.builder()
                .Idx((long) 4)
                .build();
        BlogDto blogDto = blogService.read(dto); //repository에게 요청
        System.out.println(blogDto.toString());
    }

    @Test
    public void deleteBlog() {
        BlogDto dto = BlogDto.builder()
                .Idx((long) 4)
                .build();
        blogService.delete(dto); //repository 호출
    }

    BlogEntity dtoToEntity(BlogDto dto) {
        MemberEntity member = MemberEntity.builder()
                .idx(dto.getWriterIdx())
                .build();
        BlogEntity entity = BlogEntity.builder()
                .idx(dto.getIdx())
                .title(dto.getTitle())
                .content(dto.getContent())
                .block(dto.getBlock())
                .views(dto.getViews())
                .blogger(member)
                .build();
        return entity;
    }
    // MemberEntity -> : Controller에서는 Member를 다룸
    BlogDto entityToDto(BlogEntity entity, MemberEntity member) {
        BlogDto dto = BlogDto.builder()
                .Idx(entity.getIdx())
                .title(entity.getTitle())
                .views(entity.getViews())
                .content(entity.getContent())
                .writerIdx(member.getIdx())
                .writerName(member.getName())
                .writerEmail(member.getEmail())
                .regDate((entity.getRegDate()))
                .modDate(entity.getModDate())
                .build();
        return dto;
    }
}

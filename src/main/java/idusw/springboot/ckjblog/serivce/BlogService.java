package idusw.springboot.ckjblog.serivce;

import idusw.springboot.ckjblog.entity.BlogEntity;
import idusw.springboot.ckjblog.entity.MemberEntity;
import idusw.springboot.ckjblog.model.BlogDto;

import java.util.List;

public interface BlogService {
    int create(BlogDto dto);
    BlogDto read(BlogDto dto);
    List<BlogDto> readList();
    int update(BlogDto dto);
    int delete(BlogDto dto);

    default BlogEntity dtoToEntity(BlogDto dto) {
        MemberEntity member = MemberEntity.builder()
                .idx(dto.getWriterIdx())
                .build();
        BlogEntity entity = BlogEntity.builder()
                .idx(dto.getIdx())
                .title(dto.getTitle())
                .content(dto.getContent())
                .views(dto.getViews())
                .blogger(member)
                .build();
        return entity;
    }
    // MemberEntity -> : Controller에서는 Member를 다룸
    default BlogDto entityToDto(BlogEntity entity, MemberEntity member) {
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
